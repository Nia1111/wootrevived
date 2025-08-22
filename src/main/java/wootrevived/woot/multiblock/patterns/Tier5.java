package wootrevived.woot.multiblock.patterns;

import net.minecraft.core.Direction;
import wootrevived.woot.registries.BlocksRegistry;

public class Tier5 extends Pattern {
    private static final PatternBlock[] pattern =  new PatternBlock[]{
            /* Pillar 1 */
            new PatternBlock(+4, -5, -1, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(+4, -4, -1, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(+4, -3, -1, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 2 */
            new PatternBlock(+3, -5, -2, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(+3, -4, -2, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(+3, -3, -2, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 3 */
            new PatternBlock(+2, -5, -3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, -3, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, -3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 4 */
            new PatternBlock(-2, -5, -3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, -3, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, -3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 5 */
            new PatternBlock(-3, -5, -2, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(-3, -4, -2, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(-3, -3, -2, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 6 */
            new PatternBlock(-4, -5, -1, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(-4, -4, -1, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(-4, -3, -1, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 7 */
            new PatternBlock(-4, -5, +3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(-4, -4, +3, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(-4, -3, +3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 8 */
            new PatternBlock(-3, -5, +4, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(-3, -4, +4, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(-3, -3, +4, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 9 */
            new PatternBlock(-2, -5, +5, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(-2, -4, +5, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(-2, -3, +5, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 10 */
            new PatternBlock(+2, -5, +5, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(+2, -4, +5, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(+2, -3, +5, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 11 */
            new PatternBlock(+3, -5, +4, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(+3, -4, +4, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(+3, -3, +4, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            /* Pillar 12 */
            new PatternBlock(+4, -5, +3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
            new PatternBlock(+4, -4, +3, BlocksRegistry.NETHERITE_PYLON_BLOCK.get()),
            new PatternBlock(+4, -3, +3, BlocksRegistry.NETHERITE_PLINTH_BLOCK.get()),
    };

    private static final Direction direction =  Direction.NORTH;

    public Tier5(){
        super(pattern, direction);
    }
}
