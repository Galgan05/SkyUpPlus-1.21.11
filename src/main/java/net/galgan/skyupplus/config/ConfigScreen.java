package net.galgan.skyupplus.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class ConfigScreen {
    private ConfigScreen() {}

    public static Screen create(Screen parent) {
        Config cfg = Config.get();

        // --- MAIN HUD ---
        // --- MAIN HUD ---
        // --- MAIN HUD ---

        Option<Boolean> customScoreboard = Option.<Boolean>createBuilder()
                .name(Text.literal("Customowy scoreboard"))
                .description(OptionDescription.of(
                        Text.literal("Włącza wyświetlanie customowego scoreboarda").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Możlwiość wyłączenia dodana z myślą o tych, co preferują domyślny wygląd scoreboarda").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Wpisz /sboard jeżeli nie widzisz żadnego scoreboarda!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.customScoreboard, v -> cfg.customScoreboard = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Config.DisplayLocation> scoreboardDisplayLocation = Option.<Config.DisplayLocation>createBuilder()
                .name(Text.literal("Pozycja customowego scoreboarda"))
                .description(OptionDescription.of(
                        Text.literal("Zmienia pozycję wyświetlania customowego scoreboarda").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Wpisz /sboard jeżeli nie widzisz żadnego scoreboarda!").formatted(Formatting.RED)
                ))
                .binding(Config.DisplayLocation.MIDDLE_RIGHT, () -> cfg.scoreboardDisplayLocation, v -> cfg.scoreboardDisplayLocation = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.DisplayLocation.class)
                        .formatValue(v -> switch (v) {
                            case TOP_LEFT -> Text.literal("Lewy górny");
                            case MIDDLE_LEFT -> Text.literal("Lewy środek");
                            case BOTTOM_LEFT -> Text.literal("Lewy dolny");
                            case TOP_RIGHT -> Text.literal("Prawy górny");
                            case MIDDLE_RIGHT -> Text.literal("Prawy środek");
                            case BOTTOM_RIGHT -> Text.literal("Prawy dolny");
                        })
                )
                .build();

        Option<Config.DisplayLocation> requirementsDisplayLocation = Option.<Config.DisplayLocation>createBuilder()
                .name(Text.literal("Pozycja wyświetlania wymagań"))
                .description(OptionDescription.of(
                        Text.literal("Zmienia pozycję wyświetlania wymagań zadań i szamaństwa").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Kliknij środkowym przyciskiem na zadanie bądź moba, którego wymagania chcesz wyświetlić").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Aby przestać wyświetlać wymagania, kliknij środkowym przyciskiem gdziekolwiek w menu zadań lub szamaństwa").formatted(Formatting.GRAY)
                ))
                .binding(Config.DisplayLocation.TOP_LEFT, () -> cfg.requirementsDisplayLocation, v -> cfg.remindersDisplayLocation = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.DisplayLocation.class)
                        .formatValue(v -> switch (v) {
                            case TOP_LEFT -> Text.literal("Lewy górny");
                            case MIDDLE_LEFT -> Text.literal("Lewy środek");
                            case BOTTOM_LEFT -> Text.literal("Lewy dolny");
                            case TOP_RIGHT -> Text.literal("Prawy górny");
                            case MIDDLE_RIGHT -> Text.literal("Prawy środek");
                            case BOTTOM_RIGHT -> Text.literal("Prawy dolny");
                        })
                )
                .build();

        Option<Boolean> showFullStorages = Option.<Boolean>createBuilder()
                .name(Text.literal("Pokazuj pełne magazyny"))
                .description(OptionDescription.of(
                        Text.literal("Pokazuje pełne magazyny w menu kontrolera magazynów").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.showFullStorages, v -> cfg.showFullStorages = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        // --- REMINDERS ---
        // --- REMINDERS ---
        // --- REMINDERS ---

        Option<Boolean> toggleReminders = Option.<Boolean>createBuilder()
                .name(Text.literal("Wyświetlanie przypominajek"))
                .description(OptionDescription.of(
                        Text.literal("Zmienia kiedy ma się wyświetlać HUD przypominajek").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleReminders, v -> cfg.toggleReminders = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Config.DisplayLocation> remindersDisplayLocation = Option.<Config.DisplayLocation>createBuilder()
                .name(Text.literal("Pozycja wyświetlania przypominajek"))
                .description(OptionDescription.of(
                        Text.literal("Zmienia pozycję wyświetlania przypominajek").formatted(Formatting.GRAY)
                ))
                .binding(Config.DisplayLocation.BOTTOM_LEFT, () -> cfg.remindersDisplayLocation, v -> cfg.remindersDisplayLocation = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.DisplayLocation.class)
                        .formatValue(v -> switch (v) {
                            case TOP_LEFT -> Text.literal("Lewy górny");
                            case MIDDLE_LEFT -> Text.literal("Lewy środek");
                            case BOTTOM_LEFT -> Text.literal("Lewy dolny");
                            case TOP_RIGHT -> Text.literal("Prawy górny");
                            case MIDDLE_RIGHT -> Text.literal("Prawy środek");
                            case BOTTOM_RIGHT -> Text.literal("Prawy dolny");
                        })
                )
                .build();

        Option<Boolean> toggleDaily = Option.<Boolean>createBuilder()
                .name(Text.literal("Zadanie codzienne"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla informację o tym, czy dostępne jest zadanie codzienne").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Kliknij środkowym przyciskiem myszki w zadanie dzienne aby zamknąć to powiadomienie").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.toggleDaily, v -> cfg.toggleDaily = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleZawody = Option.<Boolean>createBuilder()
                .name(Text.literal("Zawody rybackie"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla termin następnych zawodów rybackich").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! W przypadku zmiany grafiku bądź dodatkowych zawodów termin może być niepoprawny!").formatted(Formatting.RED)
                ))
                .binding(false, () -> cfg.toggleZawody, v -> cfg.toggleZawody = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleEventy = Option.<Boolean>createBuilder()
                .name(Text.literal("Eventy"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla termin następnych eventów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! W przypadku zmiany grafiku bądź dodatkowych eventów termin może być niepoprawny!").formatted(Formatting.RED)
                ))
                .binding(false, () -> cfg.toggleEventy, v -> cfg.toggleEventy = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        // --- CHAT ---
        // --- CHAT ---
        // --- CHAT ---

        Option<Boolean> disableAnnouncements = Option.<Boolean>createBuilder()
                .name(Text.literal("Usuń ogłoszenia"))
                .description(OptionDescription.of(
                        Text.literal("Wyłącza wyświetlanie ogłoszeń serwerowych na czacie").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.disableAnnouncements, v -> cfg.disableAnnouncements = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> disableCrouchingInfo = Option.<Boolean>createBuilder()
                .name(Text.literal("Usuń informację o kucaniu"))
                .description(OptionDescription.of(
                        Text.literal("Wyłącza wyświetlanie informacji o kucaniu aby rozwalić niektóre bloki").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.disableCrouchingInfo, v -> cfg.disableCrouchingInfo = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> shortenAuctions = Option.<Boolean>createBuilder()
                .name(Text.literal("Skracaj aukcje"))
                .description(OptionDescription.of(
                        Text.literal("Skraca ogłoszenie o nowej aukcji o niepotrzebne wiadomości").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.shortenAuctions, v -> cfg.shortenAuctions = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> shortenPechowiec = Option.<Boolean>createBuilder()
                .name(Text.literal("Skracaj pechowca"))
                .description(OptionDescription.of(
                        Text.literal("Skraca ogłoszenie o pechowcu o niepotrzebne wiadomości").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.shortenPechowiec, v -> cfg.shortenPechowiec = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        // --- WARNINGS ---
        // --- WARNINGS ---
        // --- WARNINGS ---

        Option<Boolean> togglePlugWarning = Option.<Boolean>createBuilder()
                .name(Text.literal("Ostrzeżenie o pługu"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla ostrzeżenie kiedy zbierasz uprawy bez aktywnego pługu").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.togglePlugWarning, v -> cfg.togglePlugWarning = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleToolDurabilityWarning = Option.<Boolean>createBuilder()
                .name(Text.literal("Ostrzeżenie o narzędziach"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla ostrzeżenie kiedy użycia narzędzi spadną poniżej ustawionej wartości").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.toggleToolDurabilityWarning, v -> cfg.toggleToolDurabilityWarning = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleArmorDurabilityWarning = Option.<Boolean>createBuilder()
                .name(Text.literal("Ostrzeżenie o zbroi"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla ostrzeżenie kiedy użycia zbroi spadną poniżej ustawionej wartości").formatted(Formatting.GRAY)
                ))
                .binding(false, () -> cfg.toggleArmorDurabilityWarning, v -> cfg.toggleArmorDurabilityWarning = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Config.WarningSound> warningSound = Option.<Config.WarningSound>createBuilder()
                .name(Text.literal("Dźwięk ostrzeżenia"))
                .description(OptionDescription.of(
                        Text.literal("Ustawia dźwięk odtwarzany podczas ostrzeżenia").formatted(Formatting.GRAY)
                ))
                .binding(Config.WarningSound.PLING, () -> cfg.warningSound, v -> cfg.warningSound = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.WarningSound.class)
                        .formatValue(v -> switch (v) {
                            case PLING -> Text.literal("Pling");
                            case TUBE -> Text.literal("Tuba");
                            case BASS -> Text.literal("Gitara basowa");
                            case ANVIL -> Text.literal("Kowadło");
                        })
                )
                .build();

        Option<Integer> toolDurabilityThreshold = Option.<Integer>createBuilder()
                .name(Text.literal("Próg ostrzeżenia o narzędziach"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla ostrzeżenie kiedy użycia zbroi spadną poniżej ustawionej wartości").formatted(Formatting.GRAY)
                ))
                .binding(5, () -> cfg.toolDurabilityThreshold, v -> cfg.toolDurabilityThreshold = v)
                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                        .range(1, 99)
                        .step(1)
                        .formatValue(val -> Text.literal(val + "%")))
                .build();

        Option<Integer> armorDurabilityThreshold = Option.<Integer>createBuilder()
                .name(Text.literal("Próg ostrzeżenia o zbroi"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla ostrzeżenie kiedy użycia zbroi spadną poniżej ustawionej wartości").formatted(Formatting.GRAY)
                ))
                .binding(5, () -> cfg.armorDurabilityThreshold, v -> cfg.armorDurabilityThreshold = v)
                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                        .range(1, 99)
                        .step(1)
                        .formatValue(val -> Text.literal(val + "%")))
                .build();

        // --- COOLDOWN ---
        // --- COOLDOWN ---
        // --- COOLDOWN ---

        Option<Boolean> abilityCooldown = Option.<Boolean>createBuilder()
                .name(Text.literal("Cooldown umiejętności"))
                .description(OptionDescription.of(
                        Text.literal("Włącza wyświetlanie customowego cooldownu umiejętności").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Możlwiość wyłączenia dodana z myślą o tych, co preferują domyślny wygląd cooldownu").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.abilityCooldown, v -> cfg.abilityCooldown = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> dungeonCooldown = Option.<Boolean>createBuilder()
                .name(Text.literal("Cooldown dungeonu"))
                .description(OptionDescription.of(
                        Text.literal("Włącza wyświetlanie customowego cooldownu dungeonu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Możlwiość wyłączenia dodana z myślą o tych, co preferują domyślny wygląd cooldownu").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.dungeonCooldown, v -> cfg.dungeonCooldown = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Config.DisplayLocation> cooldownDisplayLocation = Option.<Config.DisplayLocation>createBuilder()
                .name(Text.literal("Pozycja customowego cooldownu"))
                .description(OptionDescription.of(
                        Text.literal("Zmienia pozycję wyświetlania customowego cooldownu").formatted(Formatting.GRAY)
                ))
                .binding(Config.DisplayLocation.BOTTOM_RIGHT, () -> cfg.cooldownDisplayLocation, v -> cfg.cooldownDisplayLocation = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.DisplayLocation.class)
                        .formatValue(v -> switch (v) {
                            case TOP_LEFT -> Text.literal("Lewy górny");
                            case MIDDLE_LEFT -> Text.literal("Lewy środek");
                            case BOTTOM_LEFT -> Text.literal("Lewy dolny");
                            case TOP_RIGHT -> Text.literal("Prawy górny");
                            case MIDDLE_RIGHT -> Text.literal("Prawy środek");
                            case BOTTOM_RIGHT -> Text.literal("Prawy dolny");
                        })
                )
                .build();

        Option<Config.MainSound> abilitySound = Option.<Config.MainSound>createBuilder()
                .name(Text.literal("Dźwięk umiejętności"))
                .description(OptionDescription.of(
                        Text.literal("Ustawia dźwięk odtwarzany po zakończeniu umiejętności lub jej cooldownu").formatted(Formatting.GRAY)
                ))
                .binding(Config.MainSound.BELL, () -> cfg.abilitySound, v -> cfg.abilitySound = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.MainSound.class)
                        .formatValue(v -> switch (v) {
                            case BELL -> Text.literal("Dzwon");
                            case GOAT_HORN -> Text.literal("Kozi róg");
                            case WITHER -> Text.literal("Wither");
                            case CHALLENGE -> Text.literal("Wyzwanie");
                        })
                )
                .build();

        Option<Config.MainSound> dungeonSound = Option.<Config.MainSound>createBuilder()
                .name(Text.literal("Dżwięk dungeonu"))
                .description(OptionDescription.of(
                        Text.literal("Ustawia dźwięk odtwarzany po zakończeniu cooldownu dungeonu").formatted(Formatting.GRAY)
                ))
                .binding(Config.MainSound.GOAT_HORN, () -> cfg.dungeonSound, v -> cfg.dungeonSound = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.MainSound.class)
                        .formatValue(v -> switch (v) {
                            case BELL -> Text.literal("Dzwon");
                            case GOAT_HORN -> Text.literal("Kozi Róg");
                            case WITHER -> Text.literal("Wither");
                            case CHALLENGE -> Text.literal("Wyzwanie");
                        })
                )
                .build();

        Option<Config.CountdownSound> countdownSound = Option.<Config.CountdownSound>createBuilder()
                .name(Text.literal("Dżwięk odliczania"))
                .description(OptionDescription.of(
                        Text.literal("Ustawia dźwięk odtwarzany podczas odliczania do końca umiejętności").formatted(Formatting.GRAY)
                ))
                .binding(Config.CountdownSound.EXP, () -> cfg.countdownSound, v -> cfg.countdownSound = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.CountdownSound.class)
                        .formatValue(v -> switch (v) {
                            case EXP -> Text.literal("Exp");
                            case CLICK -> Text.literal("Klik");
                            case EIGHT_BIT -> Text.literal("8-Bit");
                            case NOTEBLOCK -> Text.literal("Noteblock");
                        })
                )
                .build();

        Option<Boolean> soundPlug = Option.<Boolean>createBuilder()
                .name(Text.literal("Pług"))
                .description(OptionDescription.of(
                        Text.literal("Dodaje dźwięk odliczania na ostatnie 3 sekundy pługu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo dodaje powiadomienie o skończeniu się cooldownu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundPlug, v -> cfg.soundPlug = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> soundWiertlo = Option.<Boolean>createBuilder()
                .name(Text.literal("Wiertło"))
                .description(OptionDescription.of(
                        Text.literal("Dodaje dźwięk odliczania na ostatnie 3 sekundy wiertła").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo dodaje powiadomienie o skończeniu się cooldownu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundWiertlo, v -> cfg.soundWiertlo = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> soundRozbiorka = Option.<Boolean>createBuilder()
                .name(Text.literal("Rozbiórka"))
                .description(OptionDescription.of(
                        Text.literal("Dodaje dźwięk odliczania na ostatnie 3 sekundy rozbiórki").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo dodaje powiadomienie o skończeniu się cooldownu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundRozbiorka, v -> cfg.soundRozbiorka = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> soundPila = Option.<Boolean>createBuilder()
                .name(Text.literal("Piła"))
                .description(OptionDescription.of(
                        Text.literal("Dodaje dźwięk odliczania na ostatnie 3 sekundy piły").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo dodaje powiadomienie o skończeniu się cooldownu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundPila, v -> cfg.soundPila = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> soundSieciRybackie = Option.<Boolean>createBuilder()
                .name(Text.literal("Sieci rybackie"))
                .description(OptionDescription.of(
                        Text.literal("Dodaje dźwięk odliczania na ostatnie 3 sekundy rybackich").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo dodaje powiadomienie o skończeniu się cooldownu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundSieciRybackie, v -> cfg.soundSieciRybackie = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> soundNawalnica = Option.<Boolean>createBuilder()
                .name(Text.literal("Nawałnica"))
                .description(OptionDescription.of(
                        Text.literal("Dodaje dźwięk odliczania na ostatnie 3 sekundy nawałnicy").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo dodaje powiadomienie o skończeniu się cooldownu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundNawalnica, v -> cfg.soundNawalnica = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> soundEskalacja = Option.<Boolean>createBuilder()
                .name(Text.literal("Eskalacja"))
                .description(OptionDescription.of(
                        Text.literal("Dodaje dźwięk odliczania na ostatnie 3 sekundy eskalacji").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo dodaje powiadomienie o skończeniu się cooldownu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundEskalacja, v -> cfg.soundEskalacja = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> soundDungeon = Option.<Boolean>createBuilder()
                .name(Text.literal("Dungeon"))
                .description(OptionDescription.of(
                        Text.literal("Gra dźwięk kiedy skończy się cooldown dungeonu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("Dodatkowo wysyłą na czacie informację o tym").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Jeżeli chcesz usłyszeć dźwięk odliczania musisz mieć włączony dźwięk w ustawieniach!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.soundDungeon, v -> cfg.soundDungeon = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        // --- FISHING ---
        // --- FISHING ---
        // --- FISHING ---

        Option<Config.ConditionalDisplayBehavior> fishingDisplayBehavior = Option.<Config.ConditionalDisplayBehavior>createBuilder()
                .name(Text.literal("Wyświetlanie rybaka"))
                .description(OptionDescription.of(
                        Text.literal("Zmienia kiedy ma się wyświetlać HUD rybaka").formatted(Formatting.GRAY)
                ))
                .binding(Config.ConditionalDisplayBehavior.HOLDING_ITEM, () -> cfg.fishingDisplayBehavior, v -> cfg.fishingDisplayBehavior = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.ConditionalDisplayBehavior.class)
                        .formatValue(v -> switch (v) {
                            case HOLDING_ITEM -> Text.literal("Trzymając wędkę").formatted(Formatting.YELLOW);
                            case ALWAYS -> Text.literal("Zawsze").formatted(Formatting.GREEN);
                            case NEVER -> Text.literal("Nigdy").formatted(Formatting.RED);
                        })
                )
                .build();

        Option<Config.DisplayLocation> fishingDisplayLocation = Option.<Config.DisplayLocation>createBuilder()
                .name(Text.literal("Pozycja statystyk rybaka"))
                .description(OptionDescription.of(
                        Text.literal("Zmienia pozycję wyświetlania statystyk rybaka").formatted(Formatting.GRAY)
                ))
                .binding(Config.DisplayLocation.MIDDLE_LEFT, () -> cfg.fishingDisplayLocation, v -> cfg.fishingDisplayLocation = v)
                .controller(opt -> EnumControllerBuilder.create(opt)
                        .enumClass(Config.DisplayLocation.class)
                        .formatValue(v -> switch (v) {
                            case TOP_LEFT -> Text.literal("Lewy górny");
                            case MIDDLE_LEFT -> Text.literal("Lewy środek");
                            case BOTTOM_LEFT -> Text.literal("Lewy dolny");
                            case TOP_RIGHT -> Text.literal("Prawy górny");
                            case MIDDLE_RIGHT -> Text.literal("Prawy środek");
                            case BOTTOM_RIGHT -> Text.literal("Prawy dolny");
                        })
                )
                .build();

        Option<Boolean> toggleNiewielka = Option.<Boolean>createBuilder()
                .name(Text.literal("Niewielka"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach liczbę złowionych niewielkich ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleNiewielka, v -> cfg.toggleNiewielka = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> togglePrzecietna = Option.<Boolean>createBuilder()
                .name(Text.literal("Przeciętna"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach liczbę złowionych przeciętnych ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.togglePrzecietna, v -> cfg.togglePrzecietna = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleWymiarowa = Option.<Boolean>createBuilder()
                .name(Text.literal("Wymiarowa"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach liczbę złowionych wymiarowych ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleWymiarowa, v -> cfg.toggleWymiarowa = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleOgromna = Option.<Boolean>createBuilder()
                .name(Text.literal("Ogromna"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach liczbę złowionych ogromnych ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleOgromna, v -> cfg.toggleOgromna = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleMamucia = Option.<Boolean>createBuilder()
                .name(Text.literal("Mamucia"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach liczbę złowionych mamucich ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleMamucia, v -> cfg.toggleMamucia = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleSuma = Option.<Boolean>createBuilder()
                .name(Text.literal("Suma"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę złowionych ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleSuma, v -> cfg.toggleSuma = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleZarobek = Option.<Boolean>createBuilder()
                .name(Text.literal("Zarobek"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączny zarobek z ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleZarobek, v -> cfg.toggleZarobek = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleWaga = Option.<Boolean>createBuilder()
                .name(Text.literal("Waga"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną wagę złowionych ryb").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleWaga, v -> cfg.toggleWaga = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleNajwieksza = Option.<Boolean>createBuilder()
                .name(Text.literal("Największa"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach wagę największej złowionej ryby").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleNajwieksza, v -> cfg.toggleNajwieksza = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> toggleNajmniejsza = Option.<Boolean>createBuilder()
                .name(Text.literal("Najmniejsza"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach wagę najmniejszej złowionej ryby").formatted(Formatting.GRAY)
                ))
                .binding(true, () -> cfg.toggleNajmniejsza, v -> cfg.toggleNajmniejsza = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        // --- CRATES ---
        // --- CRATES ---
        // --- CRATES ---
        Option<Boolean> karambitToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Karambit"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych karambitów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.karambitToggle, v -> cfg.karambitToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> perunToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Perun"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych perunów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.perunToggle, v -> cfg.perunToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> cymofanToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Cymofan"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych cymofanów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.cymofanToggle, v -> cfg.cymofanToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> mlotThoraToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Młot Thora"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych młotów thora").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.mlotThoraToggle, v -> cfg.mlotThoraToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> urizelToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Urizel"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych urizelów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.urizelToggle, v -> cfg.urizelToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> azadaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Azada"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych azad").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.azadaToggle, v -> cfg.azadaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> spinelToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Spinel"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych spineli").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.spinelToggle, v -> cfg.spinelToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> karpiolapToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Karpiołap"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych karpiołapów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.karpiolapToggle, v -> cfg.karpiolapToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> ethericaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Etherica"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych etheric").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.ethericaToggle, v -> cfg.ethericaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> lukLegolasaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Łuk Legolasa"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych łuków legolasa").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.lukLegolasaToggle, v -> cfg.lukLegolasaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> arbaletToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Arbalet"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych arbaletów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.arbaletToggle, v -> cfg.arbaletToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> powrotOdysaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Powrót Odysa"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych powrotów odysa").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.powrotOdysaToggle, v -> cfg.powrotOdysaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> cassisToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Cassis"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych cassisów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.cassisToggle, v -> cfg.cassisToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> cuirassToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Cuirass"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych cuirassów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.cuirassToggle, v -> cfg.cuirassToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> cuissotToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Cuissot"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych cuissotów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.cuissotToggle, v -> cfg.cuissotToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> cossetToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Cosset"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych cossetów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.cossetToggle, v -> cfg.cossetToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> kapcieLotnikaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Kapcie lotnika"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych kapci lotnika").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.kapcieLotnikaToggle, v -> cfg.kapcieLotnikaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> rivendellToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Rivendell"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych rivendelli").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.rivendellToggle, v -> cfg.rivendellToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> impetToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Impet"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych impetów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.impetToggle, v -> cfg.impetToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> phloxToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Phlox"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych phloxów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.phloxToggle, v -> cfg.phloxToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> majsterToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Majster"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych majstrów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.majsterToggle, v -> cfg.majsterToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> magiczneWiaderkoToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Magiczne Wiaderko"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych magicznych wiaderek").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.magiczneWiaderkoToggle, v -> cfg.magiczneWiaderkoToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> statTrackerToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Stat Tracker"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych stat trackerów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.statTrackerToggle, v -> cfg.statTrackerToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> jajkoNiespodziankaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Jajko niespodzianka"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych jajek niespodzianka").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.jajkoNiespodziankaToggle, v -> cfg.jajkoNiespodziankaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> klejnotKupieckiToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Klejnot kupiecki"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych klejnotów kupieckich").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.klejnotKupieckiToggle, v -> cfg.klejnotKupieckiToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> kontrolerMagazynowToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Kontroler magazynów"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych kontrolerów magazynów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.kontrolerMagazynowToggle, v -> cfg.kontrolerMagazynowToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> tajemniceSkyUPaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Tajemnice SkyUPa"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych tajemnic skyupa").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.tajemniceSkyUPaToggle, v -> cfg.tajemniceSkyUPaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> elementiumToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Elementium"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych kluczy do elementium").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.elementiumToggle, v -> cfg.elementiumToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> mendingToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Mending"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych mendingów").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.mendingToggle, v -> cfg.mendingToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> emeraldBlockToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Blok emeraldu"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych bloków emeraldu").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.emeraldBlockToggle, v -> cfg.emeraldBlockToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> goldBlockToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Blok złota"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych bloków złota").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.goldBlockToggle, v -> cfg.goldBlockToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> ironBlockToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Blok żelaza"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych bloków żelaza").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.ironBlockToggle, v -> cfg.ironBlockToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> redstoneBlockToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Blok redstone"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych bloków redstona").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.redstoneBlockToggle, v -> cfg.redstoneBlockToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> dirtToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Ziemia"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionej ziemii").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.dirtToggle, v -> cfg.dirtToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> grassBlockToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Blok trawy"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych bloków trawy").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.grassBlockToggle, v -> cfg.grassBlockToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> sandToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Piasek"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionego piasku").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.sandToggle, v -> cfg.sandToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> oceanicznaRudaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Oceaniczna ruda"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych oceanicznych rud").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.oceanicznaRudaToggle, v -> cfg.oceanicznaRudaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> wybitnaPrzynetaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Wybitna przynęta"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych wybitnych przynęt").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.wybitnaPrzynetaToggle, v -> cfg.wybitnaPrzynetaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();

        Option<Boolean> dobraPrzynetaToggle = Option.<Boolean>createBuilder()
                .name(Text.literal("Dobra przynęta"))
                .description(OptionDescription.of(
                        Text.literal("Wyświetla w statystykach łączną liczbę wydropionych dobrych przynęt").formatted(Formatting.GRAY),
                        Text.empty(),
                        Text.literal("UWAGA! Przy zbyt dużych wielkościach interfejsu statystyki mogą wystawać poza ekran!").formatted(Formatting.RED)
                ))
                .binding(true, () -> cfg.dobraPrzynetaToggle, v -> cfg.dobraPrzynetaToggle = v)
                .controller(TickBoxControllerBuilder::create)
                .build();


        // Build the screen (IMPORTANT: build fresh each time)
        return YetAnotherConfigLib.createBuilder()
                .title(Text.empty()
                        .append(Text.literal("Sky").formatted(Formatting.AQUA, Formatting.BOLD))
                        .append(Text.literal("UP").formatted(Formatting.WHITE, Formatting.BOLD))
                        .append(Text.literal("+ ").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                        .append(Text.literal("» ").formatted(Formatting.DARK_GRAY, Formatting.BOLD))
                        .append(Text.literal("Ustawienia").formatted(Formatting.YELLOW, Formatting.BOLD)))
                .save(Config::save)
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("HUD").formatted(Formatting.GREEN, Formatting.BOLD))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Scoreboard"))
                                .option(customScoreboard)
                                .option(scoreboardDisplayLocation)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Wymagania"))
                                .option(requirementsDisplayLocation)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Kontroler magazynow"))
                                .option(showFullStorages)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Przypominajki"))
                                .option(toggleReminders)
                                .option(remindersDisplayLocation)
                                .option(toggleDaily)
                                .option(toggleZawody)
                                .option(toggleEventy)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Czat").formatted(Formatting.YELLOW, Formatting.BOLD))
                        .group(OptionGroup.createBuilder()
                                .option(disableAnnouncements)
                                .option(disableCrouchingInfo)
                                .option(shortenAuctions)
                                .option(shortenPechowiec)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Ostrzeżenia").formatted(Formatting.RED, Formatting.BOLD))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Główne"))
                                .option(togglePlugWarning)
                                .option(toggleToolDurabilityWarning)
                                .option(toggleArmorDurabilityWarning)
                                .option(warningSound)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Konfiguracja"))
                                .option(toolDurabilityThreshold)
                                .option(armorDurabilityThreshold)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Cooldown").formatted(Formatting.BLUE, Formatting.BOLD))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Główne"))
                                .option(abilityCooldown)
                                .option(dungeonCooldown)
                                .option(cooldownDisplayLocation)
                                .option(abilitySound)
                                .option(dungeonSound)
                                .option(countdownSound)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Efekty dźwiękowe"))
                                .option(soundPlug)
                                .option(soundWiertlo)
                                .option(soundRozbiorka)
                                .option(soundPila)
                                .option(soundSieciRybackie)
                                .option(soundNawalnica)
                                .option(soundEskalacja)
                                .option(soundDungeon)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Skrzynki").formatted(Formatting.LIGHT_PURPLE, Formatting.BOLD))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Rybacka"))
                                .option(oceanicznaRudaToggle)
                                .option(wybitnaPrzynetaToggle)
                                .option(dobraPrzynetaToggle)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Platinum"))
                                .option(elementiumToggle)
                                .option(mendingToggle)
                                .option(emeraldBlockToggle)
                                .option(goldBlockToggle)
                                .option(ironBlockToggle)
                                .option(redstoneBlockToggle)
                                .option(dirtToggle)
                                .option(grassBlockToggle)
                                .option(sandToggle)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Elementium"))
                                .option(karambitToggle)
                                .option(perunToggle)
                                .option(cymofanToggle)
                                .option(mlotThoraToggle)
                                .option(urizelToggle)
                                .option(azadaToggle)
                                .option(spinelToggle)
                                .option(karpiolapToggle)
                                .option(ethericaToggle)
                                .option(lukLegolasaToggle)
                                .option(arbaletToggle)
                                .option(powrotOdysaToggle)
                                .option(cassisToggle)
                                .option(cuirassToggle)
                                .option(cuissotToggle)
                                .option(cossetToggle)
                                .option(kapcieLotnikaToggle)
                                .option(rivendellToggle)
                                .option(impetToggle)
                                .option(phloxToggle)
                                .option(majsterToggle)
                                .option(magiczneWiaderkoToggle)
                                .option(statTrackerToggle)
                                .option(jajkoNiespodziankaToggle)
                                .option(klejnotKupieckiToggle)
                                .option(kontrolerMagazynowToggle)
                                .option(tajemniceSkyUPaToggle)
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Rybak").formatted(Formatting.DARK_AQUA, Formatting.BOLD))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Główne"))
                                .option(fishingDisplayBehavior)
                                .option(fishingDisplayLocation)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Konfiguracja"))
                                .option(toggleNiewielka)
                                .option(togglePrzecietna)
                                .option(toggleWymiarowa)
                                .option(toggleOgromna)
                                .option(toggleMamucia)
                                .option(toggleSuma)
                                .option(toggleZarobek)
                                .option(toggleWaga)
                                .option(toggleNajwieksza)
                                .option(toggleNajmniejsza)
                                .build())
                        .build())
                .build()
                .generateScreen(parent);
    }


}