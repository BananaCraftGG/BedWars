package de.papiertuch.bedwars.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndingEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    public GameEndingEvent() {

    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
