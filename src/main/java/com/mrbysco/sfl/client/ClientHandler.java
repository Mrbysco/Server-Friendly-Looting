package com.mrbysco.sfl.client;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.client.renderer.EndMimicRenderer;
import com.mrbysco.sfl.client.renderer.MimicRenderer;
import com.mrbysco.sfl.client.renderer.NetherMimicRenderer;
import com.mrbysco.sfl.client.renderer.WaterMimicRenderer;
import com.mrbysco.sfl.init.MimicRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static final ModelLayerLocation MIMIC = new ModelLayerLocation(new ResourceLocation(ServerFriendlyLoot.MOD_ID, "main"), "mimic");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(MimicRegistry.MIMIC.get(), MimicRenderer::new);
		event.registerEntityRenderer(MimicRegistry.NETHER_MIMIC.get(), NetherMimicRenderer::new);
		event.registerEntityRenderer(MimicRegistry.END_MIMIC.get(), EndMimicRenderer::new);
		event.registerEntityRenderer(MimicRegistry.WATER_MIMIC.get(), WaterMimicRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(MIMIC, MimicModel::createBodyLayer);
	}
}
