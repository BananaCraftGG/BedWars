package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.ItemStorage;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
                event.setCancelled(true);
                if (player.getGameMode() == GameMode.CREATIVE) {
                    event.setCancelled(false);
                }
                if (event.getCurrentItem().getType() == Material.LEATHER_BOOTS) {
                    event.setCancelled(true);
                    BedWars.getInstance().getGameHandler().addPlayerToTeam(event);
                }
            }
            if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
            if (event.getClickedInventory().getName().contains("Teams") && BedWars.getInstance().getGameHandler().getSetup().containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(new ItemStorage().getBack().getItemMeta().getDisplayName())) {
                    player.getInventory().clear();
                    player.getInventory().setItem(0, new ItemStorage().getSetLobby());
                    player.getInventory().setItem(1, new ItemStorage().getSetStatsWall());
                    player.getInventory().setItem(2, new ItemStorage().getSetSpectator());
                    player.getInventory().setItem(3, new ItemStorage().getSetBronzeSpawner());
                    player.getInventory().setItem(4, new ItemStorage().getSetIronSpawner());
                    player.getInventory().setItem(5, new ItemStorage().getSetGoldSpawner());
                    player.getInventory().setItem(6, new ItemStorage().getSaveMap());
                    player.getInventory().setItem(7, new ItemStorage().getOptions());
                    player.getInventory().setItem(8, new ItemStorage().getFinish());
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.closeInventory();
                    return;
                }
                BedWarsTeam team = BedWars.getInstance().getBedWarsTeams().get(event.getSlot());
                BedWars.getInstance().getGameHandler().getSetupTeam().put(player.getUniqueId(), team);
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.setup.setupTeam")
                .replace("%team%", team.getColor() + team.getName()));
                player.getInventory().clear();
                player.getInventory().setItem(0, new ItemStorage().getTeam(team));
                player.getInventory().setItem(1, new ItemStorage().getBed(team));
                player.getInventory().setItem(2, new ItemStorage().getBedTop(team));
                player.getInventory().setItem(4, new ItemStorage().getOptions());
                player.getInventory().setItem(5, new ItemStorage().getFinish());
                player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                player.closeInventory();
                return;
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.vote.name"))) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("item.voting.mapVote"))) {
                    BedWars.getInstance().getGameHandler().getMapVoteInventory(player, event.getCurrentItem().getItemMeta().getDisplayName());
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("item.voting.itemDropVote"))) {
                    BedWars.getInstance().getGameHandler().getItemDropVote(player, event.getCurrentItem().getItemMeta().getDisplayName());
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("item.voting.goldVote"))) {
                    BedWars.getInstance().getGameHandler().getGoldVoteInventory(player, event.getCurrentItem().getItemMeta().getDisplayName());
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                }
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.voting.mapVote"))) {
                if (event.getCurrentItem().getType() == Material.EMPTY_MAP) {
                    if (!BedWars.getInstance().isForceMap()) {
                        String map = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName().replace("§8» ", ""));
                        for (ArrayList<UUID> list : BedWars.getInstance().getMaps().values()) {
                            if (list.contains(player.getUniqueId())) {
                                list.remove(player.getUniqueId());
                            }
                        }
                        ArrayList<UUID> votes = BedWars.getInstance().getMaps().get(map);
                        votes.add(player.getUniqueId());
                        BedWars.getInstance().getMaps().put(map, votes);
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.voting.mapVote")
                        .replace("%map%", map));
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                        player.closeInventory();
                    } else {
                        player.closeInventory();
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.voting.alreadyForeMap"));
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    }
                }
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.voting.goldVote"))) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withGold"))) {
                    BedWars.getInstance().getNoGold().remove(player.getUniqueId());
                    BedWars.getInstance().getWithGold().remove(player.getUniqueId());
                    BedWars.getInstance().getWithGold().add(player.getUniqueId());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.voting.yes"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withOutGold"))) {
                    BedWars.getInstance().getWithGold().remove(player.getUniqueId());
                    BedWars.getInstance().getNoGold().remove(player.getUniqueId());
                    BedWars.getInstance().getNoGold().add(player.getUniqueId());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.voting.no"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.closeInventory();
                }
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.voting.itemDropVote"))) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withItemDrop"))) {
                    BedWars.getInstance().getNoItemDrop().remove(player.getUniqueId());
                    BedWars.getInstance().getWithItemDrop().remove(player.getUniqueId());
                    BedWars.getInstance().getWithItemDrop().add(player.getUniqueId());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.voting.yes"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.closeInventory();
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withOutItemDrop"))) {
                    BedWars.getInstance().getWithItemDrop().remove(player.getUniqueId());
                    BedWars.getInstance().getNoItemDrop().remove(player.getUniqueId());
                    BedWars.getInstance().getNoItemDrop().add(player.getUniqueId());
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.voting.no"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.closeInventory();
                }
            }
            if (event.getClickedInventory().getName().equals(BedWars.getInstance().getBedWarsConfig().getString("item.compass.name"))) {
                event.setCancelled(true);
                String name = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                Player target = Bukkit.getPlayer(name);
                if (target != null) {
                    player.teleport(target);
                    player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teleportToPlayer")
                            .replace("%player%", target.getDisplayName()));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                }
            }
        } catch (NullPointerException ex) {

        }
    }
}
