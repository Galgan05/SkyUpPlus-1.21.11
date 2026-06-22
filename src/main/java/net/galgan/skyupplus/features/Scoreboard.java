package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.galgan.skyupplus.util.Number;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scoreboard {

    public static List<Text> body = new ArrayList<>();

    private static List<String> cleanBody = new ArrayList<>();
    private static String sc = "-";
    private static String sp = "-";
    private static String st = "-";
    private static String bank = "-";
    private static String level = "-";
    private static String limit = "-";
    private static String reset = "-";
    private static String bonus = "-";
    private static List<Text> abilities = new ArrayList<>();

    private static boolean customScoreboard;

    private static final Set<Character> ABILITY_CHARS = Set.of('\uF011','\uF012','\uF013','\uF014','\uF015','\uF016','\uF017','\uF018','\uF019','\uF01A','\uF01B','\uF01C','\uF01D','\uF01E','\uF050','\uF051');

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(!ServerRestrictor.isAllowed()) return;
            if (client.player == null) return;

            cleanBody = getCustomBody(client);

            customScoreboard = false;
            bonus = "-";

            for (String line : cleanBody) {
                if (line.startsWith("Portfel")) {
                    customScoreboard = true;
                    sc = parseDoubleField(line, "[\\d ,]+(?:\\.\\d+)?", " SC");
                }
                else if (line.startsWith("Punkty")) {
                    sp = parseIntField(line, "([\\d ]+)\\s*SP\\b", " SP");
                }
                else if (line.startsWith("Tokeny")) {
                    st = parseIntField(line, "([\\d ]+)\\s*ST\\b", " ST");
                }
                else if (line.startsWith("Bank")) {
                    bank = parseDoubleField(line, "[\\d ,]+(?:\\.\\d+)?", " SC");
                }
                else if (line.startsWith("Poziom")) {
                    level = parseIntField(line, "Poziom:\\s*(-?[\\d ]+)", "");
                }
                else if (line.startsWith("Limit")) {
                    Matcher m = Pattern.compile("([\\d, ]+/[\\d ]+)").matcher(line);
                    limit = m.find() ? m.group(1).trim().replace(" ", "").replace(",", ".") : "-";
                }
                else if (line.startsWith("Reset")) {
                    reset = parseStringField(line, "Reset limitu:\\s*(.+)");
                }
                else if (line.contains("BONUS")) {
                    int timeLeft = parseTimeToSeconds(line);
                    bonus = timeLeft > 0 ? Number.formatTimeNoSeconds(timeLeft) : "-";
                }
            }

            if (customScoreboard) {
                body = getCustomScoreboard();
            } else {
                body = getDefaultScoreboard(client);
            }

            abilities.clear();
        });
    }

    private static List<Text> getCustomScoreboard() {
        List<Text> bodyText = new ArrayList<>();

        bodyText.add(Text.empty()
                .append(Text.literal("Portfel: ").formatted(Formatting.RED))
                .append(Text.literal(sc).formatted(Formatting.YELLOW)));
        bodyText.add(Text.empty()
                .append(Text.literal("Punkty: ").formatted(Formatting.RED))
                .append(Text.literal(sp).formatted(Formatting.YELLOW)));
        bodyText.add(Text.empty()
                .append(Text.literal("Tokeny: ").formatted(Formatting.RED))
                .append(Text.literal(st).formatted(Formatting.YELLOW)));
        bodyText.add(Text.empty()
                .append(Text.literal("Bank: ").formatted(Formatting.AQUA))
                .append(Text.literal(bank).formatted(Formatting.YELLOW)));
        bodyText.add(Text.empty()
                .append(Text.literal("Poziom: ").formatted(Formatting.AQUA))
                .append(Text.literal(level).formatted(Formatting.YELLOW)));
        bodyText.add(Text.empty()
                .append(Text.literal("Limit: ").formatted(Formatting.GREEN))
                .append(Text.literal(limit).formatted(Formatting.YELLOW)));
        bodyText.add(Text.empty()
                .append(Text.literal("Reset: ").formatted(Formatting.GREEN))
                .append(Text.literal(reset).formatted(Formatting.YELLOW)));

        if (!bonus.equals("-")) {
            bodyText.add(Text.empty()
                    .append(Text.literal("Bonus: ").formatted(Formatting.GOLD))
                    .append(Text.literal(bonus).formatted(Formatting.YELLOW)));
        }

        if (abilities != null && !abilities.isEmpty()) {
            bodyText.addAll(abilities);
        }

        return bodyText;
    }

    private static List<String> getCustomBody(MinecraftClient client) {
        if (client.world == null) return List.of();

        net.minecraft.scoreboard.Scoreboard scoreboard = client.world.getScoreboard();
        ScoreboardObjective sidebar = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

        if (sidebar == null) return List.of();

        List<ScoreboardEntry> entries = new ArrayList<>(scoreboard.getScoreboardEntries(sidebar));

        entries.sort(Comparator
                .comparingInt(ScoreboardEntry::value).reversed()
                .thenComparing(ScoreboardEntry::owner));

        List<String> cleanBody = new ArrayList<>();

        for (ScoreboardEntry entry : entries) {
            if (entry.hidden()) continue;

            Text line = entry.name();

            Team team = scoreboard.getScoreHolderTeam(entry.owner());
            if (team != null) {
                line = Team.decorateName(team, line.copy());
            }

            String lineString = line.getString();

            if (lineString.startsWith(" §a§5")) continue;

            lineString = lineString.replaceAll("§.", "");

            if (lineString.startsWith("  »") && !lineString.contains("BONUS")) continue;

            if (lineString.chars().anyMatch(c -> ABILITY_CHARS.contains((char) c))) {
                abilities.add(line);
            }

            cleanBody.add(lineString);
        }

        return cleanBody;
    }

    private static List<Text> getDefaultScoreboard(MinecraftClient client) {
        if (client.world == null) return List.of();

        net.minecraft.scoreboard.Scoreboard scoreboard = client.world.getScoreboard();
        ScoreboardObjective sidebar = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

        if (sidebar == null) return List.of();

        List<ScoreboardEntry> entries = new ArrayList<>(scoreboard.getScoreboardEntries(sidebar));

        entries.sort(Comparator
                .comparingInt(ScoreboardEntry::value).reversed()
                .thenComparing(ScoreboardEntry::owner));

        List<Text> lines = new ArrayList<>();

        for (ScoreboardEntry entry : entries) {
            if (entry.hidden()) continue;
            if (lines.size() >= 15) break;

            Text line = entry.name();

            Team team = scoreboard.getScoreHolderTeam(entry.owner());
            if (team != null) {
                line = Team.decorateName(team, line.copy());
            }

            if (line.getString().startsWith(" §a§5")) continue;

            lines.add(line);
        }

        return lines;
    }

    private static String parseDoubleField(String line, String regex, String suffix) {
        Matcher m = Pattern.compile(regex).matcher(line);
        if (m.find()) {
            try {
                String raw = m.group(m.groupCount() > 0 ? 1 : 0)
                        .trim().replace(",", ".").replace(" ", "");
                return Number.format(Double.parseDouble(raw)) + suffix;
            } catch (NumberFormatException ignored) {}
        }
        return "-";
    }

    private static String parseIntField(String line, String regex, String suffix) {
        Matcher m = Pattern.compile(regex).matcher(line);
        if (m.find()) {
            try {
                String raw = m.group(1).trim().replace(" ", "");
                return Integer.parseInt(raw) + suffix;
            } catch (NumberFormatException ignored) {}
        }
        return "-";
    }

    private static String parseStringField(String line, String regex) {
        Matcher m = Pattern.compile(regex).matcher(line);
        if (m.find()) {
            String v = m.group(1).trim();
            return v.isEmpty() ? "-" : v;
        }
        return "-";
    }

    private static int parseTimeToSeconds(String line) {
        int total = 0;
        Matcher m = Pattern.compile("(\\d+)\\s*([gms])").matcher(line);
        while (m.find()) {
            int value = Integer.parseInt(m.group(1));
            switch (m.group(2)) {
                case "g" -> total += value * 3600;
                case "m" -> total += value * 60;
                case "s" -> total += value;
            }
        }
        return total;
    }
}