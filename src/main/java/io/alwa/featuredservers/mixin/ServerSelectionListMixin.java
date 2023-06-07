package io.alwa.featuredservers.mixin;

import io.alwa.featuredservers.FeaturedList;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class ServerSelectionListMixin {

    @Shadow protected MultiplayerServerListWidget serverListWidget;
    @Shadow private ButtonWidget buttonDelete, buttonEdit;

    @Inject(method = "updateButtonActivationStates", at = @At("TAIL"))
    protected void updateButtons(CallbackInfo ci) {
        MultiplayerServerListWidget.Entry entry = this.serverListWidget.getSelectedOrNull();
        if(entry instanceof MultiplayerServerListWidget.ServerEntry sEntry && FeaturedList.containsKey(sEntry.getServer().address)) {
            boolean active = FeaturedList.get(sEntry.getServer().address).disableButtons;
            buttonDelete.active = !active;
            buttonEdit.active = !active;
        }
    }
}
