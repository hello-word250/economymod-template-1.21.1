package com.feb17.economy_mod.dominion.playerclass;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

// 阶级测试指令：查看/提升/设置
public class ClassCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // 主指令 /class
        dispatcher.register(Commands.literal("class")
                .requires(source -> source.hasPermission(2)) // OP权限（测试用）
                // 子指令：查看当前阶级 /class get
                .then(Commands.literal("get")
                        .executes(ClassCommands::getPlayerClass))
                // 子指令：进阶阶级 /class advance
                .then(Commands.literal("advance")
                        .executes(ClassCommands::advancePlayerClass))
                // 子指令：设置阶级 /class set <1-4>
                .then(Commands.literal("set")
                        .then(Commands.argument("tier", IntegerArgumentType.integer(1, 4))
                                .executes(ClassCommands::setPlayerClass))));
    }

    // 查看阶级指令逻辑
    private static int getPlayerClass(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (source.getEntity() instanceof ServerPlayer player) {
            PlayerClass playerClass = ClassManager.getPlayerClass(player);
            source.sendSuccess(() -> Component.literal("汝为：" + playerClass.getDisplayName()), false);
        } else {
            source.sendFailure(Component.literal("仅玩家可执行此指令！"));
        }
        return 1;
    }

    // 进阶阶级指令逻辑
    private static int advancePlayerClass(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        if (source.getEntity() instanceof ServerPlayer player) {
            PlayerClass oldClass = ClassManager.getPlayerClass(player);
            ClassManager.advancePlayerClass(player);
            PlayerClass newClass = ClassManager.getPlayerClass(player);
            if (oldClass == newClass) {
                source.sendSuccess(() -> Component.literal("已达到最高阶级！"), false);
            } else {
                source.sendSuccess(() -> Component.literal("烙印加深！你已蜕变为：" + newClass.getDisplayName()), false);
            }
        } else {
            source.sendFailure(Component.literal("仅玩家可执行此指令！"));
        }
        return 1;
    }

    // 设置阶级指令逻辑
    private static int setPlayerClass(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        int tier = IntegerArgumentType.getInteger(context, "tier") - 1; // 转换为枚举索引
        if (source.getEntity() instanceof ServerPlayer player) {
            PlayerClass targetClass = PlayerClass.fromTier(tier);
            ClassManager.setPlayerClass(player, targetClass);
            source.sendSuccess(() -> Component.literal("已设置阶级为：" + targetClass.getDisplayName()), false);
        } else {
            source.sendFailure(Component.literal("仅玩家可执行此指令！"));
        }
        return 1;
    }
}
