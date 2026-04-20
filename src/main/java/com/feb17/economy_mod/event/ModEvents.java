package com.feb17.economy_mod.event;

import com.feb17.economy_mod.EconomyMod;
import com.feb17.economy_mod.claim.ClaimManager;
import com.feb17.economy_mod.item.HammerItem;
import com.feb17.economy_mod.item.ModItmes;
import com.feb17.economy_mod.loyalty.LoyaltyManager;
import com.feb17.economy_mod.villager.ModVillager;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
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
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
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
        if (event.getEntity() instanceof Sheep && event.getSource().getDirectEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == ModItmes.CITIZEN_DAGGER.get()) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + "啊，我操了"));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 3));

            }
        }
        return null;
    }


    //右键event
    @SubscribeEvent
    public static Void rightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide()) {
            return null;
        }
        Player player = event.getEntity();
        ItemStack heldStack = event.getItemStack();
        boolean isShiftDown = player.isShiftKeyDown();

        if (!isShiftDown && heldStack.getItem() == ModItmes.COPPER_COIN.get() && heldStack.getCount() == 64) {
            heldStack.shrink(64);
            ItemStack ironCoinStack = new ItemStack(ModItmes.IRON_COIN.get(), 1);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added) {
                player.drop(ironCoinStack, false);
            }
        }

        if (!isShiftDown && heldStack.getItem() == ModItmes.IRON_COIN.get() && heldStack.getCount() == 64) {
            heldStack.shrink(64);
            ItemStack ironCoinStack = new ItemStack(ModItmes.GOLD_COIN.get(), 1);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added) {
                player.drop(ironCoinStack, false);
            }
        }

        if (!isShiftDown && heldStack.getItem() == ModItmes.GOLD_COIN.get() && heldStack.getCount() == 64) {
            heldStack.shrink(64);
            ItemStack ironCoinStack = new ItemStack(ModItmes.KING_COIN.get(), 1);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added) {
                player.drop(ironCoinStack, false);
            }
        }

        if (isShiftDown && heldStack.getItem() == ModItmes.KING_COIN.get()) {
            heldStack.shrink(1);
            ItemStack ironCoinStack = new ItemStack(ModItmes.GOLD_COIN.get(), 64);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added) {
                player.drop(ironCoinStack, false);
            }
        }

        if (isShiftDown && heldStack.getItem() == ModItmes.GOLD_COIN.get()) {
            heldStack.shrink(1);
            ItemStack ironCoinStack = new ItemStack(ModItmes.IRON_COIN.get(), 64);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added) {
                player.drop(ironCoinStack, false);
            }
        }

        if (isShiftDown && heldStack.getItem() == ModItmes.IRON_COIN.get()) {
            heldStack.shrink(1);
            ItemStack ironCoinStack = new ItemStack(ModItmes.COPPER_COIN.get(), 64);
            boolean added = player.getInventory().add(ironCoinStack);

            if (!added) {
                player.drop(ironCoinStack, false);
            }
        }

        return null;
    }

    //右键交互loyalty
    @SubscribeEvent
    public static void onRightClickTrustItem(PlayerInteractEvent.RightClickItem event) {
        var player = event.getEntity();
        var hand = event.getHand();

        // 只处理右手、只在服务端运行
        if (hand != InteractionHand.MAIN_HAND) return;
        if (player.level().isClientSide()) return;

        var stack = player.getMainHandItem();
        if (stack.is(ModItmes.TRUST.get())) {
            // 加10忠诚（自动限制 -100 ~ 100）
            LoyaltyManager.addLoyalty(player, 1);
            int current = LoyaltyManager.getLoyalty(player);

            player.sendSystemMessage(Component.literal("§a忠诚值 +1 → " + current));//系统文本信息
            player.getCooldowns().addCooldown(stack.getItem(), 10);//冷却
        }
    }

    // 玩家登录时初始化忠诚度
    @SubscribeEvent
    public static void onPlayerLoginInitLoyalty(PlayerEvent.PlayerLoggedInEvent event) {
        var player = event.getEntity();
        // 确保数据存在，默认0
        LoyaltyManager.setLoyalty(player, LoyaltyManager.getLoyalty(player));
    }

    //村民交易
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1)//传入等级
                    .add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItmes.GOLD_COIN, 3),
                            new ItemStack(ModItmes.CITIZEN_DAGGER.get(), 1), 6, 3, 0.05f
                    ));


        }
        if (event.getType() == ModVillager.MONSTER_HUNTER.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1)//传入等级
                    .add((entity, randomSource) -> new MerchantOffer(
                            new ItemCost(ModItmes.GOLD_COIN, 3),
                            new ItemStack(ModItmes.CITIZEN_DAGGER.get(), 1), 6, 3, 0.05f
                    ));
        }
    }

    //阻止破坏
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (event.getLevel().isClientSide()) return;
        if (!ClaimManager.canInteract(player, event.getPos(), player.level())) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.literal("§c你没有权限操作此领地"));
        }
    }

    // 阻止放置
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (!ClaimManager.canInteract(player, event.getPos(), player.level())) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.literal("§c你没有权限操作此领地"));
        }
    }

    // 阻止交互（箱子、门、按钮、床等）
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        if (event.getLevel().isClientSide()) return;
        if (!ClaimManager.canInteract(player, event.getPos(), player.level())) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.literal("§c你没有权限操作此领地"));
        }
    }

}