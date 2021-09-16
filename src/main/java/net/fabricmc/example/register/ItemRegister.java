package net.fabricmc.example.register;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.items.FabricItem;
import net.fabricmc.example.items.ProjectileItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemRegister{

    //items
    public static final Item COPPER_PICKAXE = new Item(new Item.Settings().group(ItemGroup.TOOLS));
	public static final Item FABRIC_ITEM = new FabricItem(new Item.Settings().group(ItemGroup.MISC));
    public static final ProjectileItem MY_PROJECTILE = new ProjectileItem(new Item.Settings().group(ItemGroup.COMBAT));

    //spawn eggs
    public static final Item TEST_SPAWN_EGG = new SpawnEggItem(ExampleMod.TEST_ENTITY, 255, 16777215, new Item.Settings().group(ItemGroup.MISC));
    public static final Item MY_SKELETON_SPAWN_EGG = new SpawnEggItem(ExampleMod.MY_SKELETON, 255, 255, new Item.Settings().group(ItemGroup.MISC));

    public static void init(){
        Registry.register(Registry.ITEM, new Identifier(ExampleMod.MODID, "copper_pickaxe"), COPPER_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier(ExampleMod.MODID, "fabric_item"), FABRIC_ITEM);
        Registry.register(Registry.ITEM, new Identifier(ExampleMod.MODID, "test_spawn_egg"), TEST_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(ExampleMod.MODID, "my_skeleton_spawn_egg"), MY_SKELETON_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(ExampleMod.MODID, "my_projectile"), MY_PROJECTILE);
    }
}