package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.nickaddon.NickAddon;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import xyz.haoshoku.nick.api.NickAPI;

import java.text.SimpleDateFormat;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class Board {

    public void setScoreBoard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            Team playerTeam = board.registerNewTeam(team.getTagId() + team.getName().toLowerCase());
            playerTeam.setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.teams.prefix")
                    .replace("%team%", team.getColor()));
            playerTeam.setAllowFriendlyFire(false);
        }
        Team spec = board.registerNewTeam("99spec");
        Team players = board.registerNewTeam("players");
        Team mode = board.registerNewTeam("mode");
        Team map = board.registerNewTeam("map");

        spec.setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.spectator.prefix"));

        players.setPrefix(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line1.input"))
                .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size())));
        players.addEntry(" §e");

        mode.setPrefix(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line2.input"))
                .replace("%mode%", BedWars.getInstance().getBedWarsConfig().getString("settings.mode")));
        mode.addEntry(" §b");

        map.setPrefix(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line3.input"))
                .replace("%map%", ""));
        map.setSuffix(BedWars.getInstance().getMap());
        map.addEntry("");

        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            Team score = board.registerNewTeam(team.getName());
            if (team.hasBed()) {
                score.setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasBed.one")
                        .replace("%team%", team.getColor()));
                if (BedWars.getInstance().getGameHandler().getMaxPlayerAtTeam() != 1) {
                    score.setSuffix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasBed.two")
                            .replace("%players%", String.valueOf(team.getPlayers().size())));
                }
            } else if (team.getPlayers().size() == 0) {
                score.setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamDeath.one")
                        .replace("%team%", team.getColor()));
                score.setSuffix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamDeath.two"));
            } else {
                score.setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasNoBed.one")
                        .replace("%team%", team.getColor()));
                if (BedWars.getInstance().getGameHandler().getMaxPlayerAtTeam() != 1) {
                    score.setSuffix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasNoBed.two")
                            .replace("%players%", String.valueOf(team.getPlayers().size())));
                }
            }
            score.addEntry(team.getName());
        }

        for (Player s : Bukkit.getOnlinePlayers()) {
            if (BedWars.getInstance().getGameState() == GameState.INGAME) {
                if (BedWars.getInstance().getPlayers().contains(s.getUniqueId())) {
                    BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(s);
                    board.getTeam(team.getTagId() + team.getName().toLowerCase()).addEntry(s.getName());
                    s.setDisplayName(team.getColor() + s.getName());
                } else if (BedWars.getInstance().getSpectators().contains(s.getUniqueId())) {
                    spec.addEntry(s.getName());
                    s.setDisplayName(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.spectator.display") + s.getName());
                }
            }
        }
        Objective obj = board.registerNewObjective("lobby", "system");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(getTitle());
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            obj.getScore("     ").setScore(9);
            obj.getScore(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line1.title")).setScore(8);
            obj.getScore(" §e").setScore(7);
            obj.getScore("  ").setScore(6);
            obj.getScore(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line2.title")).setScore(5);
            obj.getScore(" §b").setScore(4);
            obj.getScore("   ").setScore(3);
            obj.getScore(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line3.title"))).setScore(2);
            obj.getScore("").setScore(1);
            obj.getScore("    ").setScore(0);
        } else if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            obj.getScore("      ").setScore(9);
            obj.getScore(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line4.title"))).setScore(8);
            if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                obj.getScore(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line4.input"))
                        .replace("%team%", BedWars.getInstance().getGameHandler().getTeam(player).getColor() + BedWars.getInstance().getGameHandler().getTeam(player).getName())).setScore(7);
            } else {
                obj.getScore(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line4.input"))
                        .replace("%team%", "§7Spectator")).setScore(7);
            }
            obj.getScore(" ").setScore(6);
            obj.getScore(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line5.title"))).setScore(5);
            int i = 5;
            for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                obj.getScore(team.getName()).setScore(i - 1);
                i = i - 1;
            }
            obj.getScore("   ").setScore(i - 1);

        } else {
            obj.getScore("       ").setScore(6);
            obj.getScore(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line1.title"))).setScore(5);
            obj.getScore(" §e").setScore(4);
            obj.getScore(" ").setScore(3);
            obj.getScore(addPlaceHolders(player, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line3.title"))
                    .replace("%map%", BedWars.getInstance().getMap())).setScore(2);
            obj.getScore("").setScore(1);
            obj.getScore("   ").setScore(0);
        }
        player.setScoreboard(board);
    }

    public void updateBoard() {
        for (Player a : Bukkit.getOnlinePlayers()) {
            a.getScoreboard().getObjective("lobby").setDisplayName(getTitle());
            a.getScoreboard().getTeam("map").setPrefix(addPlaceHolders(a, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line3.input"))
                    .replace("%map%", ""));
            a.getScoreboard().getTeam("map").setSuffix(BedWars.getInstance().getMap());
            a.getScoreboard().getTeam("players").setPrefix(addPlaceHolders(a, BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line1.input"))
                    .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size())));
            if (BedWars.getInstance().getGameState() == GameState.INGAME) {
                for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                    if (team.hasBed()) {
                        a.getScoreboard().getTeam(team.getName()).setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasBed.one")
                                .replace("%team%", team.getColor()));
                        if (BedWars.getInstance().getGameHandler().getMaxPlayerAtTeam() != 1) {
                            a.getScoreboard().getTeam(team.getName()).setSuffix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasBed.two")
                                    .replace("%players%", String.valueOf(team.getPlayers().size())));
                        }
                    } else if (team.getPlayers().size() == 0) {
                        a.getScoreboard().getTeam(team.getName()).setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamDeath.one")
                                .replace("%team%", team.getColor()));
                        a.getScoreboard().getTeam(team.getName()).setSuffix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamDeath.two"));
                    } else {
                        a.getScoreboard().getTeam(team.getName()).setPrefix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasNoBed.one")
                                .replace("%team%", team.getColor()));
                        if (BedWars.getInstance().getGameHandler().getMaxPlayerAtTeam() != 1) {
                            a.getScoreboard().getTeam(team.getName()).setSuffix(BedWars.getInstance().getBedWarsConfig().getString("scoreboard.line.teamHasNoBed.two")
                                    .replace("%players%", String.valueOf(team.getPlayers().size())));
                        }
                    }
                }
            }
        }
    }
    

    public void updateNameTags(Player player) {
        TabListGroup playerPermissionGroup = BedWars.getInstance().getGameHandler().getTabListGroup(player);

        initScoreboard(player);
        for (Player all : player.getServer().getOnlinePlayers()) {
            initScoreboard(all);
            if (playerPermissionGroup != null)
                addTeamEntry(player, all, playerPermissionGroup);

            TabListGroup targetPermissionGroup = BedWars.getInstance().getGameHandler().getTabListGroup(all);

            if (targetPermissionGroup != null)
                addTeamEntry(all, player, targetPermissionGroup);
        }
    }

    private void addTeamEntry(Player target, Player all, TabListGroup permissionGroup) {
        Team team = all.getScoreboard().getTeam(permissionGroup.getTagId() + permissionGroup.getName());

        if (team == null)
            team = all.getScoreboard().registerNewTeam(permissionGroup.getTagId() + permissionGroup.getName());

        team.setPrefix(ChatColor.translateAlternateColorCodes('&', permissionGroup.getPrefix()));
        team.setSuffix(ChatColor.translateAlternateColorCodes('&', permissionGroup.getSuffix()));
        team.addEntry(target.getName());
        target.setDisplayName(ChatColor.translateAlternateColorCodes('&', permissionGroup.getDisplay()) + target.getName());
    }

    private void initScoreboard(Player all) {
        if (all.getScoreboard() == null)
            all.setScoreboard(all.getServer().getScoreboardManager().getNewScoreboard());
    }

    public void addPlayerToBoard(Player player) {
        setScoreBoard(player);
        for (Player a : Bukkit.getOnlinePlayers()) {
            if (BedWars.getInstance().getGameState() == GameState.INGAME) {
                if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                    BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                    a.getScoreboard().getTeam(team.getTagId() + team.getName().toLowerCase()).addEntry(player.getName());
                } else {
                    a.getScoreboard().getTeam("99spec").addEntry(player.getName());
                }
            } else {
                updateNameTags(player);
            }
        }
    }

    private String getTitle() {
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(BedWars.getInstance().getScheduler().getLobby().getSeconds() * 1000);
            return BedWars.getInstance().getBedWarsConfig().getString("scoreboard.title")
                    .replace("%time%", time);

        } else if (BedWars.getInstance().getGameState() == GameState.INGAME && BedWars.getInstance().isBoarder()) {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(BedWars.getInstance().getScheduler().getBoarder().getMinutes() * 1000);
            return BedWars.getInstance().getBedWarsConfig().getString("scoreboard.title")
                    .replace("%time%", time);

        } else if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(BedWars.getInstance().getScheduler().getGame().getMinutes() * 1000);
            return BedWars.getInstance().getBedWarsConfig().getString("scoreboard.title")
                    .replace("%time%", time);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(BedWars.getInstance().getScheduler().getEnding().getSeconds() * 1000);
            return BedWars.getInstance().getBedWarsConfig().getString("scoreboard.title")
                    .replace("%time%", time);
        }
    }

    private String addPlaceHolders(Player player, String string) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return string;
        }
        return PlaceholderAPI.setPlaceholders(player, string);
    }
}
