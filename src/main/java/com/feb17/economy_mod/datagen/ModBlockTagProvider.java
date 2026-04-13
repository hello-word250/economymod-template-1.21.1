package com.feb17.economy_mod.datagen;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.block.ModBlocks;
import com.feb17.economy_mod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EconomyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //example
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.LUMINATE_ORE.get())
                .add(ModBlocks.MITHRIL_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.LUMINATE_ORE.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.MITHRIL_ORE.get());

        //给予镐等级
        tag(ModTags.Blocks.NEED_LUMINATE_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);
        tag(ModTags.Blocks.INCORRECT_FOR_LUMINATE_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEED_LUMINATE_TOOL);

    }
}
