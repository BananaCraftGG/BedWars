package de.papiertuch.bedwars.game;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Lobby {

    private int seconds;
    private int taskID, waitingID;
    private float xp = 1.00f;
    private boolean isRunning = false;
    private boolean waiting = false;


    public void startCountdown() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.team.slot"), new ItemStorage().getTeams());
            player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.vote.slot"), new ItemStorage().getVote());
            if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("command.start.permission"))) {
                player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.start.slot"), new ItemStorage().getStartItem());
            }
            player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.leave.slot"), new ItemStorage().getLeave());
        }
        seconds = BedWars.getInstance().getBedWarsConfig().getInt("countDown.lobbyDuration");
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), () -> {
            BedWars.getInstance().getSpectators().clear();
            xp = (((float) 1 / BedWars.getInstance().getBedWarsConfig().getInt("countDown.lobbyDuration")) * seconds);
            for (Player a : Bukkit.getOnlinePlayers()) {
                BedWars.getInstance().getBoard().updateBoard();
                a.setExp(xp);
                a.showPlayer(a);
                a.setLevel(seconds);
                BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(a);
                if (team != null) {
                    BedWars.getInstance().getGameHandler().sendActionBar(a, BedWars.getInstance().getBedWarsConfig().getString("message.actionBar.lobby")
                            .replace("%team%", team.getColor() + "§l" + team.getName()));
                }
            }
            switch (seconds) {
                case 60:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStarting"));
                    playSound();
                    break;
                case 45:
                case 30:
                case 10:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                            .replace("%seconds%", String.valueOf(seconds)));
                    playSound();
                    break;
                case 15:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                            .replace("%seconds%", String.valueOf(seconds)));
                    playSound();
                    if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.sendTitle")) {
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle(BedWars.getInstance().getBedWarsConfig().getString("message.prefix"), BedWars.getInstance().getMap());
                        }
                    }
                    break;
                case 5:
                    BedWars.getInstance().getGameHandler().checkGoldVoting();
                    BedWars.getInstance().getGameHandler().checkMapVoting();
                    BedWars.getInstance().getGameHandler().checkItemDropVoting();
                    BedWars.getInstance().getGameHandler().sendBroadCast("");


                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.goldStatus")
                            .replace("%state%", BedWars.getInstance().isGold() ? BedWars.getInstance().getBedWarsConfig().getString("message.voting.voteEnable") : BedWars.getInstance().getBedWarsConfig().getString("message.voting.voteDisable")));
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.itemDropStatus")
                            .replace("%state%", BedWars.getInstance().isItemDrop() ? BedWars.getInstance().getBedWarsConfig().getString("message.voting.voteEnable") : BedWars.getInstance().getBedWarsConfig().getString("message.voting.voteDisable")));
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.mapStatus")
                            .replace("%map%", BedWars.getInstance().getMap()));

                    BedWars.getInstance().getGameHandler().sendBroadCast("");
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                            .replace("%seconds%", String.valueOf(seconds)));
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound("ANVIL_LAND"), 1, 1);
                        a.getInventory().remove(new ItemStorage().getVote());
                        a.getInventory().remove(new ItemStorage().getStartItem());
                    }
                    break;
                case 3:
                case 4:
                case 2:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingIn")
                            .replace("%seconds%", String.valueOf(seconds)));
                    playSound();
                    if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.sendTitle")) {
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle("§e" + seconds, "");
                        }
                    }
                    break;
                case 1:
                    BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.gameStartingInOneSecond"));
                    playSound();
                    if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.sendTitle")) {
                        for (Player a : Bukkit.getOnlinePlayers()) {
                            a.sendTitle("§e" + seconds, "");
                        }
                    }
                    WorldBorder wb = Bukkit.getWorld(BedWars.getInstance().getMap()).getWorldBorder();
                    wb.setCenter(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
                    wb.setSize(50, 2000000);
                    break;
                case 0:
                    for (Entity entity : Bukkit.getWorld(BedWars.getInstance().getMap()).getEntities()) {
                        if (entity instanceof Item) {
                            entity.remove();
                        }
                    }
                    ArrayList<BedWarsTeam> teamWithPlayers = new ArrayList<>();
                    for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                        if (!team.getPlayers().isEmpty()) {
                            teamWithPlayers.add(team);
                        }
                    }
                    for (Player a : Bukkit.getOnlinePlayers()) {
                        if (teamWithPlayers.size() == 1 && teamWithPlayers.get(0).getPlayers().size() == BedWars.getInstance().getPlayers().size()) {
                            a.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teamEmpty"));
                            BedWars.getInstance().getGameHandler().removePlayerFromCurrentTeam(a);
                        }
                        if (!BedWars.getInstance().getGameHandler().hasTeam(a)) {
                            BedWars.getInstance().getGameHandler().getFreeTeamForPlayer(a);
                        }
                        BedWars.getInstance().getGameHandler().teleportToMap(a);
                    }
                    BedWars.getInstance().setGameState(GameState.INGAME);
                    BedWars.getInstance().getScheduler().getGame().startCountdown();
                    stopCountdown();
                    stopWaiting();
                    break;
            }
            if (BedWars.getInstance().getGameHandler().getSetup().isEmpty()) {
                seconds--;
            }
        }, 0, 20);
    }

    private void playSound() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.countdown")), 10F, 10F);
        }
    }

    public void startWaiting() {
        waiting = true;
        seconds = BedWars.getInstance().getBedWarsConfig().getInt("countDown.lobbyDuration");
        xp = (((float) 1 / BedWars.getInstance().getBedWarsConfig().getInt("countDown.lobbyDuration")) * seconds);
        waitingID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BedWars.getInstance(), () -> {
            int missing = (BedWars.getInstance().getBedWarsConfig().getInt("settings.minPlayers") - BedWars.getInstance().getPlayers().size());
            for (Player a : Bukkit.getOnlinePlayers()) {
                if (BedWars.getInstance().getPlayers().size() == 1) {
                    BedWars.getInstance().getGameHandler().sendActionBar(a, BedWars.getInstance().getBedWarsConfig().getString("message.actionBar.lobbyWaitingOnes"));
                } else {
                    BedWars.getInstance().getGameHandler().sendActionBar(a, BedWars.getInstance().getBedWarsConfig().getString("message.actionBar.lobbyWaiting")
                            .replace("%players%", String.valueOf(missing)));
                }
                a.setExp(xp);
                a.setLevel(seconds);
            }
        }, 0, 20 * 1);
    }


    public void stopWaiting() {
        if (isWaiting()) {
            setWaiting(false);
            Bukkit.getScheduler().cancelTask(waitingID);
        }
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopCountdown() {
        if (isRunning) {
            isRunning = false;
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Integer getSeconds() {
        return seconds;
    }
}
