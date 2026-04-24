package com.feb17.economy_mod.dominion.tax;

import com.feb17.economy_mod.dominion.loyalty.LoyaltyManager;
import com.feb17.economy_mod.dominion.playerclass.ClassManager;
import com.feb17.economy_mod.dominion.playerclass.PlayerClass;
import net.minecraft.world.entity.player.Player;

public class TaxManager {

    // ====================== 存储 KEY ======================
    private static final String KEY_LAST_TAX = "lastTaxAmount";
    private static final String KEY_PUNISH_POINTS = "punishmentPoints"; // 累计惩罚分
    private static final String KEY_HAS_PAID = "hasPaidTax";

    // ====================== 基础税收 ======================
    public static int getBaseTax(Player player) {
        PlayerClass cls = ClassManager.getPlayerClass(player);
        return switch (cls) {
            case CLASS_1 -> 100;
            case CLASS_2 -> 200;
            case CLASS_3 -> 400;
            case CLASS_4 -> 800;
        };
    }

    // ====================== 惩罚系统（你要的核心） ======================

    // 违规事件等级：小1，中2，大3
    public static void addPunishment(Player player, int n) {
        //int punishValue = (n * n) / 6;
        //if (punishValue < 1) punishValue = 1; // 至少 1

        addPunishmentPoints(player, n);
    }

    // 获取阶级惩罚系数
    public static int getPunishCoefficient(Player player) {
        PlayerClass cls = ClassManager.getPlayerClass(player);
        return switch (cls) {
            case CLASS_1 -> 50;
            case CLASS_2 -> 200;
            case CLASS_3 -> 500;
            case CLASS_4 -> 1000;
        };
    }

    // 计算当前总惩罚税
    public static int getTotalPunishmentTax(Player player) {
        return getPunishmentPoints(player) * getPunishCoefficient(player);
    }

    // ====================== 核心税收公式（已包含惩罚税） ======================
    public static int calculateCurrentTax(Player player) {
        int base = getBaseTax(player);
        int loyalty = LoyaltyManager.getLoyalty(player);
        int punishTax = getTotalPunishmentTax(player);

        int tax = base - loyalty + punishTax;
        return Math.max(tax, 0);
    }

    // 生成新税收（保留惩罚分，未缴税不清空）
    public static int computeAndSaveNewTax(Player player) {
        int tax = calculateCurrentTax(player);
        setLastTaxAmount(player, tax);
        setHasPaidTax(player, false);
        return tax;
    }

    // 按时缴税 → 清空惩罚分
    public static void payTax(Player player) {
        setHasPaidTax(player, true);
        setPunishmentPoints(player, 0); // 清空惩罚
    }

    // ====================== 基础数据读写 ======================
    public static int getLastTaxAmount(Player player) {
        return player.getPersistentData().getInt(KEY_LAST_TAX);
    }

    public static void setLastTaxAmount(Player player, int amount) {
        player.getPersistentData().putInt(KEY_LAST_TAX, amount);
    }

    public static int getPunishmentPoints(Player player) {
        return player.getPersistentData().getInt(KEY_PUNISH_POINTS);
    }

    public static void setPunishmentPoints(Player player, int points) {
        player.getPersistentData().putInt(KEY_PUNISH_POINTS, points);
    }

    public static void addPunishmentPoints(Player player, int add) {
        setPunishmentPoints(player, getPunishmentPoints(player) + add);
    }

    public static boolean hasPaidTax(Player player) {
        return player.getPersistentData().getBoolean(KEY_HAS_PAID);
    }

    public static void setHasPaidTax(Player player, boolean paid) {
        player.getPersistentData().putBoolean(KEY_HAS_PAID, paid);
    }
}
