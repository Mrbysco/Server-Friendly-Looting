package com.mrbysco.sfl.config;

import com.google.common.collect.Lists;
import com.mrbysco.sfl.ServerFriendlyLoot;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class SFLConfig {
    public static class Spawn {
        public final ForgeConfigSpec.IntValue minGroup;
        public final ForgeConfigSpec.IntValue maxGroup;
        public final ForgeConfigSpec.IntValue weight;

        public final ForgeConfigSpec.IntValue waterMinGroup;
        public final ForgeConfigSpec.IntValue waterMaxGroup;
        public final ForgeConfigSpec.IntValue waterWeight;

        public final ForgeConfigSpec.IntValue netherMinGroup;
        public final ForgeConfigSpec.IntValue netherMaxGroup;
        public final ForgeConfigSpec.IntValue netherWeight;

        public final ForgeConfigSpec.IntValue endMinGroup;
        public final ForgeConfigSpec.IntValue endMaxGroup;
        public final ForgeConfigSpec.IntValue endWeight;

        public final ConfigValue<List<? extends String>> dimension_blacklist;

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("General settings")
                    .comment("Configure General Mimic settings");

            dimension_blacklist = builder
                    .comment("Dimensions in which the mimic's can't spawn")
                    .defineList("dimensionBlacklist", Lists.newArrayList(""), o -> (o instanceof String));

            builder.pop();

            builder.push("Spawn settings")
                    .comment("Configure spawning rate of regular Mimic");

            minGroup = builder
                    .comment("Min group size [Default: 1]")
                    .defineInRange("minGroup", 1, 0, 64);

            maxGroup = builder
                    .comment("Max group size [Default: 1]")
                    .defineInRange("maxGroup", 1, 0, 64);

            weight = builder
                    .comment("Spawn weight [Default: 1]")
                    .defineInRange("weight", 1, 0, 100);

            builder.pop();
            builder.push("Water Spawn Settings")
                    .comment("Configure spawning rate of Water Mimic");

            waterMinGroup = builder
                    .comment("Min group size [Default: 1]")
                    .defineInRange("waterMinGroup", 1, 0, 64);

            waterMaxGroup = builder
                    .comment("Max group size [Default: 1]")
                    .defineInRange("waterMaxGroup", 1, 0, 64);

            waterWeight = builder
                    .comment("Spawn weight [Default: 1]")
                    .defineInRange("waterWeight", 1, 0, 100);
            builder.pop();
            builder.push("Nether Spawn Settings")
                    .comment("Configure spawning rate of Nether Mimic");

            netherMinGroup = builder
                    .comment("Min group size [Default: 1]")
                    .defineInRange("netherMinGroup", 1, 0, 64);

            netherMaxGroup = builder
                    .comment("Max group size [Default: 1]")
                    .defineInRange("netherMaxGroup", 1, 0, 64);

            netherWeight = builder
                    .comment("Spawn weight [Default: 1]")
                    .defineInRange("netherWeight", 1, 0, 100);
            builder.pop();
            builder.push("End Spawn Settings")
                    .comment("Configure spawning rate of End Mimic");

            endMinGroup = builder
                    .comment("Min group size [Default: 1]")
                    .defineInRange("endMinGroup", 1, 0, 64);

            endMaxGroup = builder
                    .comment("Max group size [Default: 1]")
                    .defineInRange("endMaxGroup", 1, 0, 64);

            endWeight = builder
                    .comment("Spawn weight [Default: 1]")
                    .defineInRange("endWeight", 1, 0, 100);
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
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        ServerFriendlyLoot.LOGGER.fatal("ServerFriendlyLoot's config just got changed on the file system!");
    }
}
