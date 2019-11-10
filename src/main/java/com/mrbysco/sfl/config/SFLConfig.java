package com.mrbysco.sfl.config;

import com.mrbysco.sfl.ServerFriendlyLoot;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class SFLConfig {
    public static class Spawn {
        public final ForgeConfigSpec.IntValue minGroup;
        public final ForgeConfigSpec.IntValue maxGroup;
        public final ForgeConfigSpec.IntValue weight;

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("Spawn settings")
                    .comment("Configure spawning rate of Mimic");

            minGroup = builder
                    .comment("Min group size [Default: 1]")
                    .defineInRange("minGroup", 1, 0, 64);

            maxGroup = builder
                    .comment("Max group size [Default: 1]")
                    .defineInRange("maxGroup", 1, 0, 64);

            weight = builder
                    .comment("Spawn weight [Default: 1]")
                    .defineInRange("minGroup", 1, 0, 100);
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
    public static void onLoad(final ModConfig.Loading configEvent) {
        ServerFriendlyLoot.LOGGER.debug("Loaded ServerFriendlyLoot's config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.ConfigReloading configEvent) {
        ServerFriendlyLoot.LOGGER.fatal("ServerFriendlyLoot's config just got changed on the file system!");
    }
}
