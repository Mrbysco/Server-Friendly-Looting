package com.mrbysco.sfl.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

import javax.annotation.Nullable;
import java.util.UUID;

public class EndMimicEntity extends AbstractMimicEntity {
	private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("03D531C2-DD68-431A-AFB5-8F79AD6990CB");
	private static final AttributeModifier ATTACKING_SPEED_BOOST = new AttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", (double) 0.15F, AttributeModifier.Operation.ADDITION);

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

	protected void customServerAiStep() {
		if (this.level().isDay() && this.tickCount >= this.targetChangeTime + 600) {
			float f = this.getLightLevelDependentMagicValue();
			if (f > 0.5F && this.level().canSeeSky(blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setTarget((LivingEntity) null);
				this.teleportRandomly();
			}
		}

		super.customServerAiStep();
	}

	protected boolean teleportRandomly() {
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

	public void setTarget(@Nullable LivingEntity livingEntity) {
		AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (attributeInstance == null) return;
		if (livingEntity == null) {
			this.targetChangeTime = 0;
			attributeInstance.removeModifier(ATTACKING_SPEED_BOOST_ID);
		} else {
			this.targetChangeTime = this.tickCount;
			if (!attributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
				attributeInstance.addTransientModifier(ATTACKING_SPEED_BOOST);
			}
		}

		super.setTarget(livingEntity); //Forge: Moved down to allow event handlers to write data manager values.
	}

	public void aiStep() {
		if (this.level().isClientSide) {
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

	public boolean hurt(DamageSource source, float amount) {
		if (this.isInvulnerableTo(source)) {
			return false;
		} else if (!source.isIndirect()) {
			boolean flag = super.hurt(source, amount);
			if (!source.is(DamageTypeTags.BYPASSES_ARMOR) && this.random.nextInt(10) != 0) {
				this.teleportRandomly();
			}

			return flag;
		} else {
			for (int i = 0; i < 64; ++i) {
				if (this.teleportRandomly()) {
					return true;
				}
			}

			return false;
		}
	}
}
