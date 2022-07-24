package de.papiertuch.bedwars.api.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@Getter
public class GameEndingEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    public GameEndingEvent() {

    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
