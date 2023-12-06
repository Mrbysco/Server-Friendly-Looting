package com.mrbysco.sfl.init;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MimicRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ServerFriendlyLoot.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, ServerFriendlyLoot.MOD_ID);

	public static final Supplier<EntityType<MimicEntity>> MIMIC = ENTITY_TYPES.register("mimic", () -> register("mimic", EntityType.Builder.<MimicEntity>of(MimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));
	public static final Supplier<EntityType<EndMimicEntity>> END_MIMIC = ENTITY_TYPES.register("end_mimic", () -> register("end_mimic", EntityType.Builder.<EndMimicEntity>of(EndMimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));
	public static final Supplier<EntityType<NetherMimicEntity>> NETHER_MIMIC = ENTITY_TYPES.register("nether_mimic", () -> register("nether_mimic", EntityType.Builder.<NetherMimicEntity>of(NetherMimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));
	public static final Supplier<EntityType<WaterMimicEntity>> WATER_MIMIC = ENTITY_TYPES.register("water_mimic", () -> register("water_mimic", EntityType.Builder.<WaterMimicEntity>of(WaterMimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));

	public static final DeferredItem<DeferredSpawnEggItem> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg", () -> new DeferredSpawnEggItem(MIMIC, 8282679, 16368742, itemBuilder()));
	public static final DeferredItem<DeferredSpawnEggItem> END_MIMIC_SPAWN_EGG = ITEMS.register("end_mimic_spawn_egg", () -> new DeferredSpawnEggItem(END_MIMIC, 1057581, 16368742, itemBuilder()));
	public static final DeferredItem<DeferredSpawnEggItem> NETHER_MIMIC_SPAWN_EGG = ITEMS.register("nether_mimic_spawn_egg", () -> new DeferredSpawnEggItem(NETHER_MIMIC, 3151900, 16368742, itemBuilder()));
	public static final DeferredItem<DeferredSpawnEggItem> WATER_MIMIC_SPAWN_EGG = ITEMS.register("water_mimic_spawn_egg", () -> new DeferredSpawnEggItem(WATER_MIMIC, 5540220, 16368742, itemBuilder()));

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder, boolean sendVelocityUpdates) {
		return builder.setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(sendVelocityUpdates).build(id);
	}

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return register(id, builder, true);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties();
	}
}