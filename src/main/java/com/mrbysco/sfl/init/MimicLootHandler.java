package com.mrbysco.sfl.init;

import net.minecraft.loot.LootTables;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class MimicLootHandler {
    private static HashMap<RegistryKey<World>, ArrayList<ResourceLocation>> DIMENSIONAL_TABLES = new HashMap<>();
    private static ArrayList<ResourceLocation> WATER_LOOT_TABLES = new ArrayList<>();

    static {
        DIMENSIONAL_TABLES.put(World.OVERWORLD, new ArrayList<>());
        DIMENSIONAL_TABLES.put(World.THE_NETHER, new ArrayList<>());
        DIMENSIONAL_TABLES.put(World.THE_END, new ArrayList<>());

        //Nether
        addDimensionalTable(World.THE_NETHER, LootTables.CHESTS_NETHER_BRIDGE);
        addDimensionalTable(World.THE_NETHER, LootTables.BASTION_TREASURE);
        addDimensionalTable(World.THE_NETHER, LootTables.BASTION_BRIDGE);
        addDimensionalTable(World.THE_NETHER, LootTables.BASTION_OTHER);
        addDimensionalTable(World.THE_NETHER, LootTables.BASTION_HOGLIN_STABLE);

        //End
        addDimensionalTable(World.THE_END, LootTables.CHESTS_END_CITY_TREASURE);

        //Overworld
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_SIMPLE_DUNGEON);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_WEAPONSMITH);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TOOLSMITH);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_ARMORER);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_CARTOGRAPHER);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_MASON);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_SHEPHERD);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_BUTCHER);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_FLETCHER);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_FISHER);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TANNERY);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TEMPLE);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_DESERT_HOUSE);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_PLAINS_HOUSE);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TAIGA_HOUSE);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_SNOWY_HOUSE);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_SAVANNA_HOUSE);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_ABANDONED_MINESHAFT);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_STRONGHOLD_LIBRARY);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_STRONGHOLD_CROSSING);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_STRONGHOLD_CORRIDOR);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_DESERT_PYRAMID);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_JUNGLE_TEMPLE);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_JUNGLE_TEMPLE_DISPENSER);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_IGLOO_CHEST);
        addDimensionalTable(World.OVERWORLD, LootTables.CHESTS_WOODLAND_MANSION);

        addWaterTable(LootTables.CHESTS_UNDERWATER_RUIN_SMALL);
        addWaterTable(LootTables.CHESTS_UNDERWATER_RUIN_BIG);
        addWaterTable(LootTables.CHESTS_BURIED_TREASURE);
        addWaterTable(LootTables.CHESTS_SHIPWRECK_MAP);
        addWaterTable(LootTables.CHESTS_SHIPWRECK_SUPPLY);
        addWaterTable(LootTables.CHESTS_SHIPWRECK_TREASURE);
        addWaterTable(LootTables.CHESTS_PILLAGER_OUTPOST);
    }

    public static void addWaterTable(ResourceLocation lootTable)
    {
        if(!WATER_LOOT_TABLES.contains(lootTable)) {
            WATER_LOOT_TABLES.add(lootTable);
        }
    }

    public static void removeWaterTable(ResourceLocation lootTable)
    {
        WATER_LOOT_TABLES.remove(lootTable);
    }

    public static void addDimensionalTable(RegistryKey<World> dimType, ResourceLocation lootTable)
    {
        if(DIMENSIONAL_TABLES.containsKey(dimType)) {
            ArrayList<ResourceLocation> tableList = DIMENSIONAL_TABLES.get(dimType);
            if(!tableList.contains(lootTable)) {
                tableList.add(lootTable);
                DIMENSIONAL_TABLES.put(dimType, tableList);
            }
        } else {
            ArrayList<ResourceLocation> tableList = new ArrayList<>();
            tableList.add(lootTable);
            DIMENSIONAL_TABLES.put(dimType, tableList);
        }
    }

    public static RegistryKey<World> getKeyFromLocation(ResourceLocation loc) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, loc);
    }

    public static void addDimensionalTable(ResourceLocation dimensionLocation, ResourceLocation lootTable)
    {
        addDimensionalTable(getKeyFromLocation(dimensionLocation), lootTable);
    }

    public static void removeDimensionalTable(RegistryKey<World> dimType, ResourceLocation lootTable)
    {
        if(DIMENSIONAL_TABLES.containsKey(dimType)) {
            ArrayList<ResourceLocation> tableList = DIMENSIONAL_TABLES.get(dimType);
            if(tableList.contains(lootTable)) {
                tableList.remove(lootTable);
                DIMENSIONAL_TABLES.put(dimType, tableList);
            }
        }
    }

    public static void removeDimensionalTable(ResourceLocation dimensionID, ResourceLocation lootTable)
    {
        removeDimensionalTable(getKeyFromLocation(dimensionID), lootTable);
    }

    public static HashMap<RegistryKey<World>, ArrayList<ResourceLocation>> getDimensionalTables() {
        return DIMENSIONAL_TABLES;
    }

    public static ArrayList<ResourceLocation> getDimensionTables(RegistryKey<World> type) {
        if(DIMENSIONAL_TABLES.containsKey(type)) {
            return DIMENSIONAL_TABLES.get(type);
        }
        return new ArrayList<>();
    }

    public static ArrayList<String> getStringDimensionTables(RegistryKey<World> type) {
        ArrayList<String> stringTables = new ArrayList<>();
        if(DIMENSIONAL_TABLES.containsKey(type)) {
            ArrayList<ResourceLocation> tables = new ArrayList<>(DIMENSIONAL_TABLES.get(type));
            for(ResourceLocation table : tables) {
                stringTables.add(table.toString());
            }

            return stringTables;
        }
        return stringTables;
    }

    public static ArrayList<String> getStringDimensionTables(ResourceLocation dim) {
        return getStringDimensionTables(getKeyFromLocation(dim));
    }


    public static ArrayList<String> getStringWaterTables() {
        ArrayList<String> stringTables = new ArrayList<>();
        ArrayList<ResourceLocation> tables = new ArrayList<>(WATER_LOOT_TABLES);
        for(ResourceLocation table : tables) {
            stringTables.add(table.toString());
        }
        return stringTables;
    }
}
