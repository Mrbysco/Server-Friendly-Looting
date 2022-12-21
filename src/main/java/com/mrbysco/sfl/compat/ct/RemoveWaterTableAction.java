//package com.mrbysco.sfl.compat.ct;
//
//import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
//import com.mrbysco.sfl.init.MimicLootHandler;
//import net.minecraft.resources.ResourceLocation;
//
//public class RemoveWaterTableAction implements IUndoableAction {
//	public final ResourceLocation lootTable;
//
//	public RemoveWaterTableAction(String table) {
//		this.lootTable = new ResourceLocation(table);
//	}
//
//	@Override
//	public void apply() {
//		MimicLootHandler.removeWaterTable(lootTable);
//	}
//
//	@Override
//	public String describe() {
//		return String.format("Loot table: " + lootTable.toString() + " has been removed from the water mimic tables");
//	}
//
//	@Override
//	public void undo() {
//		MimicLootHandler.addWaterTable(lootTable);
//	}
//
//	@Override
//	public String describeUndo() {
//		return String.format("Change to water mimic tables has been undone, " + lootTable.toString() + " has been added back to the list");
//	}
//}
