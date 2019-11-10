package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.ModEntities;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MimicEntity extends CreatureEntity {
    private static final DataParameter<Integer> MIMIC_TYPE = EntityDataManager.createKey(MimicEntity.class, DataSerializers.VARINT);
    private ResourceLocation defaultLootTable;

    public MimicEntity(EntityType<? extends MimicEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public MimicEntity(World worldIn)
    {
        super(ModEntities.MIMIC, worldIn);
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

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.25F);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return super.canBreatheUnderwater();
    }

    @Override
    protected ResourceLocation getLootTable() {
        return this.defaultLootTable;
    }

    @Override
    protected void dropLoot(DamageSource damageSourceIn, boolean wasRecentlyHit) {
        ResourceLocation resourcelocation = this.getLootTableResourceLocation();
        LootTable loottable = this.world.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);
        LootContext.Builder lootcontext$builder = this.getLootContextBuilder(wasRecentlyHit, damageSourceIn);
        List<ItemStack> loot = loottable.generate(lootcontext$builder.build(LootParameterSets.ENTITY));
        int randNumber = rand.nextInt(loot.size());
        this.entityDropItem(loot.get(randNumber));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putInt("MimicType", this.getMimicType());
        compound.putString("DefaultLootTable", this.defaultLootTable.toString());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.setMimicType(compound.getInt("MimicType"));
        this.defaultLootTable = new ResourceLocation(compound.getString("DefaultLootTable"));
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        int i = this.getRandomMimicType(worldIn);
        this.setMimicType(i);

        ArrayList<ResourceLocation> tables = MimicLootHandler.getDimensionTables(world.getDimension().getType());
        if(tables.isEmpty()) {
            this.defaultLootTable = LootTables.CHESTS_VILLAGE_VILLAGE_FISHER;
        } else {
            int idx = rand.nextInt(tables.size());
            this.defaultLootTable = tables.get(idx);
        }

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
        Biome biome = world.getBiome(new BlockPos(this));
        int i = this.rand.nextInt(6);
        if (biome.getPrecipitation() == Biome.RainType.SNOW) {
            return this.rand.nextBoolean() ? 1 : i;
        } else if (biome.getCategory() == Biome.Category.DESERT) {
            return this.rand.nextBoolean() ? 4 : i;
        } else {
            return i;
        }
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }
}
