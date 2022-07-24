package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.nickaddon.NickAddon;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import xyz.haoshoku.nick.api.NickAPI;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */
@Getter
public class GameHandler {

    private BukkitTask bronzeId;
    private BukkitTask ironId;
    private BukkitTask goldId;
    private HashMap<UUID, String> setup = new HashMap<>();
    private HashMap<UUID, BedWarsTeam> setupTeam = new HashMap<>();

    public void startSetup(Player player, String map) {
        setup.put(player.getUniqueId(), map);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        player.getInventory().setItem(0, new ItemStorage().getSetLobby());
        player.getInventory().setItem(1, new ItemStorage().getSetStatsWall());
        player.getInventory().setItem(2, new ItemStorage().getSetSpectator());
        player.getInventory().setItem(3, new ItemStorage().getSetBronzeSpawner());
        player.getInventory().setItem(4, new ItemStorage().getSetIronSpawner());
        player.getInventory().setItem(5, new ItemStorage().getSetGoldSpawner());
        player.getInventory().setItem(6, new ItemStorage().getSaveMap());
        player.getInventory().setItem(7, new ItemStorage().getOptions());
        player.getInventory().setItem(8, new ItemStorage().getFinish());
    }

    public Color getColorFromString(String color) {
        if (BedWars.getInstance().getColors().containsKey(color)) {
            return BedWars.getInstance().getColors().get(color);
        }
        return null;
    }

