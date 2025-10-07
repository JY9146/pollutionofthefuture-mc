
/*
 * MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fluids.FluidType;

import net.mcreator.pollution.fluid.types.OilFluidType;
import net.mcreator.pollution.PollutionModMod;

public class PollutionModModFluidTypes {
	public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, PollutionModMod.MODID);
	public static final RegistryObject<FluidType> OIL_TYPE = REGISTRY.register("oil", () -> new OilFluidType());
}
