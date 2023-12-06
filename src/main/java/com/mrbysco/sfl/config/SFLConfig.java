package com.mrbysco.sfl.config;

import com.mrbysco.sfl.ServerFriendlyLoot;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class SFLConfig {
	public static class Spawn {

		public final ModConfigSpec.ConfigValue<List<? extends String>> dimension_blacklist;

		Spawn(ModConfigSpec.Builder builder) {
			builder.push("General settings")
					.comment("Configure General Mimic settings");

			dimension_blacklist = builder
					.comment("Dimensions in which the mimic's can't spawn")
					.defineListAllowEmpty(List.of("dimensionBlacklist"), () -> List.of(""), o -> (o instanceof String));

			builder.pop();
		}
	}

	public static final ModConfigSpec spawnSpec;
	public static final Spawn SPAWN;

	static {
		final Pair<Spawn, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(SFLConfig.Spawn::new);
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
