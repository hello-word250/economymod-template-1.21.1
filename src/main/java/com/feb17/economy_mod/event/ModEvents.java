package com.feb17.economy_mod.event;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.item.HammerItem;
import com.feb17.economy_mod.item.ModItmes;
import com.feb17.economy_mod.villager.ModVillager;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = EconomyMod.MODID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if (mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    //.Pre意味着在伤害之前触发的事件
    @SubscribeEvent
    public static Void livingDamage(LivingDamageEvent.Pre event) {
        if(event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == ModItmes.CITIZEN_DAGGER.get()) {
                player.sendSystemMessage(Component.literal(player.getName().getString()+"啊，我操了"));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,300,3));

            }
        }
        return null;
    }


    //右键event
    @SubscribeEvent
    public static Void rightClick(PlayerInteractEvent.RightClickItem event) {
        if(event.getLevel().isClientSide()) {
            return null;
        }
        Player player = event.getEntity();
        ItemStack heldStack = event.getItemStack();
        boolean isShiftDown = player.isShiftKeyDown();

        if (!isShiftDown&&heldStack.getItem() == ModItmes.COPPER_COIN.get()&&heldStack.getCount() == 64){
            heldStack.shrink(64);
            ItemStack ironCoinStack = new ItemStack(ModItmes.IRON_COIN.get(), 1);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added){
                player.drop(ironCoinStack, false);
            }
        }

        if (!isShiftDown&&heldStack.getItem() == ModItmes.IRON_COIN.get()&&heldStack.getCount() == 64){
            heldStack.shrink(64);
            ItemStack ironCoinStack = new ItemStack(ModItmes.GOLD_COIN.get(), 1);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added){
                player.drop(ironCoinStack, false);
            }
        }

        if (!isShiftDown&&heldStack.getItem() == ModItmes.GOLD_COIN.get()&&heldStack.getCount() == 64){
            heldStack.shrink(64);
            ItemStack ironCoinStack = new ItemStack(ModItmes.KING_COIN.get(), 1);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added){
                player.drop(ironCoinStack, false);
            }
        }

        if (isShiftDown&&heldStack.getItem() == ModItmes.KING_COIN.get()){
            heldStack.shrink(1);
            ItemStack ironCoinStack = new ItemStack(ModItmes.GOLD_COIN.get(), 64);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added){
                player.drop(ironCoinStack, false);
            }
        }

        if (isShiftDown&&heldStack.getItem() == ModItmes.GOLD_COIN.get()){
            heldStack.shrink(1);
            ItemStack ironCoinStack = new ItemStack(ModItmes.IRON_COIN.get(), 64);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added){
                player.drop(ironCoinStack, false);
            }
        }

        if (isShiftDown&&heldStack.getItem() == ModItmes.IRON_COIN.get()){
            heldStack.shrink(1);
            ItemStack ironCoinStack = new ItemStack(ModItmes.COPPER_COIN.get(), 64);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added){
                player.drop(ironCoinStack, false);
            }
        }

        return null;
    }
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1)//传入等级
                    .add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItmes.GOLD_COIN,3),
                            new ItemStack(ModItmes.CITIZEN_DAGGER.get(),1),6,3,0.05f
                    ));

            
        }
        if (event.getType() == ModVillager.MONSTER_HUNTER.value()){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1)//传入等级
                    .add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItmes.GOLD_COIN,3),
                            new ItemStack(ModItmes.CITIZEN_DAGGER.get(),1),6,3,0.05f
                    ));


        }
    }
}