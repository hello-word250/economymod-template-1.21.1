package com.feb17.economy_mod.dominion.claim;

import com.feb17.economy_mod.dominion.playerclass.ClassManager;
import com.feb17.economy_mod.dominion.playerclass.PlayerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ClaimManager {
    private static final String CLAIM_CENTER = "ClaimCenter";
    private static final String TRUSTED_KEY = "TrustedPlayers";

    // 阶级对应半径
    public static int getClaimRadius(Player player) {
        PlayerClass cls = ClassManager.getPlayerClass(player);
        return switch (cls) {
            case CLASS_1 -> 4;
            case CLASS_2 -> 8;
            case CLASS_3 -> 16;
            case CLASS_4 -> 32;
        };
    }

    // 设置领地中心
    public static void setClaimCenter(Player player, BlockPos pos) {
        CompoundTag tag = player.getPersistentData();
        tag.putIntArray(CLAIM_CENTER, new int[]{pos.getX(), pos.getY(), pos.getZ()});
    }

    // 获取领地中心
    public static BlockPos getClaimCenter(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(CLAIM_CENTER)) return null;
        int[] arr = tag.getIntArray(CLAIM_CENTER);
        return new BlockPos(arr[0], arr[1], arr[2]);
    }

    // 判断坐标是否在玩家领地内
    public static boolean isInClaim(Player owner, BlockPos pos) {
        BlockPos center = getClaimCenter(owner);
        if (center == null) return false;
        int r = getClaimRadius(owner);
        return center.distManhattan(pos) <= r;
    }

    // 信任列表
    public static Set<UUID> getTrustedUuids(Player player) {
        Set<UUID> set = new HashSet<>();
        CompoundTag data = player.getPersistentData();
        if (!data.contains(TRUSTED_KEY)) return set;
        ListTag list = data.getList(TRUSTED_KEY, 8);
        for (var tag : list) {
            try {
                set.add(UUID.fromString(tag.getAsString()));
            } catch (Exception ignored) {}
        }
        return set;
    }

    public static void addTrusted(Player owner, UUID uuid) {
        var set = getTrustedUuids(owner);
        set.add(uuid);
        saveTrusted(owner, set);
    }

    public static void removeTrusted(Player owner, UUID uuid) {
        var set = getTrustedUuids(owner);
        set.remove(uuid);
        saveTrusted(owner, set);
    }

    private static void saveTrusted(Player owner, Set<UUID> set) {
        ListTag list = new ListTag();
        for (UUID uuid : set) list.add(StringTag.valueOf(uuid.toString()));
        owner.getPersistentData().put(TRUSTED_KEY, list);
    }

    // 核心权限判断
    public static boolean canInteract(Player visitor, BlockPos pos, Level level) {
        // 遍历所有在线玩家，看这个位置是谁的领地
        for (Player owner : level.players()) {
            // 该位置属于 owner 的领地
            if (isInClaim(owner, pos)) {
                // 是主人 → 允许
                if (owner == visitor)
                    return true;

                // 在白名单里 → 允许
                if (getTrustedUuids(owner).contains(visitor.getUUID()))
                    return true;

                // 否则 → 禁止！
                return false;
            }
        }

        // 不属于任何领地 → 允许
        return true;
    }
    public static void clearClaimCenter(Player player) {
        player.getPersistentData().remove(CLAIM_CENTER);
    }
}
