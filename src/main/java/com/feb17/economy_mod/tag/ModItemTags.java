package com.feb17.economy_mod.tag;

import com.feb17.economy_mod.EconomyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;


public class ModItemTags {

    private static TagKey<Item> bind(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(EconomyMod.MODID,name));
    }
}
