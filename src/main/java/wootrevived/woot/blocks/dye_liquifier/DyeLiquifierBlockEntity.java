package wootrevived.woot.blocks.dye_liquifier;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.client.render.dye_liquifier.DyeLiquifierContainerMenu;
import wootrevived.woot.config.DyeLiquifierConfig;
import wootrevived.woot.data.DyeLiquifierData;
import wootrevived.woot.registries.BlocksRegistry;
import wootrevived.woot.registries.ComponentsRegistry;
import wootrevived.woot.registries.FluidsRegistry;
import wootrevived.woot.recipes.dye_liquifier.DyeLiquifierRecipe;
import wootrevived.woot.registries.RecipesRegistry;
import wootrevived.woot.util.common.MachineSide;
import wootrevived.woot.util.common.MachineSideProperty;
import wootrevived.woot.util.handlers.*;
import wootrevived.woot.util.entity.WootTags;
import wootrevived.woot.util.entity.WootMachineBlockEntity;

import org.jetbrains.annotations.Nullable;
import wootrevived.woot.util.recipes.WootRecipeInput;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;

public class DyeLiquifierBlockEntity extends WootMachineBlockEntity implements MenuProvider {
    private int red = 0;
    private int yellow = 0;
    private int blue = 0;
    private int white = 0;

    private final List<EnumMap<MachineSide, MachineSideProperty>> directionsProperties = new ArrayList<>(2);

    public static final int OUTPUT_FLUID_PROPERTY = 0;
    public static final int INGREDIENT_PROPERTY = 1;

