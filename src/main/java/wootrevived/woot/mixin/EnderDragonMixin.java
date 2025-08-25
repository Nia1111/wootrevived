package wootrevived.woot.mixin;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EnderDragon.class)
public interface EnderDragonMixin {
    @Accessor(value = "unlimitedLastHurtByPlayer", remap = false)
    void setUnlimitedLastHurtByPlayer(Player unlimitedLastHurtByPlayer);
}
