package io.alwa.featuredservers.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.alwa.featuredservers.FeaturedList;
import io.alwa.featuredservers.FeaturedServers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawableHelper.class)
public abstract class DrawableHelperMixin {

    @Inject(method = "drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIFFIIII)V", at = @At("HEAD"), cancellable = true)
    private static void drawTexture(MatrixStack matrices, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, CallbackInfo ci) {
        if(RenderSystem.getShaderTexture(0) == MinecraftClient.getInstance().getTextureManager()
                .getTexture(MultiplayerServerListWidgetAccessor.getServerSelectionTexture()).getGlId() && u > 1 &&
                FeaturedServers.getCurrentServerInfo() != null && FeaturedList.containsKey(FeaturedServers.getCurrentServerInfo().address) &&
                FeaturedList.get(FeaturedServers.getCurrentServerInfo().address).disableButtons) {
            ci.cancel();
        }
    }

}
