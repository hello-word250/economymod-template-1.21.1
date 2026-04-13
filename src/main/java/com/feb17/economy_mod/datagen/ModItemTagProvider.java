package com.feb17.economy_mod.datagen;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.item.ModItmes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, EconomyMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //赋予工具属性
        tag(ItemTags.SWORDS)
                .add(ModItmes.LUMINATE_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItmes.LUMINATE_PICKAXE.get());
        tag(ItemTags.AXES)
                .add(ModItmes.LUMINATE_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItmes.LUMINATE_HOE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItmes.LUMINATE_SHOVEL.get());
    }
}
