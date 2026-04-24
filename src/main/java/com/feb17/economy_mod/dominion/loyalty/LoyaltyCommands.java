package com.feb17.economy_mod.dominion.loyalty;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class LoyaltyCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("loyalty")
                .requires(cmd -> cmd.hasPermission(2))
                // 查询 /loyalty get
                .then(Commands.literal("get")
                        .executes(ctx -> getLoyalty(ctx.getSource())))
                // 增加 /loyalty add <数值>
                .then(Commands.literal("add")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> addLoyalty(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value")))))
                // 设置 /loyalty set <数值>
                .then(Commands.literal("set")
                        .then(Commands.argument("value", IntegerArgumentType.integer(-100, 100))
                                .executes(ctx -> setLoyalty(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value")))))
        );
    }

    private static int getLoyalty(CommandSourceStack source) {
        if (source.getEntity() instanceof ServerPlayer player) {
            int val = LoyaltyManager.getLoyalty(player);
            source.sendSystemMessage(Component.literal("当前忠诚度: " + val));
        }
        return 1;
    }

    private static int addLoyalty(CommandSourceStack source, int value) {
        if (source.getEntity() instanceof ServerPlayer player) {
            LoyaltyManager.addLoyalty(player, value);
            int now = LoyaltyManager.getLoyalty(player);
            source.sendSystemMessage(Component.literal("忠诚度变化: " + value + " → " + now));
        }
        return 1;
    }

    private static int setLoyalty(CommandSourceStack source, int value) {
        if (source.getEntity() instanceof ServerPlayer player) {
            LoyaltyManager.setLoyalty(player, value);
            source.sendSystemMessage(Component.literal("已设置忠诚度: " + value));
        }
        return 1;
    }
}
