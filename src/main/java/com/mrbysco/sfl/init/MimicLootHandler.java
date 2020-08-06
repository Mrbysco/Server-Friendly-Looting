package com.mrbysco.sfl.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.loot.LootTables;

import java.util.ArrayList;
import java.util.HashMap;

public class MimicLootHandler {
    private static HashMap<DimensionType, ArrayList<ResourceLocation>> DIMENSIONAL_TABLES = new HashMap<>();
    private static ArrayList<ResourceLocation> WATER_LOOT_TABLES = new ArrayList<>();

    static {
        DIMENSIONAL_TABLES.put(DimensionType.THE_NETHER, new ArrayList<>());
        DIMENSIONAL_TABLES.put(DimensionType.THE_END, new ArrayList<>());
        DIMENSIONAL_TABLES.put(DimensionType.OVERWORLD, new ArrayList<>());

        //Nether
        addDimensionalTable(DimensionType.THE_NETHER, LootTables.CHESTS_NETHER_BRIDGE);

        //End
        addDimensionalTable(DimensionType.THE_END, LootTables.CHESTS_END_CITY_TREASURE);

        //Overworld
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_SIMPLE_DUNGEON);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_WEAPONSMITH);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TOOLSMITH);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_ARMORER);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_CARTOGRAPHER);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_MASON);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_SHEPHERD);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_BUTCHER);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_FLETCHER);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_FISHER);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TANNERY);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TEMPLE);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_DESERT_HOUSE);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_PLAINS_HOUSE);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_TAIGA_HOUSE);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_SNOWY_HOUSE);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_VILLAGE_VILLAGE_SAVANNA_HOUSE);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_ABANDONED_MINESHAFT);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_STRONGHOLD_LIBRARY);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_STRONGHOLD_CROSSING);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_STRONGHOLD_CORRIDOR);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_DESERT_PYRAMID);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_JUNGLE_TEMPLE);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_JUNGLE_TEMPLE_DISPENSER);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_IGLOO_CHEST);
        addDimensionalTable(DimensionType.OVERWORLD, LootTables.CHESTS_WOODLAND_MANSION);


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

    public static void addDimensionalTable(DimensionType dimType, ResourceLocation lootTable)
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

    public static void addDimensionalTable(int dimensionID, ResourceLocation lootTable)
    {
        addDimensionalTable(DimensionType.getById(dimensionID), lootTable);
    }

    public static void removeDimensionalTable(DimensionType dimType, ResourceLocation lootTable)
    {
        if(DIMENSIONAL_TABLES.containsKey(dimType)) {
            ArrayList<ResourceLocation> tableList = DIMENSIONAL_TABLES.get(dimType);
            if(tableList.contains(lootTable)) {
                tableList.remove(lootTable);
                DIMENSIONAL_TABLES.put(dimType, tableList);
            }
        }
    }

    public static void removeDimensionalTable(int dimensionID, ResourceLocation lootTable)
    {
        removeDimensionalTable(DimensionType.getById(dimensionID), lootTable);
    }

    public static HashMap<DimensionType, ArrayList<ResourceLocation>> getDimensionalTables() {
        return DIMENSIONAL_TABLES;
    }

    public static ArrayList<ResourceLocation> getDimensionTables(DimensionType type) {
        if(DIMENSIONAL_TABLES.containsKey(type)) {
            return DIMENSIONAL_TABLES.get(type);
        }
        return new ArrayList<>();
    }

    public static ArrayList<String> getStringDimensionTables(DimensionType type) {
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

    public static ArrayList<String> getStringDimensionTables(int dim) {
        return getStringDimensionTables(DimensionType.getById(dim));
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
