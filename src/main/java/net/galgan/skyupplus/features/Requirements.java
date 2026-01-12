package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.mixin.HandledScreenAccess;
import net.galgan.skyupplus.util.Chat;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Requirements {
    public static Text name;
    public static List<Text> filteredDescription;
    public static boolean isDisplayed;
    public static List<Text> body = new ArrayList<>();

    public static void handleRequirements() {

        ScreenEvents.BEFORE_INIT.register((client, screen, w, h) -> {
            if(!ServerRestrictor.isAllowed()) return;

            if (!(screen instanceof HandledScreen<?> handled)) return;
            if (!CONTAINER_TITLES.contains(handled.getTitle().getString())) return;

            ScreenMouseEvents.beforeMouseClick(screen).register((s, click) -> {

                if (click.button()!= 2) return;

                Slot slot = getSlotAt(handled, click.x(), click.y());

                if (slot == null) {
                    nullVariables();
                    return;
                }

                LoreComponent lore = slot.getStack().get(DataComponentTypes.LORE);

                if (lore == null) {
                    nullVariables();
                    return;
                }

                name = slot.getStack().getName();

                for(Text line : lore.lines()) {
                    if (line.getString().startsWith("▪ Zadanie ukończone!") && !name.getString().equals("» Zadanie codzienne «")) {
                        nullVariables();
                        return;
                    }
                }

                if (!((name.getString().startsWith("» ") && !handled.getTitle().getString().equals("\uE003\uE150\uE002Wyzwania")) || name.getString().equals("» Zadanie codzienne «"))) {
                    nullVariables();
                    return;
                }

                isDisplayed = true;

                if (name.getString().equals("» Zadanie codzienne «")) {
                    Config.get().hasSeenDaily = true;
                    Config.save();
                }

                Chat.send(Text.empty().append(Text.literal("Wybrano: ").formatted(Formatting.DARK_AQUA)).append(name));

                filteredDescription = loreFilter(lore);
                body = getBody();
            });
        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            if(!ServerRestrictor.isAllowed()) return;

            if (!overlay && message.getString().startsWith("Wyzwania » Ukończono zadanie")) {
                isDisplayed = false;
                body = new ArrayList<>();

                if (message.getString().contains("Zadanie codzienne")) {
                    Config.get().hasSeenDaily = true;
                    Config.save();
                }
            }
        });
    }

    private static Slot getSlotAt(HandledScreen<?> screen, double x, double y) {
        return ((HandledScreenAccess) screen).invokeGetSlotAt(x, y);
    }

    private static void nullVariables() {
        name = null;
        filteredDescription = null;
        body = new ArrayList<>();
        isDisplayed = false;
    }

    public static List<Text> loreFilter(LoreComponent lore) {
        List<Text> filtered = new ArrayList<>();
        boolean addNext = false;

        for (Text line : lore.lines()) {
            String s = line.getString().trim();

            if (!s.isEmpty()) {
                if (s.startsWith("▪")) {
                    if (s.startsWith("▪ Opis") || s.startsWith("▪ Zalecane") || s.startsWith("▪ Za ukończenie") || s.startsWith("▪ LPM") || s.startsWith("▪ Uwaga") || s.startsWith("▪ Kategoria") || s.startsWith("▪ Ukończone") || s.startsWith("▪ Następny")) {
                        addNext = false;
                    } else {
                        filtered.add(line);
                        addNext = true;
                    }
                } else {
                    if (addNext) filtered.add(line);
                }
            }
        }
        return filtered;
    }

    private static List<Text> getBody() {
        List<Text> bodyText = new ArrayList<>();

        if (!isDisplayed) return bodyText;

        for (Text line : filteredDescription) {
            if (line.getSiblings().isEmpty()) {
                bodyText.add(line);
            }

            List<Text> siblings = line.getSiblings();

            if (!siblings.isEmpty() && siblings.get(0).getString().trim().startsWith("▪")) {
                int startIndex = 1;
                MutableText result = Text.empty();
                for (int i = startIndex; i < siblings.size(); i++) {
                    result.append(siblings.get(i).copy().setStyle(siblings.get(i).getStyle()));
                }
                bodyText.add(result);
            } else if (!siblings.isEmpty() && siblings.get(1).getString().trim().startsWith("»")) {
                int startIndex = 2;
                MutableText result = Text.empty();
                for (int i = startIndex; i < siblings.size(); i++) {
                    result.append(siblings.get(i).copy().setStyle(siblings.get(i).getStyle()));
                }
                bodyText.add(result);
            } else if (!siblings.isEmpty()) {
                int startIndex = 1;
                MutableText result = Text.empty();
                for (int i = startIndex; i < siblings.size(); i++) {
                    result.append(siblings.get(i).copy().setStyle(siblings.get(i).getStyle()));
                }
                bodyText.add(result);
            }
        }

        return new ArrayList<>(bodyText);
    }

    private static final Set<String> CONTAINER_TITLES = Set.of(
            "\uE003\uE150\uE002Wyzwania",
            "\uE001\uE151\uE002Wyzwania » Nowicjusz",
            "\uE001\uE152\uE002Wyzwania » Wtajemniczony",
            "\uE001\uE153\uE002Wyzwania » Zaawansowany",
            "\uE001\uE154\uE002Wyzwania » Znawca",
            "\uE001\uE155\uE002Wyzwania » Ekspert",
            "\uE001\uE156\uE002Wyzwania » Mistrz",
            "\uE001\uE157\uE002Wyzwania » Guru",
            "\uE001\uE158\uE002Wyzwania » Legenda",
            "\uE001\uE159\uE002Wyzwania » Wirtuoz",
            "\uE001\uE15A\uE002Wyzwania » Bonus",
            "\uE001\uE039\uE002          Szamaństwo"
    );
}

