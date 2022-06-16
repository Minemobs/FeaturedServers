package io.alwa.featuredservers.mixin;

import io.alwa.featuredservers.FeaturedList;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MultiplayerScreen.class)
public abstract class MixinServerSelectionList {

    @Shadow protected MultiplayerServerListWidget serverListWidget;
    @Shadow private ButtonWidget buttonJoin, buttonDelete, buttonEdit;

    @SuppressWarnings("OverwriteAuthorRequired")
    @Overwrite
    protected void updateButtonActivationStates() {
        this.buttonJoin.active = false;
        this.buttonEdit.active = false;
        this.buttonDelete.active = false;
        MultiplayerServerListWidget.Entry serverEntry = this.serverListWidget.getSelectedOrNull();
        if (serverEntry != null && !(serverEntry instanceof MultiplayerServerListWidget.LanServerEntry)) {
            this.buttonJoin.active = true;
            if (serverEntry instanceof MultiplayerServerListWidget.ServerEntry entry) {
                if (FeaturedList.containsKey((entry).getServer().address)) {
                    boolean active = FeaturedList.get((entry).getServer().address).disableButtons;
                    this.buttonEdit.active = !active;
                    this.buttonDelete.active = !active;
                    return;
                }
                this.buttonEdit.active = true;
                this.buttonDelete.active = true;
            }
        }
    }
}
