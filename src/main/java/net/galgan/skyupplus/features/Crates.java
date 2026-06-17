package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.util.DrawUtil;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class Crates {

    private static String playerName;
    public static List<Text> body = new ArrayList<>();
    public static int color = 0xFFFFFFFF;
    
    public static void register() {

        ScreenEvents.BEFORE_INIT.register((client, screen, w, h) -> {
            if(!ServerRestrictor.isAllowed()) return;

            var cfg = Config.get();

            List<Text> bodyText = new ArrayList<>();

            if (!(screen instanceof HandledScreen<?> handled)) {
                body = bodyText;
                color = 0xFFFFFFFF;
                return;
            }

            String title = handled.getTitle().getString();

            //ELEMENTIUM
            if (title.contains("\uE001\uE080\uE002") || title.contains("\uE001\uE170\uE002")) {
                color = 0xFF55FFFF;

                if (cfg.karambitToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Karambit: ").formatted(Formatting.DARK_RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.karambitDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.perunToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Perun: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.perunDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.cymofanToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cymofan: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.cymofanDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.mlotThoraToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Młot Thora: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.mlotThoraDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.urizelToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Urizel: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.urizelDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.azadaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Azada: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.azadaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.spinelToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Spinel: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.spinelDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.karpiolapToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Karpiołap: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.karpiolapDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.ethericaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Etherica: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xA956FC)).withBold(true)))
                            .append(Text.literal(String.valueOf(cfg.ethericaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.lukLegolasaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Łuk Legolasa: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x56FCA9)).withBold(true)))
                            .append(Text.literal(String.valueOf(cfg.lukLegolasaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.arbaletToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Arbalet: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.arbaletDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.powrotOdysaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Powrót Odysa: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.powrotOdysaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.cassisToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cassis: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.cassisDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.cuirassToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cuirass: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.cuirassDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.cuissotToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cuissot: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.cuissotDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.cossetToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cosset: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.cossetDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.kapcieLotnikaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapcie lotnika: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.kapcieLotnikaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.rivendellToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Rivendell: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.rivendellDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.impetToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Impet: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.impetDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.phloxToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Phlox: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.phloxDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.majsterToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Majster: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.majsterDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.magiczneWiaderkoToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Magiczne wiaderko: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.magiczneWiaderkoDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.statTrackerToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("StatTracker: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.statTrackerDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.jajkoNiespodziankaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Jajko niespodzianka: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.jajkoNiespodziankaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.klejnotKupieckiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Klejnot kupiecki: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.klejnotKupieckiDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.kontrolerMagazynowToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kontroler magazynów: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.kontrolerMagazynowDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.tajemniceSkyUPaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Tajemnice SkyUPa: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.tajemniceSkyUPaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //PLATINUM
            } else if (title.contains("\uE001\uE081\uE002") || title.contains("\uE001\uE171\uE002")) {
                color = 0xFF55FF55;

                if (cfg.elementiumToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Elementium: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.elementiumDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.mendingToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Mending: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.mendingDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.emeraldBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok szmaragdu: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.emeraldBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.goldBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok złota: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.goldBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.ironBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok żelaza: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xDCDCDC)).withBold(true)))
                            .append(Text.literal(String.valueOf(cfg.ironBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.redstoneBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok redstone: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xB01905)).withBold(true)))
                            .append(Text.literal(String.valueOf(cfg.redstoneBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.dirtToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Ziemia: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x866043)).withBold(true)))
                            .append(Text.literal(String.valueOf(cfg.dirtDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.grassBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok trawy: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x2FA81F)).withBold(true)))
                            .append(Text.literal(String.valueOf(cfg.grassBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.sandToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Piasek: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.sandDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //GÓRNICZA
            } else if (title.contains("\uE001\uE082\uE002") || title.contains("\uE001\uE172\uE002")) {
                color = 0xFFAAAAAA;

                if (cfg.czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kask Górnika: ").formatted(Formatting.GRAY, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.kaskGornikaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //DRWALA
            } else if (title.contains("\uE001\uE083\uE002") || title.contains("\uE001\uE173\uE002")) {
                color = 0xFF00AA00;

                if (cfg.czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Czapka Drwala: ").formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.czapkaDrwalaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //FARMERSKA
            } else if (title.contains("\uE001\uE084\uE002") || title.contains("\uE001\uE174\uE002")) {
                color = 0xFFFFFF55;

                if (cfg.czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapelusz Farmera: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.kapeluszFarmeraDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //ŁOWIECKA
            } else if (title.contains("\uE001\uE085\uE002") || title.contains("\uE001\uE175\uE002")) {
                color = 0xFFFF5555;

                if (cfg.czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Bandana Łowcy: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.bandanaLowcyDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //BUDOWLANA
            } else if (title.contains("\uE001\uE086\uE002") || title.contains("\uE001\uE176\uE002")) {
                color = 0xFF00AAAA;

                if (cfg.czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kask Budowniczego: ").formatted(Formatting.DARK_AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.kaskBudowniczegoDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //RYBACKA
            } else if (title.contains("\uE001\uE087\uE002") || title.contains("\uE001\uE177\uE002")) {
                color = 0xFF5555FF;

                if (cfg.czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapelusz Rybaka: ").formatted(Formatting.BLUE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.kapeluszRybakaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.oceanicznaRudaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Oceaniczna ruda: ").formatted(Formatting.DARK_AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.oceanicznaRudaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.wybitnaPrzynetaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Wybitna przynęta: ").formatted(Formatting.DARK_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.wybitnaPrzynetaDropped)).formatted(Formatting.WHITE)));
                }
                if (cfg.wybitnaPrzynetaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Dobra przynęta: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.dobraPrzynetaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //CZARODZIEJSKA
            } else if (title.contains("\uE001\uE088\uE002") || title.contains("\uE001\uE178\uE002")) {
                color = 0xFFFF55FF;

                if (cfg.czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapelusz Czarodzieja: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(cfg.kapeluszCzarodziejaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //NEITHER
            } else {
                body = bodyText;
            }

            if (!body.isEmpty()) {
                ScreenEvents.afterRender(screen).register((s, ctx, mouseX, mouseY, delta) -> DrawUtil.drawWidget(ctx, color, Config.DisplayLocation.MIDDLE_LEFT, body));
            }
        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if(!ServerRestrictor.isAllowed()) return;

            String msgString = message.getString();

            if (!overlay && msgString.startsWith("SkyCase »")) {
                MinecraftClient client = MinecraftClient.getInstance();
                ClientPlayerEntity player = client.player;

                if (player != null) playerName = player.getName().getString();

                //OTHERS
                if (msgString.startsWith("SkyCase » Wygrano: Kask Górnika")) {
                    Config.get().kaskGornikaDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Czapka Drwala")) {
                    Config.get().czapkaDrwalaDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Kapelusz Farmera")) {
                    Config.get().kapeluszFarmeraDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Bandana Łowcy")) {
                    Config.get().bandanaLowcyDropped += 1;
                    Config.save();
                    return;
                }if (msgString.startsWith("SkyCase » Wygrano: Kask Budowniczego")) {
                    Config.get().kaskBudowniczegoDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Kapelusz Rybaka")) {
                    Config.get().kapeluszRybakaDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Kapelusz Czarodzieja")) {
                    Config.get().kapeluszCzarodziejaDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Oceaniczna ruda")) {
                    Config.get().oceanicznaRudaDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 2x Oceaniczna ruda")) {
                    Config.get().oceanicznaRudaDropped += 2;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 3x Oceaniczna ruda")) {
                    Config.get().oceanicznaRudaDropped += 3;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Wybitna przynęta")) {
                    Config.get().wybitnaPrzynetaDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Dobra przynęta")) {
                    Config.get().dobraPrzynetaDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 2x Dobra przynęta")) {
                    Config.get().dobraPrzynetaDropped += 2;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 3x Dobra przynęta")) {
                    Config.get().dobraPrzynetaDropped += 3;
                    Config.save();
                    return;
                }

                //PLATINUM
                if (msgString.startsWith("SkyCase » Wygrano: Klucz do Elementium")) {
                    Config.get().elementiumDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Zaklęta książka (Naprawa I)")) {
                    Config.get().mendingDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 2x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 3x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 4x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 5x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 5;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Blok złota")) {
                    Config.get().goldBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 2x Blok złota")) {
                    Config.get().goldBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 3x Blok złota")) {
                    Config.get().goldBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 4x Blok złota")) {
                    Config.get().goldBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 5x Blok złota")) {
                    Config.get().goldBlockDropped += 5;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 6x Blok złota")) {
                    Config.get().goldBlockDropped += 6;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 7x Blok złota")) {
                    Config.get().goldBlockDropped += 7;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Blok żelaza")) {
                    Config.get().ironBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 2x Blok żelaza")) {
                    Config.get().ironBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 3x Blok żelaza")) {
                    Config.get().ironBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 4x Blok żelaza")) {
                    Config.get().ironBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 5x Blok żelaza")) {
                    Config.get().ironBlockDropped += 5;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 6x Blok żelaza")) {
                    Config.get().ironBlockDropped += 6;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: Blok redstone")) {
                    Config.get().redstoneBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 2x Blok redstone")) {
                    Config.get().redstoneBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 3x Blok redstone")) {
                    Config.get().redstoneBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 4x Blok redstone")) {
                    Config.get().redstoneBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 10x Ziemia")) {
                    Config.get().dirtDropped += 10;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 15x Ziemia")) {
                    Config.get().dirtDropped += 15;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 20x Ziemia")) {
                    Config.get().dirtDropped += 20;
                    Config.save();
                    return;

                }
                if (msgString.startsWith("SkyCase » Wygrano: 25x Ziemia")) {
                    Config.get().dirtDropped += 25;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 30x Ziemia")) {
                    Config.get().dirtDropped += 30;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 10x Blok trawy")) {
                    Config.get().grassBlockDropped += 10;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 20x Blok trawy")) {
                    Config.get().grassBlockDropped += 20;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 30x Blok trawy")) {
                    Config.get().grassBlockDropped += 30;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 10x Piasek")) {
                    Config.get().sandDropped += 10;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 20x Piasek")) {
                    Config.get().sandDropped += 20;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » Wygrano: 30x Piasek")) {
                    Config.get().sandDropped += 30;
                    Config.save();
                    return;
                }

                //ELEMENTIUM
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Karambit ::")) {
                    Config.get().karambitDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Perun ::")) {
                    Config.get().perunDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cymofan ::")) {
                    Config.get().cymofanDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Młot Thora ::")) {
                    Config.get().mlotThoraDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Urizel ::")) {
                    Config.get().urizelDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Azada ::")) {
                    Config.get().azadaDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Spinel ::")) {
                    Config.get().spinelDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Karpiołap ::")) {
                    Config.get().karpiolapDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Etherica ::")) {
                    Config.get().ethericaDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Łuk Legolasa ::")) {
                    Config.get().lukLegolasaDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Arbalet ::")) {
                    Config.get().arbaletDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Powrót Odysa ::")) {
                    Config.get().powrotOdysaDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cassis ::")) {
                    Config.get().cassisDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cuirass ::")) {
                    Config.get().cuirassDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cuissot ::")) {
                    Config.get().cuissotDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cosset ::")) {
                    Config.get().cossetDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Kapcie lotnika ::")) {
                    Config.get().kapcieLotnikaDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Rivendell ::")) {
                    Config.get().rivendellDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Impet ::")) {
                    Config.get().impetDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Phlox ::")) {
                    Config.get().phloxDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Majster ::")) {
                    Config.get().majsterDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Magiczne wiaderko ::")) {
                    Config.get().magiczneWiaderkoDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: StatTracker ::")) {
                    Config.get().statTrackerDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Jajko niespodzianka ::")) {
                    Config.get().jajkoNiespodziankaDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Klejnot kupiecki uniwersalny ::")) {
                    Config.get().klejnotKupieckiDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Kontroler magazynów ::")) {
                    Config.get().kontrolerMagazynowDropped++;
                    Config.save();
                    return;
                }
                if (msgString.startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Tajemnice SkyUPa v.5 ::")) {
                    Config.get().tajemniceSkyUPaDropped++;
                    Config.save();
                }
            }
        });
    }
}