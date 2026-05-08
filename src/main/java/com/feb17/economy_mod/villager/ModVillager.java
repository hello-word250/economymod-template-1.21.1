package com.feb17.economy_mod.villager;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.block.ModBlocks;
import com.feb17.economy_mod.sound.ModSounds;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModVillager {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, EconomyMod.MODID);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, EconomyMod.MODID);

    public static final Holder<PoiType> RUNE_POI = POI_TYPES.register("rune_poi",
            ()-> new PoiType(ImmutableSet.copyOf(ModBlocks.TRADING.get().getStateDefinition().getPossibleStates()),2,4));

    public static final Holder<VillagerProfession> MONSTER_HUNTER = VILLAGER_PROFESSION.register("monster_hunter",
            ()-> new VillagerProfession("monster_hunter", holder -> holder.value() == RUNE_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == RUNE_POI.value(), ImmutableSet.of(), ImmutableSet.of(),
                    ModSounds.LUMINATE_SOUNDS.getHitSound()));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSION.register(eventBus);
    }
}
