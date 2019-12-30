package com.mrbysco.sfl.init;

import com.google.common.base.Preconditions;
import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ServerFriendlyLoot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final EntityType<MimicEntity> MIMIC = register("mimic", EntityType.Builder.<MimicEntity>create(MimicEntity::new, EntityClassification.MONSTER)
            .size(1.0F, 0.9F)
            .setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true));
    public static final EntityType<EndMimicEntity> END_MIMIC = register("end_mimic", EntityType.Builder.<EndMimicEntity>create(EndMimicEntity::new, EntityClassification.MONSTER)
            .size(1.0F, 0.9F)
            .setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true));
    public static final EntityType<NetherMimicEntity> NETHER_MIMIC = register("nether_mimic", EntityType.Builder.<NetherMimicEntity>create(NetherMimicEntity::new, EntityClassification.MONSTER)
            .size(1.0F, 0.9F).immuneToFire()
            .setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true));
    public static final EntityType<WaterMimicEntity> WATER_MIMIC = register("water_mimic", EntityType.Builder.<WaterMimicEntity>create(WaterMimicEntity::new, EntityClassification.MONSTER)
            .size(1.0F, 0.9F)
            .setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true));

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        EntityType<T> entityType = builder.build(id);
        ResourceLocation name = new ResourceLocation(ServerFriendlyLoot.MOD_ID, id);

        entityType.setRegistryName(name);

        return entityType;
    }

    public static void register(EntityType<?> entity, String name, RegistryEvent.Register<EntityType<?>> event) {
        Preconditions.checkNotNull(entity, "registryName");
        event.getRegistry().register(entity);
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
    {
        ServerFriendlyLoot.LOGGER.debug("Registering Mimic Entities");
        register(MIMIC, "mimic", event);
        register(END_MIMIC, "end_mimic", event);
        register(NETHER_MIMIC, "nether_mimic", event);
        register(WATER_MIMIC, "water_mimic", event);
    }

    public static void addSpawn() {
        for(Biome biome : ForgeRegistries.BIOMES) {
            biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(MIMIC, SFLConfig.SPAWN.weight.get(), SFLConfig.SPAWN.minGroup.get(), SFLConfig.SPAWN.maxGroup.get()));

            if(BiomeDictionary.hasType(biome, Type.NETHER)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(NETHER_MIMIC, SFLConfig.SPAWN.netherWeight.get(), SFLConfig.SPAWN.netherMinGroup.get(), SFLConfig.SPAWN.netherMaxGroup.get()));
            }

            if(BiomeDictionary.hasType(biome, Type.END)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(END_MIMIC, SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
            }

            if(BiomeDictionary.hasType(biome, Type.WATER)) {
                biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(WATER_MIMIC, SFLConfig.SPAWN.endWeight.get(), SFLConfig.SPAWN.endMinGroup.get(), SFLConfig.SPAWN.endMaxGroup.get()));
            }
        }
        EntitySpawnPlacementRegistry.register(MIMIC, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(END_MIMIC, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(NETHER_MIMIC, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NetherMimicEntity::spawnPredicate);
        EntitySpawnPlacementRegistry.register(WATER_MIMIC, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WaterMimicEntity::spawnPredicate);
    }
}
