package com.mrbysco.sfl.datagen;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFLDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new SFLLanguageProvider(generator));
		}

		if (event.includeServer()) {
			final HolderSet.Named<Biome> overworldTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), BiomeTags.IS_OVERWORLD);
			final BiomeModifier addOverworldSpawn = AddSpawnsBiomeModifier.singleSpawn(
					overworldTag,
					new SpawnerData(MimicRegistry.MIMIC.get(), 1, 1, 1));

			final HolderSet.Named<Biome> netherTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), BiomeTags.IS_NETHER);
			final BiomeModifier addNetherSpawn = AddSpawnsBiomeModifier.singleSpawn(
					netherTag,
					new SpawnerData(MimicRegistry.NETHER_MIMIC.get(), 1, 1, 1));

			final HolderSet.Named<Biome> endTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), BiomeTags.IS_END);
			final BiomeModifier addEndSpawn = AddSpawnsBiomeModifier.singleSpawn(
					endTag,
					new SpawnerData(MimicRegistry.END_MIMIC.get(), 1, 1, 1));

			final HolderSet.Named<Biome> waterTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).orElseThrow(), Tags.Biomes.IS_WATER);
			final BiomeModifier addWaterTag = AddSpawnsBiomeModifier.singleSpawn(
					waterTag,
					new SpawnerData(MimicRegistry.WATER_MIMIC.get(), 1, 1, 1));

			generator.addProvider(event.includeServer(), JsonCodecProvider.forDatapackRegistry(
					generator, helper, ServerFriendlyLoot.MOD_ID, ops, ForgeRegistries.Keys.BIOME_MODIFIERS,
					Map.of(
							new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_mimic"), addOverworldSpawn,
							new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_nether_mimic"), addNetherSpawn,
							new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_end_mimic"), addEndSpawn,
							new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_water_mimic"), addWaterTag
					)
			));
		}
	}

	public static class SFLLanguageProvider extends LanguageProvider {
		public SFLLanguageProvider(DataGenerator generator) {
			super(generator, ServerFriendlyLoot.MOD_ID, "en_us");
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

		public void addSubtitle(RegistryObject<SoundEvent> sound, String name) {
			this.addSubtitle(sound.get(), name);
		}

		public void addSubtitle(SoundEvent sound, String name) {
			String path = ServerFriendlyLoot.MOD_ID + ".subtitle." + sound.getLocation().getPath();
			this.add(path, name);
		}
	}
}
