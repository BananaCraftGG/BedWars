package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            if (BedWars.getInstance().getGameHandler().getSetup().containsKey(player.getUniqueId())) {
                BedWars.getInstance().getGameHandler().getSetupTeam().remove(player.getUniqueId());
                BedWars.getInstance().getGameHandler().getSetup().remove(player.getUniqueId());
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
            }
            BedWars.getInstance().getGameHandler().clearFromTeams(player);
            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.leaveGame")
                    .replace("%player%", player.getDisplayName()));
            BedWars.getInstance().getPlayers().remove(player.getUniqueId());
            BedWars.getInstance().getNoGold().remove(player.getUniqueId());
            BedWars.getInstance().getWithGold().remove(player.getUniqueId());
            BedWars.getInstance().getNoItemDrop().remove(player.getUniqueId());
            BedWars.getInstance().getWithItemDrop().remove(player.getUniqueId());
            BedWars.getInstance().getBoard().updateBoard();
            for (ArrayList<UUID> list : BedWars.getInstance().getMaps().values()) {
                if (list.contains(player.getUniqueId())) {
                    list.remove(player.getUniqueId());
                }
            }
            if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
                if ((BedWars.getInstance().getPlayers().size() < BedWars.getInstance().getBedWarsConfig().getInt("settings.minPlayers")) && (BedWars.getInstance().getScheduler().getLobby().isRunning())) {
                    if (!BedWars.getInstance().getScheduler().getLobby().isWaiting()) {
                        BedWars.getInstance().getScheduler().getLobby().stopCountdown();
                        BedWars.getInstance().getScheduler().getLobby().startWaiting();
                    }
                }
            }
            BedWars.getInstance().getBoard().updateBoard();
        }
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                BedWars.getInstance().getGameHandler().checkTeams(player);
                BedWars.getInstance().getGameHandler().checkWinner();
                BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.leaveGame")
                        .replace("%player%", player.getDisplayName()));
            } else {
                BedWars.getInstance().getSpectators().remove(player.getUniqueId());
            }
        }
    }
}