    public void addPlayerToTeam(InventoryClickEvent event) {
        if (event.getInventory().getName().equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("item.team.name"))) {
            Player player = (Player) event.getWhoClicked();
            BedWarsTeam team = BedWars.getInstance().getBedWarsTeams().get(event.getSlot());
            if (team.getPlayers().size() == team.getSize()) {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.teamFull"));
                player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                return;
            }
            clearFromTeams(player);
            team.addPlayer(player.getUniqueId());
            player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.choseTeam")
                    .replace("%team%",
                            team.getColorCode() + team.getName()));
            player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
            player.closeInventory();
        }
    }

    public int getMaxPlayerAtTeam() {
        String mode = BedWars.getInstance().getBedWarsConfig().getString("settings.mode");
        return Integer.valueOf(mode.split("x")[1]);
    }


    public int getMaxPlayers() {
        String mode = BedWars.getInstance().getBedWarsConfig().getString("settings.mode");
        return Integer.valueOf(mode.split("x")[0]) * Integer.valueOf(mode.split("x")[1]);
    }

    public void setSpectator(Player player) {
        if (!BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
            BedWars.getInstance().getSpectators().add(player.getUniqueId());
        }
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemStorage().getCompass());
        player.getInventory().setItem(8, new ItemStorage().getLeave());
        BedWars.getInstance().getBoard().addPlayerToBoard(player);
        player.teleport(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("spectator"));
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> {
            BedWars.getInstance().getBoard().addPlayerToBoard(player);
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 9999));
        }, 20);
    }

    public void copyFilesInDirectory(File from, File to) {
        try {
            if (to == null || from == null) return;

            if (!to.exists()) to.mkdirs();

            if (!from.isDirectory()) return;

            for (File file : from.listFiles()) {
                if (file == null) continue;

                if (file.isDirectory()) {
                    copyFilesInDirectory(file, new File(to.getAbsolutePath() + "/" + file.getName()));
                } else {
                    File n = new File(to.getAbsolutePath() + "/" + file.getName());
                    Files.copy(file.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void getTeamInventory(Player player, String name) {
        Inventory inv = Bukkit.createInventory(null, 9, name);
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            ArrayList<String> list = new ArrayList<>();
            for (UUID uuid : team.getPlayers()) {
                list.add(BedWars.getInstance().getBedWarsConfig().getString("message.inventory.teamChose")
                        .replace("%count%", Bukkit.getPlayer(uuid).getName()));
            }
            inv.addItem(new ItemBuilder(Material.LEATHER_BOOTS, 1)
                    .setName(team.getColorCode() + team.getName())
                    .setLeatherColor(team.getColor())
                    .addFlags(ItemFlag.values())
                    .setLore(list)
                    .build());

            player.openInventory(inv);
        }
    }

    public void getGoldVoteInventory(Player player, String name) {
        Inventory inv = Bukkit.createInventory(null, 9, name);
        inv.setItem(3, new ItemBuilder(Material.INK_SACK, 1, 10)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withGold"))
                .setLore(BedWars.getInstance().getBedWarsConfig().getString("message.inventory.votingAmount")
                        .replace("%votes%", String.valueOf(BedWars.getInstance().getWithGold().size())))
                .build());
        inv.setItem(5, new ItemBuilder(Material.INK_SACK, 1, 8)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withOutGold"))
                .setLore(BedWars.getInstance().getBedWarsConfig().getString("message.inventory.votingAmount")
                        .replace("%votes%", String.valueOf(BedWars.getInstance().getNoGold().size())))
                .build());
        player.openInventory(inv);
    }


    public void getItemDropVote(Player player, String name) {
        Inventory inv = Bukkit.createInventory(null, 9, name);
        inv.setItem(3, new ItemBuilder(Material.INK_SACK, 1, 10)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withItemDrop"))
                .setLore(BedWars.getInstance().getBedWarsConfig().getString("message.inventory.votingAmount")
                        .replace("%votes%", String.valueOf(BedWars.getInstance().getWithItemDrop().size())))
                .build());
        inv.setItem(5, new ItemBuilder(Material.INK_SACK, 1, 8)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("message.voting.withOutItemDrop"))
                .setLore(BedWars.getInstance().getBedWarsConfig().getString("message.inventory.votingAmount")
                        .replace("%votes%", String.valueOf(BedWars.getInstance().getNoItemDrop().size())))
                .build());
        player.openInventory(inv);
    }

    public void getMapVoteInventory(Player player, String name) {
        Inventory inv = Bukkit.createInventory(null, 9, name);
        for (String map : BedWars.getInstance().getMaps().keySet()) {
            inv.addItem(new ItemBuilder(Material.EMPTY_MAP, 1)
                    .setName("§8» §e" + map)
                    .setLore(BedWars.getInstance().getBedWarsConfig().getString("message.inventory.votingAmount")
                            .replace("%votes%", String.valueOf(BedWars.getInstance().getMaps().get(map).size())))
                    .build());
        }
        player.openInventory(inv);
    }

    public void getVoteInventory(Player player, String name) {
        Inventory inv = Bukkit.createInventory(null, 9, name);
        inv.setItem(2, new ItemBuilder(Material.EMPTY_MAP, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("item.voting.mapVote"))
                .build());
        inv.setItem(4, new ItemBuilder(Material.WOOD_PICKAXE, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("item.voting.itemDropVote"))
                .build());
        inv.setItem(6, new ItemBuilder(Material.GOLD_INGOT, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("item.voting.goldVote"))
                .build());
        player.openInventory(inv);
    }

    public void checkMapVoting() {
        if (!BedWars.getInstance().isForceMap()) {
            ArrayList<Integer> votes = new ArrayList<>();
            for (String map : BedWars.getInstance().getMaps().keySet()) {
                votes.add(BedWars.getInstance().getMaps().get(map).size());
            }
            Collections.sort(votes);
            for (String map : BedWars.getInstance().getMaps().keySet()) {
                if (BedWars.getInstance().getMaps().get(map).size() != 0) {
                    if (BedWars.getInstance().getMaps().get(map).size() == votes.get((votes.size() - 1))) {
                        BedWars.getInstance().setMap(map);
                        BedWars.getInstance().getBoard().updateBoard();
                    }
                }
            }
        }
    }

    public void checkItemDropVoting() {
        if (BedWars.getInstance().getWithItemDrop().size() == BedWars.getInstance().getNoItemDrop().size()) {
            BedWars.getInstance().setItemDrop(false);
        } else if (BedWars.getInstance().getWithItemDrop().size() >= BedWars.getInstance().getNoItemDrop().size()) {
            BedWars.getInstance().setItemDrop(true);
        } else {
            BedWars.getInstance().setItemDrop(false);
        }
    }

    public void checkGoldVoting() {
        if (BedWars.getInstance().getWithGold().size() == BedWars.getInstance().getNoGold().size()) {
            BedWars.getInstance().setGold(true);
        } else if (BedWars.getInstance().getWithGold().size() >= BedWars.getInstance().getNoGold().size()) {
            BedWars.getInstance().setGold(true);
        } else {
            BedWars.getInstance().setGold(false);
        }
    }

    public TabListGroup getDefaultGroup() {
        return BedWars.getInstance().getTabListGroups().get(BedWars.getInstance().getTabListGroups().size() - 1);
    }

    public TabListGroup getTabListGroup(Player player) {
        if (BedWars.getInstance().isNickEnable() && NickAPI.isNicked(player)) {
            return getDefaultGroup();
        }
        for (TabListGroup tabListGroup : BedWars.getInstance().getTabListGroups()) {
                if (player.hasPermission(tabListGroup.getPermission())) {
                    return tabListGroup;
                }
        }
        return getDefaultGroup();
    }

    public void checkTeams(Player player) {
        BedWarsTeam team = getTeam(player);
        if (!BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            team.removePlayer(player.getUniqueId());
            if (team.getPlayers().size() == 0) {
                sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.teamDeath")
                        .replace("%team%", team.getColorCode() + team.getName()));
                BedWars.getInstance().getAliveTeams().remove(team);
                team.setBed(false);
                for (Player a : Bukkit.getOnlinePlayers()) {
                    a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.destroyBed")), 10F, 10F);
                    BedWars.getInstance().getBoard().addPlayerToBoard(a);
                }
            } else {
                sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.teamReamingPlayers")
                        .replace("%team%", team.getColorCode() + team.getName())
                        .replace("%players%", String.valueOf(team.getPlayers().size())));
                for (Player a : Bukkit.getOnlinePlayers()) {
                    a.playSound(a.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.error")), 10F, 10F);
                    BedWars.getInstance().getBoard().addPlayerToBoard(a);
                }
            }
        }
    }

    public void clearFromTeams(Player player) {
        BedWarsTeam team = getTeam(player);
        if (team != null) {
            team.removePlayer(player.getUniqueId());
        }
    }


    public BedWarsTeam getTeam(Player player) {
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().contains(player.getUniqueId())) {
                return team;
            }
        }
        return null;
    }

    public void teleportToMap(Player p) {
        String team = getTeam(p).getName().toLowerCase();
        p.teleport(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation(team + ".spawn"));
    }


    public void checkWinner() {
        BedWarsTeam winner = null;
        if (BedWars.getInstance().getAliveTeams().size() == 1) {
            winner = BedWars.getInstance().getAliveTeams().get(0);
            for (UUID uuid : winner.getPlayers()) {
                Player a = Bukkit.getPlayer(uuid);
                BedWars.getInstance().getStatsHandler().addWin(a);
            }
            BedWars.getInstance().getScheduler().getEnding().startCountdown();
            for (Player a : Bukkit.getOnlinePlayers()) {
                a.setHealth(20);
                a.setAllowFlight(false);
                a.setFlying(false);
                a.getInventory().clear();
                a.getInventory().setArmorContents(null);
                a.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.leave.slot"), new ItemStorage().getLeave());
                a.sendTitle(BedWars.getInstance().getBedWarsConfig().getString("message.title.win.one")
                        .replace("%team%", winner.getName())
                        .replace("%teamColor%", winner.getColorCode()), BedWars.getInstance().getBedWarsConfig().getString("message.title.win.two"));
            }
            sendBroadCast("§7");
            sendBroadCast(BedWars.getInstance().getBedWarsConfig().getString("message.teamWin")
                    .replace("%team%", winner.getColorCode() + winner.getName()));
            sendBroadCast("§7");
            return;
        }
    }

    public Sound getSound(String string) {
        Sound sound;
        try {
            sound = Sound.valueOf(string);
        } catch (Exception e) {
            System.out.println("wrong sound pls change this in the config.yml [" + string + "]");
            return Sound.valueOf("UI_BUTTON_CLICK");
        }
        return sound;
    }

    public void hidePlayer(Player player) {
        for (UUID uuid : BedWars.getInstance().getPlayers()) {
            Player a = Bukkit.getPlayer(uuid);
            if (BedWars.getInstance().getSpectators().contains(player.getUniqueId())) {
                a.hidePlayer(player);
                continue;
            }
            a.showPlayer(player);
        }
    }

    public void stopSpawner() {
        if (bronzeId != null) {
            bronzeId.cancel();
        }
        if (ironId != null) {
            ironId.cancel();
        }
        if (goldId != null) {
            goldId.cancel();
        }
    }

    public void startSpawner() {
        LocationAPI locationAPI = new LocationAPI(BedWars.getInstance().getMap());
        bronzeId = Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), () -> {
            String type = "bronze";
            int spawner = locationAPI.getCfg().getInt(type + ".spawnerCount");
            for (int i = 1; i <= spawner; i++) {
                Location loc = locationAPI.getLocation(type + "." + i).add(0.5, 1, 0.5);
                spawnItem(loc, new ItemBuilder(Material.CLAY_BRICK, 1)
                        .setName(BedWars.getInstance().getBedWarsConfig().getString("message.drops.bronze"))
                        .build());
            }
        }, 1, BedWars.getInstance().getBedWarsConfig().getInt("countDown.bronzeSpawnRate") == 0 ? 10 : 20 * BedWars.getInstance().getBedWarsConfig().getInt("countDown.bronzeSpawnRate"));
        ironId = Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), () -> {
            String type = "iron";
            int spawner = locationAPI.getCfg().getInt(type + ".spawnerCount");
            for (int i = 1; i <= spawner; i++) {
                Location loc = locationAPI.getLocation(type + "." + i).add(0.5, 1, 0.5);
                spawnItem(loc, new ItemBuilder(Material.IRON_INGOT, 1)
                        .setName(BedWars.getInstance().getBedWarsConfig().getString("message.drops.iron"))
                        .build());
            }
        }, 1, 20 * BedWars.getInstance().getBedWarsConfig().getInt("countDown.ironSpawnRate"));
        if (BedWars.getInstance().isGold()) {
            goldId = Bukkit.getScheduler().runTaskTimer(BedWars.getInstance(), () -> {
                String type = "gold";
                int spawner = locationAPI.getCfg().getInt(type + ".spawnerCount");
                for (int i = 1; i <= spawner; i++) {
                    Location loc = locationAPI.getLocation(type + "." + i).add(0.5, 1, 0.5);
                    spawnItem(loc, new ItemBuilder(Material.GOLD_INGOT, 1)
                            .setName(BedWars.getInstance().getBedWarsConfig().getString("message.drops.gold"))
                            .build());
                }

            }, 1, 20 * BedWars.getInstance().getBedWarsConfig().getInt("countDown.goldSpawnRate"));
        }
    }

    private void spawnItem(Location paramLocation, ItemStack paramItemStack) {
        Item localItem = paramLocation.getWorld().dropItem(paramLocation, paramItemStack);
        localItem.setVelocity(new Vector());
    }

    public void sendToFallback(Player player) {
        player.kickPlayer(BedWars.getInstance().getBedWarsConfig().getString("message.gameStop"));
    }

    public void sendBroadCast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    public void setPlayer(Player player) {
        player.getInventory().clear();
        Bukkit.getScheduler().runTaskLater(BedWars.getInstance(), () -> {
            player.teleport(BedWars.getInstance().getLocationAPI(BedWars.getInstance().getMap()).getLocation("lobby").add(0, 1, 0));
        }, 2);
        if (!BedWars.getInstance().getPlayers().contains(player.getUniqueId())) {
            BedWars.getInstance().getPlayers().add(player.getUniqueId());
        }
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        if (BedWars.getInstance().getGameState() == GameState.LOBBY) {
            player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.team.slot"), new ItemStorage().getTeams());
            player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.vote.slot"), new ItemStorage().getVote());
            if (player.hasPermission(BedWars.getInstance().getBedWarsConfig().getString("command.start.permission"))) {
                player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.start.slot"), new ItemStorage().getStartItem());
            }
        }
        player.getInventory().setItem(BedWars.getInstance().getBedWarsConfig().getInt("item.leave.slot"), new ItemStorage().getLeave());
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public void removePlayerFromCurrentTeam(Player player) {
        BedWarsTeam bedWarsTeam = getTeam(player);
        if (bedWarsTeam != null) {
            bedWarsTeam.getPlayers().remove(player.getUniqueId());
        }
    }

    public void getFreeTeamForPlayer(Player player) {
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().isEmpty()) {
                team.addPlayer(player.getUniqueId());
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.yourInTeam")
                        .replace("%team%", team.getColorCode() + team.getName()));
                return;
            }
        }
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().size() < team.getSize()) {
                team.addPlayer(player.getUniqueId());
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.yourInTeam")
                        .replace("%team%", team.getColorCode() + team.getName()));
                return;
            }
        }
    }

    public boolean hasTeam(Player player) {
        for (BedWarsTeam team : BedWars.getInstance().getBedWarsTeams()) {
            if (team.getPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }


    public void sendActionBar(Player player, String message) {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            Class<?> iChatBaseClazz = Class.forName("net.minecraft.server." + version + ".IChatBaseComponent");
            Class<?> chatSerializerClazz = Class.forName("net.minecraft.server." + version + ".IChatBaseComponent$ChatSerializer");
            Method aMethod = chatSerializerClazz.getMethod("a", String.class);
            Object serializerObject = aMethod.invoke(null, "{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
            Class<?> chatClazz = Class.forName("net.minecraft.server." + version + ".PacketPlayOutChat");

            Object instanceChatObject;

            if (!version.equalsIgnoreCase("v1_12_R1")) {
                instanceChatObject = chatClazz.getConstructor(iChatBaseClazz, byte.class).newInstance(serializerObject, (byte) 2);
            } else {
                Class<?> chatMessageType = Class.forName("net.minecraft.server." + version + ".ChatMessageType");

                instanceChatObject = chatClazz.getConstructor(iChatBaseClazz, chatMessageType).newInstance(serializerObject, chatMessageType.getEnumConstants()[2]);
            }

            Object entityPlayerObject = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnectionObject = entityPlayerObject.getClass().getField("playerConnection").get(entityPlayerObject);
            Class<?> packetClazz = Class.forName("net.minecraft.server." + version + ".Packet");
            playerConnectionObject.getClass().getMethod("sendPacket", packetClazz).invoke(playerConnectionObject, instanceChatObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

