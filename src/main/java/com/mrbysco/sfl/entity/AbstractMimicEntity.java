package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class AbstractMimicEntity extends CreatureEntity {
    private ResourceLocation defaultLootTable;

    public AbstractMimicEntity(EntityType<? extends AbstractMimicEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
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
        int stackAmount = 1;

        if(damageSourceIn.getTrueSource() instanceof PlayerEntity && !(damageSourceIn.getTrueSource() instanceof FakePlayer)) {
            PlayerEntity player = (PlayerEntity)damageSourceIn.getTrueSource();
            Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
            if(enchants.containsKey(Enchantments.LOOTING)) {
                stackAmount = enchants.get(Enchantments.LOOTING).intValue() + 1;
            }
        }

        if(stackAmount > 1) {
            if(stackAmount > loot.size()) {
                for(int i = 0; i < loot.size(); i++) {
                    this.entityDropItem(loot.get(i));
                }
            } else {
                for(int i = 0; i < stackAmount; i++) {
                    this.entityDropItem(loot.get(i));
                }
            }
        } else {
            int randNumber = rand.nextInt(loot.size());
            this.entityDropItem(loot.get(randNumber));
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putString("DefaultLootTable", this.defaultLootTable.toString());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.defaultLootTable = new ResourceLocation(compound.getString("DefaultLootTable"));
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData data = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);

        ArrayList<ResourceLocation> tables = MimicLootHandler.getDimensionTables(world.getDimensionKey());
        if(tables.isEmpty()) {
            this.defaultLootTable = LootTables.CHESTS_VILLAGE_VILLAGE_FISHER;
        } else {
            int idx = rand.nextInt(tables.size());
            this.defaultLootTable = tables.get(idx);
        }

        return data;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLOCK_CHEST_LOCKED;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_CHEST_OPEN;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_CHEST_CLOSE;
    }

    public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && canSpawnOn(typeIn, worldIn, reason, pos, randomIn);
    }
}
