
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.mcreator.pollution.PollutionModMod;

public class PollutionModModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PollutionModMod.MODID);
	public static final RegistryObject<CreativeModeTab> POLLUTION = REGISTRY.register("pollution",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.pollution_mod.pollution")).icon(() -> new ItemStack(PollutionModModBlocks.TRASH.get())).displayItems((parameters, tabData) -> {
				tabData.accept(PollutionModModItems.TORNADO_SUMMONER.get());
				tabData.accept(PollutionModModItems.TEST_SINKHOLE_SUMMONER.get());
				tabData.accept(PollutionModModItems.WILD_FIRE_SUMMONER.get());
				tabData.accept(PollutionModModBlocks.TRASH.get().asItem());
				tabData.accept(PollutionModModItems.OIL_BUCKET.get());
			}).build());
}
