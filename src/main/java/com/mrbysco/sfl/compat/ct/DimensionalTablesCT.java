package com.mrbysco.sfl.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.mrbysco.sfl.entity.MimicLootHandler;
import org.openzen.zencode.java.ZenCodeType.Getter;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

import java.util.List;

@ZenRegister
@Name("mods.cft.DimensionalTables")
public class DimensionalTablesCT {
    @Method
    public static void addTable(int dimension, String lootTable) {
        CraftTweakerAPI.apply(new AddDimensionalTableAction(dimension, lootTable));
    }

    @Method
    public static void removeTable(int dimension, String lootTable) {
        CraftTweakerAPI.apply(new AddDimensionalTableAction(dimension, lootTable));
    }

    @Getter("tables")
    public List<String> getTables(int dimension) {
        return MimicLootHandler.getStringDimensionTables(dimension);
    }
}
