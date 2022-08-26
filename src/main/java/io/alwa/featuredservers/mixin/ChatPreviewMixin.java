package io.alwa.featuredservers.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.network.ServerInfo.ChatPreview;

@Mixin(ChatPreview.class)
public interface ChatPreviewMixin {
    @Accessor("toastShown")
    public void setToastShown(boolean toastShown);
}
