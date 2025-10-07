package net.mcreator.pollution.network;

import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Direction;
import net.minecraft.client.Minecraft;

import net.mcreator.pollution.PollutionModMod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PollutionModModVariables {
	public static double Number1 = 0.0;
	public static double Number2 = 0;
	public static double Number3 = 0;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		PollutionModMod.addNetworkMessage(SavedDataSyncMessage.class, SavedDataSyncMessage::buffer, SavedDataSyncMessage::new, SavedDataSyncMessage::handler);
		PollutionModMod.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
	}

	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide())
				((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
			if (!event.isWasDeath()) {
				clone.DrownFaster = original.DrownFaster;
			}
		}

		@SubscribeEvent
		public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide()) {
				SavedData mapdata = MapVariables.get(event.getEntity().level());
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (mapdata != null)
					PollutionModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(0, mapdata));
				if (worlddata != null)
					PollutionModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(1, worlddata));
			}
		}

		@SubscribeEvent
		public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide()) {
				SavedData worlddata = WorldVariables.get(event.getEntity().level());
				if (worlddata != null)
					PollutionModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new SavedDataSyncMessage(1, worlddata));
			}
		}
	}

	public static class WorldVariables extends SavedData {
		public static final String DATA_NAME = "pollution_mod_worldvars";
		public boolean Logic_Variable = true;
		public boolean V1 = false;
		public boolean LavaBallVariableStopper = false;
		public boolean Orphan = false;
		public boolean MessUpGround = false;
		public boolean VolcanoXoffset = false;
		public boolean VolcanoZoffset = false;
		public double RandomNum = 0;
		public double JustPickONE = 1.0;
		public double VH = 0;
		public boolean MusicWait = false;
		public double VolcanoItemTexture = 1.0;

		public static WorldVariables load(CompoundTag tag) {
			WorldVariables data = new WorldVariables();
			data.read(tag);
			return data;
		}

		public void read(CompoundTag nbt) {
			Logic_Variable = nbt.getBoolean("Logic_Variable");
			V1 = nbt.getBoolean("V1");
			LavaBallVariableStopper = nbt.getBoolean("LavaBallVariableStopper");
			Orphan = nbt.getBoolean("Orphan");
			MessUpGround = nbt.getBoolean("MessUpGround");
			VolcanoXoffset = nbt.getBoolean("VolcanoXoffset");
			VolcanoZoffset = nbt.getBoolean("VolcanoZoffset");
			RandomNum = nbt.getDouble("RandomNum");
			JustPickONE = nbt.getDouble("JustPickONE");
			VH = nbt.getDouble("VH");
			MusicWait = nbt.getBoolean("MusicWait");
			VolcanoItemTexture = nbt.getDouble("VolcanoItemTexture");
		}

		@Override
		public CompoundTag save(CompoundTag nbt) {
			nbt.putBoolean("Logic_Variable", Logic_Variable);
			nbt.putBoolean("V1", V1);
			nbt.putBoolean("LavaBallVariableStopper", LavaBallVariableStopper);
			nbt.putBoolean("Orphan", Orphan);
			nbt.putBoolean("MessUpGround", MessUpGround);
			nbt.putBoolean("VolcanoXoffset", VolcanoXoffset);
			nbt.putBoolean("VolcanoZoffset", VolcanoZoffset);
			nbt.putDouble("RandomNum", RandomNum);
			nbt.putDouble("JustPickONE", JustPickONE);
			nbt.putDouble("VH", VH);
			nbt.putBoolean("MusicWait", MusicWait);
			nbt.putDouble("VolcanoItemTexture", VolcanoItemTexture);
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level level && !level.isClientSide())
				PollutionModMod.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(level::dimension), new SavedDataSyncMessage(1, this));
		}

		static WorldVariables clientSide = new WorldVariables();

		public static WorldVariables get(LevelAccessor world) {
			if (world instanceof ServerLevel level) {
				return level.getDataStorage().computeIfAbsent(e -> WorldVariables.load(e), WorldVariables::new, DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class MapVariables extends SavedData {
		public static final String DATA_NAME = "pollution_mod_mapvars";
		public BlockState VolcanoBlock = Blocks.AIR.defaultBlockState();
		public double Consistant = 0;
		public double PleaseWorkMusic = 0;

		public static MapVariables load(CompoundTag tag) {
			MapVariables data = new MapVariables();
			data.read(tag);
			return data;
		}

		public void read(CompoundTag nbt) {
			VolcanoBlock = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), nbt.getCompound("VolcanoBlock"));
			Consistant = nbt.getDouble("Consistant");
			PleaseWorkMusic = nbt.getDouble("PleaseWorkMusic");
		}

		@Override
		public CompoundTag save(CompoundTag nbt) {
			nbt.put("VolcanoBlock", NbtUtils.writeBlockState(VolcanoBlock));
			nbt.putDouble("Consistant", Consistant);
			nbt.putDouble("PleaseWorkMusic", PleaseWorkMusic);
			return nbt;
		}

		public void syncData(LevelAccessor world) {
			this.setDirty();
			if (world instanceof Level && !world.isClientSide())
				PollutionModMod.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new SavedDataSyncMessage(0, this));
		}

		static MapVariables clientSide = new MapVariables();

		public static MapVariables get(LevelAccessor world) {
			if (world instanceof ServerLevelAccessor serverLevelAcc) {
				return serverLevelAcc.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(e -> MapVariables.load(e), MapVariables::new, DATA_NAME);
			} else {
				return clientSide;
			}
		}
	}

	public static class SavedDataSyncMessage {
		private final int type;
		private SavedData data;

		public SavedDataSyncMessage(FriendlyByteBuf buffer) {
			this.type = buffer.readInt();
			CompoundTag nbt = buffer.readNbt();
			if (nbt != null) {
				this.data = this.type == 0 ? new MapVariables() : new WorldVariables();
				if (this.data instanceof MapVariables mapVariables)
					mapVariables.read(nbt);
				else if (this.data instanceof WorldVariables worldVariables)
					worldVariables.read(nbt);
			}
		}

		public SavedDataSyncMessage(int type, SavedData data) {
			this.type = type;
			this.data = data;
		}

		public static void buffer(SavedDataSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeInt(message.type);
			if (message.data != null)
				buffer.writeNbt(message.data.save(new CompoundTag()));
		}

		public static void handler(SavedDataSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer() && message.data != null) {
					if (message.type == 0)
						MapVariables.clientSide = (MapVariables) message.data;
					else
						WorldVariables.clientSide = (WorldVariables) message.data;
				}
			});
			context.setPacketHandled(true);
		}
	}

	public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
				event.addCapability(new ResourceLocation("pollution_mod", "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		public double DrownFaster = 300.0;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				PollutionModMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
		}

		public Tag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("DrownFaster", DrownFaster);
			return nbt;
		}

		public void readNBT(Tag tag) {
			CompoundTag nbt = (CompoundTag) tag;
			DrownFaster = nbt.getDouble("DrownFaster");
		}
	}

	public static class PlayerVariablesSyncMessage {
		private final PlayerVariables data;

		public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
			this.data = new PlayerVariables();
			this.data.readNBT(buffer.readNbt());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt((CompoundTag) message.data.writeNBT());
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
					variables.DrownFaster = message.data.DrownFaster;
				}
			});
			context.setPacketHandled(true);
		}
	}
}
