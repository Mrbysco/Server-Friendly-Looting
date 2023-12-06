package com.mrbysco.sfl.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;

public class MimicEntity extends AbstractMimicEntity {
	private static final EntityDataAccessor<Integer> MIMIC_TYPE = SynchedEntityData.defineId(MimicEntity.class, EntityDataSerializers.INT);

	public MimicEntity(EntityType<? extends MimicEntity> type, Level level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return AbstractMimicEntity.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D)
				.add(Attributes.MOVEMENT_SPEED, (double) 0.25F);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);

		compound.putInt("MimicType", this.getMimicType());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);

		this.setMimicType(compound.getInt("MimicType"));
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		SpawnGroupData data = super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);
		int i = this.getRandomMimicType(level, blockPosition());
		this.setMimicType(i);

		return data;
	}


	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(MIMIC_TYPE, 0);
	}

	public int getMimicType() {
		return this.entityData.get(MIMIC_TYPE);
	}

	public void setMimicType(int mimicTypeId) {
		this.entityData.set(MIMIC_TYPE, mimicTypeId);
	}

	private int getRandomMimicType(LevelAccessor levelAccessor, BlockPos blockPos) {
		Holder<Biome> biomeHolder = levelAccessor.getBiome(blockPosition());
		int i = this.random.nextInt(6);
		if (biomeHolder.value().coldEnoughToSnow(blockPos)) {
			return this.random.nextBoolean() ? 1 : i;
		} else if (biomeHolder.is(Tags.Biomes.IS_SANDY)) {
			return this.random.nextBoolean() ? 4 : i;
		} else {
			return i;
		}
	}
}
