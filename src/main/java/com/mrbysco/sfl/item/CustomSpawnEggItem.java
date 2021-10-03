package com.mrbysco.sfl.item;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class CustomSpawnEggItem extends SpawnEggItem {
    public final Supplier<EntityType<? extends Mob>> entityType;

    private static final DefaultDispenseItemBehavior SPAWN_EGG_BEHAVIOR = new DefaultDispenseItemBehavior() {
        public ItemStack execute(BlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().getValue(BlockStateProperties.FACING);
            ((CustomSpawnEggItem) stack.getItem()).getType(stack.getTag()).spawn(source.getLevel(), stack, null,
                    source.getPos().relative(direction), MobSpawnType.DISPENSER, direction != Direction.UP, false);
            stack.shrink(1);
            return stack;
        }
    };

    public CustomSpawnEggItem(Supplier<EntityType<? extends Mob>> type, int primary, int secondary, Properties properties) {
        super(null, primary, secondary, properties);
        this.entityType = type;
        DispenserBlock.registerBehavior(this, SPAWN_EGG_BEHAVIOR);
    }

    @Override
    public EntityType<?> getType(@Nullable final CompoundTag nbt) {
        if (nbt != null && nbt.contains("EntityTag", Constants.NBT.TAG_COMPOUND)) {
            final CompoundTag entityTag = nbt.getCompound("EntityTag");
            if (entityTag.contains("id", Constants.NBT.TAG_STRING)) {
                return EntityType.byString(entityTag.getString("id")).orElse(entityType.get());
            }
        }

        return entityType.get();
    }
}