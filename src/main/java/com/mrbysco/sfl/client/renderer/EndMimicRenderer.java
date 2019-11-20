package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.EndMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class EndMimicRenderer extends MobRenderer<EndMimicEntity, MimicModel<EndMimicEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_end.png");

    public EndMimicRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MimicModel(), 0.25F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EndMimicEntity entity) {
        return TEXTURES;
    }
}
