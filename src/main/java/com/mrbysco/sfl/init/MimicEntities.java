package com.mrbysco.sfl.init;

import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;

public class MimicEntities {
	public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
		event.register(MimicRegistry.MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(MimicRegistry.END_MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MimicEntity::spawnPredicate, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(MimicRegistry.NETHER_MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NetherMimicEntity::spawnPredicate, SpawnPlacementRegisterEvent.Operation.AND);
		event.register(MimicRegistry.WATER_MIMIC.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterMimicEntity::spawnPredicate, SpawnPlacementRegisterEvent.Operation.AND);
	}

	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(MimicRegistry.MIMIC.get(), MimicEntity.registerAttributes().build());
		event.put(MimicRegistry.END_MIMIC.get(), EndMimicEntity.registerAttributes().build());
		event.put(MimicRegistry.NETHER_MIMIC.get(), NetherMimicEntity.registerAttributes().build());
		event.put(MimicRegistry.WATER_MIMIC.get(), WaterMimicEntity.registerAttributes().build());
	}
}
