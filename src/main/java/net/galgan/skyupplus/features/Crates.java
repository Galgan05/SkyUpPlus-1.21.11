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
    
    public static void handleCrates() {

        ScreenEvents.BEFORE_INIT.register((client, screen, w, h) -> {
            if(!ServerRestrictor.isAllowed()) return;

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

                if (Config.get().karambitToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Karambit: ").formatted(Formatting.DARK_RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().karambitDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().perunToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Perun: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().perunDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().cymofanToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cymofan: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().cymofanDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().mlotThoraToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Młot Thora: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().mlotThoraDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().urizelToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Urizel: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().urizelDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().azadaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Azada: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().azadaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().spinelToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Spinel: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().spinelDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().karpiolapToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Karpiołap: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().karpiolapDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().ethericaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Etherica: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xA956FC)).withBold(true)))
                            .append(Text.literal(String.valueOf(Config.get().ethericaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().lukLegolasaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Łuk Legolasa: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x56FCA9)).withBold(true)))
                            .append(Text.literal(String.valueOf(Config.get().lukLegolasaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().arbaletToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Arbalet: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().arbaletDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().powrotOdysaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Powrót Odysa: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().powrotOdysaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().cassisToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cassis: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().cassisDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().cuirassToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cuirass: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().cuirassDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().cuissotToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cuissot: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().cuissotDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().cossetToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Cosset: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().cossetDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().kapcieLotnikaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapcie lotnika: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().kapcieLotnikaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().rivendellToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Rivendell: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().rivendellDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().impetToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Impet: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().impetDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().phloxToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Phlox: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().phloxDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().majsterToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Majster: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().majsterDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().magiczneWiaderkoToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Magiczne wiaderko: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().magiczneWiaderkoDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().statTrackerToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("StatTracker: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().statTrackerDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().jajkoNiespodziankaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Jajko niespodzianka: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().jajkoNiespodziankaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().klejnotKupieckiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Klejnot kupiecki: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().klejnotKupieckiDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().kontrolerMagazynowToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kontroler magazynów: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().kontrolerMagazynowDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().tajemniceSkyUPaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Tajemnice SkyUPa: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().tajemniceSkyUPaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;
                
            //PLATINUM
            } else if (title.contains("\uE001\uE081\uE002") || title.contains("\uE001\uE171\uE002")) {
                color = 0xFF55FF55;
                
                if (Config.get().elementiumToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Elementium: ").formatted(Formatting.AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().elementiumDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().mendingToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Mending: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().mendingDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().emeraldBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok szmaragdu: ").formatted(Formatting.GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().emeraldBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().goldBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok złota: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().goldBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().ironBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok żelaza: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xDCDCDC)).withBold(true)))
                            .append(Text.literal(String.valueOf(Config.get().ironBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().redstoneBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok redstone: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xB01905)).withBold(true)))
                            .append(Text.literal(String.valueOf(Config.get().redstoneBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().dirtToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Ziemia: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x866043)).withBold(true)))
                            .append(Text.literal(String.valueOf(Config.get().dirtDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().grassBlockToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Blok trawy: ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x2FA81F)).withBold(true)))
                            .append(Text.literal(String.valueOf(Config.get().grassBlockDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().sandToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Piasek: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().sandDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //GÓRNICZA
            } else if (title.contains("\uE001\uE082\uE002") || title.contains("\uE001\uE172\uE002")) {
                color = 0xFFAAAAAA;

                if (Config.get().czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kask Górnika: ").formatted(Formatting.GRAY, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().kaskGornikaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //DRWALA
            } else if (title.contains("\uE001\uE083\uE002") || title.contains("\uE001\uE173\uE002")) {
                color = 0xFF00AA00;

                if (Config.get().czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Czapka Drwala: ").formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().czapkaDrwalaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //FARMERSKA
            } else if (title.contains("\uE001\uE084\uE002") || title.contains("\uE001\uE174\uE002")) {
                color = 0xFFFFFF55;

                if (Config.get().czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapelusz Farmera: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().kapeluszFarmeraDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //ŁOWIECKA
            } else if (title.contains("\uE001\uE085\uE002") || title.contains("\uE001\uE175\uE002")) {
                color = 0xFFFF5555;

                if (Config.get().czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Bandana Łowcy: ").formatted(Formatting.RED, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().bandanaLowcyDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //BUDOWLANA
            } else if (title.contains("\uE001\uE086\uE002") || title.contains("\uE001\uE176\uE002")) {
                color = 0xFF00AAAA;

                if (Config.get().czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kask Budowniczego: ").formatted(Formatting.DARK_AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().kaskBudowniczegoDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //RYBACKA
            } else if (title.contains("\uE001\uE087\uE002") || title.contains("\uE001\uE177\uE002")) {
                color = 0xFF5555FF;

                if (Config.get().czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapelusz Rybaka: ").formatted(Formatting.BLUE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().kapeluszRybakaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().oceanicznaRudaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Oceaniczna ruda: ").formatted(Formatting.DARK_AQUA, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().oceanicznaRudaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().wybitnaPrzynetaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Wybitna przynęta: ").formatted(Formatting.DARK_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().wybitnaPrzynetaDropped)).formatted(Formatting.WHITE)));
                }
                if (Config.get().wybitnaPrzynetaToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Dobra przynęta: ").formatted(Formatting.GOLD, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().dobraPrzynetaDropped)).formatted(Formatting.WHITE)));
                }
                body = bodyText;

            //CZARODZIEJSKA
            } else if (title.contains("\uE001\uE088\uE002") || title.contains("\uE001\uE178\uE002")) {
                color = 0xFFFF55FF;

                if (Config.get().czapkiToggle) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Kapelusz Czarodzieja: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.valueOf(Config.get().kapeluszCzarodziejaDropped)).formatted(Formatting.WHITE)));
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

            if (!overlay && message.getString().startsWith("SkyCase »")) {
                MinecraftClient client = MinecraftClient.getInstance();
                ClientPlayerEntity player = client.player;

                if (player != null) playerName = player.getName().getString();

                //OTHERS
                if (message.getString().startsWith("SkyCase » Wygrano: Kask Górnika")) {
                    Config.get().kaskGornikaDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Czapka Drwala")) {
                    Config.get().czapkaDrwalaDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Kapelusz Farmera")) {
                    Config.get().kapeluszFarmeraDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Bandana Łowcy")) {
                    Config.get().bandanaLowcyDropped += 1;
                    Config.save();
                    return;
                }if (message.getString().startsWith("SkyCase » Wygrano: Kask Budowniczego")) {
                    Config.get().kaskBudowniczegoDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Kapelusz Rybaka")) {
                    Config.get().kapeluszRybakaDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Kapelusz Czarodzieja")) {
                    Config.get().kapeluszCzarodziejaDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Oceaniczna ruda")) {
                    Config.get().oceanicznaRudaDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 2x Oceaniczna ruda")) {
                    Config.get().oceanicznaRudaDropped += 2;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 3x Oceaniczna ruda")) {
                    Config.get().oceanicznaRudaDropped += 3;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Wybitna przynęta")) {
                    Config.get().wybitnaPrzynetaDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Dobra przynęta")) {
                    Config.get().dobraPrzynetaDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 2x Dobra przynęta")) {
                    Config.get().dobraPrzynetaDropped += 2;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 3x Dobra przynęta")) {
                    Config.get().dobraPrzynetaDropped += 3;
                    Config.save();
                    return;
                }

                //PLATINUM
                if (message.getString().startsWith("SkyCase » Wygrano: Klucz do Elementium")) {
                    Config.get().elementiumDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Zaklęta książka (Naprawa I)")) {
                    Config.get().mendingDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 2x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 3x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 4x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 5x Blok szmaragdu")) {
                    Config.get().emeraldBlockDropped += 5;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Blok złota")) {
                    Config.get().goldBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 2x Blok złota")) {
                    Config.get().goldBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 3x Blok złota")) {
                    Config.get().goldBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 4x Blok złota")) {
                    Config.get().goldBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 5x Blok złota")) {
                    Config.get().goldBlockDropped += 5;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 6x Blok złota")) {
                    Config.get().goldBlockDropped += 6;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 7x Blok złota")) {
                    Config.get().goldBlockDropped += 7;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Blok żelaza")) {
                    Config.get().ironBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 2x Blok żelaza")) {
                    Config.get().ironBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 3x Blok żelaza")) {
                    Config.get().ironBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 4x Blok żelaza")) {
                    Config.get().ironBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 5x Blok żelaza")) {
                    Config.get().ironBlockDropped += 5;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 6x Blok żelaza")) {
                    Config.get().ironBlockDropped += 6;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: Blok redstone")) {
                    Config.get().redstoneBlockDropped += 1;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 2x Blok redstone")) {
                    Config.get().redstoneBlockDropped += 2;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 3x Blok redstone")) {
                    Config.get().redstoneBlockDropped += 3;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 4x Blok redstone")) {
                    Config.get().redstoneBlockDropped += 4;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 10x Ziemia")) {
                    Config.get().dirtDropped += 10;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 15x Ziemia")) {
                    Config.get().dirtDropped += 15;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 20x Ziemia")) {
                    Config.get().dirtDropped += 20;
                    Config.save();
                    return;

                }
                if (message.getString().startsWith("SkyCase » Wygrano: 25x Ziemia")) {
                    Config.get().dirtDropped += 25;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 30x Ziemia")) {
                    Config.get().dirtDropped += 30;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 10x Blok trawy")) {
                    Config.get().grassBlockDropped += 10;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 20x Blok trawy")) {
                    Config.get().grassBlockDropped += 20;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 30x Blok trawy")) {
                    Config.get().grassBlockDropped += 30;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 10x Piasek")) {
                    Config.get().sandDropped += 10;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 20x Piasek")) {
                    Config.get().sandDropped += 20;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » Wygrano: 30x Piasek")) {
                    Config.get().sandDropped += 30;
                    Config.save();
                    return;
                }

                //ELEMENTIUM
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Karambit ::")) {
                    Config.get().karambitDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Perun ::")) {
                    Config.get().perunDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cymofan ::")) {
                    Config.get().cymofanDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Młot Thora ::")) {
                    Config.get().mlotThoraDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Urizel ::")) {
                    Config.get().urizelDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Azada ::")) {
                    Config.get().azadaDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Spinel ::")) {
                    Config.get().spinelDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Karpiołap ::")) {
                    Config.get().karpiolapDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Etherica ::")) {
                    Config.get().ethericaDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Łuk Legolasa ::")) {
                    Config.get().lukLegolasaDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Arbalet ::")) {
                    Config.get().arbaletDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Powrót Odysa ::")) {
                    Config.get().powrotOdysaDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cassis ::")) {
                    Config.get().cassisDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cuirass ::")) {
                    Config.get().cuirassDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cuissot ::")) {
                    Config.get().cuissotDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Cosset ::")) {
                    Config.get().cossetDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Kapcie lotnika ::")) {
                    Config.get().kapcieLotnikaDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Rivendell ::")) {
                    Config.get().rivendellDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Impet ::")) {
                    Config.get().impetDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Phlox ::")) {
                    Config.get().phloxDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Majster ::")) {
                    Config.get().majsterDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Magiczne wiaderko ::")) {
                    Config.get().magiczneWiaderkoDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: StatTracker ::")) {
                    Config.get().statTrackerDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Jajko niespodzianka ::")) {
                    Config.get().jajkoNiespodziankaDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Klejnot kupiecki uniwersalny ::")) {
                    Config.get().klejnotKupieckiDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Kontroler magazynów ::")) {
                    Config.get().kontrolerMagazynowDropped++;
                    Config.save();
                    return;
                }
                if (message.getString().startsWith("SkyCase » " + playerName + " otworzył Elementium i wygrał: :: Tajemnice SkyUPa v.5 ::")) {
                    Config.get().tajemniceSkyUPaDropped++;
                    Config.save();
                }
            }
        });
    }
}