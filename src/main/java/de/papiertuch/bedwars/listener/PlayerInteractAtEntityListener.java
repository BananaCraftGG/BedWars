package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() != GameState.INGAME) {
            event.setCancelled(true);
            return;
        }
        if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (EntityType.valueOf(BedWars.getInstance().getBedWarsConfig().getString("settings.shopType")) != EntityType.VILLAGER) {
            if (event.getRightClicked().getType() == EntityType.valueOf(BedWars.getInstance().getBedWarsConfig().getString("settings.shopType")) && BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.openInventory(BedWars.getInstance().getShopHandler().getMainInventory(player));
                player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() != GameState.INGAME) {
            event.setCancelled(true);
            return;
        }
        if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (event.getRightClicked().getType() == EntityType.valueOf(BedWars.getInstance().getBedWarsConfig().getString("settings.shopType")) && BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.openInventory(BedWars.getInstance().getShopHandler().getMainInventory(player));
            player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
        }
    }
}
