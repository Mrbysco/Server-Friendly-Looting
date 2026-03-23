package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.client.state.MimicRenderState;
import com.mrbysco.sfl.entity.MimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class MimicRenderer extends AbstractMimicRenderer<MimicEntity> {
	private static final Identifier OAK = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_oak.png");
	private static final Identifier SPRUCE = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_spruce.png");
	private static final Identifier BIRCH = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_birch.png");
	private static final Identifier JUNGLE = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_jungle.png");
	private static final Identifier ACACIA = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_acacia.png");
	private static final Identifier DARK_OAK = Identifier.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_dark_oak.png");

	public MimicRenderer(EntityRendererProvider.Context context) {
		super(context, new MimicModel(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
	}

	@Override
	public MimicRenderState createRenderState() {
		return new MimicRenderState();
	}

	@Override
	public Identifier getTextureLocation(MimicRenderState renderState) {
		return switch (renderState.mimicType) {
			case 1 -> SPRUCE;
			case 2 -> BIRCH;
			case 3 -> JUNGLE;
			case 4 -> ACACIA;
			case 5 -> DARK_OAK;
			default -> OAK;
		};
	}
}
