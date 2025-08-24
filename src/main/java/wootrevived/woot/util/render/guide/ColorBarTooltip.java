package wootrevived.woot.util.render.guide;

import guideme.document.interaction.GuideTooltip;
import guideme.siteexport.ResourceExporter;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import wootrevived.woot.util.render.WootContainerScreen;

import java.util.List;

import static wootrevived.woot.util.render.WootStyles.MACHINE_STYLE;
import static wootrevived.woot.util.render.WootStyles.UNIT_STYLE;

public class ColorBarTooltip implements GuideTooltip {
    private final int amount;
    private final MutableComponent colorName;

    public ColorBarTooltip(int amount, MutableComponent colorName) {
        this.amount = amount;
        this.colorName = colorName;
    }

    @Override
    public List<ClientTooltipComponent> getLines() {
        List<Component> lines = List.of(
                colorName.append(Component.literal(": ")).setStyle(MACHINE_STYLE),
                Component.literal(WootContainerScreen.formatInteger(amount))
                        .append(Component.literal("mB").setStyle(UNIT_STYLE))
        );
        return lines.stream()
                .map(Component::getVisualOrderText)
                .map(ClientTooltipComponent::create)
                .toList();
    }

    @Override
    public void exportResources(ResourceExporter exporter) {
    }
}
