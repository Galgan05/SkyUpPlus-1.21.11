package net.galgan.skyupplus.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("skyupplus", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("skyupplus.json5"))
                    .setJson5(true)
                    .build())
            .build();

    public static Config get() {
        return HANDLER.instance();
    }

    public static void save() {
        HANDLER.save();
    }

    // --- SHARED ---
    public enum ConditionalDisplayBehavior {HOLDING_ITEM, ALWAYS, NEVER}
    public enum DisplayLocation {TOP_LEFT, MIDDLE_LEFT, BOTTOM_LEFT, TOP_RIGHT, MIDDLE_RIGHT, BOTTOM_RIGHT}
    public enum MainSound {BELL, GOAT_HORN, WITHER, CHALLENGE}
    public enum CountdownSound {EXP, CLICK, EIGHT_BIT, NOTEBLOCK}
    public enum WarningSound {PLING, TUBE, BASS, ANVIL}

    // --- SCOREBOARD ---
    @SerialEntry public boolean customScoreboard = true;
    @SerialEntry public DisplayLocation scoreboardDisplayLocation = DisplayLocation.MIDDLE_RIGHT;

    // --- REQUIREMENTS ---
    @SerialEntry public DisplayLocation requirementsDisplayLocation = DisplayLocation.TOP_LEFT;

    // --- STORAGE ---
    @SerialEntry public boolean showFullStorages = true;

    // --- REMINDERS ---
    @SerialEntry public boolean toggleReminders = true;
    @SerialEntry public boolean toggleDaily = false;
    @SerialEntry public boolean toggleZawody = false;
    @SerialEntry public boolean toggleEventy = false;

    @SerialEntry public int currentDay = ZonedDateTime.now(ZoneId.of("Europe/Warsaw")).getDayOfYear();
    @SerialEntry public boolean hasSeenDaily = false;

    @SerialEntry public DisplayLocation remindersDisplayLocation = DisplayLocation.BOTTOM_LEFT;

    // --- CHAT ---
    @SerialEntry public boolean disableAnnouncements = false;
    @SerialEntry public boolean disableCrouchingInfo = false;
    @SerialEntry public boolean disableForeignElementium = false;
    @SerialEntry public boolean disableAFKInfo = false;
    @SerialEntry public boolean shortenAuctions = false;
    @SerialEntry public boolean shortenPechowiec = false;

    // --- WARNINGS ---
    @SerialEntry public boolean togglePlugWarning = false;
    @SerialEntry public boolean toggleToolDurabilityWarning = false;
    @SerialEntry public boolean toggleArmorDurabilityWarning = false;

    @SerialEntry public int toolDurabilityThreshold = 5;
    @SerialEntry public int armorDurabilityThreshold = 5;

    @SerialEntry public WarningSound warningSound = WarningSound.PLING;

    // --- PRACE ---
    @SerialEntry public boolean customAbilities = true;
    @SerialEntry public boolean playAbilitiesSound = true;
    @SerialEntry public boolean playDungeonSound = true;

    @SerialEntry public DisplayLocation abilitiesDisplayLocation = DisplayLocation.BOTTOM_RIGHT;
    @SerialEntry public MainSound abilitiesSound = MainSound.BELL;
    @SerialEntry public MainSound dungeonSound = MainSound.GOAT_HORN;
    @SerialEntry public CountdownSound countdownSound = CountdownSound.EXP;

    // --- FISHING ---
    @SerialEntry public boolean toggleNiewielka = true;
    @SerialEntry public boolean togglePrzecietna = true;
    @SerialEntry public boolean toggleWymiarowa = true;
    @SerialEntry public boolean toggleOgromna = true;
    @SerialEntry public boolean toggleMamucia = true;
    @SerialEntry public boolean toggleSuma = true;
    @SerialEntry public boolean toggleZarobek = true;
    @SerialEntry public boolean toggleWaga = true;
    @SerialEntry public boolean toggleNajwieksza = true;
    @SerialEntry public boolean toggleNajmniejsza = true;

    @SerialEntry public int niewielkaCount = 0;
    @SerialEntry public int przecietnaCount = 0;
    @SerialEntry public int wymiarowaCount = 0;
    @SerialEntry public int ogromnaCount = 0;
    @SerialEntry public int mamuciaCount = 0;
    @SerialEntry public int totalCount = 0;
    @SerialEntry public double totalEarned = 0;
    @SerialEntry public double totalWeight = 0;
    @SerialEntry public double biggestWeight = 0;
    @SerialEntry public double smallestWeight = 0;

    @SerialEntry public DisplayLocation fishingDisplayLocation = DisplayLocation.MIDDLE_LEFT;
    @SerialEntry public ConditionalDisplayBehavior fishingDisplayBehavior = ConditionalDisplayBehavior.HOLDING_ITEM;

    // --- CRATES ---
    @SerialEntry public boolean karambitToggle = true;
    @SerialEntry public boolean perunToggle = true;
    @SerialEntry public boolean cymofanToggle = true;
    @SerialEntry public boolean mlotThoraToggle = true;
    @SerialEntry public boolean urizelToggle = true;
    @SerialEntry public boolean azadaToggle = true;
    @SerialEntry public boolean spinelToggle = true;
    @SerialEntry public boolean karpiolapToggle = true;
    @SerialEntry public boolean ethericaToggle = true;
    @SerialEntry public boolean lukLegolasaToggle = true;
    @SerialEntry public boolean arbaletToggle = true;
    @SerialEntry public boolean powrotOdysaToggle = true;
    @SerialEntry public boolean cassisToggle = true;
    @SerialEntry public boolean cuirassToggle = true;
    @SerialEntry public boolean cuissotToggle = true;
    @SerialEntry public boolean cossetToggle = true;
    @SerialEntry public boolean kapcieLotnikaToggle = true;
    @SerialEntry public boolean rivendellToggle = true;
    @SerialEntry public boolean impetToggle = true;
    @SerialEntry public boolean phloxToggle = true;
    @SerialEntry public boolean majsterToggle = true;
    @SerialEntry public boolean magiczneWiaderkoToggle = true;
    @SerialEntry public boolean statTrackerToggle = true;
    @SerialEntry public boolean jajkoNiespodziankaToggle = true;
    @SerialEntry public boolean klejnotKupieckiToggle = true;
    @SerialEntry public boolean kontrolerMagazynowToggle = true;
    @SerialEntry public boolean tajemniceSkyUPaToggle = true;

    @SerialEntry public boolean elementiumToggle = true;
    @SerialEntry public boolean mendingToggle = true;
    @SerialEntry public boolean emeraldBlockToggle = true;
    @SerialEntry public boolean goldBlockToggle = true;
    @SerialEntry public boolean ironBlockToggle = true;
    @SerialEntry public boolean redstoneBlockToggle = true;
    @SerialEntry public boolean dirtToggle = true;
    @SerialEntry public boolean grassBlockToggle = true;
    @SerialEntry public boolean sandToggle = true;

    @SerialEntry public boolean czapkiToggle = true;
    @SerialEntry public boolean oceanicznaRudaToggle = true;
    @SerialEntry public boolean wybitnaPrzynetaToggle = true;
    @SerialEntry public boolean dobraPrzynetaToggle = true;

    @SerialEntry public int karambitDropped = 0;
    @SerialEntry public int perunDropped = 0;
    @SerialEntry public int cymofanDropped = 0;
    @SerialEntry public int mlotThoraDropped = 0;
    @SerialEntry public int urizelDropped = 0;
    @SerialEntry public int azadaDropped = 0;
    @SerialEntry public int spinelDropped = 0;
    @SerialEntry public int karpiolapDropped = 0;
    @SerialEntry public int ethericaDropped = 0;
    @SerialEntry public int lukLegolasaDropped = 0;
    @SerialEntry public int arbaletDropped = 0;
    @SerialEntry public int powrotOdysaDropped = 0;
    @SerialEntry public int cassisDropped = 0;
    @SerialEntry public int cuirassDropped = 0;
    @SerialEntry public int cuissotDropped = 0;
    @SerialEntry public int cossetDropped = 0;
    @SerialEntry public int kapcieLotnikaDropped = 0;
    @SerialEntry public int rivendellDropped = 0;
    @SerialEntry public int impetDropped = 0;
    @SerialEntry public int phloxDropped = 0;
    @SerialEntry public int majsterDropped = 0;
    @SerialEntry public int magiczneWiaderkoDropped = 0;
    @SerialEntry public int statTrackerDropped = 0;
    @SerialEntry public int jajkoNiespodziankaDropped = 0;
    @SerialEntry public int klejnotKupieckiDropped = 0;
    @SerialEntry public int kontrolerMagazynowDropped = 0;
    @SerialEntry public int tajemniceSkyUPaDropped = 0;

    @SerialEntry public int elementiumDropped = 0;
    @SerialEntry public int mendingDropped = 0;
    @SerialEntry public int emeraldBlockDropped = 0;
    @SerialEntry public int goldBlockDropped = 0;
    @SerialEntry public int ironBlockDropped = 0;
    @SerialEntry public int redstoneBlockDropped = 0;
    @SerialEntry public int dirtDropped = 0;
    @SerialEntry public int grassBlockDropped = 0;
    @SerialEntry public int sandDropped = 0;

    @SerialEntry public int kaskGornikaDropped = 0;
    @SerialEntry public int czapkaDrwalaDropped = 0;
    @SerialEntry public int kapeluszFarmeraDropped = 0;
    @SerialEntry public int bandanaLowcyDropped = 0;
    @SerialEntry public int kaskBudowniczegoDropped = 0;
    @SerialEntry public int kapeluszRybakaDropped = 0;
    @SerialEntry public int kapeluszCzarodziejaDropped = 0;
    @SerialEntry public int oceanicznaRudaDropped = 0;
    @SerialEntry public int wybitnaPrzynetaDropped = 0;
    @SerialEntry public int dobraPrzynetaDropped = 0;
}