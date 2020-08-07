package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class MimicEntity extends AbstractMimicEntity {
    private static final DataParameter<Integer> MIMIC_TYPE = EntityDataManager.createKey(MimicEntity.class, DataSerializers.VARINT);

    public MimicEntity(EntityType<? extends MimicEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public MimicEntity(World worldIn)
    {
        super(MimicRegistry.MIMIC.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return AbstractMimicEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 12.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.25F);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putInt("MimicType", this.getMimicType());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.setMimicType(compound.getInt("MimicType"));
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int i = this.getRandomMimicType(worldIn);
        this.setMimicType(i);

        return data;
    }


    protected void registerData() {
        super.registerData();
        this.dataManager.register(MIMIC_TYPE, 0);
    }

    public int getMimicType() {
        return this.dataManager.get(MIMIC_TYPE);
    }

    public void setMimicType(int mimicTypeId) {
        this.dataManager.set(MIMIC_TYPE, mimicTypeId);
    }

    private int getRandomMimicType(IWorld world) {
        Biome biome = world.getBiome(getPosition());
        int i = this.rand.nextInt(6);
        if (biome.getPrecipitation() == Biome.RainType.SNOW) {
            return this.rand.nextBoolean() ? 1 : i;
        } else if (biome.getCategory() == Biome.Category.DESERT) {
            return this.rand.nextBoolean() ? 4 : i;
        } else {
            return i;
        }
    }
}
