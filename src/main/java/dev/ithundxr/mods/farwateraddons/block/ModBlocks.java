package dev.ithundxr.mods.farwateraddons.block;

import dev.ithundxr.mods.farwateraddons.FarwaterAddons;
import dev.ithundxr.mods.farwateraddons.block.custom.CottonPlantBlock;
import dev.ithundxr.mods.farwateraddons.block.custom.NetherStarBlock;
import dev.ithundxr.mods.farwateraddons.block.custom.Respawner;
import dev.ithundxr.mods.farwateraddons.item.ModCreativeModeTab;
import dev.ithundxr.mods.farwateraddons.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FarwaterAddons.MOD_ID);

    public static final RegistryObject<Block> COTTON_PLANT = registerBlockWithoutBlockItem("cotton_plant",
            () -> new CottonPlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));

    public static final RegistryObject<Block> AMETHYST_GLASS = registerBlock("amethyst_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()), ModCreativeModeTab.FARWATERADDONS_TAB);

    public static final RegistryObject<Block> CLEAR_AMETHYST_GLASS = registerBlock("clear_amethyst_glass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()), ModCreativeModeTab.FARWATERADDONS_TAB);

    public static final RegistryObject<Block> NETHERSTARBLOCK = registerBlock("nether_star_block",
            () -> new NetherStarBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5.0F))
            , ModCreativeModeTab.FARWATERADDONS_TAB);

    public static final RegistryObject<Block> RECALL_STATUE = registerBlock("recall_statue",
            Respawner::new, ModCreativeModeTab.FARWATERADDONS_TAB);

    //public static final RegistryObject<Block> SOLAR_PANEL = registerBlock("solar_panel",
    //        () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()), ModCreativeModeTab.FARWATERADDONS_TAB);


    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                                                                     CreativeModeTab tab, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab, String tooltipKey) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)) {
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                pTooltip.add(new TranslatableComponent(tooltipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}