package de.papiertuch.bedwars.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.stats.StatsAPI;
import de.papiertuch.bedwars.utils.LocationAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class StatsHologram {
    private Hologram hologram;
    private HashMap<Player, Hologram> hologramArrayList = new HashMap<>();

    private ArrayList<Location> hologramLocations = new ArrayList<>();

    public StatsHologram() throws SQLException {
    }

    public void load(Player p) {
        hologramLocations.forEach(holoLocation -> {
            if (holoLocation != null) {
                hologram = HologramsAPI.createHologram(BedWars.getInstance(), holoLocation);
                hologram.appendTextLine(ChatColor.DARK_GRAY + "===" + ChatColor.RED + " BedWars" + ChatColor.WHITE + " Stats " + ChatColor.DARK_GRAY + "===");
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Name: " + ChatColor.AQUA + p.getName());
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Rank: #" + ChatColor.GREEN +  new StatsAPI(p).getRankingFromUUID());
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Points: " + ChatColor.GREEN + BedWars.getInstance().getStatsAPI().getInt(p.getName(), "POINTS"));
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Kills: " + ChatColor.GREEN + BedWars.getInstance().getStatsAPI().getInt(p.getName(), "KILLS"));
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Deaths: " + ChatColor.GREEN + BedWars.getInstance().getStatsAPI().getInt(p.getName(), "DEATHS"));
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "K/D: " + ChatColor.GREEN + String.format("%.2f", (double) BedWars.getInstance().getStatsAPI().getInt(p.getName(), "KILLS") / (double) BedWars.getInstance().getStatsAPI().getInt(p.getName(), "DEATHS")));
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Games Played: " + ChatColor.GREEN + BedWars.getInstance().getStatsAPI().getInt(p.getName(), "PLAYED"));
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Wins: " + ChatColor.GREEN + BedWars.getInstance().getStatsAPI().getInt(p.getName(), "WINS"));
                hologram.appendTextLine("");
                hologram.appendTextLine(ChatColor.GRAY + "Losses: " + ChatColor.GREEN + BedWars.getInstance().getStatsAPI().getInt(p.getName(), "LOSSES"));
                hologramArrayList.put(p, hologram);
                hologramArrayList.forEach((player, hologram1) -> {
                    if (hologram1 != null) {
                        VisibilityManager visibilityManager = hologram1.getVisibilityManager();
                        visibilityManager.setVisibleByDefault(false);
                        visibilityManager.showTo(player);
                    }
                });

            }
        });
    }
    public void reload(Player p) throws SQLException {
        hologramArrayList.forEach((player, hologram) -> {
            if(p == player) {
                hologram.delete();
            }
        });
        load(p);
    }

    public void delete(Player p) {
        hologramArrayList.forEach((player, hologram) -> {
            if(p == player) {
                hologram.delete();
            }
        });
    }

    public void loadHologramLocations() {
        LocationAPI locationAPI = new LocationAPI("holograms");
        int npcCount = locationAPI.getCfg().getInt("holograms");
        Bukkit.broadcastMessage("count " + npcCount);
        for (int i = 1; i <= npcCount; i++) {
            hologramLocations.add(locationAPI.getLocation("statsHologram." + i));
        }
    }
}