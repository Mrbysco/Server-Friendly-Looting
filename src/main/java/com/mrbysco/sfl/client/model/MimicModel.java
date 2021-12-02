package com.mrbysco.sfl.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mrbysco.sfl.entity.AbstractMimicEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class MimicModel<T extends AbstractMimicEntity> extends EntityModel<T> {
	private final ModelPart Mimic;
	private final ModelPart TopHalf;
	private final ModelPart BottomHalf;
	private final ModelPart Tongue;
	private final ModelPart LeftFeet;
	private final ModelPart LeftFeet2;
	private final ModelPart RightFeet;
	private final ModelPart RightFeet2;

	public MimicModel(ModelPart root) {
		this.Mimic = root.getChild("mimic");

		this.TopHalf = Mimic.getChild("top_half");
		this.BottomHalf = Mimic.getChild("bottom_half");
		this.Tongue = BottomHalf.getChild("tongue");
		this.LeftFeet = BottomHalf.getChild("left_feet");
		this.LeftFeet2 = BottomHalf.getChild("left_feet2");
		this.RightFeet = BottomHalf.getChild("right_feet");
		this.RightFeet2 = BottomHalf.getChild("right_feet2");
	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Mimic = partdefinition.addOrReplaceChild("mimic", CubeListBuilder.create(), PartPose.offset(-8.0F, 16.0F, 8.0F));

		Mimic.addOrReplaceChild("top_half", CubeListBuilder.create().texOffs(94, 106).addBox(7.0F, -1.0F, -16.0F, 1.0F, 1.0F, 16.0F)
				.texOffs(66, 108).addBox(-8.0F, -1.0F, -16.0F, 1.0F, 1.0F, 16.0F)
				.texOffs(98, 126).addBox(-7.0F, -1.0F, -1.0F, 14.0F, 1.0F, 1.0F)
				.texOffs(66, 126).addBox(-7.0F, -1.0F, -16.0F, 14.0F, 1.0F, 1.0F)
				.texOffs(118, 118).addBox(7.0F, -3.0F, -16.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(118, 116).addBox(7.0F, -4.0F, -15.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(118, 114).addBox(7.0F, -5.0F, -14.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(118, 97).addBox(7.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(118, 99).addBox(7.0F, -5.0F, -3.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(117, 101).addBox(7.0F, -6.0F, -5.0F, 1.0F, 1.0F, 2.0F)
				.texOffs(117, 111).addBox(7.0F, -6.0F, -13.0F, 1.0F, 1.0F, 2.0F)
				.texOffs(113, 104).addBox(7.0F, -7.0F, -11.0F, 1.0F, 1.0F, 6.0F)
				.texOffs(118, 94).addBox(7.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(94, 118).addBox(-8.0F, -3.0F, -16.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(94, 116).addBox(-8.0F, -4.0F, -15.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(94, 114).addBox(-8.0F, -5.0F, -14.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(94, 97).addBox(-8.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(94, 99).addBox(-8.0F, -5.0F, -3.0F, 1.0F, 1.0F, 1.0F)
				.texOffs(93, 101).addBox(-8.0F, -6.0F, -5.0F, 1.0F, 1.0F, 2.0F)
				.texOffs(93, 111).addBox(-8.0F, -6.0F, -13.0F, 1.0F, 1.0F, 2.0F)
				.texOffs(89, 104).addBox(-8.0F, -7.0F, -11.0F, 1.0F, 1.0F, 6.0F)
				.texOffs(94, 94).addBox(-8.0F, -3.0F, -1.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(72, 54).addBox(-7.0F, -3.0F, -15.0F, 14.0F, 2.0F, 14.0F)
				.texOffs(74, 41).addBox(-7.0F, -4.0F, -14.0F, 14.0F, 1.0F, 12.0F)
				.texOffs(76, 30).addBox(-7.0F, -5.0F, -13.0F, 14.0F, 1.0F, 10.0F)
				.texOffs(80, 23).addBox(-7.0F, -6.0F, -11.0F, 14.0F, 1.0F, 6.0F)
				.texOffs(95, 18).addBox(-2.0F, -4.0F, -15.0F, 4.0F, 1.0F, 1.0F)
				.texOffs(93, 16).addBox(-3.0F, -5.0F, -14.0F, 6.0F, 1.0F, 1.0F)
				.texOffs(92, 13).addBox(-3.0F, -6.0F, -13.0F, 6.0F, 1.0F, 2.0F)
				.texOffs(95, 11).addBox(-2.0F, -7.0F, -11.0F, 4.0F, 1.0F, 1.0F)
				.texOffs(0, 43).addBox(-7.0F, -1.0F, -2.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 43).addBox(6.0F, -1.0F, -2.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 40).addBox(-7.0F, -1.0F, -4.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 40).addBox(6.0F, -1.0F, -4.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 37).addBox(-7.0F, -1.0F, -6.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 37).addBox(6.0F, -1.0F, -6.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 34).addBox(-7.0F, -1.0F, -8.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 34).addBox(6.0F, -1.0F, -8.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 31).addBox(-7.0F, -1.0F, -10.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 31).addBox(6.0F, -1.0F, -10.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 28).addBox(-7.0F, -1.0F, -12.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 28).addBox(6.0F, -1.0F, -12.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 25).addBox(-7.0F, -1.0F, -14.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 25).addBox(6.0F, -1.0F, -14.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(30, 22).addBox(5.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(26, 22).addBox(3.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(22, 22).addBox(1.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(8, 22).addBox(-4.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(12, 22).addBox(-2.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(4, 22).addBox(-6.0F, -1.0F, -15.0F, 1.0F, 2.0F, 1.0F), PartPose.offset(8.0F, -2.0F, 0.0F));

		PartDefinition BottomHalf = Mimic.addOrReplaceChild("bottom_half", CubeListBuilder.create().texOffs(31, 86).addBox(15.0F, -2.0F, -16.0F, 1.0F, 1.0F, 16.0F)
				.texOffs(31, 107).addBox(15.0F, 5.0F, -16.0F, 1.0F, 1.0F, 16.0F)
				.texOffs(0, 88).addBox(0.0F, -2.0F, -16.0F, 1.0F, 1.0F, 16.0F)
				.texOffs(0, 109).addBox(0.0F, 5.0F, -16.0F, 1.0F, 1.0F, 16.0F)
				.texOffs(34, 106).addBox(1.0F, -2.0F, -1.0F, 14.0F, 1.0F, 1.0F)
				.texOffs(34, 126).addBox(1.0F, 5.0F, -1.0F, 14.0F, 1.0F, 1.0F)
				.texOffs(0, 106).addBox(1.0F, -2.0F, -16.0F, 14.0F, 1.0F, 1.0F)
				.texOffs(0, 126).addBox(1.0F, 5.0F, -16.0F, 14.0F, 1.0F, 1.0F)
				.texOffs(4, 116).addBox(15.0F, -1.0F, -16.0F, 1.0F, 6.0F, 1.0F)
				.texOffs(24, 117).addBox(15.0F, -1.0F, -1.0F, 1.0F, 6.0F, 1.0F)
				.texOffs(0, 117).addBox(0.0F, -1.0F, -16.0F, 1.0F, 6.0F, 1.0F)
				.texOffs(19, 117).addBox(0.0F, -1.0F, -1.0F, 1.0F, 6.0F, 1.0F)
				.texOffs(72, 72).addBox(1.0F, -1.0F, -15.0F, 14.0F, 6.0F, 14.0F)
				.texOffs(74, 95).addBox(6.0F, -1.0F, -16.0F, 4.0F, 1.0F, 1.0F)
				.texOffs(76, 97).addBox(7.0F, 0.0F, -16.0F, 2.0F, 1.0F, 1.0F)
				.texOffs(76, 102).addBox(7.0F, 3.0F, -16.0F, 2.0F, 1.0F, 1.0F)
				.texOffs(74, 99).addBox(6.0F, 1.0F, -16.0F, 4.0F, 2.0F, 1.0F)
				.texOffs(0, 0).addBox(1.0F, -3.0F, -3.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 0).addBox(14.0F, -3.0F, -3.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 3).addBox(1.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 3).addBox(14.0F, -3.0F, -5.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 6).addBox(1.0F, -3.0F, -7.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 6).addBox(14.0F, -3.0F, -7.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 9).addBox(1.0F, -3.0F, -9.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 9).addBox(14.0F, -3.0F, -9.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 12).addBox(1.0F, -3.0F, -11.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 12).addBox(14.0F, -3.0F, -11.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 15).addBox(1.0F, -3.0F, -13.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 15).addBox(14.0F, -3.0F, -13.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(30, 18).addBox(12.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(26, 18).addBox(10.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(8, 18).addBox(5.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(4, 18).addBox(3.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(34, 18).addBox(14.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F)
				.texOffs(0, 18).addBox(1.0F, -3.0F, -15.0F, 1.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

		BottomHalf.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(6, 7).addBox(-2.0F, -0.5F, -9.0F, 4.0F, 1.0F, 9.0F), PartPose.offsetAndRotation(8.0F, -1.5F, -6.0F, -0.1745F, 0.0F, 0.0F));

		BottomHalf.addOrReplaceChild("left_feet", CubeListBuilder.create().texOffs(8, 79).addBox(-1.1F, -1.0F, -0.9F, 2.0F, 4.0F, 2.0F), PartPose.offset(14.0F, 5.0F, -14.0F));

		BottomHalf.addOrReplaceChild("left_feet2", CubeListBuilder.create().texOffs(0, 79).addBox(-0.9F, -1.0F, -1.1F, 2.0F, 4.0F, 2.0F), PartPose.offset(2.0F, 5.0F, -2.0F));

		BottomHalf.addOrReplaceChild("right_feet", CubeListBuilder.create().texOffs(0, 72).addBox(-0.9F, 0.0F, -0.9F, 2.0F, 4.0F, 2.0F), PartPose.offset(2.0F, 4.0F, -14.0F));

		BottomHalf.addOrReplaceChild("right_feet2", CubeListBuilder.create().texOffs(8, 72).addBox(-1.1F, -1.0F, -1.1F, 2.0F, 4.0F, 2.0F), PartPose.offset(14.0F, 5.0F, -2.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
	
	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.TopHalf.xRot = Math.min(0, Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);

		this.LeftFeet.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.RightFeet.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.LeftFeet2.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.RightFeet2.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Mimic.render(poseStack, buffer, packedLight, packedOverlay);
	}
}