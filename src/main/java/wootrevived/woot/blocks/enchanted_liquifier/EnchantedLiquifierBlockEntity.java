package wootrevived.woot.blocks.enchanted_liquifier;

import com.google.common.collect.Maps;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.client.render.enchanted_liquifier.EnchantedLiquifierContainerMenu;
import wootrevived.woot.data.EnchantedLiquifierData;
import wootrevived.woot.registries.BlocksRegistry;
import wootrevived.woot.registries.ComponentsRegistry;
import wootrevived.woot.registries.FluidsRegistry;
import wootrevived.woot.util.Config;
import wootrevived.woot.util.common.MachineSide;
import wootrevived.woot.util.common.MachineSideProperty;
import wootrevived.woot.util.handlers.WootFluidTankHandlerWrapper;
import wootrevived.woot.util.handlers.WootItemStackHandler;
import wootrevived.woot.util.entity.WootTags;
import wootrevived.woot.util.entity.WootMachineBlockEntity;
import wootrevived.woot.util.handlers.WootItemStackHandlerWrapper;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;

public class EnchantedLiquifierBlockEntity extends WootMachineBlockEntity implements MenuProvider {
    private final List<EnumMap<MachineSide, MachineSideProperty>> directionsProperties = new ArrayList<>(2);

    public static final int OUTPUT_FLUID_PROPERTY = 0;
    public static final int INGREDIENT_PROPERTY = 1;

