package com.mrbysco.sfl.datagen.client;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SFLLanguageProvider extends LanguageProvider {
	public SFLLanguageProvider(PackOutput packOutput) {
		super(packOutput, ServerFriendlyLoot.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addItem(MimicRegistry.MIMIC_SPAWN_EGG, "Mimic Spawn Egg");
		addItem(MimicRegistry.WATER_MIMIC_SPAWN_EGG, "Water Mimic Spawn Egg");
		addItem(MimicRegistry.END_MIMIC_SPAWN_EGG, "End Mimic Spawn Egg");
		addItem(MimicRegistry.NETHER_MIMIC_SPAWN_EGG, "Nether Mimic Spawn Egg");

		addEntityType(MimicRegistry.MIMIC, "Mimic");
		addEntityType(MimicRegistry.WATER_MIMIC, "Mimic");
		addEntityType(MimicRegistry.END_MIMIC, "End Mimic");
		addEntityType(MimicRegistry.NETHER_MIMIC, "Nether Mimic");

		addConfig("general", "General", "Configure General Mimic settings");
		addConfig("dimensionBlacklist", "Dimension Blacklist", "Dimensions in which the mimic's can't spawn");

		//TODO: Custom sounds
//			addSubtitle(MimicSounds.MIMIC_AMBIENT, "Mimic noises");
//			addSubtitle(MimicSounds.MIMIC_DEATH, "Mimic dies");
//			addSubtitle(MimicSounds.MIMIC_HURT, "Mimic hurts");
	}

	public void addSubtitle(Supplier<SoundEvent> sound, String name) {
		this.addSubtitle(sound.get(), name);
	}

	public void addSubtitle(SoundEvent sound, String name) {
		String path = ServerFriendlyLoot.MOD_ID + ".subtitle." + sound.location().getPath();
		this.add(path, name);
	}

	private void addConfig(String path, String name, @Nullable String description) {
		this.add("sfl.configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add("sfl.configuration." + path + ".tooltip", description);
	}
}
