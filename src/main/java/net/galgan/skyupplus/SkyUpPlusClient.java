package net.galgan.skyupplus;

import net.fabricmc.api.ClientModInitializer;
import net.galgan.skyupplus.config.Config;
import net.galgan.skyupplus.features.*;
import net.galgan.skyupplus.util.Commands;
import net.galgan.skyupplus.util.ServerRestrictor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkyUpPlusClient implements ClientModInitializer {

	public static final String MOD_ID = "skyupplus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		// --- INITIALIZATION ---
		Config.HANDLER.load();
		ServerRestrictor.checkIfAllowed();
		Commands.register();

		// --- FEATURES ---
		Scoreboard.handleScoreboard();
		Fishing.handleFishing();
		Requirements.handleRequirements();
		Crates.handleCrates();
		Reminders.handleReminders();
		Warnings.handleWarnings();
		Cooldown.handleCooldown();
		MessageRemover.removeMessage();
		HUD.renderHUD();
		//Debug.debug();

		// --- LOGGER ---
		LOGGER.info("Successfully loaded SkyUpPlus!");
	}
}