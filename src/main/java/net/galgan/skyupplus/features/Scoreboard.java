package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Scoreboard {

    public static List<Text> body = new ArrayList<>();

    public static void handleScoreboard() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(!ServerRestrictor.isAllowed()) return;
            if (client.player == null) return;

            body = getBody(client);
        });
    }

    public static List<Text> getBody(MinecraftClient client) {
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

            if (line.getString().startsWith("  » ")) {
                line = dropLeadingChars(line, 4);
            }

            lines.add(line);
        }

        return lines;
    }

    private static Text dropLeadingChars(Text text, int charsToDrop) {
        MutableText result = Text.empty();
        int[] remaining = { charsToDrop };

        text.visit((Style style, String segment) -> {
            if (segment.isEmpty()) return Optional.empty();

            if (remaining[0] == 0) {
                result.append(Text.literal(segment).setStyle(style));
                return Optional.empty();
            }

            if (segment.length() <= remaining[0]) {
                remaining[0] -= segment.length();
                return Optional.empty();
            }

            String kept = segment.substring(remaining[0]);
            remaining[0] = 0;
            result.append(Text.literal(kept).setStyle(style));
            return Optional.empty();
        }, Style.EMPTY);

        return result;
    }
}