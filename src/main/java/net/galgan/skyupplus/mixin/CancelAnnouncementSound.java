package net.galgan.skyupplus.mixin;

import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class CancelAnnouncementSound {

    @Inject(method = "onPlaySound", at = @At("HEAD"), cancellable = true)
    private void blockTipPling(PlaySoundS2CPacket packet, CallbackInfo ci) {
        if(!ServerRestrictor.isAllowed() || !Config.get().disableAnnouncements) return;

        SoundEvent sound = packet.getSound().value();

        if (sound.id().toString().equals("minecraft:block.note_block.pling") && packet.getCategory() == SoundCategory.MASTER && Math.abs(packet.getPitch() - 2.0f) < 0.001f) {
            ci.cancel();
        }
    }
}
