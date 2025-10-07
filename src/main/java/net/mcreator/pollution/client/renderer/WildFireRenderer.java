
package net.mcreator.pollution.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

import net.mcreator.pollution.entity.WildFireEntity;

public class WildFireRenderer extends HumanoidMobRenderer<WildFireEntity, HumanoidModel<WildFireEntity>> {
	public WildFireRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<WildFireEntity>(context.bakeLayer(ModelLayers.PLAYER)), 0f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(WildFireEntity entity) {
		return new ResourceLocation("pollution_mod:textures/entities/super-invisible.png");
	}
}
