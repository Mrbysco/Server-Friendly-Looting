package com.mrbysco.sfl.config;

import com.mrbysco.sfl.ServerFriendlyLoot;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class SFLConfig {
	public static class Spawn {

		public final ConfigValue<List<? extends String>> dimension_blacklist;

		Spawn(ForgeConfigSpec.Builder builder) {
			builder.push("General settings")
					.comment("Configure General Mimic settings");

			dimension_blacklist = builder
					.comment("Dimensions in which the mimic's can't spawn")
					.defineListAllowEmpty(List.of("dimensionBlacklist"), () -> List.of(""), o -> (o instanceof String));

			builder.pop();
		}
	}

	public static final ForgeConfigSpec spawnSpec;
	public static final Spawn SPAWN;

	static {
		final Pair<Spawn, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SFLConfig.Spawn::new);
		spawnSpec = specPair.getRight();
		SPAWN = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		ServerFriendlyLoot.LOGGER.debug("Loaded ServerFriendlyLoot's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		ServerFriendlyLoot.LOGGER.fatal("ServerFriendlyLoot's config just got changed on the file system!");
	}
}
