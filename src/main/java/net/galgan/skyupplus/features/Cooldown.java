package net.galgan.skyupplus.features;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.mixin.BossBarHudAccessor;
import net.galgan.skyupplus.util.Chat;
import net.galgan.skyupplus.util.ServerRestrictor;
import net.galgan.skyupplus.util.Sound;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cooldown {

    public static List<Text> body = new ArrayList<>();

    public static int[] timePlug;
    public static int[] timeWiertlo;
    public static int[] timeRozbiorka;
    public static int[] timePila;
    public static int[] timeSieciRybackie;
    public static int[] timeNawalnica;
    public static int[] timeEskalacja;
    public static int[] timeDungeon;

    public static boolean isActivePlug = false;
    public static boolean isActiveWiertlo = false;
    public static boolean isActiveRozbiorka = false;
    public static boolean isActivePila = false;
    public static boolean isActiveSieciRybackie = false;
    public static boolean isActiveNawalnica = false;
    public static boolean isActiveEskalacja = false;

    public static long lastPlugActivation = -1L;

    private enum AbilityType {
        PLUG("Pług", () -> Config.get().soundPlug),
        ROZBIORKA("Rozbiórka", () -> Config.get().soundRozbiorka),
        NAWALNICA("Nawałnica", () -> Config.get().soundNawalnica),
        SIECI_RYBACKIE("Sieci rybackie", () -> Config.get().soundSieciRybackie),
        PILA("Piła", () -> Config.get().soundPila),
        WIERTLO("Wiertło", () -> Config.get().soundWiertlo),
        ESKALACJA("Eskalacja", () -> Config.get().soundEskalacja);

        final String BOSS_BAR_PREFIX;
        final BooleanSupplier ENABLED;

        AbilityType(String BOSS_BAR_PREFIX, BooleanSupplier ENABLED) {
            this.BOSS_BAR_PREFIX = BOSS_BAR_PREFIX;
            this.ENABLED = ENABLED;
        }
    }

    private static final class AbilityState {
        long endAt = -1;
        long nextReadyAt = -1;
        int lastCountdownPlayed = Integer.MIN_VALUE;
        int lastFinishedPlayed = Integer.MIN_VALUE;

        boolean wasVisibleLastTick = false;
    }

    private static final EnumMap<Cooldown.AbilityType, Cooldown.AbilityState> states = new EnumMap<>(Cooldown.AbilityType.class);

    static {
        for (Cooldown.AbilityType type : Cooldown.AbilityType.values()) {
            states.put(type, new Cooldown.AbilityState());
        }
    }

    public static void handleCooldown() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            abilities(client);
            dungeonCooldown(client);
            body = getBody();
        });

        ClientReceiveMessageEvents.GAME.register(Cooldown::dungeonCooldownSetter);
    }

    private static void abilities(MinecraftClient client) {
        if (!ServerRestrictor.isAllowed()) return;
        if (client.player == null || client.inGameHud == null) return;

        //Reset all active flags (except for plug if it was activated 5 seconds or less ago, because reasons)
        for (Cooldown.AbilityType type : Cooldown.AbilityType.values()) {
            setActive(type, type == AbilityType.PLUG && Instant.now().getEpochSecond() - lastPlugActivation < 5);
        }

        //Check if there is a boss bar
        BossBarHud hud = client.inGameHud.getBossBarHud();
        if (hud == null) return;

        //Get the current time in seconds
        long currentTime = Instant.now().getEpochSecond();

        //Loop through every boss bar
        for (ClientBossBar bar : ((BossBarHudAccessor) hud).getBossBarsMap().values()) {
            String title = bar.getName().getString();

            //Check if its name is on the list
            for (Cooldown.AbilityType type : Cooldown.AbilityType.values()) {
                if (!title.startsWith(type.BOSS_BAR_PREFIX)) continue;

                //Set active to true if it is on the list
                setActive(type, true);

                //Set the end time and next ready time if seen for the first time
                Cooldown.AbilityState state = states.get(type);
                if (state.endAt == -1) {
                    int remaining = parseTimeRemainingSeconds(title);
                    state.endAt = currentTime + remaining;
                    state.nextReadyAt = state.endAt + 300;
                    state.lastCountdownPlayed = Integer.MIN_VALUE;
                    state.lastFinishedPlayed = Integer.MIN_VALUE;
                }
            }
        }

        //Loop through every ability
        for (Cooldown.AbilityType type : Cooldown.AbilityType.values()) {

            Cooldown.AbilityState state = states.get(type);

            boolean visible = getActive(type);

            //Detect if an ability was canceled
            if (state.wasVisibleLastTick && !visible && state.endAt != -1) {
                long remaining = state.endAt - currentTime;

                if (remaining > 5) {
                    // Force ACTIVE to end immediately -> fall into COOLDOWN branch right away
                    state.endAt = currentTime - 1;

                    // Reset so we don't have weird sound gating
                    state.lastCountdownPlayed = Integer.MIN_VALUE;
                    state.lastFinishedPlayed = Integer.MIN_VALUE;
                }
            }

            state.wasVisibleLastTick = visible;

            //Check if an ability is active
            if (currentTime <= state.endAt && visible) {

                int time = (int) (state.endAt - currentTime);
                int[] timeFormated = new int[]{time / 60, time % 60};
                setTime(type, timeFormated);

                //Play the countdown if the ability is almost over
                if (type.ENABLED.getAsBoolean() && time >= 0 && time <= 3 && state.lastCountdownPlayed != time) {

                    //Get the pitch
                    float pitch = switch (time) {
                        case 3 -> 0.9f;
                        case 2 -> 1.0f;
                        case 1 -> 1.1f;
                        default -> 1.0f;
                    };

                    Sound.playSound(client, time == 0 ? finishedSound() : countdownSound(), SoundCategory.MASTER, pitch);
                    state.lastCountdownPlayed = time;
                }

            //Check if there is a cooldown active
            } else if(currentTime <= state.nextReadyAt) {

                int time = (int) (state.nextReadyAt - currentTime);
                int[] timeFormated = new int[]{time / 60, time % 60};
                setTime(type, timeFormated);

                //Play a sound if the cooldown is about to end
                if (type.ENABLED.getAsBoolean() && time == 0 && state.lastFinishedPlayed != time) {
                    Sound.playSound(client, finishedSound(), SoundCategory.MASTER, 1.0f);
                    state.lastFinishedPlayed = time;
                }

            //If neither of them are active, null out the time
            } else {
                state.endAt = -1;
                setTime(type, null);
            }
        }
    }

    private static void dungeonCooldownSetter(Text message, boolean overlay) {
        if(!ServerRestrictor.isAllowed()) return;

        String messageString = message.getString();

        if (!overlay && messageString.startsWith("Dungeon » Opuszczono Du")) {
            Config.get().nextEntryTime = Instant.now().getEpochSecond() + 1800;
            Config.get().onDungeonCooldown = true;
            Config.save();
        }

        if (!overlay && messageString.startsWith("Dungeon » Nie musisz czekać")) {
            Config.get().nextEntryTime = Instant.now().getEpochSecond();
            Config.get().onDungeonCooldown = false;
            Config.save();
        }

        if (!overlay && message.getString().startsWith("Dungeon » Musisz odczekać jeszcze")) {
            int timeRemaining = parseTimeRemainingSeconds(messageString);

            Config.get().nextEntryTime = Instant.now().getEpochSecond() + timeRemaining;
            Config.get().onDungeonCooldown = true;
            Config.save();
        }
    }

    private static void dungeonCooldown(MinecraftClient client) {
        if(!ServerRestrictor.isAllowed()) return;
        if (!Config.get().dungeonCooldown) return;
        if (client.player == null) return;

        ZonedDateTime polishTime = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));

        if (polishTime.getHour() == 3 && polishTime.getMinute() == 0 && polishTime.getSecond() == 0) {
            Config.get().nextEntryTime = -1L;
            Config.get().onDungeonCooldown = false;
            Config.save();
        }

        if(Config.get().onDungeonCooldown) {
            long time = Config.get().nextEntryTime - Instant.now().getEpochSecond();
            int dungeonyTimer = (int) time;
            timeDungeon = new int[]{dungeonyTimer / 60, dungeonyTimer % 60};

            if(Instant.now().getEpochSecond() >= Config.get().nextEntryTime) {
                if(Config.get().dungeonCooldown && Config.get().soundDungeon) {
                    Sound.playSound(client, dungeonSound(), SoundCategory.MASTER, 1.0f);
                    Chat.send(Text.literal("Cooldown na wejście do dungeonu skończył się!").formatted(Formatting.GREEN));
                }
                Config.get().nextEntryTime = -1L;
                Config.get().onDungeonCooldown = false;
                Config.save();
            }
        }
    }

    private record Entry(String name, int[] time, boolean active) {
        int remainingSeconds() {
            return (time[0] * 60) + time[1];
        }
    }

    public static List<Text> getBody() {
        List<Text> bodyText = new ArrayList<>();

        if (Config.get().abilityCooldown) {
            var entries = List.of(
                    new Entry("Pług", timePlug, isActivePlug),
                    new Entry("Piła", timePila, isActivePila),
                    new Entry("Sieci rybackie", timeSieciRybackie, isActiveSieciRybackie),
                    new Entry("Rozbiórka", timeRozbiorka, isActiveRozbiorka),
                    new Entry("Eskalacja", timeEskalacja, isActiveEskalacja),
                    new Entry("Nawałnica", timeNawalnica, isActiveNawalnica),
                    new Entry("Wiertło", timeWiertlo, isActiveWiertlo)
            );

            Comparator<Entry> sort =
                    Comparator.comparing((Entry e) -> !e.active)
                            .thenComparingInt(Entry::remainingSeconds)
                            .thenComparing(Entry::name);

            bodyText = new ArrayList<>(entries.stream()
                    .filter(e -> e.time != null)
                    .sorted(sort)
                    .map(Cooldown::toTextLine)
                    .toList());
        }

        if (Config.get().dungeonCooldown && timeDungeon != null && Config.get().onDungeonCooldown) {
            bodyText.add(Text.empty()
                    .append(Text.literal("Dungeon: ").formatted(Formatting.GRAY, Formatting.BOLD))
                    .append(Text.literal(String.format("%02d:%02d", timeDungeon[0], timeDungeon[1]))));
        }

        return new ArrayList<>(bodyText);
    }

    private static Text toTextLine(Entry e) {
        Formatting color = e.active ? Formatting.GREEN : Formatting.RED;

        return Text.empty()
                .append(Text.literal(e.name + ": ").formatted(color, Formatting.BOLD))
                .append(Text.literal(String.format("%02d:%02d", e.time[0], e.time[1])));
    }

    private static int parseTimeRemainingSeconds(String message) {
        int minutes = 0, seconds = 0;

        Matcher mMin = Pattern.compile("(\\d+)\\s*min").matcher(message);
        if (mMin.find()) minutes = Integer.parseInt(mMin.group(1));

        Matcher mSec = Pattern.compile("(\\d+)\\s*sek").matcher(message);
        if (mSec.find()) seconds = Integer.parseInt(mSec.group(1));

        return minutes * 60 + seconds;
    }

    private static void setActive(Cooldown.AbilityType type, boolean active) {
        switch (type) {
            case PLUG -> isActivePlug = active;
            case ROZBIORKA -> isActiveRozbiorka = active;
            case NAWALNICA -> isActiveNawalnica = active;
            case SIECI_RYBACKIE -> isActiveSieciRybackie = active;
            case PILA -> isActivePila = active;
            case WIERTLO -> isActiveWiertlo = active;
            case ESKALACJA -> isActiveEskalacja = active;
        }
    }

    private static boolean getActive(Cooldown.AbilityType type) {
        return switch (type) {
            case PLUG -> isActivePlug;
            case ROZBIORKA -> isActiveRozbiorka;
            case NAWALNICA -> isActiveNawalnica;
            case SIECI_RYBACKIE -> isActiveSieciRybackie;
            case PILA -> isActivePila;
            case WIERTLO -> isActiveWiertlo;
            case ESKALACJA -> isActiveEskalacja;
        };
    }

    private static void setTime(Cooldown.AbilityType type, int[] timeFormated) {
        switch (type) {
            case PLUG -> timePlug = timeFormated;
            case ROZBIORKA -> timeRozbiorka = timeFormated;
            case NAWALNICA -> timeNawalnica = timeFormated;
            case SIECI_RYBACKIE -> timeSieciRybackie = timeFormated;
            case PILA -> timePila = timeFormated;
            case WIERTLO -> timeWiertlo = timeFormated;
            case ESKALACJA -> timeEskalacja = timeFormated;
        }
    }

    private static SoundEvent finishedSound() {
        return switch (Config.get().abilitySound) {
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