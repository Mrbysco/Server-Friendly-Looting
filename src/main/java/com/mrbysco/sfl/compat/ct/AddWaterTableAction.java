package com.mrbysco.sfl.compat.ct;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.util.ResourceLocation;

public class AddWaterTableAction implements IUndoableAction {
    public final ResourceLocation lootTable;

    public AddWaterTableAction(String table) {
        this.lootTable = new ResourceLocation(table);
    }

    @Override
    public void apply() {
        MimicLootHandler.addWaterTable(lootTable);
    }

    @Override
    public String describe() {
        return String.format("Loot table: " + lootTable.toString() + " has been added to the water mimic tables");
    }

    @Override
    public void undo() {
        MimicLootHandler.removeWaterTable(lootTable);
    }

    @Override
    public String describeUndo() {
        return String.format("Change to water mimic tables has been undone, " + lootTable.toString() + " has been removed from the list");
    }
}
