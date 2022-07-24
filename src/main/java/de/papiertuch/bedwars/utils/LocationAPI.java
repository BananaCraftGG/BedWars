package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class LocationAPI {

    private File file;
    private FileConfiguration cfg;
    private String map;

    public void setLocation(String name, Location loc) {
        String world = loc.getWorld().getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();

        cfg.set(name + ".X", x);
        cfg.set(name + ".Y", y);
        cfg.set(name + ".Z", z);
        cfg.set(name + ".Yaw", yaw);
        cfg.set(name + ".Pitch", pitch);
        cfg.set(name + ".World", world);

        try {
            cfg.save(file);
        } catch (IOException e) {
            System.err.println("Die Location " + name + " konnte nicht gespeichert werden.");
            e.printStackTrace();
        }
    }

    public LocationAPI(String map) {
        this.map = map;
        file = new File("plugins/BedWars", map + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public File getFile() {
        return file;
    }

    public boolean isExists(String name) {
        return cfg.getString(name + ".X") != null;
    }

    public boolean checkLocation(String name) {
        return cfg.get(name) != null;
    }

    public Location getLocation(String name) {
        try {
            double x = cfg.getDouble(name + ".X");
            double y = cfg.getDouble(name + ".Y");
            double z = cfg.getDouble(name + ".Z");
            double yaw = cfg.getDouble(name + ".Yaw");
            double pitch = cfg.getDouble(name + ".Pitch");
            String world = cfg.getString(name + ".World");
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            loc.setYaw((float) yaw);
            loc.setPitch((float) pitch);
            return loc;
        } catch (Exception e) {
            Bukkit.broadcastMessage(BedWars.getInstance().getBedWarsConfig().getString("message.locationNotFound")
                    .replace("%map%", this.map)
                    .replace("%loc%", name));
        }
        return null;
    }

    public void setBedLocation(String name, Location loc) {
        cfg.set(name + ".X", loc.getBlockX());
        cfg.set(name + ".Y", loc.getBlockY());
        cfg.set(name + ".Z", loc.getBlockZ());
        cfg.set(name + ".World", loc.getWorld().getName());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getBedLocation(String name) {
        double x = cfg.getDouble(name + ".X");
        double y = cfg.getDouble(name + ".Y");
        double z = cfg.getDouble(name + ".Z");
        String World = cfg.getString(name + ".World");
        org.bukkit.World w = Bukkit.getWorld(World);
        Location loc = new Location(w, x, y, z);
        return loc;
    }
}
