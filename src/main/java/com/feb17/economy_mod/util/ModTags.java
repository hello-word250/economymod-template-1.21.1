package com.feb17.economy_mod.util;

import com.feb17.economy_mod.EconomyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

//这里创建原版没有的tag


public class ModTags {
    public static class Blocks{

        public static final TagKey<Block> NEED_LUMINATE_TOOL = createTag("need_luminate_tool");
        public static final TagKey<Block> INCORRECT_FOR_LUMINATE_TOOL = createTag("incorrect_for_luminate_tool");

        public static final TagKey<Block> NEED_CITIZEN_TOOL = createTag("need_citizen_tool");
        public static final TagKey<Block> INCORRECT_FOR_CITIZEN_TOOL = createTag("incorrect_for_citizen_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(EconomyMod.MODID, name));
        }
    }

public static class Items{
        //example
    public static final TagKey<Item> MONEY_TAG = createTag("money_tag");

    private static TagKey<Item> createTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(EconomyMod.MODID, name));
    }
}
}