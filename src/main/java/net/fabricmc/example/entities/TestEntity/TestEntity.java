package net.fabricmc.example.entities.TestEntity;



import net.minecraft.entity.EntityType;

import java.util.List;
import net.fabricmc.example.ExampleMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.processor.IBone;
import net.minecraft.server.world.ServerWorld;


/*
 * Our TestEntity extends PathAwareEntity, which extends MobEntity, which extends LivingEntity.
 * 
 * LlivingEntity has health and can deal damage
 * MobEntity has movement controls and AI capabilities
 * PathAwareEntity has pathfinding favor and slightly tweaked leash behavior.
*/
public class TestEntity extends PathAwareEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this); // <- this is needed for geckolib
    public static final float TURRET_RANGE = 15f;
    public static Entity target;

    IBone head = null;

    public TestEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canMoveVoluntarily() {
        return true;
    }

    //so that the entity doesn't get randomly rotated upon egg spawn
    @Override
    public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        super.refreshPositionAndAngles(x, y, z, 0f, 0f);
    }
          

    //METHODS FOR ANIMATION////////////////////////////
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event){   
        if(target == null){
            event.getController().setAnimation(new AnimationBuilder().clearAnimations());
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));      
        }    
        else{
            event.getController().setAnimation(new AnimationBuilder().clearAnimations());
            event.getController().setAnimation(new AnimationBuilder().addAnimation("shoot"));
        }           

        if(target != null) shoot(this);

        return PlayState.CONTINUE;
    }


    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));        
    }
    ///////////////////////////////////////////////////

    public void shoot(Entity turret){
        Boolean shot = false;
    
        if(this.world.isClient && head == null){
            head = TestEntityModel.HEAD;
            return;
        }

        if(!this.world.isClient && head != null){
            FireballEntity myProjectile = new FireballEntity(EntityType.FIREBALL, turret.world);
            myProjectile.setProperties(turret, head.getRotationX(), head.getRotationY(), 0f, 1.5f, 0f);
            myProjectile.setPosition(head.getPositionX(), head.getPositionY(), head.getPositionZ());
            shot = ((ServerWorld)this.world).spawnEntity(myProjectile);
            System.out.println("server");
        }
        System.out.println(shot);
    }

   

    
    
    private Vec3d directionLook(double d, double e){
        double radYaw   = d / 180 * Math.PI;
        double radPitch = e / 180 * Math.PI;

        double x = -Math.sin(radYaw) * Math.cos(radPitch);
        double y = -Math.sin(radPitch);
        double z =  Math.cos(radYaw) * Math.cos(radPitch);
        
        return new Vec3d(x, y, z).normalize();
    }
}
