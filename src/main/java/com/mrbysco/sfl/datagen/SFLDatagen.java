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
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.AddSpawnsBiomeModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SFLDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy());
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			final HolderSet.Named<Biome> overworldTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_OVERWORLD);
			final BiomeModifier addOverworldSpawn = AddSpawnsBiomeModifier.singleSpawn(
					overworldTag,
					new SpawnerData(MimicRegistry.MIMIC.get(), 1, 1, 1));

			final HolderSet.Named<Biome> netherTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_NETHER);
			final BiomeModifier addNetherSpawn = AddSpawnsBiomeModifier.singleSpawn(
					netherTag,
					new SpawnerData(MimicRegistry.NETHER_MIMIC.get(), 1, 1, 1));

			final HolderSet.Named<Biome> endTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), BiomeTags.IS_END);
			final BiomeModifier addEndSpawn = AddSpawnsBiomeModifier.singleSpawn(
					endTag,
					new SpawnerData(MimicRegistry.END_MIMIC.get(), 1, 1, 1));

			final HolderSet.Named<Biome> waterTag = new HolderSet.Named<>(ops.registry(Registry.BIOME_REGISTRY).get(), Tags.Biomes.IS_WATER);
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
}
