package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.client.state.MimicRenderState;
import com.mrbysco.sfl.entity.EndMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EndMimicRenderer extends AbstractMimicRenderer<EndMimicEntity> {
	private static final ResourceLocation TEXTURES = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_end.png");

	public EndMimicRenderer(EntityRendererProvider.Context context) {
		super(context, new MimicModel(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
	}

	@Override
	public MimicRenderState createRenderState() {
		return new MimicRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(MimicRenderState renderState) {
		return TEXTURES;
	}
}
