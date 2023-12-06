package com.mrbysco.sfl.datagen;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFLDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();

		generator.addProvider(event.includeClient(), new SFLLanguageProvider(packOutput));

		generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
				packOutput, CompletableFuture.supplyAsync(SFLDatagen::getProvider), Set.of(ServerFriendlyLoot.MOD_ID)));
	}

	public static class SFLLanguageProvider extends LanguageProvider {
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

			//TODO: Custom sounds
//			addSubtitle(MimicSounds.MIMIC_AMBIENT, "Mimic noises");
//			addSubtitle(MimicSounds.MIMIC_DEATH, "Mimic dies");
//			addSubtitle(MimicSounds.MIMIC_HURT, "Mimic hurts");
		}

		public void addSubtitle(Supplier<SoundEvent> sound, String name) {
			this.addSubtitle(sound.get(), name);
		}

		public void addSubtitle(SoundEvent sound, String name) {
			String path = ServerFriendlyLoot.MOD_ID + ".subtitle." + sound.getLocation().getPath();
			this.add(path, name);
		}
	}

	private static HolderLookup.Provider getProvider() {
		final RegistrySetBuilder registryBuilder = new RegistrySetBuilder();
		registryBuilder.add(Registries.CONFIGURED_FEATURE, $ -> {
		});
		registryBuilder.add(Registries.PLACED_FEATURE, $ -> {
		});
		registryBuilder.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, SFLBiomeModifiers::bootstrap);
		// We need the BIOME registry to be present so we can use a biome tag, doesn't matter that it's empty
		registryBuilder.add(Registries.BIOME, $ -> {
		});
		RegistryAccess.Frozen regAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		return registryBuilder.buildPatch(regAccess, VanillaRegistries.createLookup());
	}
}
