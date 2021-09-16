package net.fabricmc.example.entities.SkeletonEntity;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.util.Identifier;

public class MySkeletonEntityRenderer extends BipedEntityRenderer<MySkeletonEntity, SkeletonEntityModel<MySkeletonEntity>> {

    public MySkeletonEntityRenderer(EntityRenderDispatcher erd){
        super(erd, new SkeletonEntityModel<>(), .5f);
    }

    @Override
    public Identifier getTexture(MySkeletonEntity entity) {
        return new Identifier("modid","textures/entities/my_skeleton_entity.png");
    }

}
