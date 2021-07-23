package com.minecraftabnormals.abnormals_core.common.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Direction;

public final class SpawnEggDispenseBehavior extends DefaultDispenseItemBehavior {

	@Override
	public ItemStack execute(IBlockSource source, ItemStack stack) {
		Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
		EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
		entitytype.spawn(source.getLevel(), stack, (PlayerEntity) null, source.getPos().relative(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
		stack.shrink(1);
		return stack;
	}

}
