package io.alwa.featuredservers.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import io.alwa.featuredservers.FeaturedList;
import io.alwa.featuredservers.FeaturedServers;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Stream;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public abstract class ServerEntryMixin {

    @Shadow @Final private ServerInfo server;

    @Shadow @Final private MultiplayerScreen screen;

    @Inject(method = "render", at = @At("HEAD"))
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        FeaturedServers.setCurrentServerInfo(server);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void renderStars(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        if(!FeaturedList.containsKey(server.address)) return;
        RenderSystem.setShaderTexture(0, new Identifier("featuredservers", "textures/gui/star.png"));
        DrawableHelper.drawTexture(matrices, x - 16, y, 0, 0, 16, 16, 16, 16);
    }

    @Inject(method = "swapEntries", at = @At("HEAD"), cancellable = true)
    public void swapEntries(int i, int j, CallbackInfo ci) {
        if(Stream.of(i, j).map(this.screen.getServerList()::get).map(serverInfo -> serverInfo.address)
                .anyMatch(ip -> FeaturedList.containsKey(ip) && FeaturedList.get(ip).disableButtons)) ci.cancel();
    }

}
