package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.EndMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class EndMimicRenderer extends MobRenderer<EndMimicEntity, MimicModel<EndMimicEntity>> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_end.png");

	public EndMimicRenderer(EntityRendererProvider.Context context) {
		super(context, new MimicModel<>(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
	}

	@Nullable
	@Override
	public ResourceLocation getTextureLocation(EndMimicEntity entity) {
		return TEXTURES;
	}
}
