package net.galgan.skyupplus.mixin;

import net.galgan.skyupplus.features.Cooldown;
import net.galgan.skyupplus.util.Chat;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ActionBarAccessor {

    @Inject(method = "onOverlayMessage", at = @At("HEAD"))
    private void getActionBar(OverlayMessageS2CPacket packet, CallbackInfo ci) {
        Cooldown.bossBarMessage = packet.text();
        Chat.sendString(packet.text().getString());
    }
}