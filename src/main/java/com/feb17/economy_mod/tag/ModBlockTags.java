package com.feb17.economy_mod.tag;

import com.feb17.economy_mod.EconomyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
    public static final TagKey<Block> ORE_TAGS = create("ore_tags");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(EconomyMod.MODID,name));
    }
}
