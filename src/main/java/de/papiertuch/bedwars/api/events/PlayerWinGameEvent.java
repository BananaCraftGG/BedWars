package de.papiertuch.bedwars.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@Getter
public class PlayerWinGameEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private Player player;

    public PlayerWinGameEvent(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlerList;
    }

}
