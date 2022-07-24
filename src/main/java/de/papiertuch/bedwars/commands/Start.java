package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Start implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("command.start.permission"))) {
            if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
                if (BedWars.getInstance().getPlayers().size() >= BedWars.getInstance().getBedWarsConfig().getInt("settings.minPlayers")) {
                    if (BedWars.getInstance().getScheduler().getLobby().getSeconds() > BedWars.getInstance().getBedWarsConfig().getInt("command.start.seconds")) {
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.start.startRound"));
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("ANVIL_USE"), 3, 2);
                        BedWars.getInstance().getScheduler().getLobby().setSeconds(BedWars.getInstance().getBedWarsConfig().getInt("command.start.seconds"));
                    } else {
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.start.countDownUnderSeconds")
                                .replace("%seconds%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("command.start.seconds"))));
                    }
                } else {
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.start.notEnoughPlayers"));
                }
            } else {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.start.roundAlreadyStarting"));
            }
        } else {
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.noPerms"));
        }
        return false;
    }
}
