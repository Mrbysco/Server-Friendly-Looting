package com.mrbysco.sfl.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import java.util.ArrayList;
import java.util.HashMap;

public class MimicLootHandler {
    private static HashMap<ResourceKey<Level>, ArrayList<ResourceLocation>> DIMENSIONAL_TABLES = new HashMap<>();
    private static ArrayList<ResourceLocation> WATER_LOOT_TABLES = new ArrayList<>();

    static {
        DIMENSIONAL_TABLES.put(Level.OVERWORLD, new ArrayList<>());
        DIMENSIONAL_TABLES.put(Level.NETHER, new ArrayList<>());
        DIMENSIONAL_TABLES.put(Level.END, new ArrayList<>());

        //Nether
        addDimensionalTable(Level.NETHER, BuiltInLootTables.NETHER_BRIDGE);
        addDimensionalTable(Level.NETHER, BuiltInLootTables.BASTION_TREASURE);
        addDimensionalTable(Level.NETHER, BuiltInLootTables.BASTION_BRIDGE);
        addDimensionalTable(Level.NETHER, BuiltInLootTables.BASTION_OTHER);
        addDimensionalTable(Level.NETHER, BuiltInLootTables.BASTION_HOGLIN_STABLE);

        //End
        addDimensionalTable(Level.END, BuiltInLootTables.END_CITY_TREASURE);

        //Overworld
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.SIMPLE_DUNGEON);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_WEAPONSMITH);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_TOOLSMITH);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_ARMORER);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_CARTOGRAPHER);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_MASON);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_SHEPHERD);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_BUTCHER);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_FLETCHER);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_FISHER);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_TANNERY);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_TEMPLE);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_DESERT_HOUSE);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_PLAINS_HOUSE);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_TAIGA_HOUSE);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_SNOWY_HOUSE);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.VILLAGE_SAVANNA_HOUSE);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.ABANDONED_MINESHAFT);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.STRONGHOLD_LIBRARY);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.STRONGHOLD_CROSSING);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.STRONGHOLD_CORRIDOR);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.DESERT_PYRAMID);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.JUNGLE_TEMPLE);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.IGLOO_CHEST);
        addDimensionalTable(Level.OVERWORLD, BuiltInLootTables.WOODLAND_MANSION);

        addWaterTable(BuiltInLootTables.UNDERWATER_RUIN_SMALL);
        addWaterTable(BuiltInLootTables.UNDERWATER_RUIN_BIG);
        addWaterTable(BuiltInLootTables.BURIED_TREASURE);
        addWaterTable(BuiltInLootTables.SHIPWRECK_MAP);
        addWaterTable(BuiltInLootTables.SHIPWRECK_SUPPLY);
        addWaterTable(BuiltInLootTables.SHIPWRECK_TREASURE);
        addWaterTable(BuiltInLootTables.PILLAGER_OUTPOST);
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

    public static void addDimensionalTable(ResourceKey<Level> dimType, ResourceLocation lootTable)
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

    public static ResourceKey<Level> getKeyFromLocation(ResourceLocation loc) {
        return ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
    }

    public static void addDimensionalTable(ResourceLocation dimensionLocation, ResourceLocation lootTable)
    {
        addDimensionalTable(getKeyFromLocation(dimensionLocation), lootTable);
    }

    public static void removeDimensionalTable(ResourceKey<Level> dimType, ResourceLocation lootTable)
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

    public static HashMap<ResourceKey<Level>, ArrayList<ResourceLocation>> getDimensionalTables() {
        return DIMENSIONAL_TABLES;
    }

    public static ArrayList<ResourceLocation> getDimensionTables(ResourceKey<Level> type) {
        if(DIMENSIONAL_TABLES.containsKey(type)) {
            return DIMENSIONAL_TABLES.get(type);
        }
        return new ArrayList<>();
    }

    public static ArrayList<String> getStringDimensionTables(ResourceKey<Level> type) {
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
