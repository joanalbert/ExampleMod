package net.fabricmc.example.items;


import net.fabricmc.example.entities.ProjectileEntity.ProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ProjectileItem extends Item {

    public ProjectileItem(Settings settings) {
        super(settings);
    }

    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, .5f, 1f);

        user.getItemCooldownManager().set(this, 1);

        if(!world.isClient){
            ProjectileEntity myProjectile = new ProjectileEntity(world, user);
            myProjectile.setItem(itemStack);
            myProjectile.setProperties(user, user.pitch, user.yaw, 0f, 1.5f, 0f);
            world.spawnEntity(myProjectile);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if(!user.abilities.creativeMode){
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient);
    }    
}