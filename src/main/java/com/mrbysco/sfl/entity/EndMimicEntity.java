package com.mrbysco.sfl.entity;

import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class EndMimicEntity extends AbstractMimicEntity {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("03D531C2-DD68-431A-AFB5-8F79AD6990CB");
    private static final AttributeModifier ATTACKING_SPEED_BOOST = (new AttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", (double)0.15F, AttributeModifier.Operation.ADDITION)).setSaved(false);

    private int targetChangeTime;

    public EndMimicEntity(EntityType<? extends EndMimicEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EndMimicEntity(World worldIn)
    {
        super(MimicRegistry.END_MIMIC.get(), worldIn);
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
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.275F);
    }

    protected void updateAITasks() {
        if (this.world.isDaytime() && this.ticksExisted >= this.targetChangeTime + 600) {
            float f = this.getBrightness();
            if (f > 0.5F && this.world.canSeeSky(new BlockPos(this)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.setAttackTarget((LivingEntity)null);
                this.teleportRandomly();
            }
        }

        super.updateAITasks();
    }

    protected boolean teleportRandomly() {
        double d0 = this.getPosX() + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.getPosY() + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.getPosZ() + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(d0, d1, d2);
    }

    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable(x, y, z);

        while(blockpos$mutableblockpos.getY() > 0 && !this.world.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMovement()) {
            blockpos$mutableblockpos.move(Direction.DOWN);
        }

        if (!this.world.getBlockState(blockpos$mutableblockpos).getMaterial().blocksMovement()) {
            return false;
        } else {
            net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
            boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag) {
                this.world.playSound((PlayerEntity)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag;
        }
    }

    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        IAttributeInstance iattributeinstance = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (entitylivingbaseIn == null) {
            this.targetChangeTime = 0;
            iattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
        } else {
            this.targetChangeTime = this.ticksExisted;
            if (!iattributeinstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                iattributeinstance.applyModifier(ATTACKING_SPEED_BOOST);
            }
        }

        super.setAttackTarget(entitylivingbaseIn); //Forge: Moved down to allow event handlers to write data manager values.
    }

    public void livingTick() {
        if (this.world.isRemote) {
            for(int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.PORTAL, this.getPosX() + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth(), this.getPosY() + this.rand.nextDouble() * (double)this.getHeight() - 0.25D, this.getPosZ() + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth(), (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }

        this.isJumping = false;
        super.livingTick();
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!(source instanceof IndirectEntityDamageSource) && source != DamageSource.FIREWORKS) {
            boolean flag = super.attackEntityFrom(source, amount);
            if (source.isUnblockable() && this.rand.nextInt(10) != 0) {
                this.teleportRandomly();
            }

            return flag;
        } else {
            for(int i = 0; i < 64; ++i) {
                if (this.teleportRandomly()) {
                    return true;
                }
            }

            return false;
        }
    }
}
