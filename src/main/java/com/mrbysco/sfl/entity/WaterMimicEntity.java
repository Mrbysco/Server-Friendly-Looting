package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.ModEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class WaterMimicEntity extends AbstractMimicEntity {
    protected final SwimmerPathNavigator waterNavigator;
    protected final GroundPathNavigator groundNavigator;
    private boolean swimmingUp;

    public WaterMimicEntity(EntityType<? extends WaterMimicEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveController = new WaterMimicEntity.MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.waterNavigator = new SwimmerPathNavigator(this, worldIn);
        this.groundNavigator = new GroundPathNavigator(this, worldIn);
    }

    public WaterMimicEntity(World worldIn)
    {
        super(ModEntities.NETHER_MIMIC, worldIn);
        this.moveController = new WaterMimicEntity.MoveHelperController(this);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.waterNavigator = new SwimmerPathNavigator(this, worldIn);
        this.groundNavigator = new GroundPathNavigator(this, worldIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new WaterMimicEntity.GoToWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterMimicEntity.SwimUpGoal(this, 1.0D, this.world.getSeaLevel()));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.25F);
    }

    private int getRandomMimicType(IWorld world) {
        if(rand.nextBoolean()) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isPushedByWater() {
        return !this.isSwimming();
    }

    public static boolean spawnPredicate(EntityType<? extends AbstractMimicEntity> typeIn, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
        Biome biome = worldIn.getBiome(pos);
        boolean lvt_6_1_ = worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn) && (reason == SpawnReason.SPAWNER || worldIn.getFluidState(pos).isTagged(FluidTags.WATER));
        if (biome != Biomes.RIVER && biome != Biomes.FROZEN_RIVER) {
            return randomIn.nextInt(40) == 0 && isUnderSeaLevel(worldIn, pos) && lvt_6_1_;
        } else {
            return randomIn.nextInt(15) == 0 && lvt_6_1_;
        }
    }

    public static boolean isValidLightLevel(IWorld p_223323_0_, BlockPos p_223323_1_, Random p_223323_2_) {
        if (p_223323_0_.getLightFor(LightType.SKY, p_223323_1_) > p_223323_2_.nextInt(32)) {
            return false;
        } else {
            int lvt_3_1_ = p_223323_0_.getWorld().isThundering() ? p_223323_0_.getNeighborAwareLightSubtracted(p_223323_1_, 10) : p_223323_0_.getLight(p_223323_1_);
            return lvt_3_1_ <= p_223323_2_.nextInt(8);
        }
    }

    private static boolean isUnderSeaLevel(IWorld p_223333_0_, BlockPos p_223333_1_) {
        return p_223333_1_.getY() < p_223333_0_.getSeaLevel() - 5;
    }

    public void updateSwimming() {
        if (!this.world.isRemote) {
            if (this.isServerWorld() && this.isInWater() && this.func_204715_dF()) {
                this.navigator = this.waterNavigator;
                this.setSwimming(true);
            } else {
                this.navigator = this.groundNavigator;
                this.setSwimming(false);
            }
        }

    }

    protected boolean isCloseToPathTarget() {
        Path lvt_1_1_ = this.getNavigator().getPath();
        if (lvt_1_1_ != null) {
            BlockPos lvt_2_1_ = lvt_1_1_.func_224770_k();
            if (lvt_2_1_ != null) {
                double lvt_3_1_ = this.getDistanceSq((double)lvt_2_1_.getX(), (double)lvt_2_1_.getY(), (double)lvt_2_1_.getZ());
                if (lvt_3_1_ < 4.0D) {
                    return true;
                }
            }
        }

        return false;
    }

    public void travel(Vec3d p_213352_1_) {
        if (this.isServerWorld() && this.isInWater() && this.func_204715_dF()) {
            this.moveRelative(0.01F, p_213352_1_);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
        } else {
            super.travel(p_213352_1_);
        }
    }

    public void setSwimmingUp(boolean p_204713_1_) {
        this.swimmingUp = p_204713_1_;
    }

    private boolean func_204715_dF() {
        if (this.swimmingUp) {
            return true;
        } else {
            LivingEntity lvt_1_1_ = this.getAttackTarget();
            return lvt_1_1_ != null && lvt_1_1_.isInWater();
        }
    }

    static class MoveHelperController extends MovementController {
        private final WaterMimicEntity mimic;

        public MoveHelperController(WaterMimicEntity p_i48909_1_) {
            super(p_i48909_1_);
            this.mimic = p_i48909_1_;
        }

        public void tick() {
            LivingEntity lvt_1_1_ = this.mimic.getAttackTarget();
            if (this.mimic.func_204715_dF() && this.mimic.isInWater()) {
                if (lvt_1_1_ != null && lvt_1_1_.posY > this.mimic.posY || this.mimic.swimmingUp) {
                    this.mimic.setMotion(this.mimic.getMotion().add(0.0D, 0.002D, 0.0D));
                }

                if (this.action != Action.MOVE_TO || this.mimic.getNavigator().noPath()) {
                    this.mimic.setAIMoveSpeed(0.0F);
                    return;
                }

                double lvt_2_1_ = this.posX - this.mimic.posX;
                double lvt_4_1_ = this.posY - this.mimic.posY;
                double lvt_6_1_ = this.posZ - this.mimic.posZ;
                double lvt_8_1_ = (double) MathHelper.sqrt(lvt_2_1_ * lvt_2_1_ + lvt_4_1_ * lvt_4_1_ + lvt_6_1_ * lvt_6_1_);
                lvt_4_1_ /= lvt_8_1_;
                float lvt_10_1_ = (float)(MathHelper.atan2(lvt_6_1_, lvt_2_1_) * 57.2957763671875D) - 90.0F;
                this.mimic.rotationYaw = this.limitAngle(this.mimic.rotationYaw, lvt_10_1_, 90.0F);
                this.mimic.renderYawOffset = this.mimic.rotationYaw;
                float lvt_11_1_ = (float)(this.speed * this.mimic.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                float lvt_12_1_ = MathHelper.lerp(0.125F, this.mimic.getAIMoveSpeed(), lvt_11_1_);
                this.mimic.setAIMoveSpeed(lvt_12_1_);
                this.mimic.setMotion(this.mimic.getMotion().add((double)lvt_12_1_ * lvt_2_1_ * 0.005D, (double)lvt_12_1_ * lvt_4_1_ * 0.1D, (double)lvt_12_1_ * lvt_6_1_ * 0.005D));
            } else {
                if (!this.mimic.onGround) {
                    this.mimic.setMotion(this.mimic.getMotion().add(0.0D, -0.008D, 0.0D));
                }

                super.tick();
            }

        }
    }

    static class GoToWaterGoal extends Goal {
        private final CreatureEntity field_204730_a;
        private double field_204731_b;
        private double field_204732_c;
        private double field_204733_d;
        private final double field_204734_e;
        private final World field_204735_f;

        public GoToWaterGoal(CreatureEntity p_i48910_1_, double p_i48910_2_) {
            this.field_204730_a = p_i48910_1_;
            this.field_204734_e = p_i48910_2_;
            this.field_204735_f = p_i48910_1_.world;
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean shouldExecute() {
            if (!this.field_204735_f.isDaytime()) {
                return false;
            } else if (this.field_204730_a.isInWater()) {
                return false;
            } else {
                Vec3d lvt_1_1_ = this.func_204729_f();
                if (lvt_1_1_ == null) {
                    return false;
                } else {
                    this.field_204731_b = lvt_1_1_.x;
                    this.field_204732_c = lvt_1_1_.y;
                    this.field_204733_d = lvt_1_1_.z;
                    return true;
                }
            }
        }

        public boolean shouldContinueExecuting() {
            return !this.field_204730_a.getNavigator().noPath();
        }

        public void startExecuting() {
            this.field_204730_a.getNavigator().tryMoveToXYZ(this.field_204731_b, this.field_204732_c, this.field_204733_d, this.field_204734_e);
        }

        @Nullable
        private Vec3d func_204729_f() {
            Random lvt_1_1_ = this.field_204730_a.getRNG();
            BlockPos lvt_2_1_ = new BlockPos(this.field_204730_a.posX, this.field_204730_a.getBoundingBox().minY, this.field_204730_a.posZ);

            for(int lvt_3_1_ = 0; lvt_3_1_ < 10; ++lvt_3_1_) {
                BlockPos lvt_4_1_ = lvt_2_1_.add(lvt_1_1_.nextInt(20) - 10, 2 - lvt_1_1_.nextInt(8), lvt_1_1_.nextInt(20) - 10);
                if (this.field_204735_f.getBlockState(lvt_4_1_).getBlock() == Blocks.WATER) {
                    return new Vec3d((double)lvt_4_1_.getX(), (double)lvt_4_1_.getY(), (double)lvt_4_1_.getZ());
                }
            }

            return null;
        }
    }

    static class SwimUpGoal extends Goal {
        private final WaterMimicEntity field_204736_a;
        private final double field_204737_b;
        private final int targetY;
        private boolean obstructed;

        public SwimUpGoal(WaterMimicEntity p_i48908_1_, double p_i48908_2_, int p_i48908_4_) {
            this.field_204736_a = p_i48908_1_;
            this.field_204737_b = p_i48908_2_;
            this.targetY = p_i48908_4_;
        }

        public boolean shouldExecute() {
            return !this.field_204736_a.world.isDaytime() && this.field_204736_a.isInWater() && this.field_204736_a.posY < (double)(this.targetY - 2);
        }

        public boolean shouldContinueExecuting() {
            return this.shouldExecute() && !this.obstructed;
        }

        public void tick() {
            if (this.field_204736_a.posY < (double)(this.targetY - 1) && (this.field_204736_a.getNavigator().noPath() || this.field_204736_a.isCloseToPathTarget())) {
                Vec3d lvt_1_1_ = RandomPositionGenerator.findRandomTargetBlockTowards(this.field_204736_a, 4, 8, new Vec3d(this.field_204736_a.posX, (double)(this.targetY - 1), this.field_204736_a.posZ));
                if (lvt_1_1_ == null) {
                    this.obstructed = true;
                    return;
                }

                this.field_204736_a.getNavigator().tryMoveToXYZ(lvt_1_1_.x, lvt_1_1_.y, lvt_1_1_.z, this.field_204737_b);
            }

        }

        public void startExecuting() {
            this.field_204736_a.setSwimmingUp(true);
            this.obstructed = false;
        }

        public void resetTask() {
            this.field_204736_a.setSwimmingUp(false);
        }
    }
}
