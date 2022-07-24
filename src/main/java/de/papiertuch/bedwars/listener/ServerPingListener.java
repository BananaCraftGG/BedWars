package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Leon on 25.10.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ServerPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (BedWars.getInstance().getGameState() != GameState.LOADGAME) {
            if (!BedWars.getInstance().getBedWarsConfig().getBoolean("module.cloudNet.v2.enable") && !BedWars.getInstance().getBedWarsConfig().getBoolean("module.cloudNet.v2.enable")) {
               /*
                if (BedWars.getInstance().getGameState() == GameState.INGAME || BedWars.getInstance().getGameState() == GameState.ENDING) {
                    event.setMaxPlayers(BedWars.getInstance().getGameHandler().getMaxPlayers() + 50);
                }
                */
            }
            event.setMotd(BedWars.getInstance().getBedWarsConfig().getString("settings.motd." + BedWars.getInstance().getGameState().toString().toLowerCase())
                    .replace("%map%", BedWars.getInstance().getMap()));
        }
    }
}
