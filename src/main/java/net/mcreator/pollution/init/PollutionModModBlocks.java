
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mcreator.pollution.block.TrashBlock;
import net.mcreator.pollution.block.OilBlock;
import net.mcreator.pollution.PollutionModMod;

public class PollutionModModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, PollutionModMod.MODID);
	public static final RegistryObject<Block> TRASH = REGISTRY.register("trash", () -> new TrashBlock());
	public static final RegistryObject<Block> OIL = REGISTRY.register("oil", () -> new OilBlock());
	// Start of user code block custom blocks
	// End of user code block custom blocks
}
