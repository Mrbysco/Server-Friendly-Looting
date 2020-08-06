package com.mrbysco.sfl.client;

import com.mrbysco.sfl.client.renderer.EndMimicRenderer;
import com.mrbysco.sfl.client.renderer.MimicRenderer;
import com.mrbysco.sfl.client.renderer.NetherMimicRenderer;
import com.mrbysco.sfl.client.renderer.WaterMimicRenderer;
import com.mrbysco.sfl.init.MimicRegistry;
import com.mrbysco.sfl.item.CustomSpawnEggItem;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
    public static void registerRenders(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(MimicRegistry.MIMIC.get(), MimicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MimicRegistry.NETHER_MIMIC.get(), NetherMimicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MimicRegistry.END_MIMIC.get(), EndMimicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MimicRegistry.WATER_MIMIC.get(), WaterMimicRenderer::new);
    }

    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();

        for(CustomSpawnEggItem spawneggitem : CustomSpawnEggItem.getEggs()) {
            colors.register((p_198141_1_, p_198141_2_) -> {
                return spawneggitem.getColor(p_198141_2_);
            }, spawneggitem);
        }
    }
}
