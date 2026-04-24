package com.feb17.economy_mod.dominion.loyalty;

import net.minecraft.nbt.CompoundTag;

public class LoyaltyData {
    public static final int MIN = -100;
    public static final int MAX = 100;

    private int value = 0;

    public int get() {
        return value;
    }

    // 设置值，自动限制 ±100
    public void set(int value) {
        this.value = Math.clamp(value, MIN, MAX);
    }

    public void add(int amount) {
        set(value + amount);
    }

    public void sub(int amount) {
        set(value - amount);
    }

    // NBT 存档
    public void load(CompoundTag tag) {
        value = tag.getInt("Loyalty");
    }

    public void save(CompoundTag tag) {
        tag.putInt("Loyalty", value);
    }
}
