package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.galgan.skyupplus.config.Config;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reminders {

    public static List<Text> body = new ArrayList<>();
    private static String zawodyDate;
    private static String eventyDate;

    public static void handleReminders() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            List<Text> bodyText = new ArrayList<>();

            ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
            int weekTime = (zdt.getDayOfWeek().getValue() - 1) * 86400 + zdt.getHour() * 3600 + zdt.getMinute() * 60 + zdt.getSecond();

            //ZAWODY
            if (Config.get().toggleZawody) {
                if (weekTime <= 75600) {
                    zawodyDate = "Poniedziałek 21:00";
                } else if (weekTime <= 151200) {
                    zawodyDate = "Wtorek 18:00";
                } else if (weekTime <= 241200) {
                    zawodyDate = "Środa 19:00";
                } else if (weekTime <= 331200) {
                    zawodyDate = "Czwartek 20:00";
                } else if (weekTime <= 421200) {
                    zawodyDate = "Piątek 21:00";
                } else if (weekTime <= 475200) {
                    zawodyDate = "Sobota 12:00";
                } else if (weekTime <= 493200) {
                    zawodyDate = "Sobota 17:00";
                } else if (weekTime <= 568800) {
                    zawodyDate = "Niedziela 14:00";
                } else if (weekTime <= 594000) {
                    zawodyDate = "Niedziela 21:00";
                } else {
                    zawodyDate = "Poniedziałek 21:00";
                }

                bodyText.add(Text.empty()
                        .append(Text.literal("Zawody: ").formatted(Formatting.DARK_AQUA, Formatting.BOLD))
                        .append(Text.literal(zawodyDate)));
            }

            //EVENTY
            if (Config.get().toggleEventy) {
                if (weekTime <= 68400) {
                    eventyDate = "Poniedziałek 19:00"; //MON 19:00
                } else if (weekTime <= 158400) {
                    eventyDate = "Wtorek 20:00"; //TUE 20:00
                } else if (weekTime <= 248400) {
                    eventyDate = "Środa 21:00"; //WED 21:00
                } else if (weekTime <= 324000) {
                    eventyDate = "Czwartek 18:00"; //THU 18:00
                } else if (weekTime <= 414000) {
                    eventyDate = "Piątek 19:00"; //FRI 19:00
                } else if (weekTime <= 482400) {
                    eventyDate = "Sobota 14:00"; //SAT 14:00
                } else if (weekTime <= 500400) {
                    eventyDate = "Sobota 19:00"; //SAT 19:00
                } else if (weekTime <= 561600) {
                    eventyDate = "Niedziela 12:00"; //SUN 12:00
                } else if (weekTime <= 579600) {
                    eventyDate = "Niedziela 17:00"; //SUN 17:00
                } else {
                    eventyDate = "Poniedziałek 19:00"; //MON 19:00
                }

                bodyText.add(Text.empty()
                        .append(Text.literal("Eventy: ").formatted(Formatting.DARK_PURPLE, Formatting.BOLD))
                        .append(Text.literal(eventyDate)));
            }

            //DAILY QUEST
            if (zdt.getDayOfYear() != Config.get().currentDay) {
                Config.get().hasSeenDaily = false;
                Config.get().currentDay = zdt.getDayOfYear();
                Config.save();
            }

            if (Config.get().toggleDaily && !Config.get().hasSeenDaily) {
                bodyText.add(Text.empty()
                        .append(Text.literal("Daily: ").formatted(Formatting.YELLOW, Formatting.BOLD))
                        .append(Text.literal("Dostępne")));
            }

            //GENERATE BODY
            body = bodyText;
        });
    }
}