    public EnchantedLiquifierBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.ENCHANTED_LIQUIFIER_BLOCK_ENTITY.get(), pos, state);
        for(int i = 0; i < 2; i++){
            EnumMap<MachineSide, MachineSideProperty> properties = Maps.newEnumMap(MachineSide.class);
            for(MachineSide side : MachineSide.values()){
                properties.put(side, MachineSideProperty.ENABLED);
            }
            directionsProperties.add(properties);
        }
    }

    public static void ticker(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity){
        if(blockEntity instanceof EnchantedLiquifierBlockEntity enchantedLiquifierBlockEntity){
            enchantedLiquifierBlockEntity.tick(level, pos, state, blockEntity);
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
            EnchantedLiquifierBlockEntity.this.onContentsChanged(slot);
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getItem() == Items.ENCHANTED_BOOK && EnchantmentHelper.hasAnyEnchantments(stack);
        }
    };

    public static int INPUT_SLOT = 0;
    public IItemHandler getInventory() { return inventoryHandler; }

    public record Properties(EnchantedLiquifierBlockEntity entity, MachineSide machineSide){
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

    public static IItemHandler getItemHandlerCapability(EnchantedLiquifierBlockEntity blockEntity, Direction side){
        Properties properties = blockEntity.getProperties(side);
        if(properties.getIngredientProperty() != MachineSideProperty.DISABLED)
            return new WootItemStackHandlerWrapper(blockEntity.inventoryHandler, properties::getIngredientProperty);
        return null;
    }

    public static IFluidHandler getFluidHandlerCapability(EnchantedLiquifierBlockEntity blockEntity, Direction side){
        Properties properties = blockEntity.getProperties(side);
        MachineSideProperty property = properties.getOutputFluidProperty();
        if(property != MachineSideProperty.DISABLED && property != MachineSideProperty.PULL)
            return new WootFluidTankHandlerWrapper(blockEntity.outputTankHandler, properties::getOutputFluidProperty);
        return null;
    }

    private EnchantedLiquifierData.Component getComponent(){
        return new EnchantedLiquifierData.Component(
                energyHandler.getEnergyStored(),
                getOutputTank().getFluid(),
                getAllMachineSidesProperties()
        );
    }

    private void setComponent(EnchantedLiquifierData.Component component){
        energyHandler.setEnergy(component.energy());
        getOutputTank().setFluid(component.outputFluid());
        setAllMachineSidesProperties(component.listMachineProperties());
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput input){
        EnchantedLiquifierData.Component component = input.get(ComponentsRegistry.ENCHANTED_LIQUIFIER_DATA);
        if(component == null)
            return;

        setComponent(component);
        setChanged();
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder){
        builder.set(ComponentsRegistry.ENCHANTED_LIQUIFIER_DATA, getComponent());
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider){
        super.saveAdditional(tag, provider);

        tag.put(WootTags.INPUT_INVENTORY_TAG, inventoryHandler.serializeNBT(provider));

        EnchantedLiquifierData.CODEC.encodeStart(NbtOps.INSTANCE, getComponent()).result().ifPresent(t -> {
            if(t instanceof CompoundTag compound) tag.merge(compound);
        });
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider){
        super.loadAdditional(tag, provider);

        if(tag.contains(WootTags.INPUT_INVENTORY_TAG))
            inventoryHandler.deserializeNBT(provider, tag.getCompound(WootTags.INPUT_INVENTORY_TAG));

        EnchantedLiquifierData.CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), tag).result().ifPresent(this::setComponent);
    }

    public void dropContents(Level level, BlockPos pos) {
        List<ItemStack> drops = new ArrayList<>();
        ItemStack itemStack = inventoryHandler.getStackInSlot(INPUT_SLOT);
        if (!itemStack.isEmpty()) {
            drops.add(itemStack);
            inventoryHandler.insertItem(INPUT_SLOT, ItemStack.EMPTY, false);
        }
        super.dropContents(drops);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("gui.woot_revived.enchanted_liquifier.name");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new EnchantedLiquifierContainerMenu(containerId, level, getBlockPos(), playerInventory, player);
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

    @Override
    protected boolean hasEnergy() { return energyHandler.getEnergyStored() > 0; }

    @Override
    protected int useEnergy(){
        return energyHandler.extractEnergy(getEnergyProcessTransfer(), false);
    }

    @Override
    protected void clearRecipe() { }

    @Override
    protected int getRecipeEnergy() {
        ItemStack itemStack = inventoryHandler.getStackInSlot(INPUT_SLOT);
        if (itemStack.isEmpty())
            return 0;

        return getEnchantEnergy(itemStack);
    }

    @Override
    protected void processFinished() {
        ItemStack itemStack = inventoryHandler.getStackInSlot(INPUT_SLOT);
        if (itemStack.isEmpty())
            return;

        inventoryHandler.extractItem(INPUT_SLOT, 1, false);

        int amount = getEnchantAmount(itemStack);
        outputTankHandler.fill(new FluidStack(FluidsRegistry.SOURCE_ENCHANTED_FLUID.get(), amount), IFluidHandler.FluidAction.EXECUTE);

        setChanged();
    }

    @Override
    protected boolean canProcess(boolean checkEnergy) {
        if (checkEnergy && energyHandler.getEnergyStored() <= 0)
            return false;

        ItemStack itemStack = inventoryHandler.getStackInSlot(INPUT_SLOT);
        if (itemStack.isEmpty())
            return false;

        if (!EnchantmentHelper.hasAnyEnchantments(itemStack))
            return false;

        int amount = getEnchantAmount(itemStack);
        int filled = outputTankHandler.fill(new FluidStack(FluidsRegistry.SOURCE_ENCHANTED_FLUID.get(), amount), IFluidHandler.FluidAction.SIMULATE);

        return amount == filled;
    }

    private int getEnchantAmount(ItemStack itemStack) {
        int amount = 0;
        if (!itemStack.isEmpty() && EnchantmentHelper.hasAnyEnchantments(itemStack)) {
            ItemEnchantments enchantments = itemStack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);

            for(Holder<Enchantment> enchantmentHolder : enchantments.keySet())
                amount += Mth.clamp(enchantments.getLevel(enchantmentHolder), 1, Config.EnchantedLiquifier.MAX_ENCHANT_LVL) * Config.EnchantedLiquifier.PER_ENCHANT_FLUID;
        }
        return amount;
    }

    private int getEnchantEnergy(ItemStack itemStack) {
        int amount = 0;
        if (!itemStack.isEmpty() && EnchantmentHelper.hasAnyEnchantments(itemStack)) {
            ItemEnchantments enchantments = itemStack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY);

            for(Holder<Enchantment> enchantmentHolder : enchantments.keySet())
                amount += Mth.clamp(enchantments.getLevel(enchantmentHolder), 1, Config.EnchantedLiquifier.MAX_ENCHANT_LVL) * Config.EnchantedLiquifier.PER_ENCHANT_ENERGY;
        }
        return amount;
    }

    public int getEnergyCapacity(){
        return Config.EnchantedLiquifier.ENERGY_CAPACITY;
    }

    public int getEnergyMaxTransfer(){
        return Config.EnchantedLiquifier.ENERGY_MAX_TRANSFER;
    }

    public int getEnergyProcessTransfer(){
        return Config.EnchantedLiquifier.ENERGY_PROCESS_TRANSFER;
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
        return Config.EnchantedLiquifier.OUTPUT_TANK_CAPACITY;
    }

    public boolean hasOutputFluidCapability() {
        return true;
    }
}
