package com.mrbysco.sfl;

import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.AbstractMimicEntity;
import com.mrbysco.sfl.init.MimicRegistry;
import com.mrbysco.sfl.init.MimicSpawns;
import net.minecraft.entity.SpawnReason;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

        MimicRegistry.ENTITIES.register(eventBus);
        MimicRegistry.ITEMS.register(eventBus);

        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientHandler::registerRenders);
            eventBus.addListener(ClientHandler::registerItemColors);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(MimicSpawns::addSpawn);
    }

    @SubscribeEvent
    public void onSpawn(final LivingSpawnEvent.CheckSpawn event) {
        if (event.getSpawnReason().equals(SpawnReason.NATURAL) && event.getEntityLiving() instanceof AbstractMimicEntity) {
            List<? extends String> blacklist = SFLConfig.SPAWN.dimension_blacklist.get();
            if (!blacklist.isEmpty()) {
                int dimensionID = event.getWorld().getDimension().getType().getId();
                for (String dimension : blacklist) {
                    if (!dimension.isEmpty() && dimension.equals(String.valueOf(dimensionID)))
                        event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
