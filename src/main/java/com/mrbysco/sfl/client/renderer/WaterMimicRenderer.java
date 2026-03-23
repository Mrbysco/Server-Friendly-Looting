package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.client.state.MimicRenderState;
import com.mrbysco.sfl.entity.WaterMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class WaterMimicRenderer extends AbstractMimicRenderer<WaterMimicEntity> {
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_water.png");

	public WaterMimicRenderer(EntityRendererProvider.Context context) {
		super(context, new MimicModel(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
	}

	@Override
	public MimicRenderState createRenderState() {
		return new MimicRenderState();
	}

	@Override
	public Identifier getTextureLocation(MimicRenderState renderState) {
		return TEXTURE;
	}
}
