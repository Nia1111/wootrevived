package wootrevived.woot.items.mob_shard;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import wootrevived.api.WootFactoryMob;
import wootrevived.woot.data.MobShardData;
import wootrevived.woot.registries.ComponentsRegistry;
import wootrevived.woot.registries.ItemsRegistry;
import wootrevived.woot.registries.WootFactoryMobsRegistry;

import wootrevived.woot.util.helper.ModNameHelper;
import wootrevived.woot.util.helper.SerializeEntityNBTHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static wootrevived.woot.util.render.WootStyles.*;

public class MobShardItem extends Item {
    public MobShardItem() {
        super(new Properties().stacksTo(1).component(ComponentsRegistry.MOB_SHARD_DATA, new MobShardData.Component(Optional.empty(), 0, false)));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, LivingEntity tmpAttacker) {
        if (tmpAttacker.getCommandSenderWorld().isClientSide() || !(tmpAttacker instanceof Player))
            return false;

        if(!WootFactoryMobsRegistry.hasFactoryMob(target.getType()))
            return false;

        WootFactoryMob<?> mob = WootFactoryMobsRegistry.getFactoryMob(target.getType());
        if(mob.isBlacklisted())
            return false;

        if (isProgrammed(stack))
            return false;

        return setProgrammedMob(stack, mob.saveTag(SerializeEntityNBTHelper.serialize(target)));
    }

    public static boolean isProgrammed(ItemStack itemStack) {
        MobShardData.Component component = itemStack.get(ComponentsRegistry.MOB_SHARD_DATA);
        if(component == null)
            return false;

        return component.mobTag().isPresent();
    }

    public static CompoundTag getProgrammedMob(ItemStack itemStack) {
        MobShardData.Component component = itemStack.get(ComponentsRegistry.MOB_SHARD_DATA);
        if(component == null)
            return null;

        return component.mobTag().orElse(null);
    }

    private boolean setProgrammedMob(ItemStack itemStack, CompoundTag mobTag) {
        if(!WootFactoryMobsRegistry.hasFactoryMob(mobTag))
            return false;

        itemStack.set(ComponentsRegistry.MOB_SHARD_DATA, new MobShardData.Component(
                Optional.ofNullable(mobTag),
                0,
                false
        ));

        return true;
    }

    private static boolean isMatchingMob(ItemStack itemStack, CompoundTag mobTag) {
        if(itemStack.getItem() != ItemsRegistry.MOB_SHARD_ITEM.get())
            return false;

        if(!isProgrammed(itemStack))
            return false;

        CompoundTag programmedMob = getProgrammedMob(itemStack);
        if(programmedMob == null)
            return false;

        WootFactoryMob<?> mob = WootFactoryMobsRegistry.getFactoryMob(programmedMob);

        return mob.isSame(programmedMob, mobTag);
    }

    public static void handleKill(Player player, CompoundTag mobTag) {
        ItemStack foundStack = ItemStack.EMPTY;

        ItemStack inHandItemStack = player.getMainHandItem();

        if(!inHandItemStack.isEmpty() && isMatchingMob(inHandItemStack, mobTag) && !isFullyProgrammed(inHandItemStack)){
            foundStack = inHandItemStack;
        } else {
            List<ItemStack> inventoryItems = new ArrayList<>();
            inventoryItems.addAll(player.getInventory().offhand);
            inventoryItems.addAll(player.getInventory().items);

            for(ItemStack itemStack : inventoryItems) {
                if(inHandItemStack.equals(itemStack)) continue;
                if(!itemStack.isEmpty() && isMatchingMob(itemStack, mobTag) && !isFullyProgrammed(itemStack)){
                    foundStack = itemStack;
                    break;
                }
            }
        }

        if(!foundStack.isEmpty())
            incrementKills(foundStack, 1);
    }

