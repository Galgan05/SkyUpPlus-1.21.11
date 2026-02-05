package net.galgan.skyupplus.mixin;

import net.galgan.skyupplus.features.Cooldown;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class OverlayMessageHook {
    @Inject(method = "setOverlayMessage", at = @At("HEAD"))
    private void onOverlay(Text message, boolean tinted, CallbackInfo ci) {
        String s = message.getString();
        if (s.startsWith("Prace » Aktywowano Pług")) {
            Cooldown.lastPlugActivation = System.currentTimeMillis()/1000;
        }
    }
}
