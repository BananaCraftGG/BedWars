package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ForceStart implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission("*")) {
            if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
                if (BedWars.getInstance().getScheduler().getLobby().getSeconds() > BedWars.getInstance().getBedWarsConfig().getInt("command.start.seconds")) {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.start.startRound"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("ANVIL_USE"), 3, 2);
                    forceStart();
                } else {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.start.countDownUnderSeconds")
                            .replace("%seconds%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("command.start.seconds"))));
                }
            } else {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.start.roundAlreadyStarting"));
            }
        } else {
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.noPerms"));
        }
        return false;
    }
    private void forceStart() {
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
        for (Player a : Bukkit.getOnlinePlayers()) {
            a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound("ANVIL_LAND"), 1, 1);
            a.getInventory().remove(new ItemStorage().getVote());
            a.getInventory().remove(new ItemStorage().getStartItem());
            a.setExp(0);
            a.setLevel(0);
        }
        BedWars.getInstance().getScheduler().getLobby().playSound();
        WorldBorder wb = Bukkit.getWorld(BedWars.getInstance().getMap()).getWorldBorder();
        wb.setCenter(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
        wb.setSize(50, 2000000);
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
        BedWars.getInstance().getScheduler().getLobby().stopCountdown();
        BedWars.getInstance().getScheduler().getLobby().stopWaiting();
    }
}
