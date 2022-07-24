package de.papiertuch.bedwars.stats;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Leon on 16.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class MySQL {

    private Connection connection = null;

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + BedWars.getInstance().getBedWarsConfig().getString("settings.mysql.host")
                            + ":" + BedWars.getInstance().getBedWarsConfig().getInt("settings.mysql.port") +
                            "/" + BedWars.getInstance().getBedWarsConfig().getString("settings.mysql.dataBase") + "?autoReconnect=true",
                    BedWars.getInstance().getBedWarsConfig().getString("settings.mysql.user"),
                    BedWars.getInstance().getBedWarsConfig().getConfiguration().getString("settings.mysql.password"));
            Bukkit.getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §aEine Verbindung zum MySQl-Server war erfolgreich");
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §cDie Verbindung zum MySQL-Server ist fehlgeschlagen");
        }
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(BedWars.getInstance(), () -> update("CREATE TABLE IF NOT EXISTS bedwars (UUID VARCHAR(100), NAME VARCHAR(100), KILLS INT, DEATHS INT, WINS INT, PLAYED INT, BED INT, POINTS INT)"), 0, 216000);
    }

    public void createTable() {
        update("CREATE TABLE IF NOT EXISTS bedwars (UUID VARCHAR(100), NAME VARCHAR(100), KILLS INT, DEATHS INT, WINS INT, PLAYED INT, BED INT, POINTS INT)");
    }


    public void disconnect() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void update(String qry) {
        try {
            PreparedStatement ps = connection.prepareStatement(qry);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §cEs besteht keine Mysql-Verbindung, dass Plugin speichert keine Stats!");
        }
    }
}
