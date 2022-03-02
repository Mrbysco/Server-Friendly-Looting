package com.mrbysco.sfl.init;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerFriendlyLoot.MOD_ID)
public class MimicEntities {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void addSpawn(BiomeLoadingEvent event) {
		ResourceKey<Biome> biomeKey = ResourceKey.create(Registry.BIOME_REGISTRY, event.getName());
		event.getSpawns().getSpawner(MobCategory.MONSTER).add(new SpawnerData(MimicRegistry.MIMIC.get(), SFLConfig.SPAWN.weight.get(), SFLConfig.SPAWN.minGroup.get(), SFLConfig.SPAWN.maxGroup.get()));

		if (BiomeDictionary.hasType(biomeKey, Type.NETHER)) {
			event.getSpawns().getSpawner(MobCategory.MONSTER).add(new SpawnerData(MimicRegistry.NETHER_MIMIC.get(), SFLConfig.SPAWN.netherWeight.get(), SFLConfig.SPAWN.netherMinGroup.get(), SFLConfig.SPAWN.netherMaxGroup.get()));
		}

		if (BiomeDictionary.hasType(biomeKey, Type.END)) {
			event.getSpawns().getSpawner(MobCategory.MONSTER).add(new SpawnerData(MimicRegistry.END_MIMIC.get(), SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
		}

		if (BiomeDictionary.hasType(biomeKey, Type.WATER)) {
			event.getSpawns().getSpawner(MobCategory.MONSTER).add(new SpawnerData(MimicRegistry.WATER_MIMIC.get(), SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
		}
	}

	public static void setupPlacement() {
		SpawnPlacements.register(MimicRegistry.MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
		SpawnPlacements.register(MimicRegistry.END_MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
		SpawnPlacements.register(MimicRegistry.NETHER_MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NetherMimicEntity::spawnPredicate);
		SpawnPlacements.register(MimicRegistry.WATER_MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterMimicEntity::spawnPredicate);
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(MimicRegistry.MIMIC.get(), MimicEntity.registerAttributes().build());
		event.put(MimicRegistry.END_MIMIC.get(), EndMimicEntity.registerAttributes().build());
		event.put(MimicRegistry.NETHER_MIMIC.get(), NetherMimicEntity.registerAttributes().build());
		event.put(MimicRegistry.WATER_MIMIC.get(), WaterMimicEntity.registerAttributes().build());
	}
}