    public static void incrementKills(ItemStack itemStack, int amount) {
        if(itemStack.getItem() != ItemsRegistry.MOB_SHARD_ITEM.get())
            return;

        MobShardData.Component component = itemStack.get(ComponentsRegistry.MOB_SHARD_DATA);
        if(component == null)
            return;

        int killCount = component.killCount();
        if(!isFull(itemStack)){
            killCount += amount;
            itemStack.set(ComponentsRegistry.MOB_SHARD_DATA, new MobShardData.Component(component.mobTag(), killCount, false));
        }
    }

    private static boolean isFull(ItemStack itemStack) {
        MobShardData.Component component = itemStack.get(ComponentsRegistry.MOB_SHARD_DATA);
        if(component == null)
            return false;

        if(component.mobTag().isEmpty())
            return false;

        return component.killCount() >= 5;
    }

    public static void setJEIShard(ItemStack itemStack) {
        itemStack.set(ComponentsRegistry.MOB_SHARD_DATA, new MobShardData.Component(
                Optional.empty(),
                0,
                true
        ));
    }

    public static boolean isJEIShard(ItemStack itemStack) {
        MobShardData.Component component = itemStack.get(ComponentsRegistry.MOB_SHARD_DATA);
        if(component == null)
            return false;

        return component.jeiShard();
    }

    public static boolean isFullyProgrammed(ItemStack itemStack) {
        return isProgrammed(itemStack) && isFull(itemStack);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack itemStack) {
        if (isFullyProgrammed(itemStack)) return true;
        return isJEIShard(itemStack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext ctx, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag){
        super.appendHoverText(stack, ctx, tooltip, flag);

        if(isJEIShard(stack)) {
            tooltip.add(Component.translatable("info.woot_revived.mobshard.programmed").setStyle(SHARD_PROGRAM_STYLE));
            return;
        }

        CompoundTag mobTag = getProgrammedMob(stack);
        if(mobTag == null){
            tooltip.add(Component.translatable("info.woot_revived.mobshard.unprogrammed").setStyle(SHARD_PROGRAM_STYLE));
            tooltip.add(Component.translatable("info.woot_revived.mobshard.unprogrammed.desc").setStyle(DESCRIPTION_STYLE));
            return;
        }

        WootFactoryMob<?> mob = WootFactoryMobsRegistry.getFactoryMob(mobTag);
        if(mob != null) {
            tooltip.add(mob.getDisplayName(mobTag).setStyle(CAPTURED_STYLE));
            String modId = BuiltInRegistries.ENTITY_TYPE.getKey(mob.getEntityType()).getNamespace();
            tooltip.add(ModNameHelper.getModName(modId).setStyle(MOD_NAME_STYLE));
        }

        int killCount = 0;
        MobShardData.Component component = stack.get(ComponentsRegistry.MOB_SHARD_DATA);
        if(component != null) killCount = component.killCount();

        if(isFull(stack)){
            tooltip.add(Component.translatable("info.woot_revived.mobshard.programmed").setStyle(SHARD_PROGRAM_STYLE));
        } else {
            tooltip.add(Component.translatable("info.woot_revived.mobshard.remaining", killCount, 5).setStyle(SHARD_PROGRAM_STYLE));
            if(mob != null) {
                tooltip.add(Component.translatable("info.woot_revived.mobshard.remaining.desc", mob.getTooltipKillName(mobTag).setStyle(DESCRIPTION_STYLE)).setStyle(DESCRIPTION_STYLE));
            } else {
                tooltip.add(Component.translatable("info.woot_revived.mobshard.remaining.desc_no_entity").setStyle(DESCRIPTION_STYLE));
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand){
        ItemStack itemStack = player.getItemInHand(usedHand);

        if(isProgrammed(itemStack))
            return InteractionResultHolder.fail(itemStack);

        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity, int timeLeft){
        if(!(entity instanceof Player player)) return;
        int used = this.getUseDuration(stack, entity) - timeLeft;
        float pull = Math.min(used / 20f, 1f);

        MobShardProjectile proj = new MobShardProjectile(player, level);
        proj.setItem(stack);
        proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, pull * 2f, 1f - pull * 0.5f);
        if(!level.isClientSide) level.addFreshEntity(proj);
        stack.shrink(1);
    }
}
