package com.mrbysco.sfl.init;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerFriendlyLoot.MOD_ID)
public class MimicEntities {
    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        RegistryKey<Biome> biomeKey = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, event.getName());
        event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new Spawners(MimicRegistry.MIMIC.get(), SFLConfig.SPAWN.weight.get(), SFLConfig.SPAWN.minGroup.get(), SFLConfig.SPAWN.maxGroup.get()));

        if(BiomeDictionary.hasType(biomeKey, Type.NETHER)) {
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new Spawners(MimicRegistry.NETHER_MIMIC.get(), SFLConfig.SPAWN.netherWeight.get(), SFLConfig.SPAWN.netherMinGroup.get(), SFLConfig.SPAWN.netherMaxGroup.get()));
        }

        if(BiomeDictionary.hasType(biomeKey, Type.END)) {
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new Spawners(MimicRegistry.END_MIMIC.get(), SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
        }

        if(BiomeDictionary.hasType(biomeKey, Type.WATER)) {
            event.getSpawns().getSpawner(EntityClassification.MONSTER).add(new Spawners(MimicRegistry.WATER_MIMIC.get(), SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
        }
    }

    public static void setupPlacement() {
        EntitySpawnPlacementRegistry.register(MimicRegistry.MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(MimicRegistry.END_MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(MimicRegistry.NETHER_MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NetherMimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(MimicRegistry.WATER_MIMIC.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterMimicEntity::spawnPredicate);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(MimicRegistry.MIMIC.get(), MimicEntity.registerAttributes().create());
        event.put(MimicRegistry.END_MIMIC.get(), EndMimicEntity.registerAttributes().create());
        event.put(MimicRegistry.NETHER_MIMIC.get(), NetherMimicEntity.registerAttributes().create());
        event.put(MimicRegistry.WATER_MIMIC.get(), WaterMimicEntity.registerAttributes().create());
    }
}
