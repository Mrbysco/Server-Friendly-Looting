package com.mrbysco.sfl.init;

import com.google.common.base.Preconditions;
import com.mrbysco.sfl.ServerFriendlyLoot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ServerFriendlyLoot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static Item mimic_spawn_egg, water_mimic_spawn_egg, end_mimic_spawn_egg, nether_mimic_spawn_egg;

    public static ArrayList<Item> ITEMS = new ArrayList<>();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        mimic_spawn_egg = registerItem(new SpawnEggItem(ModEntities.MIMIC, 8282679, 16368742, itemBuilderWithGroup()), "mimic_spawn_egg");
        water_mimic_spawn_egg = registerItem(new SpawnEggItem(ModEntities.WATER_MIMIC, 5540220, 16368742, itemBuilderWithGroup()), "water_mimic_spawn_egg");
        end_mimic_spawn_egg = registerItem(new SpawnEggItem(ModEntities.END_MIMIC, 1057581, 16368742, itemBuilderWithGroup()), "end_mimic_spawn_egg");
        nether_mimic_spawn_egg = registerItem(new SpawnEggItem(ModEntities.NETHER_MIMIC, 3151900, 16368742, itemBuilderWithGroup()), "nether_mimic_spawn_egg");

        registry.registerAll(ITEMS.toArray(new Item[0]));
    }

    public static <T extends Item> T registerItem(T item, String name)
    {
        ITEMS.add(item);

        item.setRegistryName(new ResourceLocation(ServerFriendlyLoot.MOD_ID, name));
        Preconditions.checkNotNull(item, "registryName");
        return item;
    }

    private static Item.Properties itemBuilder()
    {
        return new Item.Properties();
    }

    private static Item.Properties itemBuilderWithGroup()
    {
        return itemBuilder().group(ItemGroup.MISC);
    }
}
