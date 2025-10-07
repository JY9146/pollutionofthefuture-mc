package net.mcreator.pollution.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.pollution.network.PollutionModModVariables;

import java.util.Map;
import java.util.List;
import java.util.Comparator;

public class TestOfSinkholeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		BlockState Block_ = Blocks.AIR.defaultBlockState();
		double yy = 0;
		double Block_DstroyChance = 0;
		double Every__PerUptdate = 0;
		double Fall_Chance = 0;
		double Random_Number = 0;
		Entity Entity_Variable = null;
		entity.fallDistance = 0;
		entity.setInvulnerable(true);
		entity.getPersistentData().putDouble("LagPre", (entity.getPersistentData().getDouble("LagPre") + 1));
		if (entity.getPersistentData().getDouble("LagPre") > 23) {
			entity.getPersistentData().putDouble("LagPre", 0);
			if (entity.getPersistentData().getBoolean("Bedrockstopper") == false) {
				if (entity.onGround()) {
					int horizontalRadiusSphere = (int) 36 - 1;
					int verticalRadiusSphere = (int) 10 - 1;
					int yIterationsSphere = verticalRadiusSphere;
					for (int yi = -yIterationsSphere; yi <= yIterationsSphere; yi++) {
						for (int xi = -horizontalRadiusSphere; xi <= horizontalRadiusSphere; xi++) {
							for (int zi = -horizontalRadiusSphere; zi <= horizontalRadiusSphere; zi++) {
								double distanceSq = (xi * xi) / (double) (horizontalRadiusSphere * horizontalRadiusSphere) + (yi * yi) / (double) (verticalRadiusSphere * verticalRadiusSphere)
										+ (zi * zi) / (double) (horizontalRadiusSphere * horizontalRadiusSphere);
								if (distanceSq <= 1.0) {
									world.setBlock(BlockPos.containing(x + xi, y + yi, z + zi), Blocks.AIR.defaultBlockState(), 3);
									if ((world.getBlockState(BlockPos.containing(x + xi, y + yi, z + zi))).getBlock() == Blocks.BEDROCK) {
										entity.getPersistentData().putBoolean("Bedrockstopper", true);
									} else {
										entity.getPersistentData().putBoolean("Bedrockstopper", false);
									}
									if (entity.getY() <= entity.getPersistentData().getDouble("YtillDespawn") - 110) {
										if (!entity.level().isClientSide())
											entity.discard();
										{
											BlockPos _bp = BlockPos.containing(x, y - 10, z);
											BlockState _bs = Blocks.DEEPSLATE.defaultBlockState();
											BlockState _bso = world.getBlockState(_bp);
											for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
												Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
												if (_property != null && _bs.getValue(_property) != null)
													try {
														_bs = _bs.setValue(_property, (Comparable) entry.getValue());
													} catch (Exception e) {
													}
											}
											world.setBlock(_bp, _bs, 3);
										}
									}
								}
							}
						}
					}
					{
						final Vec3 _center = new Vec3(x, y, z);
						List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(36 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
						for (Entity entityiterator : _entfound) {
							if (entityiterator instanceof Player) {
								if (PollutionModModVariables.WorldVariables.get(world).Logic_Variable == true) {
									if (Math.random() < 0.5) {
										if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2000, 5, false, false));
										PollutionModModVariables.WorldVariables.get(world).Logic_Variable = false;
										PollutionModModVariables.WorldVariables.get(world).syncData(world);
									} else {
										if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
											_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2000, 20, false, false));
										PollutionModModVariables.WorldVariables.get(world).Logic_Variable = false;
										PollutionModModVariables.WorldVariables.get(world).syncData(world);
									}
								}
							}
						}
					}
					if (world instanceof ServerLevel _level)
						_level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
								("kill @e[type=item,distance=.." + 36 + "]"));
				}
			} else if (entity.getPersistentData().getBoolean("Bedrockstopper") == true) {
				if (!entity.level().isClientSide())
					entity.discard();
			}
		}
	}
}
