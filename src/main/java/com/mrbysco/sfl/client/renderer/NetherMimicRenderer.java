package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class NetherMimicRenderer extends MobRenderer<NetherMimicEntity, MimicModel<NetherMimicEntity>> {
    private static final ResourceLocation NETHER1 = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_nether1.png");
    private static final ResourceLocation NETHER2 = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_nether2.png");

    public NetherMimicRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MimicModel(), 0.25F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(NetherMimicEntity entity) {
        switch(entity.getMimicType()) {
            default:
                return NETHER1;
            case 1:
                return NETHER2;
        }
    }
}
