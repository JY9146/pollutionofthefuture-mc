
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.pollution.PollutionModMod;

public class PollutionModModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PollutionModMod.MODID);
	public static final RegistryObject<SoundEvent> COUGHCOUGH = REGISTRY.register("coughcough", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("pollution_mod", "coughcough")));
}
