package com.feb17.economy_mod.dominion.playerclass;

// 四个阶级的枚举，后续可自定义命名（比如Peasant/Merchant/Noble/Lord）
public enum PlayerClass {
    CLASS_1("灰烬民", 0),
    CLASS_2("契约者", 1),
    CLASS_3("奥术领主", 2),
    CLASS_4("无冠候主", 3);

    private final String displayName;
    private final int tier;

    PlayerClass(String displayName, int tier) {
        this.displayName = displayName;
        this.tier = tier;
    }

    // 获取阶级显示名
    public String getDisplayName() {
        return displayName;
    }

    // 获取阶级等级（用于进阶判断）
    public int getTier() {
        return tier;
    }

    // 获取下一个阶级（进阶用，最后一个阶级返回自身）
    public PlayerClass getNextClass() {
        return switch (this) {
            case CLASS_1 -> CLASS_2;
            case CLASS_2 -> CLASS_3;
            case CLASS_3 -> CLASS_4;
            case CLASS_4 -> CLASS_4;
        };
    }

    // 通过等级获取阶级（用于指令/进阶）
    public static PlayerClass fromTier(int tier) {
        return switch (tier) {
            case 1 -> CLASS_2;
            case 2 -> CLASS_3;
            case 3 -> CLASS_4;
            default -> CLASS_1;
        };
    }
}
