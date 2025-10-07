
package net.mcreator.pollution.item;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;

import net.mcreator.pollution.procedures.TestSinkholeSummonerRIghtClickProcedure;

public class TestSinkholeSummonerItem extends Item {
	public TestSinkholeSummonerItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		TestSinkholeSummonerRIghtClickProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ());
		return InteractionResult.SUCCESS;
	}
}
