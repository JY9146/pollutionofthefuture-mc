package net.mcreator.pollution.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

public class WildFireOnEntityTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		BlockState Wildfire = Blocks.AIR.defaultBlockState();
		if (world instanceof ServerLevel _origLevel) {
			LevelAccessor _worldorig = world;
			world = _origLevel.getServer().getLevel(Level.OVERWORLD);
			if (world != null) {
				int horizontalRadiusHemiTop = (int) 100 - 1;
				int verticalRadiusHemiTop = (int) 1;
				int yIterationsHemiTop = verticalRadiusHemiTop;
				for (int yi = 0; yi < yIterationsHemiTop; yi++) {
					if (yi == verticalRadiusHemiTop) {
						continue;
					}
					for (int xi = -horizontalRadiusHemiTop; xi <= horizontalRadiusHemiTop; xi++) {
						for (int zi = -horizontalRadiusHemiTop; zi <= horizontalRadiusHemiTop; zi++) {
							double distanceSq = (xi * xi) / (double) (horizontalRadiusHemiTop * horizontalRadiusHemiTop) + (yi * yi) / (double) (verticalRadiusHemiTop * verticalRadiusHemiTop)
									+ (zi * zi) / (double) (horizontalRadiusHemiTop * horizontalRadiusHemiTop);
							if (distanceSq <= 1.0) {
								Wildfire = (world.getBlockState(BlockPos.containing(x + xi, y + yi, z + zi)));
								if (Wildfire.getBlock() == Blocks.AIR) {
									for (int index0 = 0; index0 < 2; index0++) {
										world.setBlock(BlockPos.containing(x + xi, y + yi, z + zi), Blocks.FIRE.defaultBlockState(), 3);
									}
								} else {
									if (!entity.level().isClientSide())
										entity.discard();
								}
							}
						}
					}
				}
			}
			world = _worldorig;
		}
	}
}
