package net.fabricmc.example.entities.ProjectileEntity;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.client.ExampleModClient;
import net.fabricmc.example.entities.TestEntity.TestEntity;
import net.fabricmc.example.register.ItemRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ProjectileEntity extends ThrownItemEntity {

    public ProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
 
	public ProjectileEntity(World world, LivingEntity owner) {
		super(ExampleMod.MY_PROJECTILE, owner, world); // null will be changed later
	}

    public ProjectileEntity(World world, TestEntity owner) {
		super(ExampleMod.MY_PROJECTILE, owner, world); // null will be changed later
	}
 
	public ProjectileEntity(World world, double x, double y, double z) {
		super(ExampleMod.MY_PROJECTILE, x, y, z, world); // null will be changed later
	}

    @Override
    public Packet<?> createSpawnPacket() {
        return EntitySpawnPacket.create(this, ExampleModClient.PacketID);
    }




    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegister.MY_PROJECTILE;
    }
    
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity();
        float damage = 3;

        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);

        if(entity instanceof LivingEntity ){ //check if entity is not a boat or minecart
            ((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 0)));
            entity.playSound(SoundEvents.AMBIENT_CAVE, 2f, 1f);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        
        if(!this.world.isClient){
            this.world.sendEntityStatus(this, (byte)3);
            this.remove();
        }

        super.onCollision(hitResult);
    }
}
