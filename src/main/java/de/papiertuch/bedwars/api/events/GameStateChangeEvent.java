package de.papiertuch.bedwars.api.events;

import de.papiertuch.bedwars.enums.GameState;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@Getter
public class GameStateChangeEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private GameState oldGameState;
    private GameState changeTo;

    public GameStateChangeEvent(GameState oldGameState, GameState changeTo) {
        this.oldGameState = oldGameState;
        this.changeTo = changeTo;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlerList;
    }

}
