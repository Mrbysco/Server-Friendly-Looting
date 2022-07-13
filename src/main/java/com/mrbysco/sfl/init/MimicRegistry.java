package com.mrbysco.sfl.init;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MimicRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ServerFriendlyLoot.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ServerFriendlyLoot.MOD_ID);

	public static final RegistryObject<EntityType<MimicEntity>> MIMIC = ENTITY_TYPES.register("mimic", () -> register("mimic", EntityType.Builder.<MimicEntity>of(MimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));
	public static final RegistryObject<EntityType<EndMimicEntity>> END_MIMIC = ENTITY_TYPES.register("end_mimic", () -> register("end_mimic", EntityType.Builder.<EndMimicEntity>of(EndMimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));
	public static final RegistryObject<EntityType<NetherMimicEntity>> NETHER_MIMIC = ENTITY_TYPES.register("nether_mimic", () -> register("nether_mimic", EntityType.Builder.<NetherMimicEntity>of(NetherMimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));
	public static final RegistryObject<EntityType<WaterMimicEntity>> WATER_MIMIC = ENTITY_TYPES.register("water_mimic", () -> register("water_mimic", EntityType.Builder.<WaterMimicEntity>of(WaterMimicEntity::new, MobCategory.MONSTER).sized(1.0F, 0.9F)));

	public static final RegistryObject<Item> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg", () -> new ForgeSpawnEggItem(() -> MIMIC.get(), 8282679, 16368742, itemBuilder()));
	public static final RegistryObject<Item> END_MIMIC_SPAWN_EGG = ITEMS.register("end_mimic_spawn_egg", () -> new ForgeSpawnEggItem(() -> END_MIMIC.get(), 1057581, 16368742, itemBuilder()));
	public static final RegistryObject<Item> NETHER_MIMIC_SPAWN_EGG = ITEMS.register("nether_mimic_spawn_egg", () -> new ForgeSpawnEggItem(() -> NETHER_MIMIC.get(), 3151900, 16368742, itemBuilder()));
	public static final RegistryObject<Item> WATER_MIMIC_SPAWN_EGG = ITEMS.register("water_mimic_spawn_egg", () -> new ForgeSpawnEggItem(() -> WATER_MIMIC.get(), 5540220, 16368742, itemBuilder()));

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder, boolean sendVelocityUpdates) {
		return builder.setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(sendVelocityUpdates).build(id);
	}

	public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return register(id, builder, true);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().tab(CreativeModeTab.TAB_MISC);
	}
}