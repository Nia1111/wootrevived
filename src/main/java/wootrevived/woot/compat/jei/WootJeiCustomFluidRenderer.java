package wootrevived.woot.compat.jei;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import wootrevived.woot.util.render.WootContainerScreen;

import java.util.List;

import static wootrevived.woot.util.render.WootStyles.MACHINE_STYLE;
import static wootrevived.woot.util.render.WootStyles.UNIT_STYLE;

public class WootJeiCustomFluidRenderer implements IIngredientRenderer<FluidStack> {
    private final int capacity;

    public WootJeiCustomFluidRenderer(int capacity){
        this.capacity = capacity;
    }

    @Override
    public void render(@NotNull GuiGraphics gui, @NotNull FluidStack ingredient) {
        WootContainerScreen.renderFluid(gui, -3, -3, ingredient, capacity);
    }

    @Override
    @SuppressWarnings("removal")
    public @NotNull List<Component> getTooltip(@NotNull FluidStack ingredient, @NotNull TooltipFlag tooltipFlag) {
        return List.of();
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, FluidStack ingredient, @NotNull TooltipFlag tooltipFlag) {
        tooltip.add(Component.empty()
                .append(Component.translatable("info.woot_revived.fluid").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                .append(ingredient != null && !ingredient.isEmpty() ? ingredient.getHoverName() : Component.translatable("info.woot_revived.empty"))
        );

        tooltip.add(Component.empty()
                        .append(Component.translatable("info.woot_revived.amount").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(WootContainerScreen.formatInteger(ingredient.getAmount()))
                        .append(Component.literal("mB").setStyle(UNIT_STYLE))
        );
    }

    @Override
    public int getWidth() {
        return 12;
    }

    @Override
    public int getHeight() {
        return 50;
    }
}
