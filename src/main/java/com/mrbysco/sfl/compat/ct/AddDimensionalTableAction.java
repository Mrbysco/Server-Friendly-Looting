//package com.mrbysco.sfl.compat.ct;
//
//import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
//import com.mrbysco.sfl.init.MimicLootHandler;
//import net.minecraft.resources.Identifier;
//
//public class AddDimensionalTableAction implements IUndoableAction {
//	public final String dimension;
//	public final Identifier lootTable;
//
//	public AddDimensionalTableAction(String dim, String table) {
//		this.dimension = dim;
//		this.lootTable = Identifier.tryParse(table);
//	}
//
//	@Override
//	public void apply() {
//		MimicLootHandler.addDimensionalTable(Identifier.tryParse(dimension), lootTable);
//	}
//
//	@Override
//	public String describe() {
//		return String.format("Loot table: " + lootTable.toString() + " has been added to dimension " + dimension + " of the Mimic loot list");
//	}
//
//	@Override
//	public void undo() {
//		MimicLootHandler.removeDimensionalTable(Identifier.tryParse(dimension), lootTable);
//	}
//
//	@Override
//	public String describeUndo() {
//		return String.format("Undid change to Mimic Loot list, Loot table: " + lootTable.toString() + " has been removed from dimension " + dimension + " again");
//	}
//
//	@Override
//	public String systemName() {
//		return "Server Friendly Looting";
//	}
//}
