package de.papiertuch.bedwars.commands;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.TabListGroup;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Setup implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player player = (Player) commandSender;
        if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("command.setup.permission"))) {
            if (args.length == 0) {
               sendSyntax(player);
            }
            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "listmaps":
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Maps");
                        for (String maps : BedWars.getInstance().getMaps().keySet()) {
                            player.sendMessage("§8» §f§l" + maps);
                        }
                        break;
                    case "listteams":
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Teams");
                        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                            player.sendMessage("§8» §f§l" + team.getColor() + team.getName());
                        }
                        break;
                    case "listnametags":
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7NameTags");
                        for (TabListGroup tabListGroup : BedWars.getInstance().getTabListGroups()) {
                            player.sendMessage("§8» §f§lPrefix §8»" + tabListGroup.getPrefix());
                            player.sendMessage("§8» §f§lTagId §8» §e" + tabListGroup.getTagId());
                            player.sendMessage("§8» §f§lPermission §8» §e" + tabListGroup.getPermission());
                        }
                        break;
                    default:
                        sendSyntax(player);
                        break;
                }
            }
            if (args.length == 2) {
                String value = args[1];
                switch (args[0].toLowerCase()) {
                    case "addmap":
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.addMap")
                        .replace("%map%", value));
                        BedWars.getInstance().getGameHandler().startSetup(player, value);
                        break;
                    case "addteam":
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.addTeam")
                                .replace("%team%", value));
                        List<String> list = BedWars.getInstance().getBedWarsConfig().getConfiguration().getStringList("team.teams");
                        list.add(value);
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("team.teams", list);
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("team." + value + ".colorCode", "&f");
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("team." + value + ".color", "WHITE");
                        BedWars.getInstance().getBedWarsConfig().save();
                        BedWars.getInstance().getBedWarsTeams().add(new BedWarsTeam(value,
                                0,
                                "§f",
                                Color.WHITE,
                                Integer.valueOf(BedWars.getInstance().getBedWarsConfig().getString("settings.mode").split("x")[1]),
                                new ArrayList<>()));
                        break;
                    case "addnametag":
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.addNameTag")
                                .replace("%nameTag%", value));
                        List<String> nameTags = BedWars.getInstance().getBedWarsConfig().getConfiguration().getStringList("nameTags.tabList");
                        nameTags.add(value);
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("nameTags.tabList", nameTags);
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("nameTags." + value + ".prefix", "&7");
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("nameTags." + value + ".suffix", "&7");
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("nameTags." + value + ".display", "&7");
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("nameTags." + value + ".tagId", 9999);
                        BedWars.getInstance().getBedWarsConfig().getConfiguration().set("nameTags." + value + ".permissions", "bedwars." + value.toLowerCase());
                        BedWars.getInstance().getBedWarsConfig().save();
                        BedWars.getInstance().getTabListGroups().add(new TabListGroup(value,
                                "§7",
                                "§7",
                                "§7",
                                9999,
                                "bedwars." + value.toLowerCase()));
                        break;
                    case "tp":
                        if (new File(value).exists()) {
                            if (Bukkit.getWorld(value) == null) {
                                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.loadMap"));
                                Bukkit.createWorld(WorldCreator.name(value).type(WorldType.FLAT).generatorSettings("3;minecraft:air;2").generateStructures(false));
                                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.loadFinish"));
                            }
                            player.teleport(Bukkit.getWorld(value).getSpawnLocation());
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.teleport")
                            .replace("%map%", value));
                        } else {
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.mapNotExists"));
                        }
                        break;
                    default:
                        sendSyntax(player);
                        break;
                }
            }
        } else {
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.noPerms"));
        }
        return false;
    }

    private void sendSyntax(Player player) {
        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §fCommands");
        player.sendMessage("§8» /§fsetup listMaps");
        player.sendMessage("§8» /§fsetup listTeams");
        player.sendMessage("§8» /§fsetup listNameTags");
        player.sendMessage("§8» /§fsetup tp §8<§fMap§8>");
        player.sendMessage("§8» /§fsetup addMap §8<§fMap§8>");
        player.sendMessage("§8» /§fsetup addTeam §8<§fName§8>");
        player.sendMessage("§8» /§fsetup addNameTag §8<§fName§8>");
    }
}
