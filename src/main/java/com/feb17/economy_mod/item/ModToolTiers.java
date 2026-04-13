package com.feb17.economy_mod.item;

import com.feb17.economy_mod.util.ModTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier LUMINATE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_LUMINATE_TOOL,600, 7.0F, 3F, 30, () -> Ingredient.of(ModItmes.LUMINATE_INGOT));
    public static final Tier CITIZEN = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_CITIZEN_TOOL,300, 8.0F, 1.5F, 10, () -> Ingredient.of(Items.IRON_INGOT));
}
