package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class WaterMimicEntity extends AbstractMimicEntity {
	protected final WaterBoundPathNavigation waterNavigator;
	protected final GroundPathNavigation groundNavigator;
	private boolean swimmingUp;

	public WaterMimicEntity(EntityType<? extends WaterMimicEntity> type, Level worldIn) {
		super(type, worldIn);
		this.moveControl = new WaterMimicEntity.MoveHelperController(this);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.waterNavigator = new WaterBoundPathNavigation(this, worldIn);
		this.groundNavigator = new GroundPathNavigation(this, worldIn);
	}

	public WaterMimicEntity(Level worldIn) {
		super(MimicRegistry.NETHER_MIMIC.get(), worldIn);
		this.moveControl = new WaterMimicEntity.MoveHelperController(this);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		this.waterNavigator = new WaterBoundPathNavigation(this, worldIn);
		this.groundNavigator = new GroundPathNavigation(this, worldIn);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(1, new WaterMimicEntity.GoToWaterGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new WaterMimicEntity.SwimUpGoal(this, 1.0D, this.level().getSeaLevel()));
		this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return AbstractMimicEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.MOVEMENT_SPEED, (double) 0.25F);
	}

	private int getRandomMimicType(LevelAccessor world) {
		if (random.nextBoolean()) {
			return 0;
		} else {
			return 1;
		}
	}

	public boolean isPushedByFluid(FluidType fluidType) {
		return !this.isSwimming();
	}

	public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, ServerLevelAccessor levelAccessor, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
		boolean flag = levelAccessor.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(levelAccessor, pos, randomIn) && (reason == MobSpawnType.SPAWNER || levelAccessor.getFluidState(pos).is(FluidTags.WATER));
		if (!levelAccessor.getBiome(pos).is(BiomeTags.IS_RIVER)) {
			return randomIn.nextInt(40) == 0 && isUnderSeaLevel(levelAccessor, pos) && flag;
		} else {
			return randomIn.nextInt(15) == 0 && flag;
		}
	}

	private static boolean isUnderSeaLevel(LevelAccessor world, BlockPos pos) {
		return pos.getY() < world.getSeaLevel() - 5;
	}

	public void updateSwimming() {
		if (!this.level().isClientSide) {
			if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
				this.navigation = this.waterNavigator;
				this.setSwimming(true);
			} else {
				this.navigation = this.groundNavigator;
				this.setSwimming(false);
			}
		}
	}

	protected boolean isCloseToPathTarget() {
		Path path = this.getNavigation().getPath();
		if (path != null) {
			BlockPos targetPos = path.getTarget();
			if (targetPos != null) {
				double distance = this.distanceToSqr((double) targetPos.getX(), (double) targetPos.getY(), (double) targetPos.getZ());
				return distance < 4.0D;
			}
		}

		return false;
	}

	public void travel(Vec3 p_213352_1_) {
		if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
			this.moveRelative(0.01F, p_213352_1_);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
		} else {
			super.travel(p_213352_1_);
		}
	}

	public void setSwimmingUp(boolean p_204713_1_) {
		this.swimmingUp = p_204713_1_;
	}

	private boolean wantsToSwim() {
		if (this.swimmingUp) {
			return true;
		} else {
			LivingEntity lvt_1_1_ = this.getTarget();
			return lvt_1_1_ != null && lvt_1_1_.isInWater();
		}
	}

	static class MoveHelperController extends MoveControl {
		private final WaterMimicEntity mimic;

		public MoveHelperController(WaterMimicEntity p_i48909_1_) {
			super(p_i48909_1_);
			this.mimic = p_i48909_1_;
		}

		public void tick() {
			LivingEntity target = this.mimic.getTarget();
			if (this.mimic.wantsToSwim() && this.mimic.isInWater()) {
				if (target != null && target.getY() > this.mimic.getY() || this.mimic.swimmingUp) {
					this.mimic.setDeltaMovement(this.mimic.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
				}

				if (this.operation != Operation.MOVE_TO || this.mimic.getNavigation().isDone()) {
					this.mimic.setSpeed(0.0F);
					return;
				}

				double x = this.wantedX - this.mimic.getX();
				double y = this.wantedY - this.mimic.getY();
				double z = this.wantedZ - this.mimic.getZ();
				double sqrt = Math.sqrt(x * x + y * y + z * z);
				y /= sqrt;
				float lvt_10_1_ = (float) (Mth.atan2(z, x) * 57.2957763671875D) - 90.0F;
				this.mimic.setYRot(this.rotlerp(this.mimic.getYRot(), lvt_10_1_, 90.0F));
				this.mimic.yBodyRot = this.mimic.getYRot();
				float speed = (float) (this.speedModifier * this.mimic.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
				float speedLerped = Mth.lerp(0.125F, this.mimic.getSpeed(), speed);
				this.mimic.setSpeed(speedLerped);
				this.mimic.setDeltaMovement(this.mimic.getDeltaMovement().add((double) speedLerped * x * 0.005D, (double) speedLerped * y * 0.1D, (double) speedLerped * z * 0.005D));
			} else {
				if (!this.mimic.onGround()) {
					this.mimic.setDeltaMovement(this.mimic.getDeltaMovement().add(0.0D, -0.008D, 0.0D));
				}

				super.tick();
			}

		}
	}

	static class GoToWaterGoal extends Goal {
		private final PathfinderMob mob;
		private double wantedX;
		private double wantedY;
		private double wantedZ;
		private final double speedModifier;
		private final Level level;

		public GoToWaterGoal(PathfinderMob mob, double speedModifier) {
			this.mob = mob;
			this.speedModifier = speedModifier;
			this.level = mob.level();
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		public boolean canUse() {
			if (!this.level.isDay()) {
				return false;
			} else if (this.mob.isInWater()) {
				return false;
			} else {
				Vec3 waterPos = this.getWaterPos();
				if (waterPos == null) {
					return false;
				} else {
					this.wantedX = waterPos.x;
					this.wantedY = waterPos.y;
					this.wantedZ = waterPos.z;
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return !this.mob.getNavigation().isDone();
		}

		public void start() {
			this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
		}

		@Nullable
		private Vec3 getWaterPos() {
			RandomSource random = this.mob.getRandom();
			BlockPos pos = BlockPos.containing(this.mob.getX(), this.mob.getBoundingBox().minY, this.mob.getZ());

			for (int i = 0; i < 10; ++i) {
				BlockPos offPos = pos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
				if (this.level.getBlockState(offPos).getBlock() == Blocks.WATER) {
					return new Vec3((double) offPos.getX(), (double) offPos.getY(), (double) offPos.getZ());
				}
			}

			return null;
		}
	}

	static class SwimUpGoal extends Goal {
		private final WaterMimicEntity waterMimicEntity;
		private final double speedModifier;
		private final int targetY;
		private boolean obstructed;

		public SwimUpGoal(WaterMimicEntity waterMimic, double speedModifier, int targetY) {
			this.waterMimicEntity = waterMimic;
			this.speedModifier = speedModifier;
			this.targetY = targetY;
		}

		public boolean canUse() {
			return !this.waterMimicEntity.level().isDay() && this.waterMimicEntity.isInWater() && this.waterMimicEntity.getY() < (double) (this.targetY - 2);
		}

		public boolean canContinueToUse() {
			return this.canUse() && !this.obstructed;
		}

		public void tick() {
			if (this.waterMimicEntity.getY() < (double) (this.targetY - 1) && (this.waterMimicEntity.getNavigation().isDone() || this.waterMimicEntity.isCloseToPathTarget())) {
				Vec3 vec3 = DefaultRandomPos.getPosTowards(this.waterMimicEntity, 4, 8, new Vec3(this.waterMimicEntity.getX(), (double) (this.targetY - 1), this.waterMimicEntity.getZ()), (double) ((float) Math.PI / 2F));
				if (vec3 == null) {
					this.obstructed = true;
					return;
				}

				this.waterMimicEntity.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, this.speedModifier);
			}

		}

		public void start() {
			this.waterMimicEntity.setSwimmingUp(true);
			this.obstructed = false;
		}

		public void stop() {
			this.waterMimicEntity.setSwimmingUp(false);
		}
	}
}
