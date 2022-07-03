package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

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

    public WaterMimicEntity(Level worldIn)
    {
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
        this.goalSelector.addGoal(6, new WaterMimicEntity.SwimUpGoal(this, 1.0D, this.level.getSeaLevel()));
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
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

    private int getRandomMimicType(LevelAccessor world) {
        if(random.nextBoolean()) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isPushedByFluid() {
        return !this.isSwimming();
    }

    public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random randomIn) {
        Holder<Biome> biomeHolder = worldIn.getBiome(pos);
        boolean lvt_6_1_ = worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn) && (reason == MobSpawnType.SPAWNER || worldIn.getFluidState(pos).is(FluidTags.WATER));
        if (!(Biome.getBiomeCategory(biomeHolder) == BiomeCategory.RIVER)) {
            return randomIn.nextInt(40) == 0 && isUnderSeaLevel(worldIn, pos) && lvt_6_1_;
        } else {
            return randomIn.nextInt(15) == 0 && lvt_6_1_;
        }
    }

    public static boolean isValidLightLevel(LevelAccessor world, BlockPos pos, Random random) {
        if (world.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int lvt_3_1_ = world.getLevelData().isThundering() ? world.getMaxLocalRawBrightness(pos, 10) : world.getMaxLocalRawBrightness(pos);
            return lvt_3_1_ <= random.nextInt(8);
        }
    }

    private static boolean isUnderSeaLevel(LevelAccessor world, BlockPos pos) {
        return pos.getY() < world.getSeaLevel() - 5;
    }

    public void updateSwimming() {
        if (!this.level.isClientSide) {
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
                double lvt_3_1_ = this.distanceToSqr((double)targetPos.getX(), (double)targetPos.getY(), (double)targetPos.getZ());
                if (lvt_3_1_ < 4.0D) {
                    return true;
                }
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
            LivingEntity lvt_1_1_ = this.mimic.getTarget();
            if (this.mimic.wantsToSwim() && this.mimic.isInWater()) {
                if (lvt_1_1_ != null && lvt_1_1_.getY() > this.mimic.getY() || this.mimic.swimmingUp) {
                    this.mimic.setDeltaMovement(this.mimic.getDeltaMovement().add(0.0D, 0.002D, 0.0D));
                }

                if (this.operation != Operation.MOVE_TO || this.mimic.getNavigation().isDone()) {
                    this.mimic.setSpeed(0.0F);
                    return;
                }

                double lvt_2_1_ = this.wantedX - this.mimic.getX();
                double lvt_4_1_ = this.wantedY - this.mimic.getY();
                double lvt_6_1_ = this.wantedZ - this.mimic.getZ();
                double lvt_8_1_ = Math.sqrt(lvt_2_1_ * lvt_2_1_ + lvt_4_1_ * lvt_4_1_ + lvt_6_1_ * lvt_6_1_);
                lvt_4_1_ /= lvt_8_1_;
                float lvt_10_1_ = (float)(Mth.atan2(lvt_6_1_, lvt_2_1_) * 57.2957763671875D) - 90.0F;
                this.mimic.setYRot(this.rotlerp(this.mimic.getYRot(), lvt_10_1_, 90.0F));
                this.mimic.yBodyRot = this.mimic.getYRot();
                float lvt_11_1_ = (float)(this.speedModifier * this.mimic.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                float lvt_12_1_ = Mth.lerp(0.125F, this.mimic.getSpeed(), lvt_11_1_);
                this.mimic.setSpeed(lvt_12_1_);
                this.mimic.setDeltaMovement(this.mimic.getDeltaMovement().add((double)lvt_12_1_ * lvt_2_1_ * 0.005D, (double)lvt_12_1_ * lvt_4_1_ * 0.1D, (double)lvt_12_1_ * lvt_6_1_ * 0.005D));
            } else {
                if (!this.mimic.onGround) {
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

        public GoToWaterGoal(PathfinderMob p_i48910_1_, double p_i48910_2_) {
            this.mob = p_i48910_1_;
            this.speedModifier = p_i48910_2_;
            this.level = p_i48910_1_.level;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!this.level.isDay()) {
                return false;
            } else if (this.mob.isInWater()) {
                return false;
            } else {
                Vec3 lvt_1_1_ = this.getWaterPos();
                if (lvt_1_1_ == null) {
                    return false;
                } else {
                    this.wantedX = lvt_1_1_.x;
                    this.wantedY = lvt_1_1_.y;
                    this.wantedZ = lvt_1_1_.z;
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
            Random lvt_1_1_ = this.mob.getRandom();
            BlockPos lvt_2_1_ = new BlockPos(this.mob.getX(), this.mob.getBoundingBox().minY, this.mob.getZ());

            for(int lvt_3_1_ = 0; lvt_3_1_ < 10; ++lvt_3_1_) {
                BlockPos lvt_4_1_ = lvt_2_1_.offset(lvt_1_1_.nextInt(20) - 10, 2 - lvt_1_1_.nextInt(8), lvt_1_1_.nextInt(20) - 10);
                if (this.level.getBlockState(lvt_4_1_).getBlock() == Blocks.WATER) {
                    return new Vec3((double)lvt_4_1_.getX(), (double)lvt_4_1_.getY(), (double)lvt_4_1_.getZ());
                }
            }

            return null;
        }
    }

    static class SwimUpGoal extends Goal {
        private final WaterMimicEntity drowned;
        private final double speedModifier;
        private final int targetY;
        private boolean obstructed;

        public SwimUpGoal(WaterMimicEntity p_i48908_1_, double p_i48908_2_, int p_i48908_4_) {
            this.drowned = p_i48908_1_;
            this.speedModifier = p_i48908_2_;
            this.targetY = p_i48908_4_;
        }

        public boolean canUse() {
            return !this.drowned.level.isDay() && this.drowned.isInWater() && this.drowned.getY() < (double)(this.targetY - 2);
        }

        public boolean canContinueToUse() {
            return this.canUse() && !this.obstructed;
        }

        public void tick() {
            if (this.drowned.getY() < (double)(this.targetY - 1) && (this.drowned.getNavigation().isDone() || this.drowned.isCloseToPathTarget())) {
                Vec3 vec3 = DefaultRandomPos.getPosTowards(this.drowned, 4, 8, new Vec3(this.drowned.getX(), (double)(this.targetY - 1), this.drowned.getZ()), (double)((float)Math.PI / 2F));
                if (vec3 == null) {
                    this.obstructed = true;
                    return;
                }

                this.drowned.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, this.speedModifier);
            }

        }

        public void start() {
            this.drowned.setSwimmingUp(true);
            this.obstructed = false;
        }

        public void stop() {
            this.drowned.setSwimmingUp(false);
        }
    }
}
