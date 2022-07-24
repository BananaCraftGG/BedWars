package de.papiertuch.bedwars.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWinGameEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private Player player;

    public PlayerWinGameEvent(Player player) {
        this.player = player;
    }


    public Player getPlayer() {
        return player;
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
