package com.mrbysco.sfl.init;

import com.google.common.base.Preconditions;
import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.MimicEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ServerFriendlyLoot.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final EntityType<MimicEntity> MIMIC = register("mimic", EntityType.Builder.<MimicEntity>create(MimicEntity::new, EntityClassification.MONSTER)
            .size(1.0F, 0.9F)
            .setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true));

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        EntityType<T> entityType = builder.build("");
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
        ServerFriendlyLoot.LOGGER.debug("Registering Mimic Entity");
        register(MIMIC, "mimic", event);
    }

    public static void addSpawn() {
        for(Biome biome : ForgeRegistries.BIOMES) {
            biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(MIMIC, SFLConfig.SPAWN.weight.get(), SFLConfig.SPAWN.minGroup.get(), SFLConfig.SPAWN.maxGroup.get()));
        }
        EntitySpawnPlacementRegistry.register(MIMIC, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MimicEntity::func_223315_a);
    }
}
