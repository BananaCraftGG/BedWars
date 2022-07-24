package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.api.events.GameStatingEvent;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Game {

    private int minutes = BedWars.getInstance().getBedWarsConfig().getInt("countDown.gameTime");
    private int taskID;

    public void startCountdown() {
        minutes = BedWars.getInstance().getBedWarsConfig().getConfiguration().getInt("countDown.gameTime");
        Bukkit.getPluginManager().callEvent(new GameStatingEvent());
        setGameStuff();
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), () -> {
            for (Player a : Bukkit.getOnlinePlayers()) {
                BedWars.getInstance().getGameHandler().hidePlayer(a);
                String goldState = BedWars.getInstance().isGold() ? "§a§l✔" : "§c§l✖";
                String itemState = BedWars.getInstance().isItemDrop() ? "§a§l✔" : "§c§l✖";
                BedWars.getInstance().getGameHandler().sendActionBar(a, BedWars.getInstance().getBedWarsConfig().getString("message.actionBar.game")
                        .replace("%goldVote%", goldState)
                        .replace("%itemDropVote%", itemState)
                        .replace("%map%", BedWars.getInstance().getMap()));
            }
            BedWars.getInstance().getBoard().updateBoard();
            if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.border")) {
                switch (minutes) {
                    case 1200:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.borderIn")
                                .replace("%minutes%", String.valueOf(20)));
                        break;
                    case 900:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.borderIn")
                                .replace("%minutes%", String.valueOf(15)));
                        break;
                    case 600:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.borderIn")
                                .replace("%minutes%", String.valueOf(10)));
                        break;
                    case 300:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.borderIn")
                                .replace("%minutes%", String.valueOf(5)));
                        break;
                    case 60:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.borderInOneMinute"));
                        break;
                    case 0:
                        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.border"));
                        BedWars.getInstance().getScheduler().getBoarder().startCountdown();
                        stopCountdown();
                        break;
                }
            }
            minutes--;
        }, 0, 20);

    }

    private void setGameStuff() {
        BedWars.getInstance().getScheduler().getLobby().stopWaiting();
        BedWars.getInstance().getScheduler().getLobby().stopCountdown();
        BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.roundStarting"));
        BedWars.getInstance().getGameHandler().startSpawner();
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().size() == 0) {
                team.setBed(false);
                BedWars.getInstance().getAliveTeams().remove(team);
            }
        }
        for (Player a : Bukkit.getOnlinePlayers()) {
            BedWars.getInstance().getBoard().addPlayerToBoard(a);
            a.getInventory().clear();
            a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound("ENDERMAN_TELEPORT"), 10F, 10F);
            a.setGameMode(GameMode.SURVIVAL);
            a.showPlayer(a);
            a.sendTitle("§a", "§7");
            BedWars.getInstance().getLastHit().clear();
            BedWars.getInstance().getStatsHandler().addPlayedGame(a);
        }
    }


    public int getMinutes() {
        return minutes;
    }

    public void stopCountdown() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
