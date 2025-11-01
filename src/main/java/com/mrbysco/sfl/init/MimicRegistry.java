package com.mrbysco.sfl.init;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MimicRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ServerFriendlyLoot.MOD_ID);
	public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(ServerFriendlyLoot.MOD_ID);

	public static final Supplier<EntityType<MimicEntity>> MIMIC = ENTITIES.registerEntityType("mimic",
			MimicEntity::new,
			MobCategory.MONSTER,
			builder -> builder
					.sized(1.0F, 0.9F)
					.eyeHeight(0.8F)
					.clientTrackingRange(10)
	);
	public static final Supplier<EntityType<EndMimicEntity>> END_MIMIC = ENTITIES.registerEntityType("end_mimic",
			EndMimicEntity::new,
			MobCategory.MONSTER,
			builder -> builder
					.sized(1.0F, 0.9F)
					.eyeHeight(0.8F)
					.clientTrackingRange(10)
	);
	public static final Supplier<EntityType<NetherMimicEntity>> NETHER_MIMIC = ENTITIES.registerEntityType("nether_mimic",
			NetherMimicEntity::new,
			MobCategory.MONSTER,
			builder -> builder
					.sized(1.0F, 0.9F)
					.eyeHeight(0.8F)
					.clientTrackingRange(10)
	);
	public static final Supplier<EntityType<WaterMimicEntity>> WATER_MIMIC = ENTITIES.registerEntityType("water_mimic",
			WaterMimicEntity::new,
			MobCategory.MONSTER,
			builder -> builder
					.sized(1.0F, 0.9F)
					.eyeHeight(0.8F)
					.clientTrackingRange(10)
	);

	public static final DeferredItem<SpawnEggItem> MIMIC_SPAWN_EGG = ITEMS.registerItem("mimic_spawn_egg", (properties) -> new SpawnEggItem(MIMIC.get(), properties));
	public static final DeferredItem<SpawnEggItem> END_MIMIC_SPAWN_EGG = ITEMS.registerItem("end_mimic_spawn_egg", (properties) -> new SpawnEggItem(END_MIMIC.get(), properties));
	public static final DeferredItem<SpawnEggItem> NETHER_MIMIC_SPAWN_EGG = ITEMS.registerItem("nether_mimic_spawn_egg", (properties) -> new SpawnEggItem(NETHER_MIMIC.get(), properties));
	public static final DeferredItem<SpawnEggItem> WATER_MIMIC_SPAWN_EGG = ITEMS.registerItem("water_mimic_spawn_egg", (properties) -> new SpawnEggItem(WATER_MIMIC.get(), properties));
}