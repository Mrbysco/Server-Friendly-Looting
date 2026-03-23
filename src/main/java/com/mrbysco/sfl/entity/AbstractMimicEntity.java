package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractMimicEntity extends Monster {

	public AbstractMimicEntity(EntityType<? extends AbstractMimicEntity> type, Level level) {
		super(type, level);
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		return true;
	}

	@Override
	protected void dropFromLootTable(ServerLevel serverLevel, DamageSource source, boolean recentlyHit) {
		ResourceKey<LootTable> tableResourceKey = this.getLootTable().orElse(null);
		if (tableResourceKey == null) {
			return;
		}
		LootTable loottable = this.level().getServer().reloadableRegistries().getLootTable(tableResourceKey);

		LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel) this.level()))
				.withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position())
				.withParameter(LootContextParams.DAMAGE_SOURCE, source).withOptionalParameter(LootContextParams.ATTACKING_ENTITY, source.getEntity())
				.withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, source.getDirectEntity());
		Player player = this.getLastHurtByPlayer();
		if (recentlyHit && player != null) {
			lootcontext$builder = lootcontext$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player).withLuck(player.getLuck());
		}

		List<ItemStack> loot = loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.ENTITY));
		int stackAmount = 1;

		if (!(player instanceof FakePlayer)) {
			int looting = player.getMainHandItem().getEnchantmentLevel(level().holderOrThrow(Enchantments.LOOTING));
			if (looting > 0) {
				stackAmount = looting + 1;
			}
		}

		if (stackAmount > 1) {
			if (stackAmount > loot.size()) {
				for (ItemStack stack : loot) {
					this.spawnAtLocation(serverLevel, stack);
				}
			} else {
				for (int i = 0; i < stackAmount; i++) {
					this.spawnAtLocation(serverLevel, loot.get(i));
				}
			}
		} else {
			int randNumber = !loot.isEmpty() ? random.nextInt(loot.size()) : 1;
			this.spawnAtLocation(serverLevel, loot.get(randNumber));
		}
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
	}

	private ResourceKey<LootTable> getLootKey(Identifier location) {
		return ResourceKey.create(Registries.LOOT_TABLE, location);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance,
	                                    EntitySpawnReason spawnType, @Nullable SpawnGroupData groupData) {
		SpawnGroupData data = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData);

		ArrayList<Identifier> tables = MimicLootHandler.getDimensionTables(this.level().dimension());
		if (tables.isEmpty()) {
			this.lootTable = Optional.of(BuiltInLootTables.VILLAGE_FISHER);
		} else {
			int idx = random.nextInt(tables.size());
			this.lootTable = Optional.of(getLootKey(tables.get(idx)));
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

	public int getMimicType() {
		return 0;
	}

	public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, LevelAccessor levelAccessor,
	                                     EntitySpawnReason spawnType, BlockPos pos, RandomSource randomSource) {
		return levelAccessor.getDifficulty() != Difficulty.PEACEFUL &&
				!levelAccessor.getBiome(pos).is(Tags.Biomes.NO_DEFAULT_MONSTERS) &&
				checkMobSpawnRules(typeIn, levelAccessor, spawnType, pos, randomSource);
	}
}
