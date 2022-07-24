package de.papiertuch.bedwars.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MapChangeEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private String map;

    private Player player;

    public MapChangeEvent(String map, Player player) {
        this.player = player;
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMap() {
        return map;
    }
    @Override
    public HandlerList getHandlers()
    {
        return handlerList;
    }

    public static HandlerList getHandlerList()
    {
        return handlerList;
    }
}
