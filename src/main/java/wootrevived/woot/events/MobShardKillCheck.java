package wootrevived.woot.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import wootrevived.api.WootFactoryMob;
import wootrevived.woot.Woot;
import wootrevived.woot.items.mob_shard.MobShardItem;
import wootrevived.woot.items.mob_shard.MobShardProjectile;
import wootrevived.woot.registries.WootFactoryMobsRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Woot.MOD_ID)
public class MobShardKillCheck {
    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event) {
        if(!(event.getSource().getEntity() instanceof Player killer))
            return;

        if(killer instanceof FakePlayer)
            return;

        if(event.getEntity() == null)
            return;

        if(event.getSource().getDirectEntity() != null && event.getEntity().getUUID().equals(event.getSource().getDirectEntity().getUUID()))
            return;

        LivingEntity victim = event.getEntity();

        if(ignoreDeathEvent(event.getEntity()))
            return;

        if(victim instanceof Player)
            return;

        if(!(victim instanceof Mob))
            return;

        if(!WootFactoryMobsRegistry.hasFactoryMob(victim.getType()))
            return;

        WootFactoryMob<?> mob = WootFactoryMobsRegistry.getFactoryMob(victim.getType());
        if(mob.isBlacklisted())
            return;

        victim.addTag(MobShardProjectile.MOB_SHARD_HAS_BEEN_KILLED);
        if(victim.getTags().contains(MobShardProjectile.MOB_SHARD_KILLED_BY_PROJECTILE))
            return;

        ItemStack inHandItemStack = killer.getMainHandItem();

        if(inHandItemStack.getItem() instanceof MobShardItem mobShardItem && !MobShardItem.isProgrammed(inHandItemStack)) {
            mobShardItem.hurtEnemy(inHandItemStack, victim, killer);
        }

        MobShardItem.handleKill(killer, victim.serializeNBT());
    }

    private static final List<String> uuidList = new ArrayList<>();
    private static boolean ignoreDeathEvent(Entity entity) {
        String uuid = entity.getStringUUID();
        if (uuidList.contains(uuid))
            return true;

        uuidList.add(uuid);
        int MAX_UUID_CACHE_SIZE = 10;
        if (uuidList.size() > MAX_UUID_CACHE_SIZE)
            uuidList.remove(0);

        return false;
    }
}
