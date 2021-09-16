package net.fabricmc.example.items;

import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EggTest extends SpawnEggItem {

    public EggTest(EntityType<?> type, int primaryColor, int secondaryColor, Settings settings) {
        super(type, primaryColor, secondaryColor, settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);

		if (hitResult.getType() != HitResult.Type.BLOCK)return TypedActionResult.pass(itemStack);
		else if (!(world instanceof ServerWorld))return TypedActionResult.success(itemStack);
		else {

			BlockHitResult blockHitResult = (BlockHitResult)hitResult;
			BlockPos blockPos = blockHitResult.getBlockPos();

			if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) return TypedActionResult.pass(itemStack);
			else if (world.canPlayerModifyAt(user, blockPos) && user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {

				EntityType<?> entityType = this.getEntityType(itemStack.getTag()); 
                Entity entity = entityType.spawnFromItemStack((ServerWorld)world, itemStack, user, blockPos, SpawnReason.SPAWN_EGG, false, false);
            
				if ( entity == null) return TypedActionResult.pass(itemStack);
				else {
					if (!user.abilities.creativeMode) {
						itemStack.decrement(1);
					}
                    //entity.yaw = 0f;
					user.incrementStat(Stats.USED.getOrCreateStat(this));
					return TypedActionResult.consume(itemStack);
				}
			} else {
				return TypedActionResult.fail(itemStack);
			}
		}
	}
}
