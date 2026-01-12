package net.galgan.skyupplus.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class Sound {

    public static void playSound(MinecraftClient client, SoundEvent sound, SoundCategory soundCategory, float pitch) {

        if (client.player != null && client.world != null) {

            client.getSoundManager().play(
                    new PositionedSoundInstance(
                        sound.id(),
                        soundCategory,
                        1.0f,
                        pitch,
                        client.world.random,
                        false,
                        0,
                        SoundInstance.AttenuationType.NONE,
                        0.0, 0.0, 0.0,
                        true
            ));
        }
    }
}
