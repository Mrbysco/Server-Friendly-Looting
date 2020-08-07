package com.mrbysco.sfl.init;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import com.mrbysco.sfl.item.CustomSpawnEggItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MimicRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ServerFriendlyLoot.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ServerFriendlyLoot.MOD_ID);

    public static final RegistryObject<EntityType<MimicEntity>> MIMIC = ENTITIES.register("mimic", () -> register("mimic", EntityType.Builder.<MimicEntity>create(MimicEntity::new, EntityClassification.MONSTER).size(1.0F, 0.9F)));
    public static final RegistryObject<EntityType<EndMimicEntity>> END_MIMIC = ENTITIES.register("end_mimic", () -> register("end_mimic", EntityType.Builder.<EndMimicEntity>create(EndMimicEntity::new, EntityClassification.MONSTER).size(1.0F, 0.9F)));
    public static final RegistryObject<EntityType<NetherMimicEntity>> NETHER_MIMIC = ENTITIES.register("nether_mimic", () -> register("nether_mimic", EntityType.Builder.<NetherMimicEntity>create(NetherMimicEntity::new, EntityClassification.MONSTER).size(1.0F, 0.9F)));
    public static final RegistryObject<EntityType<WaterMimicEntity>> WATER_MIMIC = ENTITIES.register("water_mimic", () -> register("water_mimic", EntityType.Builder.<WaterMimicEntity>create(WaterMimicEntity::new, EntityClassification.MONSTER).size(1.0F, 0.9F)));

    public static final RegistryObject<Item> MIMIC_SPAWN_EGG = ITEMS.register("mimic_spawn_egg" , () -> new CustomSpawnEggItem(() -> MIMIC.get(), 8282679, 16368742, itemBuilder()));
    public static final RegistryObject<Item> END_MIMIC_SPAWN_EGG = ITEMS.register("end_mimic_spawn_egg" , () -> new CustomSpawnEggItem(() -> END_MIMIC.get(), 1057581, 16368742, itemBuilder()));
    public static final RegistryObject<Item> NETHER_MIMIC_SPAWN_EGG = ITEMS.register("nether_mimic_spawn_egg" , () -> new CustomSpawnEggItem(() -> NETHER_MIMIC.get(), 3151900, 16368742, itemBuilder()));
    public static final RegistryObject<Item> WATER_MIMIC_SPAWN_EGG = ITEMS.register("water_mimic_spawn_egg" , () -> new CustomSpawnEggItem(() -> WATER_MIMIC.get(), 5540220, 16368742, itemBuilder()));

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder, boolean sendVelocityUpdates) {
        return builder.setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(sendVelocityUpdates).build(id);
    }

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return register(id, builder, true);
    }

    private static Item.Properties itemBuilder() {
        return new Item.Properties().group(ItemGroup.MISC);
    }
}