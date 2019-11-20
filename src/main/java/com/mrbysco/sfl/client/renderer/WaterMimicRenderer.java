package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class WaterMimicRenderer extends MobRenderer<WaterMimicEntity, MimicModel<WaterMimicEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_water.png");

    public WaterMimicRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MimicModel(), 0.25F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(WaterMimicEntity entity) {
        return TEXTURE;
    }
}
