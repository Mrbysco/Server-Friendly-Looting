package com.mrbysco.sfl.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType.Getter;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

import java.util.List;

@ZenRegister
@Name("mods.cft.DimensionalTables")
public class DimensionalTablesCT {
    @Method
    public static void addTable(String dimension, String lootTable) {
        CraftTweakerAPI.apply(new AddDimensionalTableAction(dimension, lootTable));
    }

    @Method
    public static void removeTable(String dimension, String lootTable) {
        CraftTweakerAPI.apply(new AddDimensionalTableAction(dimension, lootTable));
    }

    @Method
    public static void addWaterTable(String lootTable) {
        CraftTweakerAPI.apply(new AddWaterTableAction(lootTable));
    }

    @Method
    public static void removeWaterTable(String lootTable) {
        CraftTweakerAPI.apply(new RemoveWaterTableAction(lootTable));
    }

    @Getter("tables")
    public List<String> getTables(String dimension) {
        return MimicLootHandler.getStringDimensionTables(new ResourceLocation(dimension));
    }

    @Getter("waterTables")
    public List<String> getWaterTables() {
        return MimicLootHandler.getStringWaterTables();
    }
}
