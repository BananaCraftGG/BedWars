package de.papiertuch.bedwars.api.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@Getter
public class GameStatingEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    public GameStatingEvent() {

    }

    @Override
    public HandlerList getHandlers()
    {
        return handlerList;
    }

}
