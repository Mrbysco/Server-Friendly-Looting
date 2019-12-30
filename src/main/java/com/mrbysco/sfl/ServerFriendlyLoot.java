package com.mrbysco.sfl;

import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.entity.AbstractMimicEntity;
import com.mrbysco.sfl.init.ModEntities;
import net.minecraft.entity.SpawnReason;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SFLConfig.spawnSpec, "sfl_spawning.toml");
        FMLJavaModLoadingContext.get().getModEventBus().register(SFLConfig.class);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.addListener(ClientHandler::registerRenders);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::registerRenders);
        });
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModEntities.addSpawn();
    }

    @SubscribeEvent
    public void onSpawn(final LivingSpawnEvent.CheckSpawn event) {
        if (event.getSpawnReason().equals(SpawnReason.NATURAL) && event.getEntityLiving() instanceof AbstractMimicEntity) {
            List<String> blacklist = SFLConfig.SPAWN.dimension_blacklist.get();
            if (!blacklist.isEmpty()) {
                int dimensionID = event.getWorld().getDimension().getType().getId();
                for (String i : blacklist) {
                    if (!i.isEmpty() && i.equals(String.valueOf(dimensionID)))
                        event.setResult(Event.Result.DENY);
                }
            }
        }
    }
}
