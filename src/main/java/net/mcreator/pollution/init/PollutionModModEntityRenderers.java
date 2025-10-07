
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.mcreator.pollution.client.renderer.WildFireRenderer;
import net.mcreator.pollution.client.renderer.TestOfSinkholeEntityRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PollutionModModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(PollutionModModEntities.TEST_OF_SINKHOLE_ENTITY.get(), TestOfSinkholeEntityRenderer::new);
		event.registerEntityRenderer(PollutionModModEntities.WILD_FIRE.get(), WildFireRenderer::new);
	}
}
