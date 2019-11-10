package com.mrbysco.sfl.compat.ct;

import com.blamejared.crafttweaker.api.actions.IAction;
import com.mrbysco.sfl.entity.MimicLootHandler;
import net.minecraft.util.ResourceLocation;

public class AddDimensionalTableAction implements IAction {
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
}
