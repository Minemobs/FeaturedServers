package io.alwa.featuredservers.mixin;

import io.alwa.featuredservers.FeaturedList;
import io.alwa.featuredservers.FeaturedServers;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Inject(method = "drawTexture(Lnet/minecraft/util/Identifier;IIFFIIII)V", at = @At("HEAD"), cancellable = true)
    private void drawTexture(Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, CallbackInfo ci) {
        if(texture == MultiplayerServerListWidgetAccessor.getServerSelectionTexture() && FeaturedServers.getCurrentServerInfo() != null && FeaturedList.containsKey(FeaturedServers.getCurrentServerInfo().address) &&
                FeaturedList.get(FeaturedServers.getCurrentServerInfo().address).disableButtons) {
            ci.cancel();
        }
    }

}
