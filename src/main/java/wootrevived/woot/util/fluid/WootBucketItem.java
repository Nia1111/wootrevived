package wootrevived.woot.util.fluid;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.registries.FluidsRegistry;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WootBucketItem extends BucketItem {
    private final boolean isFoil;

    public WootBucketItem(Supplier<? extends Fluid> supplier) {
        super(supplier, new Properties().craftRemainder(Items.BUCKET).stacksTo(1));
        isFoil = supplier == FluidsRegistry.SOURCE_ENCHANTED_FLUID;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return isFoil;
    }

    @Override
    public @NotNull ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidBucketWrapper(stack);
    }
}
