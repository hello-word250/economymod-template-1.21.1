package com.feb17.economy_mod.sound;

import com.feb17.economy_mod.EconomyMod;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, EconomyMod.MODID);

    public static final Supplier<SoundEvent> LUMINATE_PLACE = registerSoundEvent("luminate_place");
    public static final Supplier<SoundEvent> LUMINATE_STEP = registerSoundEvent("luminate_step");
    public static final Supplier<SoundEvent> LUMINATE_BREAK = registerSoundEvent("luminate_break");
    public static final Supplier<SoundEvent> LUMINATE_HIT = registerSoundEvent("luminate_hit");
    public static final Supplier<SoundEvent> LUMINATE_FALL = registerSoundEvent("luminate_fall");

    public static final DeferredSoundType LUMINATE_SOUNDS = new DeferredSoundType(1f, 1f,
            ModSounds.LUMINATE_BREAK, ModSounds.LUMINATE_PLACE, ModSounds.LUMINATE_FALL, ModSounds.LUMINATE_STEP, ModSounds.LUMINATE_HIT);

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(EconomyMod.MODID, name);
        return SOUND_EVENT.register(name, ()-> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENT.register(eventBus);
    }
}
