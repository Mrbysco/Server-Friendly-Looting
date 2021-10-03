package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class WaterMimicRenderer extends MobRenderer<WaterMimicEntity, MimicModel<WaterMimicEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_water.png");

    public WaterMimicRenderer(EntityRendererProvider.Context context) {
        super(context, new MimicModel<>(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(WaterMimicEntity entity) {
        return TEXTURE;
    }
}
