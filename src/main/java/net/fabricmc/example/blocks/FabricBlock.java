package net.fabricmc.example.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FabricBlock extends Block {

    public static final BooleanProperty CHARGED = BooleanProperty.of("charged");

    public FabricBlock() {
        super(FabricBlockSettings.of(Material.WOOD)
                                 .breakByHand(false)
                                 .breakByTool(FabricToolTags.PICKAXES)
                                 .sounds(BlockSoundGroup.WOOD)
                                 .hardness(2f)
                                 .resistance(.2f));

        setDefaultState(getStateManager().getDefaultState().with(CHARGED, false));
    }
    
    
    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(CHARGED);
        super.appendProperties(builder);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        player.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1);
        world.setBlockState(pos, state.with(CHARGED, true));
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        
        if(world.getBlockState(pos).get(CHARGED)){
            //LightningEntity lightningentity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world);
            //lightningentity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
            //world.spawnEntity(lightningentity);
            entity.setVelocity(new Vec3d(0,4,0));
        }

        

        BlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.with(CHARGED, false));
        super.onSteppedOn(world, pos, entity);
    }
}
