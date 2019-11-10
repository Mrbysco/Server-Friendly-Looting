package com.mrbysco.sfl;

import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.config.SFLConfig;
import com.mrbysco.sfl.init.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ServerFriendlyLoot.MOD_ID)
public class ServerFriendlyLoot {
    public static final String MOD_ID = "sfl";
    public static final Logger LOGGER = LogManager.getLogger();

    public ServerFriendlyLoot() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SFLConfig.spawnSpec, "sfl_spawning.toml");
        FMLJavaModLoadingContext.get().getModEventBus().register(SFLConfig.class);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

//        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            MinecraftForge.EVENT_BUS.addListener(ClientHandler::registerRenders);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientHandler::registerRenders);
        });
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        ModEntities.addSpawn();
    }
}
