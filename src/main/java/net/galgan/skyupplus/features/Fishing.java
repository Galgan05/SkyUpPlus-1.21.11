package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class Fishing {
    public static boolean isFishing;
    public static List<Text> body = new ArrayList<>();

    public static void handleFishing() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(!ServerRestrictor.isAllowed() || client.player == null) return;

            ItemStack mainHand = client.player.getMainHandStack();
            ItemStack offHand = client.player.getOffHandStack();

            isFishing = mainHand.isOf(Items.FISHING_ROD) || offHand.isOf(Items.FISHING_ROD);

            body = getBody();
        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if(!ServerRestrictor.isAllowed()) return;

            if (!overlay && message.getString().startsWith("Wędkarstwo » ")) {

                if (message.getString().startsWith("Wędkarstwo » Wyłowiono: Niew")) {
                    Config.get().niewielkaCount++;
                }
                if (message.getString().startsWith("Wędkarstwo » Wyłowiono: Prze")) {
                    Config.get().przecietnaCount++;
                }
                if (message.getString().startsWith("Wędkarstwo » Wyłowiono: Wymi")) {
                    Config.get().wymiarowaCount++;
                }
                if (message.getString().startsWith("Wędkarstwo » Wyłowiono: Ogro")) {
                    Config.get().ogromnaCount++;
                }
                if (message.getString().startsWith("Wędkarstwo » Wyłowiono: Mamu")) {
                    Config.get().mamuciaCount++;
                }
                if (message.getString().startsWith("Wędkarstwo » Sprz")) {
                    String price = message.getString().split("za ")[1].split(" SC")[0];
                    price = price.replace(",", ".");
                    price = price.replace(" ", "");
                    double earned = Double.parseDouble(price);
                    Config.get().totalEarned += earned;
                }
                if (message.getString().startsWith("Wędkarstwo » Wyłowiono:")) {
                    String mass = message.getString().split(" \\(")[1].split("g, ")[0];
                    mass = mass.replace(",", ".");
                    mass = mass.replace(" ", "");
                    double weight = Double.parseDouble(mass);

                    Config.get().totalWeight += weight;

                    if (weight > Config.get().biggestWeight || Config.get().biggestWeight == 0) Config.get().biggestWeight = weight;
                    if (weight < Config.get().smallestWeight || Config.get().smallestWeight == 0) Config.get().smallestWeight = weight;
                }

                Config.get().totalCount = Config.get().niewielkaCount + Config.get().przecietnaCount + Config.get().wymiarowaCount + Config.get().ogromnaCount + Config.get().mamuciaCount;
                Config.save();
            }
        });
    }

    public static List<Text> getBody() {
        List<Text> bodyText = new ArrayList<>();

        if (Config.get().fishingDisplayBehavior == Config.ConditionalDisplayBehavior.ALWAYS || (Fishing.isFishing && Config.get().fishingDisplayBehavior == Config.ConditionalDisplayBehavior.HOLDING_ITEM)) {

            if (Config.get().toggleNiewielka) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Niewielka: ").formatted(Formatting.GRAY, Formatting.BOLD))
                        .append(Text.literal(String.valueOf(Config.get().niewielkaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().togglePrzecietna) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Przeciętna: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                        .append(Text.literal(String.valueOf(Config.get().przecietnaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleWymiarowa) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Wymiarowa: ").formatted(Formatting.GREEN, Formatting.BOLD))
                        .append(Text.literal(String.valueOf(Config.get().wymiarowaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleOgromna) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Ogromna: ").formatted(Formatting.GOLD, Formatting.BOLD))
                        .append(Text.literal(String.valueOf(Config.get().ogromnaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleMamucia) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Mamucia: ").formatted(Formatting.DARK_PURPLE, Formatting.BOLD))
                        .append(Text.literal(String.valueOf(Config.get().mamuciaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleSuma) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Suma: ").formatted(Formatting.BLUE, Formatting.BOLD))
                        .append(Text.literal(String.valueOf(Config.get().totalCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleZarobek) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Zarobek: ").formatted(Formatting.AQUA, Formatting.BOLD))
                        .append(Text.literal(String.format("%.2f", Config.get().totalEarned)).append(" SC").formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleWaga) {
                if (Config.get().totalWeight < 1000) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Waga: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.format("%.2f", Config.get().totalWeight)).append("g").formatted(Formatting.WHITE)));
                } else if (Config.get().totalWeight < 1000000) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Waga: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.format("%.2f", Config.get().totalWeight/1000)).append("kg").formatted(Formatting.WHITE)));
                } else if (Config.get().totalWeight >= 1000000) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Waga: ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                            .append(Text.literal(String.format("%.3f", Config.get().totalWeight/1000000)).append("t").formatted(Formatting.WHITE)));
                }
            }
            if (Config.get().toggleNajwieksza) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Max: ").formatted(Formatting.DARK_GREEN, Formatting.BOLD))
                        .append(Text.literal(String.format("%.2f", Config.get().biggestWeight)).append("g").formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleNajmniejsza) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Min: ").formatted(Formatting.DARK_RED, Formatting.BOLD))
                        .append(Text.literal(String.format("%.2f", Config.get().smallestWeight)).append("g").formatted(Formatting.WHITE)));
            }
        }

        return new ArrayList<>(bodyText);
    }
}