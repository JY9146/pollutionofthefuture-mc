
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.GameRules;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PollutionModModGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> POLLUTED_AIR = GameRules.register("pollutedAir", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
}
