package com.feb17.economy_mod.loyalty;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.capabilities.CapabilityRegistry;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public class PlayerLoyaltyProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static CapabilityRegistry<PlayerLoyalty> PLAYER_LOYALTY_CAPABILITY;
    @Nullable
    @Override
    public Object getCapability(Object o, Object o2) {
        return null;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {

    }

}
