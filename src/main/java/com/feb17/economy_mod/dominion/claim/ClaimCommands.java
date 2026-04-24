package com.feb17.economy_mod.dominion.claim;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ClaimCommands {
    public static void register(CommandDispatcher<CommandSourceStack> d) {
        d.register(Commands.literal("claim")
                // 设置领地中心
                .then(Commands.literal("set").executes(ctx -> {
                    ServerPlayer player = (ServerPlayer) ctx.getSource().getEntity();
                    BlockPos pos = player.blockPosition();
                    ClaimManager.setClaimCenter(player, pos);
                    player.sendSystemMessage(Component.literal("§a领地中心已设置在你脚下！"));
                    return 1;
                }))
                // 查看信息
                .then(Commands.literal("info").executes(ctx -> {
                    ServerPlayer player = (ServerPlayer) ctx.getSource().getEntity();
                    int r = ClaimManager.getClaimRadius(player);
                    var center = ClaimManager.getClaimCenter(player);
                    if (center == null) {
                        player.sendSystemMessage(Component.literal("§c你还未设置领地"));
                    } else {
                        player.sendSystemMessage(Component.literal("§a领地半径：" + r + " 格"));
                    }
                    return 1;
                }))
                // 信任玩家
                .then(Commands.literal("trust")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(ctx -> {
                                    ServerPlayer self = (ServerPlayer) ctx.getSource().getEntity();
                                    String name = StringArgumentType.getString(ctx, "name");
                                    ServerPlayer target = self.getServer().getPlayerList().getPlayerByName(name);
                                    if (target == null) {
                                        self.sendSystemMessage(Component.literal("§c玩家不在线"));
                                        return 1;
                                    }
                                    ClaimManager.addTrusted(self, target.getUUID());
                                    self.sendSystemMessage(Component.literal("§a已信任：" + name));
                                    return 1;
                                })))
                // 取消信任
                .then(Commands.literal("untrust")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(ctx -> {
                                    ServerPlayer self = (ServerPlayer) ctx.getSource().getEntity();
                                    String name = StringArgumentType.getString(ctx, "name");
                                    ServerPlayer target = self.getServer().getPlayerList().getPlayerByName(name);
                                    if (target == null) {
                                        self.sendSystemMessage(Component.literal("§c玩家不在线"));
                                        return 1;
                                    }
                                    ClaimManager.removeTrusted(self, target.getUUID());
                                    self.sendSystemMessage(Component.literal("§a已取消信任：" + name));
                                    return 1;
                                })))
                .then(Commands.literal("clear").executes(ctx -> {
                    ServerPlayer player = (ServerPlayer) ctx.getSource().getEntity();
                    ClaimManager.clearClaimCenter(player);
                    player.sendSystemMessage(Component.literal("§a已成功取消当前领地！"));
                    return 1;
                }))
        );
    }
}
