package io.alwa.featuredservers.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MultiplayerServerListWidget.class)
public interface MultiplayerServerListWidgetAccessor {

    @Accessor("SERVER_SELECTION_TEXTURE") static Identifier getServerSelectionTexture() {
        throw new AssertionError();
    }
}
