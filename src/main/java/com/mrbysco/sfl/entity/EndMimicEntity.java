package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.ServerFriendlyLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

import javax.annotation.Nullable;

public class EndMimicEntity extends AbstractMimicEntity {
	private static final Identifier SPEED_MODIFIER_ATTACKING_ID = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "attacking");
	private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(
			SPEED_MODIFIER_ATTACKING_ID, 0.15F, AttributeModifier.Operation.ADD_VALUE
	);
	private int targetChangeTime;

	public EndMimicEntity(EntityType<? extends EndMimicEntity> type, Level level) {
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
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 8.0D)
				.add(Attributes.MOVEMENT_SPEED, (double) 0.275F);
	}

	@Override
	protected void customServerAiStep(ServerLevel serverLevel) {
		if (this.level().isBrightOutside() && this.tickCount >= this.targetChangeTime + 600) {
			float f = this.getLightLevelDependentMagicValue();
			if (f > 0.5F && this.level().canSeeSky(blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
				this.teleport();
			}
		}

		super.customServerAiStep(serverLevel);
	}

	protected boolean teleport() {
		double d0 = this.getX() + (this.random.nextDouble() - 0.5D) * 64.0D;
		double d1 = this.getY() + (double) (this.random.nextInt(64) - 32);
		double d2 = this.getZ() + (this.random.nextDouble() - 0.5D) * 64.0D;
		return this.teleport(d0, d1, d2);
	}

	private boolean teleport(double x, double y, double z) {
		BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos(x, y, z);

		while (blockPos.getY() > 0 && !this.level().getBlockState(blockPos).blocksMotion()) {
			blockPos.move(Direction.DOWN);
		}

		if (!this.level().getBlockState(blockPos).blocksMotion()) {
			return false;
		} else {
			EntityTeleportEvent.EnderEntity event = EventHooks.onEnderTeleport(this, x, y, z);
			if (event.isCanceled()) return false;
			boolean flag = this.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
			if (flag) {
				this.level().playSound((Player) null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT,
						this.getSoundSource(), 1.0F, 1.0F);
				this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
			}

			return flag;
		}
	}

	@Override
	public void setTarget(@Nullable LivingEntity livingEntity) {
		AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (attributeInstance == null) return;
		if (livingEntity == null) {
			this.targetChangeTime = 0;
			attributeInstance.removeModifier(SPEED_MODIFIER_ATTACKING_ID);
		} else {
			this.targetChangeTime = this.tickCount;
			if (!attributeInstance.hasModifier(SPEED_MODIFIER_ATTACKING_ID)) {
				attributeInstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
			}
		}

		super.setTarget(livingEntity); //Forge: Moved down to allow event handlers to write data manager values.
	}

	@Override
	public void aiStep() {
		if (this.level().isClientSide()) {
			for (int i = 0; i < 2; ++i) {
				this.level().addParticle(ParticleTypes.PORTAL,
						this.getX() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(),
						this.getY() + this.random.nextDouble() * (double) this.getBbHeight() - 0.25D,
						this.getZ() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(),
						(this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(),
						(this.random.nextDouble() - 0.5D) * 2.0D);
			}
		}

		this.jumping = false;
		super.aiStep();
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		if (this.isInvulnerableTo(serverLevel, source)) {
			return false;
		} else {
			AbstractThrownPotion abstractthrownpotion = source.getDirectEntity() instanceof AbstractThrownPotion abstractthrownpotion1
					? abstractthrownpotion1
					: null;
			if (!source.is(DamageTypeTags.IS_PROJECTILE) && abstractthrownpotion == null) {
				boolean flag2 = super.hurtServer(serverLevel, source, amount);
				if (!(source.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
					this.teleport();
				}

				return flag2;
			} else {
				boolean flag = abstractthrownpotion != null && this.hurtWithCleanWater(serverLevel, source, abstractthrownpotion, amount);

				for (int i = 0; i < 64; i++) {
					if (this.teleport()) {
						return true;
					}
				}

				return flag;
			}
		}
	}

	private boolean hurtWithCleanWater(ServerLevel level, DamageSource damageSource, AbstractThrownPotion potion, float damageAmount) {
		ItemStack itemstack = potion.getItem();
		PotionContents potioncontents = itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
		return potioncontents.is(Potions.WATER) ? super.hurtServer(level, damageSource, damageAmount) : false;
	}
}
