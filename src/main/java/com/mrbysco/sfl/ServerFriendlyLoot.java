package com.mrbysco.sfl;

import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.AbstractMimicEntity;
import com.mrbysco.sfl.init.MimicEntities;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(ServerFriendlyLoot.MOD_ID)
public class ServerFriendlyLoot {
	public static final String MOD_ID = "sfl";
	public static final Logger LOGGER = LogManager.getLogger();

	public ServerFriendlyLoot() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SFLConfig.spawnSpec, "sfl_spawning.toml");
		eventBus.register(SFLConfig.class);

		MimicRegistry.ENTITY_TYPES.register(eventBus);
		MimicRegistry.ITEMS.register(eventBus);

		eventBus.addListener(MimicEntities::registerSpawnPlacements);
		eventBus.addListener(MimicEntities::registerEntityAttributes);
		eventBus.addListener(this::addTabContents);

		NeoForge.EVENT_BUS.register(this);

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
		}
	}

	private void addTabContents(final BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			List<ItemStack> stacks = MimicRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
			event.acceptAll(stacks);
		}
	}

	@SubscribeEvent
	public void onSpawn(final MobSpawnEvent.FinalizeSpawn event) {
		if (event.getSpawnType().equals(MobSpawnType.NATURAL) && event.getEntity() instanceof AbstractMimicEntity) {
			List<? extends String> blacklist = SFLConfig.SPAWN.dimension_blacklist.get();
			if (!blacklist.isEmpty()) {
				ResourceLocation dimensionLocation = ((Level) event.getLevel()).dimension().location();
				for (String dimension : blacklist) {
					if (!dimension.isEmpty() && new ResourceLocation(dimension).equals(dimensionLocation))
						event.setSpawnCancelled(true);
				}
			}
		}
	}
}
