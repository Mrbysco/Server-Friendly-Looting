package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.MimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class MimicRenderer extends MobRenderer<MimicEntity, MimicModel<MimicEntity>> {
    private static final ResourceLocation OAK = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_oak.png");
    private static final ResourceLocation SPRUCE = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_spruce.png");
    private static final ResourceLocation BIRCH = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_birch.png");
    private static final ResourceLocation JUNGLE = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_jungle.png");
    private static final ResourceLocation ACACIA = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_acacia.png");
    private static final ResourceLocation DARK_OAK = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_dark_oak.png");

    public MimicRenderer(EntityRendererProvider.Context context) {
        super(context, new MimicModel<>(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
    }

    @Nullable
    @Override
    public ResourceLocation getTextureLocation(MimicEntity entity) {
        return switch (entity.getMimicType()) {
            default -> OAK;
            case 1 -> SPRUCE;
            case 2 -> BIRCH;
            case 3 -> JUNGLE;
            case 4 -> ACACIA;
            case 5 -> DARK_OAK;
        };
    }
}
