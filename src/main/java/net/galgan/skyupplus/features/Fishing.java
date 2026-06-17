package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.util.Chat;
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

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(!ServerRestrictor.isAllowed() || client.player == null) return;

            ItemStack mainHand = client.player.getMainHandStack();
            ItemStack offHand = client.player.getOffHandStack();

            isFishing = mainHand.isOf(Items.FISHING_ROD) || offHand.isOf(Items.FISHING_ROD);

            body = getBody();
        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if(!ServerRestrictor.isAllowed()) return;

            String msgString = message.getString();

            if (!overlay && (msgString.startsWith("Wędkarstwo » ") || msgString.startsWith("Enchanty » "))) {

                if (msgString.startsWith("Wędkarstwo » Wyłowiono: §7Niew")) {
                    Config.get().niewielkaCount++;
                }
                if (msgString.startsWith("Wędkarstwo » Wyłowiono: §ePrze")) {
                    Config.get().przecietnaCount++;
                }
                if (msgString.startsWith("Wędkarstwo » Wyłowiono: §aWymi")) {
                    Config.get().wymiarowaCount++;
                }
                if (msgString.startsWith("Wędkarstwo » Wyłowiono: §6Ogro")) {
                    Config.get().ogromnaCount++;
                }
                if (msgString.startsWith("Wędkarstwo » Wyłowiono: §5Mamu")) {
                    Config.get().mamuciaCount++;
                }
                if (msgString.startsWith("Enchanty » Oprócz ryby")) {
                    Config.get().sashimiCount++;
                }
                if (msgString.startsWith("Wędkarstwo » Sprz")) {
                    String price = msgString.split("za ")[1].split(" SC")[0];
                    price = price.replace(",", ".");
                    price = price.replace(" ", "");
                    double earned = Double.parseDouble(price);

                    Config.get().totalEarned += earned;
                }
                if (msgString.startsWith("Wędkarstwo » Wyłowiono:")) {
                    String mass = msgString.split(" \\(")[1].split("g, ")[0];
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

        if (Config.get().fishingDisplayBehavior == Config.ConditionalDisplayBehavior.ALWAYS || (Fishing.isFishing && Config.get().fishingDisplayBehavior == Config.ConditionalDisplayBehavior.CONDITIONAL)) {

            if (Config.get().toggleNiewielka) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Niewielka: ").formatted(Formatting.GRAY))
                        .append(Text.literal(String.valueOf(Config.get().niewielkaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().togglePrzecietna) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Przeciętna: ").formatted(Formatting.YELLOW))
                        .append(Text.literal(String.valueOf(Config.get().przecietnaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleWymiarowa) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Wymiarowa: ").formatted(Formatting.GREEN))
                        .append(Text.literal(String.valueOf(Config.get().wymiarowaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleOgromna) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Ogromna: ").formatted(Formatting.GOLD))
                        .append(Text.literal(String.valueOf(Config.get().ogromnaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleMamucia) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Mamucia: ").formatted(Formatting.DARK_PURPLE))
                        .append(Text.literal(String.valueOf(Config.get().mamuciaCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleSuma) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Suma: ").formatted(Formatting.BLUE))
                        .append(Text.literal(String.valueOf(Config.get().totalCount)).formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleZarobek) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Zarobek: ").formatted(Formatting.AQUA))
                        .append(Text.literal(String.format("%.2f", Config.get().totalEarned)).append(" SC").formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleWaga) {
                if (Config.get().totalWeight < 1000) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Waga: ").formatted(Formatting.LIGHT_PURPLE))
                            .append(Text.literal(String.format("%.2f", Config.get().totalWeight)).append("g").formatted(Formatting.WHITE)));
                } else if (Config.get().totalWeight < 1000000) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Waga: ").formatted(Formatting.LIGHT_PURPLE))
                            .append(Text.literal(String.format("%.2f", Config.get().totalWeight/1000)).append("kg").formatted(Formatting.WHITE)));
                } else if (Config.get().totalWeight >= 1000000) {
                    bodyText.add(Text.empty()
                            .append(Text.literal("Waga: ").formatted(Formatting.LIGHT_PURPLE))
                            .append(Text.literal(String.format("%.3f", Config.get().totalWeight/1000000)).append("t").formatted(Formatting.WHITE)));
                }
            }
            if (Config.get().toggleNajwieksza) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Max: ").formatted(Formatting.DARK_GREEN))
                        .append(Text.literal(String.format("%.2f", Config.get().biggestWeight)).append("g").formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleNajmniejsza) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Min: ").formatted(Formatting.DARK_RED))
                        .append(Text.literal(String.format("%.2f", Config.get().smallestWeight)).append("g").formatted(Formatting.WHITE)));
            }
            if (Config.get().toggleSashimi) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Sashimi: ").formatted(Formatting.DARK_AQUA))
                        .append(Text.literal(String.valueOf(Config.get().sashimiCount)).formatted(Formatting.WHITE)));
            }
        }

        return new ArrayList<>(bodyText);
    }
}