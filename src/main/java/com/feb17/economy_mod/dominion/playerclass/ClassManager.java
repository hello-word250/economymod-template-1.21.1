package com.feb17.economy_mod.dominion.playerclass;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

// 管理玩家阶级数据（基于NBT持久化，适配1.21.1存档机制）
public class ClassManager implements IClassAdvancement {
    public static final String CLASS_TAG_KEY = "PlayerClass"; // NBT存储键名
    private static final PlayerClass DEFAULT_CLASS = PlayerClass.CLASS_1; // 默认初始阶级

    // 获取玩家当前阶级（供物品/指令调用）
    public static PlayerClass getPlayerClass(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag playerData = serverPlayer.getPersistentData();
            if (playerData.contains(CLASS_TAG_KEY)) {
                int tier = playerData.getInt(CLASS_TAG_KEY);
                return PlayerClass.fromTier(tier);
            }
        }
        return DEFAULT_CLASS;
    }

    // 设置玩家阶级（供指令/进阶逻辑调用）
    public static void setPlayerClass(Player player, PlayerClass playerClass) {
        if (player instanceof ServerPlayer serverPlayer) {
            CompoundTag playerData = serverPlayer.getPersistentData();
            playerData.putInt(CLASS_TAG_KEY, playerClass.getTier());
            // 触发进阶逻辑（预留）
            ClassManager manager = new ClassManager();
            manager.advance(player, playerClass);
        }
    }

    // 玩家进阶（自动升级到下一个阶级）
    public static void advancePlayerClass(Player player) {
        PlayerClass current = getPlayerClass(player);
        PlayerClass next = current.getNextClass();
        if (current != next) {
            ClassManager manager = new ClassManager();
            if (manager.canAdvance(player, next)) {
                setPlayerClass(player, next);
            }
        }
    }

    // （可选）初始化玩家阶级（在玩家登录时触发）
    public static void initPlayerClass(Player player) {
        if (!player.getPersistentData().contains(CLASS_TAG_KEY)) {
            setPlayerClass(player, DEFAULT_CLASS);
        }
    }
}
