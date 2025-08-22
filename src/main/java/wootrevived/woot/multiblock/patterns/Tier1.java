package wootrevived.woot.multiblock.patterns;

import net.minecraft.core.Direction;
import wootrevived.woot.registries.BlocksRegistry;

public class Tier1 extends Pattern {
    private static final PatternBlock[] pattern =  new PatternBlock[]{
            /* Main pillar */
            new PatternBlock(+0, -9, +1, BlocksRegistry.COPPER_CELL_BLOCK.get(), BlocksRegistry.IRON_CELL_BLOCK.get(),  BlocksRegistry.GOLD_CELL_BLOCK.get(), BlocksRegistry.DIAMOND_CELL_BLOCK.get(), BlocksRegistry.NETHERITE_CELL_BLOCK.get()),
            new PatternBlock(+0, -8, +1, BlocksRegistry.EXPORT_BLOCK.get()),
            new PatternBlock(+0, -7, +1, BlocksRegistry.IMPORT_BLOCK.get()),
            new PatternBlock(+0, -6, +1, BlocksRegistry.FACTORY_CONNECT_BLOCK.get()),
            new PatternBlock(+0, -5, +1, BlocksRegistry.COPPER_PLINTH_BLOCK.get()),
            new PatternBlock(+0, -4, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, -3, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, -2, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, -1, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, +0, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, +1, +1, BlocksRegistry.COPPER_PLINTH_BLOCK.get()),
            /* Pillar 1 */
            new PatternBlock(+0, -5, +0, BlocksRegistry.COPPER_PLINTH_BLOCK.get()),
            new PatternBlock(+0, -4, +0, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, -3, +0, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, -2, +0, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            /* Pillar 2 */
            new PatternBlock(-1, -5, +1, BlocksRegistry.COPPER_PLINTH_BLOCK.get()),
            new PatternBlock(-1, -4, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(-1, -3, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(-1, -2, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            /* Pillar 3 */
            new PatternBlock(+0, -5, +2, BlocksRegistry.COPPER_PLINTH_BLOCK.get()),
            new PatternBlock(+0, -4, +2, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, -3, +2, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+0, -2, +2, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            /* Pillar 4 */
            new PatternBlock(+1, -5, +1, BlocksRegistry.COPPER_PLINTH_BLOCK.get()),
            new PatternBlock(+1, -4, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+1, -3, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            new PatternBlock(+1, -2, +1, BlocksRegistry.COPPER_PYLON_BLOCK.get()),
            /* Fake Spawner (Primary) */
            new PatternBlock(+0, -3, -1, BlocksRegistry.FACTORY_CTR_BASE_PRI_BLOCK.get()),
            new PatternBlock(+0, -2, -1, BlocksRegistry.FAKE_SPAWNER_BLOCK.get()),
            new PatternBlock(+0, -1, +0, BlocksRegistry.FACTORY_UPGRADE_BLOCK.get()),
            new PatternBlock(+0, +0, +0, BlocksRegistry.HEART_BLOCK.get()),
    };

    private static final Direction direction =  Direction.NORTH;

    public Tier1(){
        super(pattern, direction);
    }
}
