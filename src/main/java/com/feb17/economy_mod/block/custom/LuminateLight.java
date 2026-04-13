package com.feb17.economy_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class LuminateLight extends Block {
    //定义boolean二极状态
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public LuminateLight(Properties properties) {
        super(properties);
        //定义初始状态为false
        this.registerDefaultState(this.defaultBlockState().setValue(CLICKED, false));
    }

    //写状态转变
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            boolean currentState = state.getValue(CLICKED);
            level.setBlockAndUpdate(pos, state.setValue(CLICKED, !currentState));
        }
        return InteractionResult.SUCCESS;
    }

    //每次添加状态都要在这进行，必须在这里调用
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLICKED);
    }
}
