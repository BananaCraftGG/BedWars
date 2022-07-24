package de.papiertuch.bedwars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStatingEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    public GameStatingEvent() {

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
