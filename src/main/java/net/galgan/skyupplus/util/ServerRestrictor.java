package net.galgan.skyupplus.util;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.network.ServerInfo;

public class ServerRestrictor {
    private static boolean allowedServer = false;

    public static void checkIfAllowed() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            ServerInfo server = client.getCurrentServerEntry();
            if (server != null) {
                allowedServer = server.address.contains("skyup.pl");
            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            allowedServer = false;
        });
    }

    public static boolean isAllowed() {
        return allowedServer;
    }
}