package com.mrbysco.sfl;

import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.AbstractMimicEntity;
import com.mrbysco.sfl.init.MimicEntities;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(ServerFriendlyLoot.MOD_ID)
public class ServerFriendlyLoot {
	public static final String MOD_ID = "sfl";
	public static final Logger LOGGER = LogManager.getLogger();

	public ServerFriendlyLoot(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.SERVER, SFLConfig.spawnSpec, "sfl_spawning.toml");
		eventBus.register(SFLConfig.class);

		MimicRegistry.ENTITIES.register(eventBus);
		MimicRegistry.ITEMS.register(eventBus);

		eventBus.addListener(MimicEntities::registerSpawnPlacements);
		eventBus.addListener(MimicEntities::registerEntityAttributes);
		eventBus.addListener(this::addTabContents);

		NeoForge.EVENT_BUS.addListener(this::onFinalizeSpawn);

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
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

	private void onFinalizeSpawn(final FinalizeSpawnEvent event) {
		if (event.getSpawnType().equals(EntitySpawnReason.NATURAL) && event.getEntity() instanceof AbstractMimicEntity) {
			List<? extends String> blacklist = SFLConfig.SPAWN.dimension_blacklist.get();
			if (!blacklist.isEmpty()) {
				ResourceLocation dimensionLocation = event.getLevel().getLevel().dimension().location();
				for (String dimension : blacklist) {
					if (!dimension.isEmpty()) {
						ResourceLocation dimLoc = ResourceLocation.tryParse(dimension);
						if (dimLoc != null && dimLoc.equals(dimensionLocation))
							event.setSpawnCancelled(true);
					}
				}
			}
		}
	}
}
