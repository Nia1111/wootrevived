package wootrevived.woot.multiblock.patterns;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import wootrevived.woot.blocks.fake_spawner.FakeSpawnerBlockEntity;
import wootrevived.woot.registries.BlocksRegistry;
import wootrevived.woot.util.block.FactoryBlockBaseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Pattern {
    public Map<Direction, List<PatternBlock>> patterns;

    public Pattern(PatternBlock[] pattern, Direction facing){
        patterns = Maps.newEnumMap(Direction.class);
        patterns.put(facing, Arrays.stream(pattern).toList());

        List<PatternBlock> blocks;

        for(int i = 0; i < 3; i++){
            blocks = patterns.get(facing);

            List<PatternBlock> rotatedPattern = new ArrayList<>();
            for(PatternBlock patternBlock : blocks){
                rotatedPattern.add(patternBlock.rotate(Rotation.CLOCKWISE_90));
            }

            facing = facing.getClockWise();
            patterns.put(facing, rotatedPattern);
        }
    }

    public boolean isPatternValid(Level level, BlockPos factoryPos, Direction facing){
        List<PatternBlock> pattern = patterns.get(facing);

        for(PatternBlock patternBlock : pattern){
            BlockPos blockPos = patternBlock.getLevelBlockPos(factoryPos);
            BlockState blockState = level.getBlockState(blockPos);

            Block block = blockState.getBlock();
            if(!patternBlock.checkBlocks(level, block, blockState, factoryPos, blockPos))
                return false;
        }

        return true;
    }

    public void setAttached(boolean isAttached, Level level, BlockPos factoryPos, Direction facing){
        List<PatternBlock> pattern = patterns.get(facing);

        for(PatternBlock patternBlock : pattern){
            BlockPos blockPos = patternBlock.getLevelBlockPos(factoryPos);
            BlockState blockState = level.getBlockState(blockPos);

            if(!patternBlock.blockCanAttach(level, blockState.getBlock(), blockState, factoryPos, blockPos))
                continue;

            BlockEntity blockEntity = level.getBlockEntity(blockPos);

            if(blockEntity instanceof FactoryBlockBaseEntity entity){
                if(isAttached && entity.isHeartPosEmpty()){
                    entity.setHeartPos(factoryPos);
                } else if(!isAttached && !entity.isSameHeartPos(factoryPos)){
                    continue;
                } else if(!isAttached){
                    entity.setHeartPos(null);
                }
            }

            if(blockState.hasProperty(BlockStateProperties.ATTACHED) && blockState.getValue(BlockStateProperties.ATTACHED) != isAttached) {
                level.setBlock(blockPos, blockState.setValue(BlockStateProperties.ATTACHED, isAttached), Block.UPDATE_ALL);
            }
        }
    }

    public static class PatternBlock {
        public Block[] blocks;
        public BlockPos pos;

        public boolean hasAir;

        public PatternBlock(BlockPos pos, Block... blocks){
            this.blocks = blocks;
            this.pos = pos;

            for(Block block : blocks){
                if(block == Blocks.AIR) {
                    hasAir = true;
                    break;
                }
            }
        }

        public PatternBlock(int x, int y, int z, Block... blocks){
            this(new BlockPos(x, y, z), blocks);
        }

        public BlockPos getLevelBlockPos(BlockPos factoryBlockPos){
            return factoryBlockPos.offset(this.pos);
        }

        public PatternBlock rotate(Rotation rotation){
            return new PatternBlock(pos.rotate(rotation), blocks);
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean checkBlocks(Level level, Block block, BlockState blockState, BlockPos factoryPos, BlockPos levelPos){
            if(hasAir){
                return true;
            } else {
                for (Block b : blocks) {
                    if (b == block) {
                        if (blockState.hasProperty(BlockStateProperties.ENABLED) && !blockState.getValue(BlockStateProperties.ENABLED))
                            continue;
                        if(block == BlocksRegistry.FAKE_SPAWNER_BLOCK.get()){
                            FakeSpawnerBlockEntity fakeSpawnerBlockEntity = (FakeSpawnerBlockEntity)level.getBlockEntity(levelPos);
                            if(fakeSpawnerBlockEntity != null && fakeSpawnerBlockEntity.getMobTag() == null)
                                continue;
                        }
                        BlockEntity blockEntity = level.getBlockEntity(levelPos);
                        if(blockEntity instanceof FactoryBlockBaseEntity entity && !entity.isHeartPosEmpty() && !entity.isSameHeartPos(factoryPos))
                            continue;
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean blockCanAttach(Level level, Block block, BlockState blockState, BlockPos factoryPos, BlockPos levelPos){
            for (Block b : blocks) {
                if(hasAir && b == Blocks.AIR) continue;
                if (b == block) {
                    if (blockState.hasProperty(BlockStateProperties.ENABLED) && !blockState.getValue(BlockStateProperties.ENABLED))
                        continue;
                    if(block == BlocksRegistry.FAKE_SPAWNER_BLOCK.get()){
                        FakeSpawnerBlockEntity fakeSpawnerBlockEntity = (FakeSpawnerBlockEntity)level.getBlockEntity(levelPos);
                        if(fakeSpawnerBlockEntity != null && fakeSpawnerBlockEntity.getMobTag() == null)
                            continue;
                    }
                    BlockEntity blockEntity = level.getBlockEntity(levelPos);
                    if(blockEntity instanceof FactoryBlockBaseEntity entity && !entity.isHeartPosEmpty() && !entity.isSameHeartPos(factoryPos))
                        continue;
                    return true;
                }
            }

            return false;
        }
    }
}
