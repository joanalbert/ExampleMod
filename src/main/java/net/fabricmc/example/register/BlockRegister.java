package net.fabricmc.example.register;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.blocks.FabricBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegister {
    
    //fabric block
	public static final Block FABRIC_BLOCK = new FabricBlock();
	public static final Item  FABRIC_BLOCKITEM = new BlockItem(FABRIC_BLOCK, new Item.Settings().group(ItemGroup.MISC));

    public static void init(){
        Registry.register(Registry.ITEM, new Identifier(ExampleMod.MODID, "fabric_block"), FABRIC_BLOCKITEM);
		Registry.register(Registry.BLOCK, new Identifier(ExampleMod.MODID, "fabric_block"), FABRIC_BLOCK);
    }
}
