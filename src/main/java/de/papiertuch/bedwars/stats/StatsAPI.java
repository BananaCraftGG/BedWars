package de.papiertuch.bedwars.stats;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import xyz.haoshoku.nick.api.NickAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class StatsAPI {

    private Player player;
    private UUID uuid;
    private String name;

    public StatsAPI(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        if (BedWars.getInstance().isNickEnable()) {
            if (NickAPI.isNicked(player)) {
                this.name = NickAPI.getOriginalName(player);
            }
        }
        this.name = player.getName();
    }

    public StatsAPI() {

    }

    public Integer getRankingFromUUID() {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return -1;
        }
        boolean done = false;
        int rank = 0;
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT UUID FROM bedwars ORDER BY POINTS DESC");
            ResultSet rs = preparedStatement.executeQuery();
            while ((rs.next()) && (!done)) {
                rank++;
                if (rs.getString("UUID").equalsIgnoreCase(uuid.toString())) {
                    done = true;
                }
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rank;
    }

    public void createPlayer() {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return;
        }
        if (!isExistPlayer()) {
            try {
                PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("INSERT INTO bedwars (UUID, NAME, KILLS, DEATHS, WINS, PLAYED, BED, POINTS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                preparedStatement.setString(1, uuid.toString());
                preparedStatement.setString(2, name);
                preparedStatement.setInt(3, 0);
                preparedStatement.setInt(4, 0);
                preparedStatement.setInt(5, 0);
                preparedStatement.setInt(6, 0);
                preparedStatement.setInt(7, 0);
                preparedStatement.setInt(8, 0);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                updateName();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addInt(String type, int value) {
        setInt(type, getInt(type) + value);
    }

    public void updateName() {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return;
        }
        BedWars.getInstance().getMySQL().update("UPDATE bedwars SET NAME= '" + name + "' WHERE UUID= '" + uuid.toString() + "';");
    }

    public void removeInt(String type, int value) {
        setInt(type, getInt(type) - value);
    }

    public void setInt(String type, int value) {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return;
        }
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("UPDATE bedwars SET " + type + " = ? WHERE UUID = ?");
            preparedStatement.setInt(1, value);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Integer getInt(String type) {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return -1;
        }
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM bedwars WHERE UUID = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(type);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public Integer getInt(String name, String type) {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return -1;
        }
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM bedwars WHERE NAME = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(type);
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }


    private boolean isExistPlayer() {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return true;
        }
        try {
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM bedwars WHERE UUID = ?");
            preparedStatement.setString(1, uuid.toString());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getString("UUID") != null) {
                    return true;
                }
                return false;
            }
            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    private ArrayList<String> getRanking() {
        if (!BedWars.getInstance().getMySQL().isConnected()) {
            return new ArrayList<>();
        }
        ArrayList<String> ranking = new ArrayList<>();
        try {
            int rank = 0;
            PreparedStatement preparedStatement = BedWars.getInstance().getMySQL().getConnection().prepareStatement("SELECT * FROM bedwars ORDER BY POINTS DESC");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                if (rank == BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getCfg().getInt("statsWall")) {
                    break;
                }
                rank++;
                ranking.add(rs.getString("NAME"));
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ranking;
    }

    public void setStatsWall() {
        if (BedWars.getInstance().getMySQL().getConnection() == null) {
            return;
        }
        ArrayList<Location> list = BedWars.getInstance().getStatsWall();
        ArrayList<String> ranking = getRanking();
        try {
            for (int i = 0; i < list.size(); i++) {
                int id = i + 1;
                String name = "???";
                if (ranking.size() >= (id)) {
                    name = ranking.get(i);
                }
                if (list.get(i).getBlock().getType() != Material.SKULL_ITEM && list.get(i).getBlock().getType() != Material.SKULL) {
                    Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §cEs wurde kein Kopf an der folgenden Stelle gefunden");
                    Bukkit.broadcastMessage("§8» §fX: " + list.get(i).getBlockX());
                    Bukkit.broadcastMessage("§8» §fY: " + list.get(i).getBlockY());
                    Bukkit.broadcastMessage("§8» §fZ: " + list.get(i).getBlockZ());
                    continue;
                }
                Skull skull = (Skull) list.get(i).getBlock().getState();
                skull.setSkullType(SkullType.PLAYER);
                if (ranking.size() >= (id)) {
                    skull.setOwner(name);
                } else {
                    skull.setOwner("MHF_Question");
                }
                skull.update();
                Location loc = new Location((list.get(i)).getWorld(), (list.get(i)).getX(), (list.get(i)).getBlockY() - 1, (list.get(i)).getZ());
                if (loc.getBlock().getState() instanceof Sign) {
                    BlockState blockState = loc.getBlock().getState();
                    Sign sign = (Sign) blockState;
                    sign.setLine(0, "Platz §8#" + id);
                    sign.setLine(1, "§8" + name);
                    if (ranking.size() >= (id)) {
                        sign.setLine(2, "§l" + getInt(name, "POINTS") + " §rPunkte");
                        sign.setLine(3, "§l" + getInt(name, "WINS") + " §rWins");
                    } else {
                        sign.setLine(2, "0 §rPunkte");
                        sign.setLine(3, "0 §rWins");
                    }
                    sign.update();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
