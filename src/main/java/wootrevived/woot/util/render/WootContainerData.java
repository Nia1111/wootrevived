package wootrevived.woot.util.render;

import net.minecraft.core.Direction;
import net.minecraft.world.inventory.ContainerData;
import net.minecraftforge.fluids.FluidStack;
import wootrevived.woot.util.common.MachineSide;
import wootrevived.woot.util.common.MachineSideProperty;
import wootrevived.woot.util.common.RedstoneMode;

import java.util.Map;

public interface WootContainerData extends ContainerData {
    float getFloat(int index);
    FluidStack getFluid(int index);
    Direction getMachineFacing();

    RedstoneMode getRedstoneMode();
    void setRedstoneMode(RedstoneMode mode);

    Map<MachineSide, MachineSideProperty> getMachineSideProperties(int index);
    void setMachineSideProperties(); // Send the client Map to the server
}
