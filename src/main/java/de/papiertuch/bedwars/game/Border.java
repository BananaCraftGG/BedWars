package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Border {

    private int minutes = 600; //600
    private int taskID;

    public void startCountdown() {
        minutes = 600;
        BedWars.getInstance().setBoarder(true);
        for (Player a : Bukkit.getOnlinePlayers()) {
            BedWars.getInstance().getBoard().setScoreBoard(a);
        }
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
            switch (minutes) {
                case 600:
                    WorldBorder wb = Bukkit.getWorld(BedWars.getInstance().getMap()).getWorldBorder();
                    wb.setCenter(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
                    wb.setSize(50, minutes);
                    for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                        team.setBed(false);
                    }
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.destroyAllBeds"));
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        a.playSound(a.getLocation(), Sound.valueOf(BedWars.getInstance().getBedWarsConfig().getString("sound.destroyBed")), 10F, 10F);
                        BedWars.getInstance().getBoard().setScoreBoard(a);
                    }
                    break;
                case 0:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.smallBoarder"));
                    break;
            }
            minutes--;

        }, 0, 20);
    }


    public int getMinutes() {
        return minutes;
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
