
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.pollution.item.WildFireSummonerItem;
import net.mcreator.pollution.item.TornadoSummonerItem;
import net.mcreator.pollution.item.TestSinkholeSummonerItem;
import net.mcreator.pollution.item.OilItem;
import net.mcreator.pollution.PollutionModMod;

public class PollutionModModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, PollutionModMod.MODID);
	public static final RegistryObject<Item> TORNADO_SUMMONER = REGISTRY.register("tornado_summoner", () -> new TornadoSummonerItem());
	public static final RegistryObject<Item> TEST_SINKHOLE_SUMMONER = REGISTRY.register("test_sinkhole_summoner", () -> new TestSinkholeSummonerItem());
	public static final RegistryObject<Item> WILD_FIRE_SUMMONER = REGISTRY.register("wild_fire_summoner", () -> new WildFireSummonerItem());
	public static final RegistryObject<Item> TRASH = block(PollutionModModBlocks.TRASH);
	public static final RegistryObject<Item> OIL_BUCKET = REGISTRY.register("oil_bucket", () -> new OilItem());

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
