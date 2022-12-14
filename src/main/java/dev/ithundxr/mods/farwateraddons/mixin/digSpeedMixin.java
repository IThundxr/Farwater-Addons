package dev.ithundxr.mods.farwateraddons.mixin;

import dev.ithundxr.mods.farwateraddons.config.Config;
import dev.ithundxr.mods.farwateraddons.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class digSpeedMixin {
    @Inject(at = @At("HEAD"), cancellable = true, method = "getDigSpeed(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)F", remap = false)
    private void getDigSpeed(BlockState blockState, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (blockState != null) {

            Player thiz = (Player) (Object) this;
            ItemStack itemStack = thiz.getMainHandItem();
            Item item = itemStack.getItem();
            int efficiency = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, itemStack);

            if (efficiency >= 5 && thiz.hasEffect(MobEffects.DIG_SPEED)) {
                if (Items.NETHERITE_PICKAXE.equals(item) && item instanceof TieredItem tItem) {
                    float speed = tItem.getTier().getSpeed();
                    MobEffectInstance eff = thiz.getEffect(MobEffects.DIG_SPEED);
                    if (eff != null && eff.getAmplifier() >= 2) {

                        int dm = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.DEEPSLATE_MINER.get(), itemStack);
                        int cm = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.COBBLESTONE_MINER.get(), itemStack);
                        int em = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ENDSTONE_MINER.get(), itemStack);

                        if (Config.DeepslateInstamine.get() && dm >= 1 && blockState.getBlock().equals(Blocks.DEEPSLATE) ||
                                (Config.CobbleInstamine.get() && cm >= 1 && blockState.getBlock().equals(Blocks.COBBLESTONE)) ||
                                (Config.EndstoneInstamine.get() && em >= 1 && blockState.getBlock().equals(Blocks.END_STONE))
                        ) {
                            speed *= 10f;
                            cir.setReturnValue(speed);
                        }
                    }
                }
            }
        }
    }
}