    public DyeLiquifierBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.DYE_LIQUIFIER_BLOCK_ENTITY.get(), pos, state);
        for(int i = 0; i < 2; i++){
            EnumMap<MachineSide, MachineSideProperty> properties = Maps.newEnumMap(MachineSide.class);
            for(MachineSide side : MachineSide.values()){
                properties.put(side, MachineSideProperty.ENABLED);
            }
            directionsProperties.add(properties);
        }
    }

    public static void ticker(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity){
        if(blockEntity instanceof DyeLiquifierBlockEntity dyeLiquifierBlockEntity){
            dyeLiquifierBlockEntity.tick(level, pos, state, blockEntity);
        }
    }

    @Override
    public void tick(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        if(level.isClientSide)
            return;

        tickFluid(outputTankHandler, pos, side -> getProperties(side).getOutputFluidProperty());
    }

    public final WootItemStackHandler inventoryHandler = new WootItemStackHandler(false) {
        @Override
        protected void onContentsChanged(int slot) {
            DyeLiquifierBlockEntity.this.onContentsChanged(slot);
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return DyeLiquifierRecipe.Validator.isIngredientValid(stack);
        }
    };

    public static int INPUT_SLOT = 0;
    public IItemHandler getInventory() { return inventoryHandler; }

    public record Properties(DyeLiquifierBlockEntity entity, MachineSide machineSide){
        public MachineSideProperty getIngredientProperty(){
            return entity.directionsProperties.get(INGREDIENT_PROPERTY).get(machineSide);
        }

        public MachineSideProperty getOutputFluidProperty(){
            return entity.directionsProperties.get(OUTPUT_FLUID_PROPERTY).get(machineSide);
        }
    }

    private Properties getProperties(Direction side){
        Direction facing = getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        return new Properties(this, MachineSide.getMachineSide(facing, side));
    }

    public static IItemHandler getItemHandlerCapability(DyeLiquifierBlockEntity blockEntity, Direction side){
        Properties properties = blockEntity.getProperties(side);

        return new WootItemHandlerWrapper()
                .addHandler(blockEntity.inventoryHandler, properties::getIngredientProperty);
    }

    public static IFluidHandler getFluidHandlerCapability(DyeLiquifierBlockEntity blockEntity, Direction side){
        Properties properties = blockEntity.getProperties(side);

        return new WootFluidHandlerWrapper()
                .addHandler(blockEntity.outputTankHandler, properties::getOutputFluidProperty);
    }

    private DyeLiquifierData.Component getComponent(){
        return new DyeLiquifierData.Component(
                energyHandler.getEnergyStored(),
                getRed(),
                getYellow(),
                getBlue(),
                getWhite(),
                getOutputTank().getFluid(),
                getAllMachineSidesProperties()
        );
    }

    private void setComponent(DyeLiquifierData.Component component){
        energyHandler.setEnergy(component.energy());
        red = component.red();
        yellow = component.yellow();
        blue = component.blue();
        white = component.white();
        getOutputTank().setFluid(component.outputFluid());
        setAllMachineSidesProperties(component.listMachineProperties());
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput input){
        DyeLiquifierData.Component component = input.get(ComponentsRegistry.DYE_LIQUIFIER_DATA);
        if(component == null)
            return;

        setComponent(component);
        setChanged();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder){
        builder.set(ComponentsRegistry.DYE_LIQUIFIER_DATA, getComponent());
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider){
        super.saveAdditional(tag, provider);

        tag.put(WootTags.INPUT_INVENTORY_TAG, inventoryHandler.serializeNBT(provider));

        DyeLiquifierData.CODEC.encodeStart(NbtOps.INSTANCE, getComponent()).result().ifPresent(t -> {
            if(t instanceof CompoundTag compound) tag.merge(compound);
        });
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider){
        super.loadAdditional(tag, provider);

        if(tag.contains(WootTags.INPUT_INVENTORY_TAG))
            inventoryHandler.deserializeNBT(provider, tag.getCompound(WootTags.INPUT_INVENTORY_TAG));

        DyeLiquifierData.CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), tag).result().ifPresent(this::setComponent);
    }

    public void dropContents(Level level, BlockPos pos) {
        List<ItemStack> drops = new ArrayList<>();
        ItemStack itemStack = inventoryHandler.getStackInSlot(INPUT_SLOT).copy();
        if (!itemStack.isEmpty()) {
            drops.add(itemStack);
            inventoryHandler.insertItem(INPUT_SLOT, ItemStack.EMPTY, false);
        }
        super.dropContents(drops);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("gui.woot_revived.dye_liquifier.name");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new DyeLiquifierContainerMenu(containerId, level, getBlockPos(), playerInventory, player);
    }

    @Override
    public EnumMap<MachineSide, MachineSideProperty> getMachineSideProperties(int index) {
        return directionsProperties.get(index);
    }

    @Override
    public List<EnumMap<MachineSide, MachineSideProperty>> getAllMachineSidesProperties() {
        return directionsProperties;
    }

    @Override
    public void setAllMachineSidesProperties(List<EnumMap<MachineSide, MachineSideProperty>> directionsProperties){
        for(int i = 0; i < directionsProperties.size(); i++){
            this.directionsProperties.set(i, directionsProperties.get(i));
        }
    }

    private DyeLiquifierRecipe recipe = null;

    @Override
    protected boolean hasEnergy() { return energyHandler.getEnergyStored() > 0; }

    @Override
    protected int useEnergy(){
        return energyHandler.extractEnergy(getEnergyProcessTransfer(), false);
    }

    @Override
    protected void clearRecipe() {
        recipe = null;
    }

    @Override
    protected int getRecipeEnergy() {
        return recipe != null ? recipe.getEnergy() : 0;
    }

    private void generatePureFluid() {
        while (canCreateOutput() && canStoreOutput()) {
            outputTankHandler.fill(new FluidStack(FluidsRegistry.SOURCE_PURE_DYE_FLUID.get(), DyeLiquifierConfig.PURE_DYE_PRODUCE_AMOUNT.get()), IFluidHandler.FluidAction.EXECUTE);
            red -= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get();
            yellow -= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get();
            blue -= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get();
            white -= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get();
            setChanged();
        }
    }

    @Override
    protected void processFinished() {
        if (recipe == null)
            getRecipe();
        if (recipe == null) {
            processOff();
            return;
        }

        DyeLiquifierRecipe recipe = this.recipe;

        red += recipe.getRed();
        yellow += recipe.getYellow();
        blue += recipe.getBlue();
        white += recipe.getWhite();

        red = Mth.clamp(red, 0, DyeLiquifierConfig.RED_TANK_CAPACITY.get());
        yellow = Mth.clamp(yellow, 0, DyeLiquifierConfig.YELLOW_TANK_CAPACITY.get());
        blue = Mth.clamp(blue, 0, DyeLiquifierConfig.BLUE_TANK_CAPACITY.get());
        white = Mth.clamp(white, 0, DyeLiquifierConfig.WHITE_TANK_CAPACITY.get());

        inventoryHandler.extractItem(INPUT_SLOT, 1, false);
        generatePureFluid();
        setChanged();
    }

    @Override
    protected boolean canProcess(boolean checkEnergy) {
        if (checkEnergy && energyHandler.getEnergyStored() <= 0)
            return false;

        getRecipe();
        return recipe != null && canStoreInternal(recipe);
    }
    //endregion

    private void getRecipe() {
        RecipeHolder<DyeLiquifierRecipe> recipeHolder = level.getRecipeManager().getRecipeFor(RecipesRegistry.DYE_LIQUIFIER_RECIPE_TYPE.get(),
                new WootRecipeInput(Either.left(inventoryHandler.getStackInSlot(INPUT_SLOT))),
                level).orElse(null);
        recipe = recipeHolder == null ? null : recipeHolder.value();
    }

    public int getRed() { return this.red; }
    public int getYellow() { return this.yellow; }
    public int getBlue() { return this.blue; }
    public int getWhite() { return this.white; }

    private boolean canStoreInternal(DyeLiquifierRecipe recipe) {
        boolean redHasSpace = recipe.getRed() + red <= DyeLiquifierConfig.RED_TANK_CAPACITY.get();
        boolean yellowHasSpace = recipe.getYellow() + yellow <= DyeLiquifierConfig.YELLOW_TANK_CAPACITY.get();
        boolean blueHasSpace = recipe.getBlue() + blue <= DyeLiquifierConfig.BLUE_TANK_CAPACITY.get();
        boolean whiteHasSpace = recipe.getWhite() + white <= DyeLiquifierConfig.WHITE_TANK_CAPACITY.get();

        return recipe.getRed() > 0 && redHasSpace ||
                recipe.getYellow() > 0 && yellowHasSpace ||
                recipe.getBlue() > 0 && blueHasSpace ||
                recipe.getWhite() > 0 && whiteHasSpace;
    }

    private boolean canCreateOutput() {
        return red >= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get() &&
                yellow >= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get() &&
                blue >= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get() &&
                white >= DyeLiquifierConfig.COLOR_PRODUCE_AMOUNT.get();
    }
    private boolean canStoreOutput() { return outputTankHandler.fill(new FluidStack(FluidsRegistry.SOURCE_PURE_DYE_FLUID.get(), DyeLiquifierConfig.PURE_DYE_PRODUCE_AMOUNT.get()), IFluidHandler.FluidAction.SIMULATE ) == DyeLiquifierConfig.PURE_DYE_PRODUCE_AMOUNT.get(); }

    public int getEnergyCapacity(){
        return DyeLiquifierConfig.ENERGY_CAPACITY.get();
    }

    public int getEnergyMaxTransfer(){
        return DyeLiquifierConfig.ENERGY_MAX_TRANSFER.get();
    }

    public int getEnergyProcessTransfer(){
        return DyeLiquifierConfig.ENERGY_PROCESS_TRANSFER.get();
    }

    public boolean hasEnergyCapability() {
        return true;
    }

    public int getInputTankCapacity() {
        return 0;
    }

    public boolean hasInputFluidCapability() {
        return false;
    }

    public Predicate<FluidStack> getInputFluidValidator() {
        return null;
    }

    public int getOutputTankCapacity() {
        return DyeLiquifierConfig.OUTPUT_TANK_CAPACITY.get();
    }

    public boolean hasOutputFluidCapability() {
        return true;
    }
}
