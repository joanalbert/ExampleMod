package net.fabricmc.example.entities.TestEntity;


import java.util.List;

import net.fabricmc.example.ExampleMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class TestEntityModel extends AnimatedGeoModel<TestEntity>{

    private BoneSnapshot headSnapshot;
    private Boolean shouldLerp = false;
    public static IBone HEAD;

    @Override
    public Identifier getModelLocation(TestEntity object) {
        return new Identifier(ExampleMod.MODID, "geo/test.geo.json");
    }

    @Override
    public Identifier getTextureLocation(TestEntity object) {
        return new Identifier(ExampleMod.MODID, "textures/entities/test_entity.png");
    }

    @Override
    public Identifier getAnimationFileLocation(TestEntity animatable) {
        return new Identifier(ExampleMod.MODID, "animations/test.animation.json");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void setLivingAnimations(TestEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        IBone head = getAnimationProcessor().getBone("turret_head"); 
        HEAD = head;
               
        if(this.headSnapshot != null){

            List<Entity> targets = entity.world.getEntitiesByClass(MobEntity.class, ExampleMod.TEST_ENTITY.getDimensions().getBoxAt(entity.getPos()).expand(TestEntity.TURRET_RANGE), null);
            TestEntity.target = getClosestTarget(targets, entity);
            
            if(TestEntity.target != null){
                double distance = TestEntity.target.getPos().distanceTo(entity.getPos());
                if(distance <= TestEntity.TURRET_RANGE && TestEntity.target.isAlive()){
                    System.out.println("TARGET: "+TestEntity.target.getDisplayName().getString()+": "+distance);
                    lookat2(TestEntity.target, head, entity);
                }
                else System.out.println("no target");
            }
            else System.out.println("no target");
        }
        
        this.headSnapshot = head.saveSnapshot();
    }

    private void lookat2(Entity player, IBone head, TestEntity turret){
        Vec3d playerPos = player.getPos();
        Vec3d headPos = new Vec3d(turret.getPos().x, turret.getPos().y + head.getPivotY()/16, turret.getPos().z);

        double xdist = playerPos.x - headPos.x;
        double ydist = playerPos.y - headPos.y;
        double zdist = playerPos.z - headPos.z;

        double xzdist = Math.sqrt(xdist*xdist + zdist*zdist);
  
        head.setRotationY((float) Math.atan2(xdist, zdist));
        head.setRotationX((float) Math.atan2(ydist, xzdist));
        head.setRotationZ(0);
    }

    private Entity getClosestTarget(List<Entity> targets, Entity entity){
        double minDist = Integer.MAX_VALUE;
        Entity closest = null;

        for (Entity target : targets) {
            double dist = target.getPos().squaredDistanceTo(entity.getPos());
            if(dist <= minDist && !(target instanceof TestEntity) && target.isAlive()){
                minDist = dist;
                closest = target;
            }
        }

        return closest;
    }

   
    private double lerp(float delta, float start, float end) {
		return start + delta * (end - start);
	}

}
