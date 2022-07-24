package de.papiertuch.bedwars.stats;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.CoinState;
import de.papiertuch.bedwars.api.events.PlayerCoinsUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class StatsHandler {


    public void createPlayer(Player player) {
        if (!BedWars.getInstance().getBedWarsConfig().getBoolean("settings.enableStats")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.getInstance(), () -> new StatsAPI(player).createPlayer());
    }

    public void addKill(Player player) {
        if (!BedWars.getInstance().getBedWarsConfig().getBoolean("settings.enableStats")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.getInstance(), () -> {
            new StatsAPI(player).addInt("KILLS", 1);
            addPoints(player, 1);
            Bukkit.getPluginManager().callEvent(new PlayerCoinsUpdateEvent(CoinState.KILL, player, BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.killValue")));

        });
        if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.coinCommand.enable")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), BedWars.getInstance().getBedWarsConfig().getString("settings.coinCommand.command")
                    .replace("%player%", player.getName())
                    .replace("%value%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.killValue"))));
        }
    }

    public void addDeath(Player player) {
        if (!BedWars.getInstance().getBedWarsConfig().getBoolean("settings.enableStats")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.getInstance(), () -> new StatsAPI(player).addInt("DEATHS", 1));
    }

    public void addPlayedGame(Player player) {
        if (!BedWars.getInstance().getBedWarsConfig().getBoolean("settings.enableStats")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.getInstance(), () -> {
            new StatsAPI(player).addInt("PLAYED", 1);
            addPoints(player, 5);
            Bukkit.getPluginManager().callEvent(new PlayerCoinsUpdateEvent(CoinState.PLAYED, player, BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.playedValue")));
        });
        if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.coinCommand.enable")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), BedWars.getInstance().getBedWarsConfig().getString("settings.coinCommand.command")
                    .replace("%player%", player.getName())
                    .replace("%value%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.playedValue"))));
        }
    }


    public void addWin(Player player) {
        if (!BedWars.getInstance().getBedWarsConfig().getBoolean("settings.enableStats")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.getInstance(), () -> {
            new StatsAPI(player).addInt("WINS", 1);
            addPoints(player, 20);
            Bukkit.getPluginManager().callEvent(new PlayerCoinsUpdateEvent(CoinState.WIN, player, BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.winValue")));

        });
        if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.coinCommand.enable")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), BedWars.getInstance().getBedWarsConfig().getString("settings.coinCommand.command")
                    .replace("%player%", player.getName())
                    .replace("%value%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.winValue"))));
        }
    }

    public void addDestroyBed(Player player) {
        if (!BedWars.getInstance().getBedWarsConfig().getBoolean("settings.enableStats")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.getInstance(), () -> {
            new StatsAPI(player).addInt("BED", 1);
            addPoints(player, 10);
            Bukkit.getPluginManager().callEvent(new PlayerCoinsUpdateEvent(CoinState.BED, player, BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.bedValue")));

        });
        if (BedWars.getInstance().getBedWarsConfig().getBoolean("settings.coinCommand.enable")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), BedWars.getInstance().getBedWarsConfig().getString("settings.coinCommand.command")
                    .replace("%player%", player.getName())
                    .replace("%value%", String.valueOf(BedWars.getInstance().getBedWarsConfig().getInt("settings.coinCommand.bedValue"))));
        }
    }

    private void addPoints(Player player, int amount) {
        if (!BedWars.getInstance().getBedWarsConfig().getBoolean("settings.enableStats")) {
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(BedWars.getInstance(), () -> new StatsAPI(player).addInt("POINTS", amount));
    }
}
