package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setRespawnLocation(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby").add(0, 1, 0));
            player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("ENDERMAN_TELEPORT"), 10F, 10F);
            player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.leave.slot"), new ItemStorage().getLeave());
        } else if (BedWars.getInstance().getGameState() == GameState.INGAME && !BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            event.setRespawnLocation(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
            BedWars.getInstance().getGameHandler().setSpectator(player);
        } else {
            String team = BedWars.getInstance().getGameHandler().getTeam(player).getName().toLowerCase();
            event.setRespawnLocation(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation(team + ".spawn"));
        }
    }
}
