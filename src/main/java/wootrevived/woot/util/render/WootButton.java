package wootrevived.woot.util.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class WootButton extends AbstractWidget {
    public WootButton(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    protected abstract void renderWidget(@NotNull GuiGraphics gui, int mouseX, int mouseY, float partialTick);

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {}

    public abstract void onPress();

    @Override
    @SuppressWarnings("deprecation")
    public void onClick(double mouseX, double mouseY) {
        if (this.active && this.visible &&
                mouseX >= this.getX() && mouseY >= this.getY() &&
                mouseX < this.getX() + this.width && mouseY < this.getY() + this.height) {
            this.playDownSound(Minecraft.getInstance().getSoundManager());
            this.onPress();
        }
    }
}
