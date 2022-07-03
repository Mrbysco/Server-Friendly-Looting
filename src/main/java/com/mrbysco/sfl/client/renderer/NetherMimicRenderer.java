package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.ServerFriendlyLoot;
import com.mrbysco.sfl.client.ClientHandler;
import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.entity.NetherMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class NetherMimicRenderer extends MobRenderer<NetherMimicEntity, MimicModel<NetherMimicEntity>> {
	private static final ResourceLocation NETHER1 = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_nether1.png");
	private static final ResourceLocation NETHER2 = new ResourceLocation(ServerFriendlyLoot.MOD_ID, "textures/entity/mimic_nether2.png");

	public NetherMimicRenderer(EntityRendererProvider.Context context) {
		super(context, new MimicModel<>(context.bakeLayer(ClientHandler.MIMIC)), 0.25F);
	}

	@Nullable
	@Override
	public ResourceLocation getTextureLocation(NetherMimicEntity entity) {
		return switch (entity.getMimicType()) {
			default -> NETHER1;
			case 1 -> NETHER2;
		};
	}
}
