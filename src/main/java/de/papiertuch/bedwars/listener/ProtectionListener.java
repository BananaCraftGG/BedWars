package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ProtectionListener implements Listener {

    @EventHandler
    public void onPlayerArmorStand(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (((Player) event.getEntity()).getGameMode() != GameMode.CREATIVE) {
                if (event.getEntity().getType() == EntityType.ARMOR_STAND) {
                    event.setCancelled(true);
                }
                if (event.getEntity().getType() == EntityType.VILLAGER) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            if (BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
                BedWarsTeam bedWarsTeam = BedWars.getInstance().getGameHandler().getTeam(player);
                if (event.getInventory().getName().equalsIgnoreCase(bedWarsTeam.getColor() + bedWarsTeam.getName())) {
                    BedWars.getInstance().getTeamChest().put(bedWarsTeam, event.getInventory());
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("CHEST_CLOSE"), 1, 1);
                }
            }
        }
    }

    @EventHandler
    public void onInt(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL) {
                event.setCancelled(true);
            }
            if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.CARROT) {
                event.setCancelled(true);
            }
            if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.WHEAT) {
                event.setCancelled(true);
            }
            if (!BedWars.getInstance().getGameHandler().getSetup().containsKey(player.getUniqueId())) {
                if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
                    event.setCancelled(true);
                }
            }
            if (event.getClickedBlock().getType() == Material.CHEST && BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                    event.setCancelled(true);
                    BedWarsTeam team = BedWars.getInstance().getGameHandler().getTeam(player);
                    if (BedWars.getInstance().getTeamChest().containsKey(team)) {
                        player.openInventory(BedWars.getInstance().getTeamChest().get(team));
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("CHEST_OPEN"), 1, 1);
                        return;
                    }
                    Inventory inventory = Bukkit.createInventory(null, 3 * 9, team.getColor() + team.getName());
                    player.openInventory(inventory);
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("CHEST_OPEN"), 1, 1);
                }
            }
        } catch (NullPointerException ex) {
        }

    }


    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
        if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            event.setCancelled(true);
        }
        if (BedWars.getInstance().getSpectators().contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }
}
