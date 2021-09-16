package net.fabricmc.example.entities.SkeletonEntity;


import net.fabricmc.example.ExampleMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.DefaultAttributeContainer.Builder;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class MySkeletonEntity extends SkeletonEntity{

    public boolean skeletonCanSpawn = true;

    public MySkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 1;
    }

    public static Builder createsKeletonAttributes(){
        return HostileEntity.createHostileAttributes()
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 3f);
    }

    @Override
    public boolean canSpawn(WorldView view){
        BlockPos blockUnderEntity = new BlockPos(this.getX(),this.getY()-1,this.getZ());
        //BlockPos positionEntity = new BlockPos(this.getX(),this.getY(),this.getZ());

        return view.intersectsEntities(this) && this.world.getBlockState(blockUnderEntity).allowsSpawning(view, blockUnderEntity, ExampleMod.MY_SKELETON);
    }
}
