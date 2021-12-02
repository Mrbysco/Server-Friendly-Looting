package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
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

import javax.annotation.Nullable;
import java.util.Random;

public class NetherMimicEntity extends AbstractMimicEntity {
    private static final EntityDataAccessor<Integer> MIMIC_TYPE = SynchedEntityData.defineId(NetherMimicEntity.class, EntityDataSerializers.INT);

    public NetherMimicEntity(EntityType<? extends NetherMimicEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public NetherMimicEntity(Level worldIn)
    {
        super(MimicRegistry.NETHER_MIMIC.get(), worldIn);
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
                .add(Attributes.MOVEMENT_SPEED, (double)0.25F);
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int i = this.getRandomMimicType(worldIn);
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

    private int getRandomMimicType(LevelAccessor world) {
        if(random.nextBoolean()) {
            return 0;
        } else {
            return 1;
        }
    }

    public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL;
    }
}
