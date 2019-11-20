package com.mrbysco.sfl.client;

import com.mrbysco.sfl.client.renderer.EndMimicRenderer;
import com.mrbysco.sfl.client.renderer.MimicRenderer;
import com.mrbysco.sfl.client.renderer.NetherMimicRenderer;
import com.mrbysco.sfl.client.renderer.WaterMimicRenderer;
import com.mrbysco.sfl.entity.EndMimicEntity;
import com.mrbysco.sfl.entity.MimicEntity;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientHandler {
    public static void registerRenders(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MimicEntity.class, MimicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WaterMimicEntity.class, WaterMimicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NetherMimicEntity.class, NetherMimicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EndMimicEntity.class, EndMimicRenderer::new);
    }
}
