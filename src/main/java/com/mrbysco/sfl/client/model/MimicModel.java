package com.mrbysco.sfl.client.model;

import com.mrbysco.sfl.entity.MimicEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;

public class MimicModel extends EntityModel<MimicEntity> {
	private final RendererModel Mimic;
	private final RendererModel TopHalf;
	private final RendererModel BottomHalf;
	private final RendererModel Tongue;
	private final RendererModel LeftFeet;
	private final RendererModel LeftFeet2;
	private final RendererModel RightFeet;
	private final RendererModel RightFeet2;

	public MimicModel() {
		textureWidth = 128;
		textureHeight = 128;

		Mimic = new RendererModel(this);
		Mimic.setRotationPoint(-8.0F, 16.0F, 8.0F);

		TopHalf = new RendererModel(this);
		TopHalf.setRotationPoint(8.0F, -2.0F, 0.0F);
		Mimic.addChild(TopHalf);
		TopHalf.cubeList.add(new ModelBox(TopHalf, 94, 106, 7.0F, -1.0F, -16.0F, 1, 1, 16, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 66, 108, -8.0F, -1.0F, -16.0F, 1, 1, 16, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 98, 126, -7.0F, -1.0F, -1.0F, 14, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 66, 126, -7.0F, -1.0F, -16.0F, 14, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 118, 118, 7.0F, -3.0F, -16.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 118, 116, 7.0F, -4.0F, -15.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 118, 114, 7.0F, -5.0F, -14.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 118, 97, 7.0F, -4.0F, -2.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 118, 99, 7.0F, -5.0F, -3.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 117, 101, 7.0F, -6.0F, -5.0F, 1, 1, 2, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 117, 111, 7.0F, -6.0F, -13.0F, 1, 1, 2, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 113, 104, 7.0F, -7.0F, -11.0F, 1, 1, 6, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 118, 94, 7.0F, -3.0F, -1.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 94, 118, -8.0F, -3.0F, -16.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 94, 116, -8.0F, -4.0F, -15.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 94, 114, -8.0F, -5.0F, -14.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 94, 97, -8.0F, -4.0F, -2.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 94, 99, -8.0F, -5.0F, -3.0F, 1, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 93, 101, -8.0F, -6.0F, -5.0F, 1, 1, 2, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 93, 111, -8.0F, -6.0F, -13.0F, 1, 1, 2, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 89, 104, -8.0F, -7.0F, -11.0F, 1, 1, 6, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 94, 94, -8.0F, -3.0F, -1.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 72, 54, -7.0F, -3.0F, -15.0F, 14, 2, 14, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 74, 41, -7.0F, -4.0F, -14.0F, 14, 1, 12, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 76, 30, -7.0F, -5.0F, -13.0F, 14, 1, 10, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 80, 23, -7.0F, -6.0F, -11.0F, 14, 1, 6, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 95, 18, -2.0F, -4.0F, -15.0F, 4, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 93, 16, -3.0F, -5.0F, -14.0F, 6, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 92, 13, -3.0F, -6.0F, -13.0F, 6, 1, 2, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 95, 11, -2.0F, -7.0F, -11.0F, 4, 1, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 0, 43, -7.0F, -1.0F, -2.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 34, 43, 6.0F, -1.0F, -2.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 0, 40, -7.0F, -1.0F, -4.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 34, 40, 6.0F, -1.0F, -4.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 0, 37, -7.0F, -1.0F, -6.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 34, 37, 6.0F, -1.0F, -6.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 0, 34, -7.0F, -1.0F, -8.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 34, 34, 6.0F, -1.0F, -8.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 0, 31, -7.0F, -1.0F, -10.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 34, 31, 6.0F, -1.0F, -10.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 0, 28, -7.0F, -1.0F, -12.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 34, 28, 6.0F, -1.0F, -12.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 0, 25, -7.0F, -1.0F, -14.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 34, 25, 6.0F, -1.0F, -14.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 30, 22, 5.0F, -1.0F, -15.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 26, 22, 3.0F, -1.0F, -15.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 22, 22, 1.0F, -1.0F, -15.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 8, 22, -4.0F, -1.0F, -15.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 12, 22, -2.0F, -1.0F, -15.0F, 1, 2, 1, 0.0F, false));
		TopHalf.cubeList.add(new ModelBox(TopHalf, 4, 22, -6.0F, -1.0F, -15.0F, 1, 2, 1, 0.0F, false));

		BottomHalf = new RendererModel(this);
		BottomHalf.setRotationPoint(0.0F, 0.0F, 0.0F);
		Mimic.addChild(BottomHalf);
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 31, 86, 15.0F, -2.0F, -16.0F, 1, 1, 16, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 31, 107, 15.0F, 5.0F, -16.0F, 1, 1, 16, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 88, 0.0F, -2.0F, -16.0F, 1, 1, 16, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 109, 0.0F, 5.0F, -16.0F, 1, 1, 16, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 106, 1.0F, -2.0F, -1.0F, 14, 1, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 126, 1.0F, 5.0F, -1.0F, 14, 1, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 106, 1.0F, -2.0F, -16.0F, 14, 1, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 126, 1.0F, 5.0F, -16.0F, 14, 1, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 4, 116, 15.0F, -1.0F, -16.0F, 1, 6, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 24, 117, 15.0F, -1.0F, -1.0F, 1, 6, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 117, 0.0F, -1.0F, -16.0F, 1, 6, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 19, 117, 0.0F, -1.0F, -1.0F, 1, 6, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 72, 72, 1.0F, -1.0F, -15.0F, 14, 6, 14, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 74, 95, 6.0F, -1.0F, -16.0F, 4, 1, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 76, 97, 7.0F, 0.0F, -16.0F, 2, 1, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 76, 102, 7.0F, 3.0F, -16.0F, 2, 1, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 74, 99, 6.0F, 1.0F, -16.0F, 4, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 0, 1.0F, -3.0F, -3.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 0, 14.0F, -3.0F, -3.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 3, 1.0F, -3.0F, -5.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 3, 14.0F, -3.0F, -5.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 6, 1.0F, -3.0F, -7.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 6, 14.0F, -3.0F, -7.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 9, 1.0F, -3.0F, -9.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 9, 14.0F, -3.0F, -9.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 12, 1.0F, -3.0F, -11.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 12, 14.0F, -3.0F, -11.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 15, 1.0F, -3.0F, -13.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 15, 14.0F, -3.0F, -13.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 30, 18, 12.0F, -3.0F, -15.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 26, 18, 10.0F, -3.0F, -15.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 8, 18, 5.0F, -3.0F, -15.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 4, 18, 3.0F, -3.0F, -15.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 34, 18, 14.0F, -3.0F, -15.0F, 1, 2, 1, 0.0F, false));
		BottomHalf.cubeList.add(new ModelBox(BottomHalf, 0, 18, 1.0F, -3.0F, -15.0F, 1, 2, 1, 0.0F, false));

		Tongue = new RendererModel(this);
		Tongue.setRotationPoint(8.0F, -1.5F, -6.0F);
		setRotationAngle(Tongue, -0.1745F, 0.0F, 0.0F);
		BottomHalf.addChild(Tongue);
		Tongue.cubeList.add(new ModelBox(Tongue, 6, 7, -2.0F, -0.5F, -9.0F, 4, 1, 9, 0.0F, false));

		LeftFeet = new RendererModel(this);
		LeftFeet.setRotationPoint(14.0F, 5.0F, -14.0F);
		BottomHalf.addChild(LeftFeet);
		LeftFeet.cubeList.add(new ModelBox(LeftFeet, 8, 79, -1.1F, -1.0F, -0.9F, 2, 4, 2, 0.0F, false));

		LeftFeet2 = new RendererModel(this);
		LeftFeet2.setRotationPoint(2.0F, 5.0F, -2.0F);
		BottomHalf.addChild(LeftFeet2);
		LeftFeet2.cubeList.add(new ModelBox(LeftFeet2, 0, 79, -0.9F, -1.0F, -1.1F, 2, 4, 2, 0.0F, false));

		RightFeet = new RendererModel(this);
		RightFeet.setRotationPoint(2.0F, 4.0F, -14.0F);
		BottomHalf.addChild(RightFeet);
		RightFeet.cubeList.add(new ModelBox(RightFeet, 0, 72, -0.9F, 0.0F, -0.9F, 2, 4, 2, 0.0F, false));

		RightFeet2 = new RendererModel(this);
		RightFeet2.setRotationPoint(14.0F, 5.0F, -2.0F);
		BottomHalf.addChild(RightFeet2);
		RightFeet2.cubeList.add(new ModelBox(RightFeet2, 8, 72, -1.1F, -1.0F, -1.1F, 2, 4, 2, 0.0F, false));
	}

	@Override
	public void render(MimicEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Mimic.render(f5);
	}

	public void setRotationAngle(RendererModel RendererModel, float x, float y, float z) {
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(MimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

		TopHalf.rotateAngleX = Math.min(0, MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);

		this.LeftFeet.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.RightFeet.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.LeftFeet2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.RightFeet2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}
}