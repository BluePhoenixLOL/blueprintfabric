package com.teamabnormals.blueprint.core;

import com.google.common.collect.Sets;
import com.teamabnormals.blueprint.client.RewardHandler;
import com.teamabnormals.blueprint.client.renderer.AbnormalsBoatRenderer;
import com.teamabnormals.blueprint.client.renderer.block.AbnormalsChestBlockEntityRenderer;
import com.teamabnormals.blueprint.common.block.AbnormalsBeehiveBlock;
import com.teamabnormals.blueprint.common.capability.chunkloading.ChunkLoaderCapability;
import com.teamabnormals.blueprint.common.capability.chunkloading.ChunkLoaderEvents;
import com.teamabnormals.blueprint.common.network.MessageC2SUpdateSlabfishHat;
import com.teamabnormals.blueprint.common.network.entity.MessageS2CEndimation;
import com.teamabnormals.blueprint.common.network.entity.MessageS2CTeleportEntity;
import com.teamabnormals.blueprint.common.network.entity.MessageS2CUpdateEntityData;
import com.teamabnormals.blueprint.common.network.particle.MessageS2CSpawnParticle;
import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.blueprint.core.api.SignManager;
import com.teamabnormals.blueprint.core.api.conditions.ACAndRecipeCondition;
import com.teamabnormals.blueprint.core.api.conditions.QuarkFlagRecipeCondition.Serializer;
import com.teamabnormals.blueprint.core.api.conditions.config.*;
import com.teamabnormals.blueprint.core.api.model.FullbrightModel;
import com.teamabnormals.blueprint.core.endimator.EndimationDataManager;
import com.teamabnormals.blueprint.core.events.CompatEvents;
import com.teamabnormals.blueprint.core.registry.BlueprintBlockEntityTypes;
import com.teamabnormals.blueprint.core.registry.BlueprintEntityTypes;
import com.teamabnormals.blueprint.core.registry.BlueprintLootConditions;
import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.blueprint.core.util.NetworkUtil;
import com.teamabnormals.blueprint.core.util.registry.BlockEntitySubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Mod class for the Blueprint mod.
 *
 * @author SmellyModder (Luke Tonon)
 * @author bageldotjpg
 * @author Jackson
 * @author abigailfails
 */
