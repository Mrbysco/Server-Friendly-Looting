package com.mrbysco.sfl.compat.ct;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.util.ResourceLocation;

public class AddDimensionalTableAction implements IUndoableAction {
    public final int dimension;
    public final ResourceLocation lootTable;

    public AddDimensionalTableAction(int dim, String table) {
        this.dimension = dim;
        this.lootTable = new ResourceLocation(table);
    }

    @Override
    public void apply() {
        MimicLootHandler.addDimensionalTable(dimension, lootTable);
    }

    @Override
    public String describe() {
        return String.format("Loot table: " + lootTable.toString() + " has been added to dimension " + dimension + " of the Mimic loot list");
    }

    @Override
    public void undo() {
        MimicLootHandler.removeDimensionalTable(dimension, lootTable);
    }

    @Override
    public String describeUndo() {
        return String.format("Undid change to Mimic Loot list, Loot table: " + lootTable.toString() + " has been removed from dimension " + dimension + " again");
    }
}
