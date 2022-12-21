package com.mrbysco.sfl.datagen;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class SFLBiomeModifiers {
	protected static final ResourceKey<BiomeModifier> ADD_MIMIC = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_mimic"));
	protected static final ResourceKey<BiomeModifier> ADD_NETHER_MIMIC = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_nether_mimic"));
	protected static final ResourceKey<BiomeModifier> ADD_END_MIMIC = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_end_mimic"));
	protected static final ResourceKey<BiomeModifier> ADD_WATER_MIMIC = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS,
			new ResourceLocation(ServerFriendlyLoot.MOD_ID, "add_water_mimic"));

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biomeGetter = context.lookup(Registries.BIOME);

		HolderSet.Named<Biome> isOverworld = biomeGetter.getOrThrow(BiomeTags.IS_OVERWORLD);
		HolderSet.Named<Biome> isNether = biomeGetter.getOrThrow(BiomeTags.IS_NETHER);
		HolderSet.Named<Biome> isEnd = biomeGetter.getOrThrow(BiomeTags.IS_END);
		HolderSet.Named<Biome> isWater = biomeGetter.getOrThrow(Tags.Biomes.IS_WATER);

		context.register(ADD_MIMIC, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				isOverworld,
				new MobSpawnSettings.SpawnerData(MimicRegistry.MIMIC.get(), 1, 1, 1)));

		context.register(ADD_NETHER_MIMIC, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				isNether,
				new MobSpawnSettings.SpawnerData(MimicRegistry.NETHER_MIMIC.get(), 1, 1, 1)));

		context.register(ADD_END_MIMIC, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				isEnd,
				new MobSpawnSettings.SpawnerData(MimicRegistry.END_MIMIC.get(), 1, 1, 1)));

		context.register(ADD_WATER_MIMIC, ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
				isWater,
				new MobSpawnSettings.SpawnerData(MimicRegistry.WATER_MIMIC.get(), 1, 1, 1)));
	}
}
