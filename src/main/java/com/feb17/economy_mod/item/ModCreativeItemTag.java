package com.feb17.economy_mod.item;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

//注册创造物品栏标签
public class ModCreativeItemTag {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EconomyMod.MODID);

    public static final Supplier<CreativeModeTab> ECONOMY_TAB =
            CREATIVE_MODE_TABS.register("economy_tab", ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ModItmes.TRUST.get()))
                    .title(Component.translatable("itemGroup.economy_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        //添加到物品栏
                        output.accept(ModItmes.TRUST);
                        output.accept(ModItmes.COINS);
                        output.accept(ModItmes.KING_COIN);
                        output.accept(ModItmes.COPPER_COIN);
                        output.accept(ModItmes.IRON_COIN);
                        output.accept(ModItmes.GOLD_COIN);
                        output.accept(ModItmes.RAW_LUMINATE);
                        output.accept(ModItmes.RAW_MITHRIL);
                        output.accept(ModBlocks.LUMINATE_ORE);
                        output.accept(ModBlocks.MITHRIL_ORE);
                        output.accept(ModItmes.MITHRIL_INGOT);
                        output.accept(ModItmes.LUMINATE_INGOT);

                        output.accept(ModItmes.LUMINATE_SWORD);
                        output.accept(ModItmes.LUMINATE_AXE);
                        output.accept(ModItmes.LUMINATE_HOE);
                        output.accept(ModItmes.LUMINATE_PICKAXE);
                        output.accept(ModItmes.LUMINATE_SHOVEL);
                        output.accept(ModBlocks.LUMINATE_LIGHT);

                        output.accept(ModItmes.CITIZEN_DAGGER);

                        output.accept(ModItmes.RUNE_KNIGHT_LEGGINGS);
                        output.accept(ModItmes.RUNE_KNIGHT_HELMET);
                        output.accept(ModItmes.RUNE_KNIGHT_BOOTS);
                        output.accept(ModItmes.RUNE_KNIGHT_CHESTPLATE);
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
