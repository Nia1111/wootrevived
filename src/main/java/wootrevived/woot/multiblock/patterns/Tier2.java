package wootrevived.woot.multiblock.patterns;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import wootrevived.woot.registries.BlocksRegistry;

public class Tier2 extends Pattern {
    private static final PatternBlock[] pattern =  new PatternBlock[]{
            /* Pillar 1 */
            new PatternBlock(-1, -5, +0, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(-1, -4, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, -3, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, -2, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, -1, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, +0, +0, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            /* Pillar 2 */
            new PatternBlock(-1, -5, +2, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(-1, -4, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, -3, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, -2, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, -1, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-1, +0, +2, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            /* Pillar 3 */
            new PatternBlock(+1, -5, +2, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(+1, -4, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, -3, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, -2, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, -1, +2, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, +0, +2, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            /* Pillar 4 */
            new PatternBlock(+1, -5, +0, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(+1, -4, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, -3, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, -2, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, -1, +0, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+1, +0, +0, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            /* Fake Spawner 1 (Primary) */
            new PatternBlock(+0, -5, -1, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(+0, -4, -1, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            /* Fake Spawner 2 (Secondary) */
            new PatternBlock(-2, -5, +1, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, +1, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, +1, BlocksRegistry.FACTORY_CTR_BASE_SEC_BLOCK.get()),
            new PatternBlock(-2, -2, +1, BlocksRegistry.FAKE_SPAWNER_BLOCK.get(), Blocks.AIR), // Optional
            new PatternBlock(-1, -1, +1, BlocksRegistry.FACTORY_UPGRADE_BLOCK.get()),
            /* Fake Spawner 3 (Secondary) */
            new PatternBlock(+0, -5, +3, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(+0, -4, +3, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+0, -3, +3, BlocksRegistry.FACTORY_CTR_BASE_SEC_BLOCK.get()),
            new PatternBlock(+0, -2, +3, BlocksRegistry.FAKE_SPAWNER_BLOCK.get(), Blocks.AIR), // Optional
            new PatternBlock(+0, -1, +2, BlocksRegistry.FACTORY_UPGRADE_BLOCK.get()),
            /* Fake Spawner 4 (Secondary) */
            new PatternBlock(+2, -5, +1, BlocksRegistry.IRON_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, +1, BlocksRegistry.IRON_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, +1, BlocksRegistry.FACTORY_CTR_BASE_SEC_BLOCK.get()),
            new PatternBlock(+2, -2, +1, BlocksRegistry.FAKE_SPAWNER_BLOCK.get(), Blocks.AIR), // Optional
            new PatternBlock(+1, -1, +1, BlocksRegistry.FACTORY_UPGRADE_BLOCK.get()),
    };

    private static final Direction direction =  Direction.NORTH;

    public Tier2(){
        super(pattern, direction);
    }
}
