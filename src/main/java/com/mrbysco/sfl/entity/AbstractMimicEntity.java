package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicLootHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractMimicEntity extends Monster {
	private ResourceLocation defaultLootTable;

	public AbstractMimicEntity(EntityType<? extends AbstractMimicEntity> type, Level level) {
		super(type, level);
	}

	@Override
	public boolean canDrownInFluidType(FluidType type) {
		return true;
	}

	@Override
	protected ResourceLocation getDefaultLootTable() {
		return this.defaultLootTable;
	}

	@Override
	protected void dropFromLootTable(DamageSource damageSourceIn, boolean wasRecentlyHit) {
		ResourceLocation resourcelocation = this.getLootTable();
		LootTable loottable = this.level().getServer().getLootData().getLootTable(resourcelocation);

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
			Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(player.getMainHandItem());
			if (enchants.containsKey(Enchantments.MOB_LOOTING)) {
				stackAmount = enchants.get(Enchantments.MOB_LOOTING).intValue() + 1;
			}
		}

		if (stackAmount > 1) {
			if (stackAmount > loot.size()) {
				for (int i = 0; i < loot.size(); i++) {
					this.spawnAtLocation(loot.get(i));
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

		this.defaultLootTable = new ResourceLocation(compound.getString("DefaultLootTable"));
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		SpawnGroupData data = super.finalizeSpawn(level, difficultyIn, reason, spawnDataIn, dataTag);

		ArrayList<ResourceLocation> tables = MimicLootHandler.getDimensionTables(this.level().dimension());
		if (tables.isEmpty()) {
			this.defaultLootTable = BuiltInLootTables.VILLAGE_FISHER;
		} else {
			int idx = random.nextInt(tables.size());
			this.defaultLootTable = tables.get(idx);
		}

		return data;
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.CHEST_LOCKED;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.CHEST_OPEN;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.CHEST_CLOSE;
	}

	public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
		return level.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(typeIn, level, reason, pos, randomIn);
	}
}
