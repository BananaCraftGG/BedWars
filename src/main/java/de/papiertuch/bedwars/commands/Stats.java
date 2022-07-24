package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.stats.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Stats implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        Bukkit.getScheduler().scheduleAsyncDelayedTask(BedWars.getInstance(), () -> {
            List<String> list = BedWars.getInstance().getBedWarsConfig().getConfiguration().getStringList("message.stats.lines");

            for (String  string : list) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', string
                        .replace("%prefix%", BedWars.getInstance().getBedWarsConfig().getString("message.prefix"))
                        .replace("%beds%", String.valueOf(new StatsAPI(player).getInt("BED")))
                        .replace("%loses%", String.valueOf((new StatsAPI(player).getInt("PLAYED") - new StatsAPI(player).getInt("WINS"))))
                        .replace("%wins%", String.valueOf(new StatsAPI(player).getInt("WINS")))
                        .replace("%played%", String.valueOf(new StatsAPI(player).getInt("PLAYED")))
                        .replace("%deaths%", String.valueOf(new StatsAPI(player).getInt("DEATHS")))
                        .replace("%kills%", String.valueOf(new StatsAPI(player).getInt("KILLS")))
                        .replace("%points%", String.valueOf(new StatsAPI(player).getInt("POINTS")))
                        .replace("%ranking%", String.valueOf(new StatsAPI(player).getRankingFromUUID()))
                        .replace("%player%", player.getDisplayName())));
            }
        });
        return false;
    }
}
