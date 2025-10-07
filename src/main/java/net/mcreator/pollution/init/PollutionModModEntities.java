
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.pollution.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.mcreator.pollution.entity.WildFireEntity;
import net.mcreator.pollution.entity.TestOfSinkholeEntityEntity;
import net.mcreator.pollution.PollutionModMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PollutionModModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PollutionModMod.MODID);
	public static final RegistryObject<EntityType<TestOfSinkholeEntityEntity>> TEST_OF_SINKHOLE_ENTITY = register("test_of_sinkhole_entity", EntityType.Builder.<TestOfSinkholeEntityEntity>of(TestOfSinkholeEntityEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(TestOfSinkholeEntityEntity::new).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<WildFireEntity>> WILD_FIRE = register("wild_fire",
			EntityType.Builder.<WildFireEntity>of(WildFireEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(WildFireEntity::new).fireImmune().sized(0.6f, 1.8f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			TestOfSinkholeEntityEntity.init();
			WildFireEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(TEST_OF_SINKHOLE_ENTITY.get(), TestOfSinkholeEntityEntity.createAttributes().build());
		event.put(WILD_FIRE.get(), WildFireEntity.createAttributes().build());
	}
}
