package net.fabricmc.example.entities.SkeletonEntity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MySkeletonEntityModel extends EntityModel<MySkeletonEntity> {

    private ModelPart base;

    private float sizeMultiplier = 2;
    private float size = 6;

    public MySkeletonEntityModel(){
        this.textureHeight = 64;
        this.textureWidth = 64;

        float upper = size*sizeMultiplier*2;
        float lower = -(size*sizeMultiplier);

        base = new ModelPart(this,0,0);
        base.addCuboid(lower, lower, lower, upper, upper, upper);
    }

    @Override
    public void setAngles(MySkeletonEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

        matrices.translate(0, -1.125, 0);

        base.render(matrices, vertices, light, overlay);
    }

    
}
