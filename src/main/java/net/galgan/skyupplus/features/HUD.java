package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.util.DrawUtil;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;



public class HUD {

    public static void renderHUD() {

        HudElementRegistry.attachElementBefore(
                VanillaHudElements.CHAT,
                Identifier.of("skyupplus", "hud"),
                HUD::render
        );
    }

    public static void render(DrawContext ctx, RenderTickCounter counter) {
        if(!ServerRestrictor.isAllowed()) return;

        if (!Cooldown.body.isEmpty() && (Config.get().abilityCooldown || Config.get().dungeonCooldown)) {
            DrawUtil.drawWidget(ctx, 0xFF5555FF, Config.get().cooldownDisplayLocation, Cooldown.body);
        }

        if (!Reminders.body.isEmpty() && Config.get().toggleReminders) {
            DrawUtil.drawWidget(ctx, 0xFFAA00AA, Config.get().remindersDisplayLocation, Reminders.body);
        }

        if (!Fishing.body.isEmpty()) {
            DrawUtil.drawWidget(ctx, 0xFF00AAAA, Config.get().fishingDisplayLocation, Fishing.body);
        }

        if (!Requirements.body.isEmpty()) {
            DrawUtil.drawWidget(ctx, 0xFFFFAA00, Config.get().requirementsDisplayLocation, Requirements.body);
        }

        if (!Scoreboard.body.isEmpty() && Config.get().customScoreboard) {
            DrawUtil.drawWidget(ctx, 0xFFFFFF55, Config.get().scoreboardDisplayLocation, Scoreboard.body);
        }
    }
}
