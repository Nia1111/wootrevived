package wootrevived.woot.guide;

import guideme.GuidesCommon;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GuideBookItem extends Item {
    public GuideBookItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if(level.isClientSide)
            GuidesCommon.openGuide(player, WootGuide.ID);

        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
