package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!BedWars.getInstance().isItemDrop()) {
            event.getDrops().clear();
        }
        event.setDeathMessage(null);
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (event.getEntity().getKiller() != null) {
                Player killer = event.getEntity().getKiller();
                respawnPlayer(player);
                BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.killMessage")
                        .replace("%player%", player.getDisplayName())
                        .replace("%killer%", killer.getDisplayName()));
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.killerLife")
                        .replace("%killer%", killer.getDisplayName())
                        .replace("%live%", round(killer.getHealth() / 2) + " ❤"));
                killer.playSound(killer.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.kill")), 1, 1);
                BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                if (team == null) {
                    BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                } else if (!team.hasBed()) {
                    BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                }
                BedWars.getInstance().getGameHandler().checkTeams(player);
                BedWars.getInstance().getGameHandler().checkWinner();
                BedWars.getInstance().getLastHit().remove(player);
                BedWars.getInstance().getStatsHandler().addKill(killer);
                BedWars.getInstance().getStatsHandler().addDeath(player);
            } else {
                respawnPlayer(player);
                BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.death")
                        .replace("%player%", player.getDisplayName()));
                BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                if (team == null) {
                    BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                } else if (!team.hasBed()) {
                    BedWars.getInstance().getPlayers().remove(player.getUniqueId());
                }
                BedWars.getInstance().getGameHandler().checkTeams(player);
                BedWars.getInstance().getGameHandler().checkWinner();
                BedWars.getInstance().getStatsHandler().addDeath(player);
            }
        } else {
            BedWars.getInstance().getGameHandler().setPlayer(player);
        }
    }

    private double round(double health) {
        if (health < 0.3) return 0.5D;
        double round = Math.round(health * 2) / 2.0;
        return round;
    }

    private void respawnPlayer(Player player) {
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> player.spigot().respawn(), 2);
    }
}
