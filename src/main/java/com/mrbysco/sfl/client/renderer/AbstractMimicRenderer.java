package com.mrbysco.sfl.client.renderer;

import com.mrbysco.sfl.client.model.MimicModel;
import com.mrbysco.sfl.client.state.MimicRenderState;
import com.mrbysco.sfl.entity.AbstractMimicEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;

public abstract class AbstractMimicRenderer<T extends AbstractMimicEntity> extends MobRenderer<T, MimicRenderState, MimicModel> {
	public AbstractMimicRenderer(EntityRendererProvider.Context context, MimicModel model, float shadow) {
		super(context, model, shadow);
	}

	@Override
	public void extractRenderState(T mimic, MimicRenderState renderState, float partialTick) {
		super.extractRenderState(mimic, renderState, partialTick);
		renderState.mimicType = mimic.getMimicType();
	}
}
