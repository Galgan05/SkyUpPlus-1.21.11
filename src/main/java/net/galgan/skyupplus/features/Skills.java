package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.mixin.BossBarAccessor;
import net.galgan.skyupplus.util.Number;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.galgan.skyupplus.util.SkillTracker;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Skills {

    public static List<Text> body = new ArrayList<>();

    private static final Map<String, SkillTracker> TRACKERS = new HashMap<>();

    static final Map<String, Long> WINDOW_CAPS = Map.of(
            "Rybak", 120000L,
            "Drwal", 30000L,
            "Górnik", 30000L,
            "Farmer", 30000L,
            "Czarodziej", 120000L,
            "Budowniczy", 30000L,
            "Łowca", 120000L
    );
    static final long DEFAULT_WINDOW_CAP = 30000L;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!ServerRestrictor.isAllowed()) return;
            if (client.player == null || client.inGameHud == null) return;

            // Check if there is a boss bar
            BossBarHud hud = client.inGameHud.getBossBarHud();
            if (hud == null) return;

            // Get the current time
            long currentTime = System.currentTimeMillis();

            //Compile the pattern ahead of time
            Pattern p = Pattern.compile("^(\\w+) \\(poziom (\\d+)\\) ([\\d ,]+)/([\\d ]+) xp", Pattern.UNICODE_CHARACTER_CLASS);

            //Loop through every boss bar
            for (ClientBossBar bar : ((BossBarAccessor) hud).getBossBarsMap().values()) {
                String title = bar.getName().getString();
                if(!isASkill(title)) continue;

                Matcher m = p.matcher(title);

                if (m.find()) {
                    String currentSkill = m.group(1);
                    int currentLVL = Integer.parseInt(m.group(2));
                    double currentXP = Double.parseDouble(m.group(3).trim().replace(" ", "").replace(",", "."));
                    double currentTarget = Double.parseDouble(m.group(4).trim().replace(" ", "").replace(",", "."));

                    SkillTracker s = TRACKERS.computeIfAbsent(currentSkill, k -> new SkillTracker(k, currentXP, currentTime));

                    double gainedXP = currentXP - s.previousXP;
                    long windowCap = WINDOW_CAPS.getOrDefault(currentSkill, DEFAULT_WINDOW_CAP);

                    if (gainedXP > 0) {
                        if (s.startTime == 0 || currentTime - s.lastUpdateTime > 120000) {
                            s.startTime = s.previousTime;
                            s.totalXPGain = 0;
                        }
                        s.lastUpdateTime = currentTime;
                        s.totalXPGain += gainedXP;

                        long elapsed = currentTime - s.startTime;

                        if (elapsed > windowCap) {
                            // shrink the window while preserving the current rate
                            double ratePerMs = s.totalXPGain / (double) elapsed;
                            s.startTime = currentTime - windowCap;
                            s.totalXPGain = ratePerMs * windowCap;
                            elapsed = windowCap;
                        }
                        if (elapsed > 0) {
                            s.avgRate = s.totalXPGain / (elapsed / 1000.0);
                        }
                    }

                    s.nextLevel = currentLVL + 1;
                    s.targetXP = currentTarget;
                    s.previousXP = currentXP;
                    s.previousTime = currentTime;
                }
            }

            body = getBody();
        });
    }

    private static boolean isASkill(String title) {
        return (title.startsWith("Farmer") || title.startsWith("Górnik") || title.startsWith("Budowniczy") || title.startsWith("Rybak") || title.startsWith("Łowca") || title.startsWith("Czarodziej") || title.startsWith("Drwal"));
    }


    public static List<Text> getBody() {

        //Check if skill tracker is enabled
        if (Config.get().skillsDisplayBehavior == Config.ConditionalDisplayBehavior.NEVER) return new ArrayList<>();

        SkillTracker mostRecent = TRACKERS.values().stream().max(Comparator.comparingLong(s -> s.lastUpdateTime)).orElse(null);

        //Check if the last xp gain happened less than 30 seconds ago
        if (mostRecent == null) return new ArrayList<>();
        if (Config.get().skillsDisplayBehavior == Config.ConditionalDisplayBehavior.CONDITIONAL && (System.currentTimeMillis() - mostRecent.lastUpdateTime) > 30000) return new ArrayList<>();

        List<Text> bodyText = new ArrayList<>();

        double remainingXP = mostRecent.targetXP - mostRecent.previousXP;
        int timeLeft = (int)(remainingXP / mostRecent.avgRate);

        bodyText.add(Text.empty()
                .append(Text.literal("Praca: ").formatted(Formatting.AQUA))
                .append(Text.literal(mostRecent.name).formatted(Formatting.WHITE)));

        bodyText.add(Text.empty()
                .append(Text.literal("Następny poziom: ").formatted(Formatting.AQUA))
                .append(Text.literal(String.valueOf(mostRecent.nextLevel)).formatted(Formatting.WHITE)));

        bodyText.add(Text.empty()
                .append(Text.literal("Pozostałe xp: ").formatted(Formatting.AQUA))
                .append(Text.literal(Number.format(remainingXP)).formatted(Formatting.WHITE)));

        if (mostRecent.avgRate == 0) {
            bodyText.add(Text.empty()
                    .append(Text.literal("xp/min: ").formatted(Formatting.AQUA))
                    .append(Text.literal("-").formatted(Formatting.WHITE)));
            bodyText.add(Text.empty()
                    .append(Text.literal("Przewidywany czas: ").formatted(Formatting.AQUA))
                    .append(Text.literal("-").formatted(Formatting.WHITE)));
        } else {
            bodyText.add(Text.empty()
                    .append(Text.literal("xp/min: ").formatted(Formatting.AQUA))
                    .append(Text.literal(Number.format(mostRecent.avgRate * 60)).formatted(Formatting.WHITE)));
            bodyText.add(Text.empty()
                    .append(Text.literal("Przewidywany czas: ").formatted(Formatting.AQUA))
                    .append(Text.literal(Number.formatTime(timeLeft)).formatted(Formatting.WHITE)));
        }

        return bodyText;
    }
}
