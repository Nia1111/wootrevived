package wootrevived.woot.multiblock.patterns;

import net.minecraft.core.Direction;
import wootrevived.woot.registries.BlocksRegistry;

public class Tier3 extends Pattern {
    private static final PatternBlock[] pattern =  new PatternBlock[]{
            /* Pillar 1 */
            new PatternBlock(-2, -5, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -2, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -1, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 2 */
            new PatternBlock(-1, -5, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(-1, -4, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-1, -3, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-1, -2, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-1, -1, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 3 */
            new PatternBlock(+1, -5, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(+1, -4, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+1, -3, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+1, -2, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+1, -1, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 4 */
            new PatternBlock(+2, -5, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -2, -1, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -1, -1, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 5 */
            new PatternBlock(+2, -5, +0, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, +0, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, +0, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -2, +0, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -1, +0, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 6 */
            new PatternBlock(+2, -5, +2, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, +2, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, +2, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -2, +2, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -1, +2, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 7 */
            new PatternBlock(+2, -5, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -2, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+2, -1, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 8 */
            new PatternBlock(+1, -5, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(+1, -4, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+1, -3, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+1, -2, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(+1, -1, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 9 */
            new PatternBlock(-1, -5, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(-1, -4, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-1, -3, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-1, -2, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-1, -1, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 10 */
            new PatternBlock(-2, -5, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -2, +3, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -1, +3, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 11 */
            new PatternBlock(-2, -5, +2, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, +2, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, +2, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -2, +2, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -1, +2, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            /* Pillar 12 */
            new PatternBlock(-2, -5, +0, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, +0, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, +0, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -2, +0, BlocksRegistry.GOLD_PYLON_BLOCK.get()),
            new PatternBlock(-2, -1, +0, BlocksRegistry.GOLD_PLINTH_BLOCK.get()),
    };

    private static final Direction direction =  Direction.NORTH;

    public Tier3(){
        super(pattern, direction);
    }
}
