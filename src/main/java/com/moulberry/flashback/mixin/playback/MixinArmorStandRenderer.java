package com.moulberry.flashback.mixin.playback;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.moulberry.flashback.Flashback;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public class MixinArmorStandRenderer {

    /*
     * Disable armor stand hit animation
     * If the game time isn't updated, it'll just keep on playing it (even if it wasn't hit)
     */

    @Inject(method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V", at = @At("HEAD"), cancellable = true)
    public void setupRotations(ArmorStandRenderState armorStandRenderState, PoseStack poseStack, float f, float g, CallbackInfo ci) {
        if (Flashback.isInReplay()) {
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - g));
            ci.cancel();
        }
    }

}
