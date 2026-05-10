package com.feb17.economy_mod.item;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.block.ModBlocks;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


//注册物品
public class ModItmes {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(EconomyMod.MODID);

    //在这里添加要添加的新物品
    public static final DeferredItem<Item> KING_COIN =
            ITEMS.register("king_coin", ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COPPER_COIN =
            ITEMS.register("copper_coin", ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> IRON_COIN =
            ITEMS.register("iron_coin", ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GOLD_COIN =
            ITEMS.register("gold_coin", ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TRUST =
            ITEMS.register("trust", ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> COINS =
            ITEMS.register("coins" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_LUMINATE =
            ITEMS.register("raw_luminate" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LUMINATE_INGOT =
            ITEMS.register("luminate_ingot" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_MITHRIL =
            ITEMS.register("raw_mithril" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MITHRIL_INGOT =
            ITEMS.register("mithril_ingot" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MOSS =
            ITEMS.register("moss" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MOSS_POWDER =
            ITEMS.register("moss_powder" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SLEEP_THISTLE =
            ITEMS.register("sleep_thistle" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SLEEP_THISTLE_POWDER =
            ITEMS.register("sleep_thistle_powder" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SUNDRIED_TOBACCO =
            ITEMS.register("sundried_tobacco" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SUNDRIED_TOBACCO_POWDER =
            ITEMS.register("sundried_tobacco_powder" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOBACCO =
            ITEMS.register("tobacco" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOUGH_THISTLE =
            ITEMS.register("tough_thistle" , ()-> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TUBER_POWDER =
            ITEMS.register("tuber_powder" , ()-> new Item(new Item.Properties()));
    //种子注册写法
    public static final DeferredItem<Item> TUBER =
            ITEMS.register("tuber" , ()-> new ItemNameBlockItem(ModBlocks.TUBER_CROP.get(), new Item.Properties()));


    //定义自定义等级的工具
    public static final DeferredItem<SwordItem> LUMINATE_SWORD = ITEMS.register("luminate_sword",
            () -> new SwordItem(ModToolTiers.LUMINATE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.LUMINATE, 5, -3F))));
    public static final DeferredItem<ShovelItem> LUMINATE_SHOVEL = ITEMS.register("luminate_shovel",
            () -> new ShovelItem(ModToolTiers.LUMINATE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.LUMINATE, 1.5F, -3F))));
    public static final DeferredItem<AxeItem> LUMINATE_AXE = ITEMS.register("luminate_axe",
            () -> new AxeItem(ModToolTiers.LUMINATE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.LUMINATE, 6, -3.4F))));
    public static final DeferredItem<HoeItem> LUMINATE_HOE = ITEMS.register("luminate_hoe",
            () -> new HoeItem(ModToolTiers.LUMINATE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.LUMINATE, 0.5f, -3F))));
    public static final DeferredItem<HammerItem> LUMINATE_PICKAXE = ITEMS.register("luminate_pickaxe",
            () -> new HammerItem(ModToolTiers.LUMINATE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.LUMINATE, 6, -3.4F))));

    public static final DeferredItem<SwordItem> CITIZEN_DAGGER = ITEMS.register("citizen_dagger",
            () -> new SwordItem(ModToolTiers.CITIZEN, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CITIZEN, 1, -1f))));

    public static final DeferredItem<Item> RUNE_KNIGHT_HELMET = ITEMS.register("rune_knight_helmet",
            () -> new ArmorItem(ModArmorMaterials.LUMINATE, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(37))));
    public static final DeferredItem<Item> RUNE_KNIGHT_CHESTPLATE = ITEMS.register("rune_knight_chestplate",
            () -> new ArmorItem(ModArmorMaterials.LUMINATE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(37))));
    public static final DeferredItem<Item> RUNE_KNIGHT_LEGGINGS = ITEMS.register("rune_knight_leggings",
            () -> new ArmorItem(ModArmorMaterials.LUMINATE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(37))));
    public static final DeferredItem<Item> RUNE_KNIGHT_BOOTS = ITEMS.register("rune_knight_boots",
            () -> new ArmorItem(ModArmorMaterials.LUMINATE, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(37))));

    public static void registerMod(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
