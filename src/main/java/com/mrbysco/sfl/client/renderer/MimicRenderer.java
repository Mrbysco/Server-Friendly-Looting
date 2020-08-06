package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.MimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel<MimicEntity>> {
    private static final ResourceLocation OAK = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_oak.png");
    private static final ResourceLocation SPRUCE = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_spruce.png");
    private static final ResourceLocation BIRCH = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_birch.png");
    private static final ResourceLocation JUNGLE = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_jungle.png");
    private static final ResourceLocation ACACIA = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_acacia.png");
    private static final ResourceLocation DARK_OAK = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_dark_oak.png");

    public MimicRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MimicModel(), 0.25F);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(MimicEntity entity) {
        switch(entity.getMimicType()) {
            default:
                return OAK;
            case 1:
                return SPRUCE;
            case 2:
                return BIRCH;
            case 3:
                return JUNGLE;
            case 4:
                return ACACIA;
            case 5:
                return DARK_OAK;
        }
    }
}
