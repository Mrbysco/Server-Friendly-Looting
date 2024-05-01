package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMimicEntity extends Monster {
	private ResourceKey<LootTable> defaultLootTable;

	public AbstractMimicEntity(EntityType<? extends AbstractMimicEntity> type, Level level) {
		super(type, level);
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		return true;
	}

	@Override
	protected ResourceKey<LootTable> getDefaultLootTable() {
		return this.defaultLootTable;
	}

	@Override
	protected void dropFromLootTable(DamageSource damageSourceIn, boolean wasRecentlyHit) {
		ResourceKey<LootTable> resourcelocation = this.getLootTable();
		LootTable loottable = this.level().getServer().reloadableRegistries().getLootTable(resourcelocation);

		LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) this.level()))
				.withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position())
				.withParameter(LootContextParams.DAMAGE_SOURCE, damageSourceIn).withOptionalParameter(LootContextParams.KILLER_ENTITY, damageSourceIn.getEntity())
				.withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSourceIn.getDirectEntity());
		if (wasRecentlyHit && this.lastHurtByPlayer != null) {
			lootcontext$builder = lootcontext$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
		}

		List<ItemStack> loot = loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.ENTITY));
		int stackAmount = 1;

		if (damageSourceIn.getEntity() instanceof Player player && !(damageSourceIn.getEntity() instanceof FakePlayer)) {
			int looting = player.getMainHandItem().getEnchantmentLevel(Enchantments.LOOTING);
			if (looting > 0) {
				stackAmount = looting + 1;
			}
		}

		if (stackAmount > 1) {
			if (stackAmount > loot.size()) {
				for (ItemStack stack : loot) {
					this.spawnAtLocation(stack);
				}
			} else {
				for (int i = 0; i < stackAmount; i++) {
					this.spawnAtLocation(loot.get(i));
				}
			}
		} else {
			int randNumber = random.nextInt(loot.size());
			this.spawnAtLocation(loot.get(randNumber));
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);

		compound.putString("DefaultLootTable", this.defaultLootTable.toString());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		this.defaultLootTable = getLootKey(new ResourceLocation(compound.getString("DefaultLootTable")));
	}

	private ResourceKey<LootTable> getLootKey(ResourceLocation location) {
		return ResourceKey.create(Registries.LOOT_TABLE, location);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance,
	                                    MobSpawnType spawnType, @Nullable SpawnGroupData groupData) {
		SpawnGroupData data = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData);

		ArrayList<ResourceLocation> tables = MimicLootHandler.getDimensionTables(this.level().dimension());
		if (tables.isEmpty()) {
			this.defaultLootTable = BuiltInLootTables.VILLAGE_FISHER;
		} else {
			int idx = random.nextInt(tables.size());
			this.defaultLootTable = getLootKey(tables.get(idx));
		}

		return data;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.CHEST_LOCKED;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.CHEST_OPEN;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.CHEST_CLOSE;
	}

	public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, LevelAccessor levelAccessor,
	                                     MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
		return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(typeIn, levelAccessor, spawnType, pos, randomSource);
	}
}
