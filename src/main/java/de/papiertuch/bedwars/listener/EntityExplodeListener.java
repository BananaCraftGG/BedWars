package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode( EntityExplodeEvent event ) {
        List<Block> block = event.blockList();
        event.setCancelled(true);
        for (Block b : block) {
            if (BedWars.getInstance().getBlocks().get(b.getLocation().getWorld().getName()).contains(b.getLocation())) {
                b.getWorld().dropItem(b.getLocation(), new ItemStack(b.getType(), 1, b.getData()));
                b.setType(Material.AIR);
            }
        }
    }
}
