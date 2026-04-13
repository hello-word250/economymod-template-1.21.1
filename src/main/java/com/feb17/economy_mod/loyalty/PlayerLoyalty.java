package com.feb17.economy_mod.loyalty;

import net.minecraft.nbt.CompoundTag;

public class PlayerLoyalty {
    private int loyalty;
    private final int MIN_LOYALTY = 0;
    private final int MAX_LOYALTY = 10;


    public int getLoyalty(){
        return loyalty;
    }
    public void addLoyalty(int add){
        this.loyalty = Math.min(loyalty + add, MAX_LOYALTY);
    }
    public void subLoyalty(int sub){
        this.loyalty = Math.max(loyalty - sub, MIN_LOYALTY);
    }
    public void resetLoyalty(){
        this.loyalty = MIN_LOYALTY;
    }
    public void copyLoyalty(PlayerLoyalty source){
        this.loyalty = source.getLoyalty();
    }
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("loyalty", this.loyalty);
    }
    public void loadNBTData(CompoundTag nbt){
        this.loyalty = nbt.getInt("loyalty");
    }
}
