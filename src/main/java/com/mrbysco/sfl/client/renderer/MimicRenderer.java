package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.client.state.MimicRenderState;
import com.mrbysco.sfl.entity.MimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MimicRenderer extends AbstractMimicRenderer<MimicEntity> {
	private static final ResourceLocation OAK = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_oak.png");
	private static final ResourceLocation SPRUCE = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_spruce.png");
	private static final ResourceLocation BIRCH = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_birch.png");
	private static final ResourceLocation JUNGLE = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_jungle.png");
	private static final ResourceLocation ACACIA = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_acacia.png");
	private static final ResourceLocation DARK_OAK = ResourceLocation.fromNamespaceAndPath(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_dark_oak.png");

	public MimicRenderer(EntityRendererProvider.Context context) {
		super(context, new MimicModel(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
	}

	@Override
	public MimicRenderState createRenderState() {
		return new MimicRenderState();
	}

	@Override
	public ResourceLocation getTextureLocation(MimicRenderState renderState) {
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
