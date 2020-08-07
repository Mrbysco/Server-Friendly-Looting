package com.mrbysco.sfl.init;

import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.registries.ForgeRegistries;

public class MimicEntities {
    public static void addSpawn() {
        for(Biome biome : ForgeRegistries.BIOMES) {
            biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(MimicRegistry.MIMIC.get(), SFLConfig.SPAWN.weight.get(), SFLConfig.SPAWN.minGroup.get(), SFLConfig.SPAWN.maxGroup.get()));

            if(BiomeDictionary.hasType(biome, Type.NETHER)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(MimicRegistry.NETHER_MIMIC.get(), SFLConfig.SPAWN.netherWeight.get(), SFLConfig.SPAWN.netherMinGroup.get(), SFLConfig.SPAWN.netherMaxGroup.get()));
            }

            if(BiomeDictionary.hasType(biome, Type.END)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(MimicRegistry.END_MIMIC.get(), SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
            }

            if(BiomeDictionary.hasType(biome, Type.WATER)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(MimicRegistry.WATER_MIMIC.get(), SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
            }
        }
        EntitySpawnPlacementRegistry.register(MimicRegistry.MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(MimicRegistry.END_MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(MimicRegistry.NETHER_MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NetherMimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(MimicRegistry.WATER_MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterMimicEntity::spawnPredicate);
    }

    public static void entityAttributes() {
        GlobalEntityTypeAttributes.put(MimicRegistry.MIMIC.get(), MimicEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(MimicRegistry.END_MIMIC.get(), EndMimicEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(MimicRegistry.NETHER_MIMIC.get(), NetherMimicEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(MimicRegistry.WATER_MIMIC.get(), WaterMimicEntity.registerAttributes().create());
    }
}
