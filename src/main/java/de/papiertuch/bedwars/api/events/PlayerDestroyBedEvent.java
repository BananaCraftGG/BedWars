package de.papiertuch.bedwars.api.events;

import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDestroyBedEvent extends Event {

    private static HandlerList handlerList = new HandlerList();

    private Player player;

    private Location location;

    private BedWarsTeam team;

    public PlayerDestroyBedEvent(Player player, Location location, BedWarsTeam team) {
        this.player = player;
        this.location = location;
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public BedWarsTeam getTeam() {
        return team;
    }

    public Location getLocation() {
        return location;
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