@Mod(Blueprint.MOD_ID)
@Mod.EventBusSubscriber(modid = Blueprint.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Blueprint {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "blueprint";
	public static final String NETWORK_PROTOCOL = "BP1";
	public static final EndimationDataManager ENDIMATION_DATA_MANAGER = new EndimationDataManager();
	public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);
	public static final TrackedData<Byte> SLABFISH_SETTINGS = TrackedData.Builder.create(DataProcessors.BYTE, () -> (byte) 8).enablePersistence().build();

	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MOD_ID, "net"))
			.networkProtocolVersion(() -> NETWORK_PROTOCOL)
			.clientAcceptedVersions(NETWORK_PROTOCOL::equals)
			.serverAcceptedVersions(NETWORK_PROTOCOL::equals)
			.simpleChannel();

	public Blueprint() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext context = ModLoadingContext.get();
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new ChunkLoaderEvents());

		this.registerMessages();

		CraftingHelper.register(new Serializer());
		CraftingHelper.register(new ACAndRecipeCondition.Serializer());
		DataUtil.registerConfigCondition(Blueprint.MOD_ID, BlueprintConfig.COMMON, BlueprintConfig.CLIENT, BlueprintConfig.CLIENT.slabfishSettings);
		DataUtil.registerConfigPredicate(new EqualsPredicate.Serializer());
		DataUtil.registerConfigPredicate(new GreaterThanOrEqualPredicate.Serializer());
		DataUtil.registerConfigPredicate(new GreaterThanPredicate.Serializer());
		DataUtil.registerConfigPredicate(new LessThanOrEqualPredicate.Serializer());
		DataUtil.registerConfigPredicate(new LessThanPredicate.Serializer());
		DataUtil.registerConfigPredicate(new ContainsPredicate.Serializer());
		DataUtil.registerConfigPredicate(new MatchesPredicate.Serializer());

		REGISTRY_HELPER.getEntitySubHelper().register(modEventBus);
		REGISTRY_HELPER.getBlockEntitySubHelper().register(modEventBus);

		modEventBus.addListener((ModConfigEvent event) -> {
			final ModConfig config = event.getConfig();
			if (config.getSpec() == BlueprintConfig.COMMON_SPEC) {
				BlueprintConfig.COMMON.load();
			} else if (config.getSpec() == BlueprintConfig.CLIENT_SPEC) {
				BlueprintConfig.CLIENT.load();
			}
		});

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			modEventBus.addListener(EventPriority.NORMAL, false, ColorHandlerEvent.Block.class, event -> {
				ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
				if (resourceManager instanceof ReloadableResourceManager) {
					((ReloadableResourceManager) resourceManager).registerReloadListener(ENDIMATION_DATA_MANAGER);
				}
			});
			modEventBus.addListener(EventPriority.NORMAL, false, ModConfigEvent.Reloading.class, event -> {
				if (event.getConfig().getModId().equals(Blueprint.MOD_ID)) NetworkUtil.updateSlabfish(RewardHandler.SlabfishSetting.getConfig());
			});
			modEventBus.addListener(this::clientSetup);
			modEventBus.addListener(this::modelSetup);
			modEventBus.addListener(this::rendererSetup);
			modEventBus.addListener(RewardHandler::clientSetup);
		});

		modEventBus.addListener(EventPriority.LOWEST, this::commonSetup);
		modEventBus.addListener(EventPriority.LOWEST, this::postLoadingSetup);
		modEventBus.addListener(this::registerCapabilities);
		context.registerConfig(ModConfig.Type.COMMON, BlueprintConfig.COMMON_SPEC);
		context.registerConfig(ModConfig.Type.CLIENT, BlueprintConfig.CLIENT_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			this.replaceBeehivePOI();
			BlueprintLootConditions.registerLootConditions();
		});
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(MOD_ID, "slabfish_head"), SLABFISH_SETTINGS);
	}

	private void clientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(SignManager::setupAtlas);
	}

	private void rendererSetup(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(BlueprintEntityTypes.BOAT.get(), AbnormalsBoatRenderer::new);

		event.registerBlockEntityRenderer(BlueprintBlockEntityTypes.CHEST.get(), AbnormalsChestBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(BlueprintBlockEntityTypes.TRAPPED_CHEST.get(), AbnormalsChestBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(BlueprintBlockEntityTypes.SIGN.get(), SignRenderer::new);
	}

	private void postLoadingSetup(FMLLoadCompleteEvent event) {
		event.enqueueWork(() -> {
			DataUtil.getSortedAlternativeDispenseBehaviors().forEach(DataUtil.AlternativeDispenseBehavior::register);
			CompatEvents.SORTED_CUSTOM_NOTE_BLOCK_INSTRUMENTS = DataUtil.getSortedCustomNoteBlockInstruments();
		});
	}

	private void registerCapabilities(RegisterCapabilitiesEvent event) {
		ChunkLoaderCapability.register(event);
	}

	private void modelSetup(ModelRegistryEvent event) {
		ModelLoaderRegistry.registerLoader(new ResourceLocation(MOD_ID, "fullbright"), FullbrightModel.Loader.INSTANCE);
	}

	private void registerMessages() {
		int id = -1;
		CHANNEL.registerMessage(++id, MessageS2CEndimation.class, MessageS2CEndimation::serialize, MessageS2CEndimation::deserialize, MessageS2CEndimation::handle);
		CHANNEL.registerMessage(++id, MessageS2CTeleportEntity.class, MessageS2CTeleportEntity::serialize, MessageS2CTeleportEntity::deserialize, MessageS2CTeleportEntity::handle);
		CHANNEL.registerMessage(++id, MessageS2CSpawnParticle.class, MessageS2CSpawnParticle::serialize, MessageS2CSpawnParticle::deserialize, MessageS2CSpawnParticle::handle);
		CHANNEL.registerMessage(++id, MessageS2CUpdateEntityData.class, MessageS2CUpdateEntityData::serialize, MessageS2CUpdateEntityData::deserialize, MessageS2CUpdateEntityData::handle);
		CHANNEL.registerMessage(++id, MessageC2SUpdateSlabfishHat.class, MessageC2SUpdateSlabfishHat::serialize, MessageC2SUpdateSlabfishHat::deserialize, MessageC2SUpdateSlabfishHat::handle);
	}

	private void replaceBeehivePOI() {
		PoiType.BEEHIVE.matchingStates = Sets.newHashSet(PoiType.BEEHIVE.matchingStates);
		Map<BlockState, PoiType> statePointOfInterestMap = ObfuscationReflectionHelper.getPrivateValue(PoiType.class, null, "f_27323_");
		if (statePointOfInterestMap != null) {
			for (Block block : BlockEntitySubRegistryHelper.collectBlocks(AbnormalsBeehiveBlock.class)) {
				block.getStateDefinition().getPossibleStates().forEach(state -> {
					statePointOfInterestMap.put(state, PoiType.BEEHIVE);
					PoiType.BEEHIVE.matchingStates.add(state);
				});
			}
		}
	}
}