package de.papiertuch.bedwars.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */
@Getter
@Setter
public class BedWarsTeam {

    private String name;
    private String colorCode;
    private ArrayList<UUID> players;
    private int size;
    private int tagId;
    private boolean bed;
    private Color color;

    public BedWarsTeam(String name, int tagId, String colorCode, Color color, int size, ArrayList<UUID> players) {
        this.name = name;
        this.tagId = tagId;
        this.colorCode = colorCode;
        this.players = players;
        this.size = size;
        this.bed = true;
        this.color = color;
    }

    public void addPlayer(UUID player) {
        if (!this.players.contains(player)) {
            this.players.add(player);
        }
    }

    public void removePlayer(UUID player) {
        if (this.players.contains(player)) {
            this.players.remove(player);
        }
    }

    public boolean hasBed() {
        return bed;
    }
}
