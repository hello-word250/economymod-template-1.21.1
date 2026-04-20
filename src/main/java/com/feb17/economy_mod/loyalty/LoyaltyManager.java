package com.feb17.economy_mod.loyalty;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import com.feb17.economy_mod.playerclass.ClassManager;
import com.feb17.economy_mod.playerclass.PlayerClass;

// 全局调用入口：所有地方都用这个类操作忠诚度
public class LoyaltyManager {
    private static final String KEY = "LoyaltyData";

    // 获取玩家数据
    public static LoyaltyData get(Player player) {
        LoyaltyData data = new LoyaltyData();
        CompoundTag persistent = player.getPersistentData();

        if (persistent.contains(KEY)) {
            data.load(persistent.getCompound(KEY));
        }
        return data;
    }

    // 保存数据
    public static void save(Player player, LoyaltyData data) {
        CompoundTag tag = new CompoundTag();
        data.save(tag);
        player.getPersistentData().put(KEY, tag);
    }

    // ===================== 对外接口 =====================
    // 获取当前忠诚值
    public static int getLoyalty(Player player) {
        return get(player).get();
    }

    // 增加忠诚值（支持负数 = 减少）
    public static void addLoyalty(Player player, int amount) {
        LoyaltyData data = get(player);
        data.add(amount);
        save(player, data);
        updateClassByLoyalty(player); // 自动升级阶级
    }

    // 直接设置忠诚值
    public static void setLoyalty(Player player, int value) {
        LoyaltyData data = get(player);
        data.set(value);
        save(player, data);
        updateClassByLoyalty(player); // 自动升级阶级
    }

    //自动结算阶段
    public static void updateClassByLoyalty(Player player) {
        int loyalty = getLoyalty(player);
        PlayerClass currentClass = ClassManager.getPlayerClass(player);
        PlayerClass targetClass = PlayerClass.CLASS_1;

        // 按忠诚值自动判断阶级
        if (loyalty >= 90) {
            targetClass = PlayerClass.CLASS_4;
        } else if (loyalty >= 30) {
            targetClass = PlayerClass.CLASS_3;
        } else if (loyalty >= 10) {
            targetClass = PlayerClass.CLASS_2;
        }

        // 只有阶级变化时才设置（避免重复写入）
        if (currentClass != targetClass) {
            ClassManager.setPlayerClass(player, targetClass);
            // 可选：给玩家提示
            player.sendSystemMessage(Component.literal("§e你已升阶为：" + targetClass.getDisplayName()));
        }
    }
}
