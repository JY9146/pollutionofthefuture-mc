
package net.mcreator.pollution.fluid;

import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.LiquidBlock;

import net.mcreator.pollution.init.PollutionModModItems;
import net.mcreator.pollution.init.PollutionModModFluids;
import net.mcreator.pollution.init.PollutionModModFluidTypes;
import net.mcreator.pollution.init.PollutionModModBlocks;

public abstract class OilFluid extends ForgeFlowingFluid {
	public static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> PollutionModModFluidTypes.OIL_TYPE.get(), () -> PollutionModModFluids.OIL.get(), () -> PollutionModModFluids.FLOWING_OIL.get())
			.explosionResistance(100f).bucket(() -> PollutionModModItems.OIL_BUCKET.get()).block(() -> (LiquidBlock) PollutionModModBlocks.OIL.get());

	private OilFluid() {
		super(PROPERTIES);
	}

	public static class Source extends OilFluid {
		public int getAmount(FluidState state) {
			return 8;
		}

		public boolean isSource(FluidState state) {
			return true;
		}
	}

	public static class Flowing extends OilFluid {
		protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}

		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
		}

		public boolean isSource(FluidState state) {
			return false;
		}
	}
}
