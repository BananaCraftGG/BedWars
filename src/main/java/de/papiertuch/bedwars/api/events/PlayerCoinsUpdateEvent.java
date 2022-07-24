package de.papiertuch.bedwars.api.events;

import de.papiertuch.bedwars.enums.CoinState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCoinsUpdateEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private CoinState coinState;

    private Player player;

    private int coins;

    public PlayerCoinsUpdateEvent(CoinState coinState, Player player, int coins) {
        this.coinState = coinState;
        this.player = player;
        this.coins = coins;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCoins() {
        return coins;
    }

    public CoinState getCoinState() {
        return coinState;
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
