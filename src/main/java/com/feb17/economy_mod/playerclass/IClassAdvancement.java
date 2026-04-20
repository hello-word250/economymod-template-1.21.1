package com.feb17.economy_mod.playerclass;

import net.minecraft.world.entity.player.Player;

// 阶级进阶接口，后续可实现NPC交互/货币进阶逻辑
public interface IClassAdvancement {
    /**
     * 检查玩家是否满足进阶条件（预留接口，当前返回true仅用于测试）
     * @param player 目标玩家
     * @param targetClass 目标阶级
     * @return 是否满足进阶条件
     */
    default boolean canAdvance(Player player, PlayerClass targetClass) {
        // 后续实现：检测货币/完成NPC任务/其他条件
        return true;
    }

    /**
     * 执行进阶逻辑（预留接口，当前仅标记进阶状态）
     * @param player 目标玩家
     * @param targetClass 目标阶级
     */
    default void advance(Player player, PlayerClass targetClass) {
        // 后续实现：扣除货币/发送通知/解锁权限等
    }
}
