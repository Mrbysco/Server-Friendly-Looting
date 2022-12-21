//package com.mrbysco.sfl.compat.ct;
//
//import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
//import com.mrbysco.sfl.init.MimicLootHandler;
//import net.minecraft.resources.ResourceLocation;
//
//public class RemoveDimensionalTableAction implements IUndoableAction {
//	public final String dimension;
//	public final ResourceLocation lootTable;
//
//	public RemoveDimensionalTableAction(String dim, String table) {
//		this.dimension = dim;
//		this.lootTable = new ResourceLocation(table);
//	}
//
//	@Override
//	public void apply() {
//		MimicLootHandler.removeDimensionalTable(new ResourceLocation(dimension), lootTable);
//	}
//
//	@Override
//	public String describe() {
//		return String.format("Loot table: " + lootTable.toString() + " has been removed from dimension " + dimension + " of the Mimic loot list");
//	}
//
//	@Override
//	public void undo() {
//		MimicLootHandler.addDimensionalTable(new ResourceLocation(dimension), lootTable);
//	}
//
//	@Override
//	public String describeUndo() {
//		return String.format("Loot table: " + lootTable.toString() + " has been re-added to dimension " + dimension + " of the Mimic loot list");
//	}
//}
