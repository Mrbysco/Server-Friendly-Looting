package com.mrbysco.sfl.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mrbysco.sfl.entity.AbstractMimicEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MimicModel<T extends AbstractMimicEntity> extends EntityModel<T> {
	private final ModelRenderer Mimic;
	private final ModelRenderer TopHalf;
	private final ModelRenderer BottomHalf;
	private final ModelRenderer Tongue;
	private final ModelRenderer LeftFeet;
	private final ModelRenderer LeftFeet2;
	private final ModelRenderer RightFeet;
	private final ModelRenderer RightFeet2;

	public MimicModel() {
		textureWidth = 128;
		textureHeight = 128;

		Mimic = new ModelRenderer(this);
		Mimic.setRotationPoint(-8.0F, 16.0F, 8.0F);
		

		TopHalf = new ModelRenderer(this);
		TopHalf.setRotationPoint(8.0F, -2.0F, 0.0F);
		Mimic.addChild(TopHalf);
		TopHalf.setTextureOffset(94, 106).addBox(7.0F, -1.0F, -16.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
		TopHalf.setTextureOffset(66, 108).addBox(-8.0F, -1.0F, -16.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
		TopHalf.setTextureOffset(98, 126).addBox(-7.0F, -1.0F, -1.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(66, 126).addBox(-7.0F, -1.0F, -16.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(118, 118).addBox(7.0F, -3.0F, -16.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(118, 116).addBox(7.0F, -4.0F, -15.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(118, 114).addBox(7.0F, -5.0F, -14.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(118, 97).addBox(7.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(118, 99).addBox(7.0F, -5.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(117, 101).addBox(7.0F, -6.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		TopHalf.setTextureOffset(117, 111).addBox(7.0F, -6.0F, -13.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		TopHalf.setTextureOffset(113, 104).addBox(7.0F, -7.0F, -11.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		TopHalf.setTextureOffset(118, 94).addBox(7.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(94, 118).addBox(-8.0F, -3.0F, -16.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(94, 116).addBox(-8.0F, -4.0F, -15.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(94, 114).addBox(-8.0F, -5.0F, -14.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(94, 97).addBox(-8.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(94, 99).addBox(-8.0F, -5.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(93, 101).addBox(-8.0F, -6.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		TopHalf.setTextureOffset(93, 111).addBox(-8.0F, -6.0F, -13.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		TopHalf.setTextureOffset(89, 104).addBox(-8.0F, -7.0F, -11.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		TopHalf.setTextureOffset(94, 94).addBox(-8.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(72, 54).addBox(-7.0F, -3.0F, -15.0F, 14.0F, 2.0F, 14.0F, 0.0F, false);
		TopHalf.setTextureOffset(74, 41).addBox(-7.0F, -4.0F, -14.0F, 14.0F, 1.0F, 12.0F, 0.0F, false);
		TopHalf.setTextureOffset(76, 30).addBox(-7.0F, -5.0F, -13.0F, 14.0F, 1.0F, 10.0F, 0.0F, false);
		TopHalf.setTextureOffset(80, 23).addBox(-7.0F, -6.0F, -11.0F, 14.0F, 1.0F, 6.0F, 0.0F, false);
		TopHalf.setTextureOffset(95, 18).addBox(-2.0F, -4.0F, -15.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(93, 16).addBox(-3.0F, -5.0F, -14.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(92, 13).addBox(-3.0F, -6.0F, -13.0F, 6.0F, 1.0F, 2.0F, 0.0F, false);
		TopHalf.setTextureOffset(95, 11).addBox(-2.0F, -7.0F, -11.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(0, 43).addBox(-7.0F, -1.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(34, 43).addBox(6.0F, -1.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(0, 40).addBox(-7.0F, -1.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(34, 40).addBox(6.0F, -1.0F, -4.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(0, 37).addBox(-7.0F, -1.0F, -6.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(34, 37).addBox(6.0F, -1.0F, -6.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(0, 34).addBox(-7.0F, -1.0F, -8.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(34, 34).addBox(6.0F, -1.0F, -8.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(0, 31).addBox(-7.0F, -1.0F, -10.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(34, 31).addBox(6.0F, -1.0F, -10.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(0, 28).addBox(-7.0F, -1.0F, -12.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(34, 28).addBox(6.0F, -1.0F, -12.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(0, 25).addBox(-7.0F, -1.0F, -14.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(34, 25).addBox(6.0F, -1.0F, -14.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(30, 22).addBox(5.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(26, 22).addBox(3.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(22, 22).addBox(1.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(8, 22).addBox(-4.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(12, 22).addBox(-2.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		TopHalf.setTextureOffset(4, 22).addBox(-6.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		BottomHalf = new ModelRenderer(this);
		BottomHalf.setRotationPoint(0.0F, 0.0F, 0.0F);
		Mimic.addChild(BottomHalf);
		BottomHalf.setTextureOffset(31, 86).addBox(15.0F, -2.0F, -16.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
		BottomHalf.setTextureOffset(31, 107).addBox(15.0F, 5.0F, -16.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 88).addBox(0.0F, -2.0F, -16.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 109).addBox(0.0F, 5.0F, -16.0F, 1.0F, 1.0F, 16.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 106).addBox(1.0F, -2.0F, -1.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 126).addBox(1.0F, 5.0F, -1.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 106).addBox(1.0F, -2.0F, -16.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 126).addBox(1.0F, 5.0F, -16.0F, 14.0F, 1.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(4, 116).addBox(15.0F, -1.0F, -16.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(24, 117).addBox(15.0F, -1.0F, -1.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 117).addBox(0.0F, -1.0F, -16.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(19, 117).addBox(0.0F, -1.0F, -1.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(72, 72).addBox(1.0F, -1.0F, -15.0F, 14.0F, 6.0F, 14.0F, 0.0F, false);
		BottomHalf.setTextureOffset(74, 95).addBox(6.0F, -1.0F, -16.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(76, 97).addBox(7.0F, 0.0F, -16.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(76, 102).addBox(7.0F, 3.0F, -16.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(74, 99).addBox(6.0F, 1.0F, -16.0F, 4.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 0).addBox(1.0F, -3.0F, -3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 0).addBox(14.0F, -3.0F, -3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 3).addBox(1.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 3).addBox(14.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 6).addBox(1.0F, -3.0F, -7.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 6).addBox(14.0F, -3.0F, -7.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 9).addBox(1.0F, -3.0F, -9.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 9).addBox(14.0F, -3.0F, -9.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 12).addBox(1.0F, -3.0F, -11.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 12).addBox(14.0F, -3.0F, -11.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 15).addBox(1.0F, -3.0F, -13.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 15).addBox(14.0F, -3.0F, -13.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(30, 18).addBox(12.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(26, 18).addBox(10.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(8, 18).addBox(5.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(4, 18).addBox(3.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(34, 18).addBox(14.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
		BottomHalf.setTextureOffset(0, 18).addBox(1.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		Tongue = new ModelRenderer(this);
		Tongue.setRotationPoint(8.0F, -1.5F, -6.0F);
		BottomHalf.addChild(Tongue);
		setRotationAngle(Tongue, -0.1745F, 0.0F, 0.0F);
		Tongue.setTextureOffset(6, 7).addBox(-2.0F, -0.5F, -9.0F, 4.0F, 1.0F, 9.0F, 0.0F, false);

		LeftFeet = new ModelRenderer(this);
		LeftFeet.setRotationPoint(14.0F, 5.0F, -14.0F);
		BottomHalf.addChild(LeftFeet);
		LeftFeet.setTextureOffset(8, 79).addBox(-1.1F, -1.0F, -0.9F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		LeftFeet2 = new ModelRenderer(this);
		LeftFeet2.setRotationPoint(2.0F, 5.0F, -2.0F);
		BottomHalf.addChild(LeftFeet2);
		LeftFeet2.setTextureOffset(0, 79).addBox(-0.9F, -1.0F, -1.1F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		RightFeet = new ModelRenderer(this);
		RightFeet.setRotationPoint(2.0F, 4.0F, -14.0F);
		BottomHalf.addChild(RightFeet);
		RightFeet.setTextureOffset(0, 72).addBox(-0.9F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		RightFeet2 = new ModelRenderer(this);
		RightFeet2.setRotationPoint(14.0F, 5.0F, -2.0F);
		BottomHalf.addChild(RightFeet2);
		RightFeet2.setTextureOffset(8, 72).addBox(-1.1F, -1.0F, -1.1F, 2.0F, 4.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(AbstractMimicEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Mimic.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}