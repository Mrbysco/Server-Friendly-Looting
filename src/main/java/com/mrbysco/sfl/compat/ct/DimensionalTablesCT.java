package com.mrbysco.sfl.compat.ct;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.cft.DimensionalTables")
public class DimensionalTablesCT {
	@ZenCodeType.Method
	public static void addTable(String dimension, String lootTable) {
		CraftTweakerAPI.apply(new AddDimensionalTableAction(dimension, lootTable));
	}

	@ZenCodeType.Method
	public static void removeTable(String dimension, String lootTable) {
		CraftTweakerAPI.apply(new RemoveDimensionalTableAction(dimension, lootTable));
	}

	@ZenCodeType.Method
	public static void addWaterTable(String lootTable) {
		CraftTweakerAPI.apply(new AddWaterTableAction(lootTable));
	}

	@ZenCodeType.Method
	public static void removeWaterTable(String lootTable) {
		CraftTweakerAPI.apply(new RemoveWaterTableAction(lootTable));
	}

	@ZenCodeType.Getter("tables")
	public List<String> getTables(String dimension) {
		return MimicLootHandler.getStringDimensionTables(new ResourceLocation(dimension));
	}

	@ZenCodeType.Getter("waterTables")
	public List<String> getWaterTables() {
		return MimicLootHandler.getStringWaterTables();
	}
}
