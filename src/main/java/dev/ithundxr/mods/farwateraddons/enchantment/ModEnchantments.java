package dev.ithundxr.mods.farwateraddons.enchantment;

import dev.ithundxr.mods.farwateraddons.FarwaterAddons;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FarwaterAddons.MOD_ID);

    public static RegistryObject<Enchantment> DEEPSLATE_MINER =
            ENCHANTMENTS.register("deepslate_miner",
                    () -> new DeepslateMinerEnchant(Enchantment.Rarity.VERY_RARE,
                            EnchantmentCategory.DIGGER, EquipmentSlot.MAINHAND));


    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}