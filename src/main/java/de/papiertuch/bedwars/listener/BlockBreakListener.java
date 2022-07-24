package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.api.events.PlayerDestroyBedEvent;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.utils.BedWarsTeam;
import de.papiertuch.bedwars.utils.LocationAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;
import java.util.UUID;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (BedWars.getInstance().getGameState() == GameState.LOBBY || BedWars.getInstance().getGameState() == GameState.ENDING) {
            if (BedWars.getInstance().getGameHandler().getSetupTeam().containsKey(player.getUniqueId())) {
                LocationAPI locationAPI = new LocationAPI(BedWars.getInstance().getGameHandler().getSetup().get(player.getUniqueId()));
                BedWarsTeam team = BedWars.getInstance().getGameHandler().getSetupTeam().get(player.getUniqueId());
                event.setCancelled(true);
                if (player.getItemInHand() == null) {
                    event.setCancelled(false);
                    return;
                }
                switch (player.getItemInHand().getType()) {
                    case STONE_AXE:
                        if (player.getItemInHand().getItemMeta().getDisplayName().contains("Unteres")) {
                            locationAPI.setBedLocation(team.getName().toLowerCase() + ".bed", event.getBlock().getLocation());
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Du hast das §a§lUntere Bett §7von " + team.getColor() + team.getName() + " §7gesetzt");
                            player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
                            return;
                        }
                        locationAPI.setBedLocation(team.getName().toLowerCase() + ".bedTop", event.getBlock().getLocation());
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Du hast das §a§lObere Bett §7von " + team.getColor() + team.getName() + " §7gesetzt");
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
                        return;
                    default:
                        event.setCancelled(false);
                        return;
                }
            }
            if (BedWars.getInstance().getGameHandler().getSetup().containsKey(player.getUniqueId())) {
                LocationAPI locationAPI = new LocationAPI(BedWars.getInstance().getGameHandler().getSetup().get(player.getUniqueId()));
                event.setCancelled(true);
                if (player.getItemInHand() == null) {
                    event.setCancelled(false);
                    return;
                }
                switch (player.getItemInHand().getType()) {
                    case WATCH:
                    case ARROW:
                    case INK_SACK:
                    case PAPER:
                    case LEATHER_BOOTS:
                        event.setCancelled(true);
                        return;
                    case SKULL_ITEM:
                        int count;
                        try {
                            count = locationAPI.getCfg().getInt("statsWall");
                        } catch (Exception e) {
                            locationAPI.getCfg().addDefault("statsWall", 0);
                            count = 0;
                            locationAPI.save();
                        }
                        int newCount = count + 1;
                        locationAPI.getCfg().set("statsWall", newCount);
                        locationAPI.save();
                        locationAPI.setLocation("statsSkull." + newCount, event.getBlock().getLocation());
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Du hast den Kopf §a§l" + newCount + " §7gesetzt");
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
                        return;
                    case WOOD_AXE:
                        int bronzeSpawnerCount;
                        try {
                            bronzeSpawnerCount = locationAPI.getCfg().getInt("bronze.spawnerCount");
                        } catch (Exception e) {
                            locationAPI.getCfg().addDefault("bronze.spawnerCount", 0);
                            bronzeSpawnerCount = 0;
                            locationAPI.save();
                        }
                        int newBronzeSpawnerCount = bronzeSpawnerCount + 1;
                        locationAPI.getCfg().set("bronze.spawnerCount", newBronzeSpawnerCount);
                        locationAPI.save();
                        locationAPI.setLocation("bronze." + newBronzeSpawnerCount, event.getBlock().getLocation());
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Du hast den §cBronze §7Spawner §a§l" + newBronzeSpawnerCount + " §7gesetzt");
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
                        return;
                    case IRON_AXE:
                        int IronSpawnerCount;
                        try {
                            IronSpawnerCount = locationAPI.getCfg().getInt("iron.spawnerCount");
                        } catch (Exception e) {
                            locationAPI.getCfg().addDefault("iron.spawnerCount", 0);
                            IronSpawnerCount = 0;
                            locationAPI.save();
                        }
                        int newIronSpawnerCount = IronSpawnerCount + 1;
                        locationAPI.getCfg().set("iron.spawnerCount", newIronSpawnerCount);
                        locationAPI.save();
                        locationAPI.setLocation("iron." + newIronSpawnerCount, event.getBlock().getLocation());
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Du hast den §fEisen §7Spawner §a§l" + newIronSpawnerCount + " §7gesetzt");
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
                        return;
                    case GOLD_AXE:
                        int goldSpawnerCount;
                        try {
                            goldSpawnerCount = locationAPI.getCfg().getInt("gold.spawnerCount");
                        } catch (Exception e) {
                            locationAPI.getCfg().addDefault("gold.spawnerCount", 0);
                            goldSpawnerCount = 0;
                            locationAPI.save();
                        }
                        int newGoldSpawnerCount = goldSpawnerCount + 1;
                        locationAPI.getCfg().set("gold.spawnerCount", newGoldSpawnerCount);
                        locationAPI.save();
                        locationAPI.setLocation("gold." + newGoldSpawnerCount, event.getBlock().getLocation());
                        player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.prefix") + " §7Du hast den §6Gold §7Spawner §a§l" + newGoldSpawnerCount + " §7gesetzt");
                        player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.interact")), 1, 1);
                        return;
                    default:
                        event.setCancelled(false);
                        return;
                }
            }
            if (player.getGameMode() == GameMode.CREATIVE) {
                event.setCancelled(false);
                if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                    List<Location> list = BedWars.getInstance().getBlocks().get(player.getLocation().getWorld().getName());
                    list.remove(event.getBlock().getLocation());
                    BedWars.getInstance().getBlocks().put(player.getLocation().getWorld().getName(), list);
                }
                return;
            }
        } else if (BedWars.getInstance().getGameState() == GameState.INGAME) {
            event.setCancelled(true);
            if (player.getGameMode() == GameMode.CREATIVE) {
                if (event.getBlock().getType() == Material.BED) {
                    event.setCancelled(true);
                    return;
                }
                if (event.getBlock().getType() == Material.BED_BLOCK) {
                    event.setCancelled(true);
                    return;
                }
                event.setCancelled(false);
                if (!player.getWorld().getName().equalsIgnoreCase("world")) {
                    List<Location> list = BedWars.getInstance().getBlocks().get(player.getLocation().getWorld().getName());
                    list.remove(event.getBlock().getLocation());
                    BedWars.getInstance().getBlocks().put(player.getLocation().getWorld().getName(), list);
                }
                return;
            }
            if (event.getBlock().getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
                return;
            }
            if (BedWars.getInstance().getBlocks().get(player.getWorld().getName()).contains(event.getBlock().getLocation()) || event.getBlock().getType() == Material.SLIME_BLOCK) {
                List<Location> list = BedWars.getInstance().getBlocks().get(player.getLocation().getWorld().getName());
                list.remove(event.getBlock().getLocation());
                BedWars.getInstance().getBlocks().put(player.getLocation().getWorld().getName(), list);
                event.setCancelled(false);
                return;
            }
            if (event.getBlock().getType() == Material.BED || event.getBlock().getType() == Material.BED_BLOCK) {
                event.setCancelled(true);
                LocationAPI locationAPI = BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap());
                for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
                    if (event.getBlock().getLocation().equals(locationAPI.getBedLocation(team.getName().toLowerCase() + ".bed")) || event.getBlock().getLocation().equals(locationAPI.getBedLocation(team.getName().toLowerCase() + ".bedTop"))) {
                        if (team.getPlayers().contains(player.getUniqueId())) {
                            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.destroyOwnBed"));
                            player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.error")), 1, 1);
                            return;
                        }
                        if (team.hasBed()) {
                            team.setBed(false);
                            BedWars.getInstance().getGameHandler().sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.destroyBed")
                                    .replace("%player%", player.getDisplayName())
                                    .replace("%team%", team.getColor() + team.getName()));
                            player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.kill")), 1F, 1F);
                            event.getBlock().setType(Material.AIR);
                            event.getBlock().getDrops().clear();
                            for (Player a : Bukkit.getOnlinePlayers()) {
                                a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.destroyBed")), 10F, 10F);
                                BedWars.getInstance().getBoard().updateBoard();
                            }
                            for (UUID uuid : team.getPlayers()) {
                                Player a = Bukkit.getPlayer(uuid);
                                a.sendTitle(BedWars.getInstance().getBedWarsConfig().getString("message.title.bedDestroy.one"),
                                        BedWars.getInstance().getBedWarsConfig().getString("message.title.bedDestroy.two"));
                                a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.error")), 1, 1);
                            }
                            Bukkit.getPluginManager().callEvent(new PlayerDestroyBedEvent(player, event.getBlock().getLocation(), team));
                            BedWars.getInstance().getStatsHandler().addDestroyBed(player);
                        }
                    }
                }
            }
        }
    }

  /*
    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent event) {
        event.setBuildable(true);
    }
   */
}
