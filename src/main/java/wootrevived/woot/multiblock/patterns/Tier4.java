package wootrevived.woot.multiblock.patterns;

import net.minecraft.core.Direction;
import wootrevived.woot.registries.BlocksRegistry;

public class Tier4 extends Pattern {
    private static final PatternBlock[] pattern =  new PatternBlock[]{
            /* Pillar 1 */
            new PatternBlock(+2, -5, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+2, -2, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 2 */
            new PatternBlock(+1, -5, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+1, -4, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+1, -3, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+1, -2, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 3 */
            new PatternBlock(-1, -5, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-1, -4, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-1, -3, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-1, -2, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 4 */
            new PatternBlock(-2, -5, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, -2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-2, -2, -2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 5 */
            new PatternBlock(-3, -5, -1, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-3, -4, -1, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -3, -1, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -2, -1, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 6 */
            new PatternBlock(-3, -5, +0, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-3, -4, +0, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -3, +0, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -2, +0, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 7 */
            new PatternBlock(-3, -5, +2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-3, -4, +2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -3, +2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -2, +2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 8 */
            new PatternBlock(-3, -5, +3, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-3, -4, +3, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -3, +3, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-3, -2, +3, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 9 */
            new PatternBlock(-2, -5, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-2, -2, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 10 */
            new PatternBlock(-1, -5, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(-1, -4, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-1, -3, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(-1, -2, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 11 */
            new PatternBlock(+1, -5, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+1, -4, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+1, -3, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+1, -2, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 12 */
            new PatternBlock(+2, -5, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, +4, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+2, -2, +4, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 13 */
            new PatternBlock(+3, -5, +3, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+3, -4, +3, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -3, +3, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -2, +3, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 14 */
            new PatternBlock(+3, -5, +2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+3, -4, +2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -3, +2, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -2, +2, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 15 */
            new PatternBlock(+3, -5, +0, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+3, -4, +0, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -3, +0, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -2, +0, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            /* Pillar 16 */
            new PatternBlock(+3, -5, -1, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
            new PatternBlock(+3, -4, -1, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -3, -1, BlocksRegistry.DIAMOND_PYLON_BLOCK.get()),
            new PatternBlock(+3, -2, -1, BlocksRegistry.DIAMOND_PLINTH_BLOCK.get()),
    };

    private static final Direction direction =  Direction.NORTH;

    public Tier4(){
        super(pattern, direction);
    }
}
