package wootrevived.woot.util.render;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WootShapes {
    public static final VoxelShape nonAttachedShape = Block.box(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D);
    public static final VoxelShape disabledShape = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 12.0D, 12.0D);
}
