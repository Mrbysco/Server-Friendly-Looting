package com.mrbysco.sfl.datagen.client;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class SFLModelProvider extends ModelProvider {
	public SFLModelProvider(PackOutput output) {
		super(output, ServerFriendlyLoot.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		itemModels.generateFlatItem(MimicRegistry.MIMIC_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(MimicRegistry.END_MIMIC_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(MimicRegistry.NETHER_MIMIC_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
		itemModels.generateFlatItem(MimicRegistry.WATER_MIMIC_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
	}
}
