package wootrevived.woot.compat.kubejs.mobs;

import dev.latvian.mods.kubejs.event.KubeStartupEvent;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.fluids.FluidStack;
import wootrevived.api.interfaces.WootDropsProperties;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WootDropsPropertiesJS implements KubeStartupEvent {
    private final WootDropsProperties properties;

    public WootDropsProperties getRawProperties(){
        return properties;
    }

    public WootDropsPropertiesJS(WootDropsProperties properties) {
        this.properties = properties;
    }

    public int getEnchantmentLevel(String enchantmentId){
        return getEnchantmentLevel(enchantmentId, "MAIN_HAND");
    }

    public int getEnchantmentLevel(String enchantmentId, String handId) {
        InteractionHand hand = InteractionHand.MAIN_HAND;
        try {
            hand = InteractionHand.valueOf(handId);
        } catch(IllegalArgumentException ignored){}

        ItemStack stack = switch(hand){
            case MAIN_HAND -> properties.getMainHandItem();
            case OFF_HAND -> properties.getOffHandItem();
        };

        if(!stack.getItem().isEnchantable(stack))
            return 0;

        HolderLookup.Provider lookupProvider = properties.getLookupProvider();
        HolderLookup.RegistryLookup<Enchantment> lookup = lookupProvider.lookupOrThrow(Registries.ENCHANTMENT);

        ResourceLocation id = ResourceLocation.tryParse(enchantmentId);
        ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, id);

        AtomicInteger enchantmentLevel = new AtomicInteger();
        lookup.get(key).ifPresent(enchantment -> {
            enchantmentLevel.set(stack.getEnchantmentLevel(enchantment));
        });

        return enchantmentLevel.get();
    }

    public ItemStack[] getItemDrops(){
        List<ItemStack> stacks = properties.getItemDrops();
        return stacks.toArray(ItemStack[]::new);
    }

    public void setItemDrops(ItemStack[] items){
        List<ItemStack> stacks = properties.getItemDrops();
        stacks.clear();
        stacks.addAll(Arrays.stream(items).toList());
    }

    public FluidStack[] getFluidDrops(){
        List<FluidStack> stacks = properties.getFluidDrops();
        return stacks.toArray(FluidStack[]::new);
    }

    public void setFluidDrops(FluidStack[] items){
        List<FluidStack> stacks = properties.getFluidDrops();
        stacks.clear();
        stacks.addAll(Arrays.stream(items).toList());
    }

    public String getFactoryTier(){
        return properties.getFactoryTier().getSerializedName();
    }

    public RandomSource getRandom() {
        return properties.getRandom();
    }

    public int getExperience(){
        return properties.getExperience();
    }

    public void setExperience(int experience){
        properties.setExperience(experience);
    }

    public float getLuck(){
        return properties.getLuck();
    }

    public boolean doSimulateChargedCreeper(){
        return properties.doSimulateChargedCreeper();
    }

    public boolean isEnderDragonAlreadyKilled(){
        return properties.isEnderDragonAlreadyKilled();
    }

    public boolean isInFire(){
        return properties.isInFire();
    }
}
