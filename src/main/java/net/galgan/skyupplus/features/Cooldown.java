package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.mixin.BossBarAccessor;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.galgan.skyupplus.util.Sound;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cooldown {

    public static List<Text> body = new ArrayList<>();
    public static Text bossBarMessage = Text.empty();
    public static long lastPlugActivation = -1L;

    record Line(String ability, int time) {}

    public enum AbilityType {
        WIERTLO("\uF011 Wiertło", () -> Config.get().playAbilitiesSound),
        PILA("\uF012 Piła", () -> Config.get().playAbilitiesSound),
        PLUG("\uF013 Pług", () -> Config.get().playAbilitiesSound),
        NAWALNICA("\uF014 Nawałnica", () -> Config.get().playAbilitiesSound),
        ROZBIORKA("\uF015 Rozbiórka", () -> Config.get().playAbilitiesSound),
        SIECI_RYBACKIE("\uF016 Sieci rybackie", () -> Config.get().playAbilitiesSound),
        ESKALACJA("\uF017 Eskalacja", () -> Config.get().playAbilitiesSound);

        final String BOSS_BAR_PREFIX;
        final BooleanSupplier ENABLED;

        AbilityType(String BOSS_BAR_PREFIX, BooleanSupplier ENABLED) {
            this.BOSS_BAR_PREFIX = BOSS_BAR_PREFIX;
            this.ENABLED = ENABLED;
        }

        public static AbilityType fromMessage(String message) {
            for (AbilityType type : values()) {
                if (message.contains(type.BOSS_BAR_PREFIX)) return type;
            }
            return null;
        }
    }

    public static final class AbilityState {
        int timeRemaining = Integer.MIN_VALUE;
        boolean isActive = false;
        int lastSoundTime = Integer.MIN_VALUE;
    }

    public static final EnumMap<AbilityType, AbilityState> states = new EnumMap<>(Cooldown.AbilityType.class);

    static {
        for (Cooldown.AbilityType type : Cooldown.AbilityType.values()) {
            states.put(type, new Cooldown.AbilityState());
        }
    }

    public static void handleCooldown() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (!ServerRestrictor.isAllowed()) return;
            if (client.player == null || client.inGameHud == null) return;

            // Get the current time in seconds
            long currentTime = Instant.now().getEpochSecond();

            // Reset all active flags (except for plug if it was activated less than 5 seconds ago)
            for (Cooldown.AbilityType type : Cooldown.AbilityType.values()) {
                AbilityState state = states.get(type);

                if (type == AbilityType.PLUG && currentTime - lastPlugActivation < 5) {
                    state.isActive = true;
                    continue;
                }

                state.isActive = false;
                state.timeRemaining = Integer.MIN_VALUE;
            }

            // Check if there is a boss bar
            BossBarHud hud = client.inGameHud.getBossBarHud();
            if (hud == null) return;

            //Loop through every boss bar
            for (ClientBossBar bar : ((BossBarAccessor) hud).getBossBarsMap().values()) {
                String title = bar.getName().getString();

                //Check if its name is on the list
                for (Cooldown.AbilityType type : Cooldown.AbilityType.values()) {
                    if (!title.startsWith(type.BOSS_BAR_PREFIX)) continue;

                    //Set all the needed perimeters if the name is correct
                    AbilityState state = states.get(type);
                    state.isActive = true;
                    state.timeRemaining = getRemainingSeconds(title);

                    if (!type.ENABLED.getAsBoolean() || state.timeRemaining < 0 || state.timeRemaining > 3 || state.lastSoundTime == state.timeRemaining) continue;

                    //Play the countdown sound
                    float pitch = switch (state.timeRemaining) {
                        case 3 -> 0.9f;
                        case 1 -> 1.1f;
                        default -> 1.0f;
                    };

                    Sound.playSound(client, state.timeRemaining == 0 ? finishedSound() : countdownSound(), SoundCategory.MASTER, pitch);
                    state.lastSoundTime = state.timeRemaining;
                }
            }

            // Detect if an ability cooldown has ended
            if (bossBarMessage.getString().startsWith("Prace » Umiejętność ") && Config.get().playAbilitiesSound) {
                Sound.playSound(client, finishedSound(), SoundCategory.MASTER, 1.0f);
                bossBarMessage = Text.empty();
            }

            // Detect if dungeon cooldown has ended
            if (bossBarMessage.getString().startsWith("Dungeon » Możesz ponownie zawalczyć w Dungeonie!") && Config.get().playDungeonSound) {
                Sound.playSound(client, dungeonSound(), SoundCategory.MASTER, 1.0f);
                bossBarMessage = Text.empty();
            }

            // Get the body of the cooldown HUD
            List<Text> bodyText = new ArrayList<>();

            if (!Config.get().customAbilities) {
                body = bodyText;
                return;
            }

            List<Line> bodyList = List.of(
                    new Line("Pług", states.get(AbilityType.PLUG).timeRemaining),
                    new Line("Piła", states.get(AbilityType.PILA).timeRemaining),
                    new Line("Sieci rybackie", states.get(AbilityType.SIECI_RYBACKIE).timeRemaining),
                    new Line("Rozbiórka", states.get(AbilityType.ROZBIORKA).timeRemaining),
                    new Line("Eskalacja", states.get(AbilityType.ESKALACJA).timeRemaining),
                    new Line("Nawałnica", states.get(AbilityType.NAWALNICA).timeRemaining),
                    new Line("Wiertło", states.get(AbilityType.WIERTLO).timeRemaining)
            );

            // Sort by lowest time remaining
            bodyList.stream()
                    .sorted(Comparator.comparingInt(Line::time))
                    .forEach(line -> {
                        if(line.time >= 0) {
                            bodyText.add(Text.empty()
                                    .append(Text.literal(line.ability + ": ").formatted(Formatting.GREEN, Formatting.BOLD))
                                    .append(Text.literal(String.format("%02d:%02d", line.time / 60, line.time % 60))));
                        }
                    });

            body = bodyText;
        });

        ClientReceiveMessageEvents.GAME.register((Text message, boolean overlay) -> {
            if(!ServerRestrictor.isAllowed()) return;

            String messageString = message.getString();

            if(!overlay && messageString.startsWith("Prace » Przerwano umiejętność")) {
                AbilityType type = AbilityType.fromMessage(messageString);
                if (type != null) {
                    states.get(type).isActive = false;
                }
            }
        });
    }

    private static int getRemainingSeconds(String message) {
        int minutes = 0, seconds = 0;

        Matcher mMin = Pattern.compile("(\\d+)\\s*min").matcher(message);
        if (mMin.find()) minutes = Integer.parseInt(mMin.group(1));

        Matcher mSec = Pattern.compile("(\\d+)\\s*sek").matcher(message);
        if (mSec.find()) seconds = Integer.parseInt(mSec.group(1));

        return minutes * 60 + seconds;
    }

    private static SoundEvent finishedSound() {
        return switch (Config.get().abilitiesSound) {
            case BELL -> SoundEvents.BLOCK_BELL_USE;
            case GOAT_HORN -> SoundEvents.GOAT_HORN_SOUNDS.get(1).value();
            case WITHER -> SoundEvents.ENTITY_WITHER_SPAWN;
            case CHALLENGE -> SoundEvents.UI_TOAST_CHALLENGE_COMPLETE;
        };
    }

    private static SoundEvent countdownSound() {
        return switch (Config.get().countdownSound) {
            case EXP -> SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
            case CLICK -> SoundEvents.BLOCK_NOTE_BLOCK_HAT.value();
            case EIGHT_BIT -> SoundEvents.BLOCK_NOTE_BLOCK_BIT.value();
            case NOTEBLOCK -> SoundEvents.BLOCK_NOTE_BLOCK_BASS.value();
        };
    }

    private static SoundEvent dungeonSound() {
        return switch (Config.get().dungeonSound) {
            case BELL -> SoundEvents.BLOCK_BELL_USE;
            case GOAT_HORN -> SoundEvents.GOAT_HORN_SOUNDS.get(1).value();
            case WITHER -> SoundEvents.ENTITY_WITHER_SPAWN;
            case CHALLENGE -> SoundEvents.UI_TOAST_CHALLENGE_COMPLETE;
        };
    }
}