package de.papiertuch.bedwars.listener;

import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;
import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.nickaddon.NickAddon;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        BedWars.getInstance().getStatsAPI().loadStatsWall();
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
                BedWars.getInstance().getStatsHologram().load(player);
            }
            BedWars.getInstance().getStatsHandler().createPlayer(player);
            BedWars.getInstance().getGameHandler().setPlayer(player);
            BedWars.getInstance().getBoard().addPlayerToBoard(player);
            if (BedWars.getInstance().isNickEnable()) {
                if (NickAddon.getInstance().getApi().getAutoNickState(player)) {
                    Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> {
                        NickAddon.getInstance().getApi().setNick(player, true);
                        BedWars.getInstance().getBoard().addPlayerToBoard(player);
                        Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.joinGame")
                                .replace("%player%", player.getDisplayName())
                                .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                                .replace("%maxPlayers%", String.valueOf(BedWars.getInstance().getGameHandler().getMaxPlayers())));

                    }, 2);
                } else {
                    Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.joinGame")
                            .replace("%player%", player.getDisplayName())
                            .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                            .replace("%maxPlayers%", String.valueOf(BedWars.getInstance().getGameHandler().getMaxPlayers())));
                }
            } else {
                Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.joinGame")
                        .replace("%player%", player.getDisplayName())
                        .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                        .replace("%maxPlayers%", String.valueOf(BedWars.getInstance().getGameHandler().getMaxPlayers())));
            }
            if ((BedWars.getInstance().getPlayers().size() >= BedWars.getInstance().getBedWarsConfig().getInt("settings.minPlayers")) && (!BedWars.getInstance().getScheduler().getLobby().isRunning())) {
                BedWars.getInstance().getScheduler().getLobby().stopWaiting();
                BedWars.getInstance().getScheduler().getLobby().startCountdown();
            }
            if ((BedWars.getInstance().getPlayers().size() < BedWars.getInstance().getBedWarsConfig().getInt("settings.minPlayers")) && (!BedWars.getInstance().getScheduler().getLobby().isWaiting())) {
                BedWars.getInstance().getScheduler().getLobby().startWaiting();
            }
            if (BedWars.getInstance().getPlayers().size() == BedWars.getInstance().getGameHandler().getMaxPlayers()) {
                BedWars.getInstance().getScheduler().getLobby().setSeconds((BedWars.getInstance().getBedWarsConfig().getInt("countDown.lobbyDuration") / 2));
                BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStarting"));
            }
        }
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            event.setJoinMessage(null);
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.spectator"));
            BedWars.getInstance().getGameHandler().setSpectator(player);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLoginEvent(PlayerLoginEvent event) {
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.premiumKick.enable")) {
                Player player = event.getPlayer();
                int i = getMaxPlayers();
                if (Bukkit.getOnlinePlayers().size() != i) {
                    return;
                }
                if (!player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("settings.premiumKick.permission"))) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, BedWars.getInstance().getBedWarsConfig().getString("message.premiumKick.full"));
                    return;
                }
                List<Player> list = new ArrayList<>();
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (!other.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("settings.premiumKick.permission"))) {
                        list.add(other);
                    }
                }
                if (list.isEmpty()) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, BedWars.getInstance().getBedWarsConfig().getString("message.premiumKick.fullPremium"));
                }

                Player random = list.get(new Random().nextInt(list.size()));
                random.kickPlayer(BedWars.getInstance().getBedWarsConfig().getString("message.premiumKick.kickPlayer"));
                event.allow();
            }
        }
        if (BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §cDie Runde ist bereits zuende...");
        }
    }

    private int getMaxPlayers() {
        if (BedWars.getInstance().getBedWarsConfig().getBoolean("module.cloudNet.v3.enable")) {
            return BukkitCloudNetHelper.getMaxPlayers();
        }
        return Bukkit.getMaxPlayers();
    }
}
