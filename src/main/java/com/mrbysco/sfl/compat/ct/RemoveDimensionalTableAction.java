package com.mrbysco.sfl.compat.ct;

import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.util.ResourceLocation;

public class RemoveDimensionalTableAction implements IUndoableAction {
    public final int dimension;
    public final ResourceLocation lootTable;

    public RemoveDimensionalTableAction(int dim, String table) {
        this.dimension = dim;
        this.lootTable = new ResourceLocation(table);
    }

    @Override
    public void apply() {
        MimicLootHandler.removeDimensionalTable(dimension, lootTable);
    }

    @Override
    public String describe() {
        return String.format("Loot table: " + lootTable.toString() + " has been removed from dimension " + dimension + " of the Mimic loot list");
    }

    @Override
    public void undo() {
        MimicLootHandler.addDimensionalTable(dimension, lootTable);
    }

    @Override
    public String describeUndo() {
        return String.format("Loot table: " + lootTable.toString() + " has been re-added to dimension " + dimension + " of the Mimic loot list");
    }
}
