package de.papiertuch.bedwars.api.events;

import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStateChangeEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private GameState oldGameState;
    private GameState changeTo;

    public GameStateChangeEvent(GameState oldGameState, GameState changeTo) {
        this.oldGameState = oldGameState;
        this.changeTo = changeTo;
    }

    public GameState getChangeTo() {
        return changeTo;
    }

    public GameState getOldGameState() {
        return oldGameState;
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
