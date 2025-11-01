package com.mrbysco.sfl.datagen.client;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;

public class SFLModelProvider extends ModelProvider {
	public SFLModelProvider(PackOutput output) {
		super(output, ServerFriendlyLoot.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		itemModels.generateSpawnEgg(MimicRegistry.MIMIC_SPAWN_EGG.get(), 8282679, 16368742);
		itemModels.generateSpawnEgg(MimicRegistry.END_MIMIC_SPAWN_EGG.get(), 1057581, 16368742);
		itemModels.generateSpawnEgg(MimicRegistry.NETHER_MIMIC_SPAWN_EGG.get(), 3151900, 16368742);
		itemModels.generateSpawnEgg(MimicRegistry.WATER_MIMIC_SPAWN_EGG.get(), 5540220, 16368742);
	}
}
