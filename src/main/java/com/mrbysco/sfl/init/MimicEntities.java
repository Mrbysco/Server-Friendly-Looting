package com.mrbysco.sfl.init;

import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class MimicEntities {
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
