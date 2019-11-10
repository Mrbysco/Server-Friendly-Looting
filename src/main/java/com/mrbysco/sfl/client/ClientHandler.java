package com.mrbysco.sfl.client;

import com.mrbysco.sfl.client.renderer.MimicRenderer;
import com.mrbysco.sfl.entity.MimicEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientHandler {
    public static void registerRenders(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MimicEntity.class, MimicRenderer::new);
    }
}
