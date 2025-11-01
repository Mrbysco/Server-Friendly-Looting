package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.client.state.MimicRenderState;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NetherMimicRenderer extends MobRenderer<NetherMimicEntity, MimicRenderState, MimicModel> {
	private static final ResourceLocation NETHER1 = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_nether1.png");
	private static final ResourceLocation NETHER2 = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_nether2.png");

	public NetherMimicRenderer(EntityRendererProvider.Context context) {
		super(context, new MimicModel(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
	}

	@Override
	public MimicRenderState createRenderState() {
		return new MimicRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(MimicRenderState renderState) {
		return switch (renderState.mimicType) {
			case 1 -> NETHER2;
			default -> NETHER1;
		};
	}
}
