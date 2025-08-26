package wootrevived.woot.util.render.guide;

import guideme.document.interaction.GuideTooltip;
import guideme.siteexport.ResourceExporter;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import wootrevived.woot.util.render.WootContainerScreen;

import java.util.List;

import static wootrevived.woot.util.render.WootStyles.MACHINE_STYLE;
import static wootrevived.woot.util.render.WootStyles.UNIT_STYLE;

public class FluidTooltip implements GuideTooltip {
    private final FluidStack stack;

    public FluidTooltip(FluidStack stack) {
        this.stack = stack;
    }

    @Override
    public ItemStack getIcon() {
        return stack.getFluid().getBucket().getDefaultInstance();
    }

    @Override
    public List<ClientTooltipComponent> getLines() {
        List<Component> lines = List.of(
                Component.empty()
                        .append(Component.translatable("info.woot_revived.fluid").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(!stack.isEmpty() ? stack.getDisplayName() : Component.translatable("info.woot_revived.empty")),
                Component.empty()
                        .append(Component.translatable("info.woot_revived.amount").append(Component.literal(": ")).setStyle(MACHINE_STYLE))
                        .append(WootContainerScreen.formatInteger(stack.getAmount()))
                        .append(Component.literal("mB").setStyle(UNIT_STYLE))
        );
        return lines.stream()
                .map(Component::getVisualOrderText)
                .map(ClientTooltipComponent::create)
                .toList();
    }

    @Override
    public void exportResources(ResourceExporter exporter) {
        exporter.referenceFluid(stack.getFluid());
    }
}
