package de.papiertuch.bedwars.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@Getter
public class MapChangeEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private String map;

    private Player player;

    public MapChangeEvent(String map, Player player) {
        this.player = player;
        this.map = map;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlerList;
    }

}
