package com.feb17.economy_mod.block;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.block.custom.LuminateLight;
import com.feb17.economy_mod.item.ModItmes;
import com.feb17.economy_mod.sound.ModSounds;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


//方块连带物品一起注册
public class ModBlocks {
    public static final DeferredRegister BLOCKS =
            DeferredRegister.createBlocks(EconomyMod.MODID);

    //添加新物品
    public static final DeferredBlock<Block> LUMINATE_ORE =
            registerBlcoks("luminate_ore",()-> new Block(BlockBehaviour.Properties.of()
                    .strength(2.5F,10.0F)
                    .requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MITHRIL_ORE =
            registerBlcoks("mithril_ore",()-> new Block(BlockBehaviour.Properties.of()
                    .strength(5F,600.0F)
                    .requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> MAGIC_ORE =
            registerBlcoks("magic_ore",()-> new Block(BlockBehaviour.Properties.of()
                    .strength(10F,600.0F)));
    public static final DeferredBlock<Block> TRADING =
            registerBlcoks("trading",()-> new Block(BlockBehaviour.Properties.of()
                    .strength(2.5F,100.0F)));

    public static final DeferredBlock<Block> LUMINATE_LIGHT =
            registerBlcoks("luminate_light.json",()-> new LuminateLight(BlockBehaviour.Properties.of()
                    .strength(2f).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(LuminateLight.CLICKED) ? 15 : 0)
                    .sound(ModSounds.LUMINATE_SOUNDS)));



    private static <T extends Block> void registerBlockItems(String name, DeferredBlock<T> block){
        ModItmes.ITEMS.register(name,()->new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredBlock<T> registerBlcoks(String name, Supplier<T> block){
        DeferredBlock<T> blcoks = (DeferredBlock<T>) BLOCKS.register(name, block);
        registerBlockItems(name, blcoks);
        return blcoks;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
