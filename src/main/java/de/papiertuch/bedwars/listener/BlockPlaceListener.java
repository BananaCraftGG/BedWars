package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        event.setCancelled(true);
        if (p.getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(false);
            if (!p.getWorld().getName().equalsIgnoreCase("world")) {
                if (BedWars.getInstance().getBlocks().containsKey(p.getWorld().getName())) {
                    List<Location> list = BedWars.getInstance().getBlocks().get(p.getWorld().getName());
                    list.add(event.getBlock().getLocation());
                    BedWars.getInstance().getBlocks().put(p.getLocation().getWorld().getName(), list);
                }
            }
            return;
        }
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (event.getBlock().getType() == Material.TNT) {
                event.setCancelled(false);
                event.getBlock().setType(Material.AIR);
                p.getWorld().spawn(event.getBlock().getLocation(), TNTPrimed.class);
                return;
            }
            event.setCancelled(false);
            if (!p.getWorld().getName().equalsIgnoreCase("world")) {
                List<Location> list = BedWars.getInstance().getBlocks().get(p.getWorld().getName());
                list.add(event.getBlock().getLocation());
                BedWars.getInstance().getBlocks().put(p.getLocation().getWorld().getName(), list);
            }
            return;
        }
    }
}
