package de.papiertuch.bedwars;

import com.github.juliarn.npc.NPCPool;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.ext.bridge.BridgeHelper;
import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;
import de.papiertuch.bedwars.api.events.GameStateChangeEvent;
import de.papiertuch.bedwars.commands.*;
import de.papiertuch.bedwars.enums.GameState;
import de.papiertuch.bedwars.game.Scheduler;
import de.papiertuch.bedwars.listener.*;
import de.papiertuch.bedwars.npc.ShopNPC;
import de.papiertuch.bedwars.stats.MySQL;
import de.papiertuch.bedwars.stats.StatsAPI;
import de.papiertuch.bedwars.stats.StatsHandler;
import de.papiertuch.bedwars.utils.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

@Getter
@Setter
public class BedWars extends JavaPlugin {

    private static BedWars instance;
    private Scheduler scheduler;
    private GameHandler gameHandler;
    private GameState gameState;
    private Board board;
    private BedWarsConfig bedWarsConfig;
    private StatsHandler statsHandler;
    private MySQL mySQL;
    private ShopHandler shopHandler;

    private ArrayList<TabListGroup> tabListGroups;
    private ArrayList<BedWarsTeam> bedWarsTeams;
    private ArrayList<BedWarsTeam> aliveTeams;
    private ArrayList<UUID> spectators;
    private ArrayList<UUID> players;
    private ArrayList<UUID> withGold;
    private ArrayList<UUID> noGold;
    private ArrayList<UUID> withItemDrop;
    private ArrayList<UUID> noItemDrop;
    private ArrayList<Player> death;
    private ArrayList<Location> statsWall;
    private ArrayList<String> randomMap;
    private ArrayList<UUID> uuid;

    private StatsAPI statsAPI = new StatsAPI();

    private HashMap<UUID, String> setupBed;
    private HashMap<UUID, String> setupBedTop;
    private HashMap<UUID, Integer> setupStatsWall;
    private HashMap<UUID, String> setupStatsWallMap;
    private HashMap<String, Color> colors;
    private HashMap<Color, Integer> colorIds;
    private HashMap<Player, Player> lastHit;
    private HashMap<BedWarsTeam, Inventory> teamChest;
    private HashMap<String, ArrayList<UUID>> maps;
    private HashMap<String, List<Location>> blocks;

    private boolean boarder, gold, nickEnable, forceMap, itemDrop;

    private NPCPool npcPool;

    private ShopNPC shopNPC;

    private String map;

    @Override
    public void onEnable() {
        instance = this;

        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        List<String> supportedVersions = new LinkedList<>();
        supportedVersions.add("v1_8_R3");
        supportedVersions.add("v1_9_R2");
        supportedVersions.add("v1_10_R1");
        supportedVersions.add("v1_11_R1");
        supportedVersions.add("v1_12_R1");

        if (!supportedVersions.contains(version)) {
            getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §cDas Plugin ist nur für die Versionen 1.8.3 - 1.12.2 gedacht");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        statsHandler = new StatsHandler();
        mySQL = new MySQL();
        shopHandler = new ShopHandler();
        gameHandler = new GameHandler();
        bedWarsConfig = new BedWarsConfig();
        scheduler = new Scheduler();
        board = new Board();

        players = new ArrayList<>();
        death = new ArrayList<>();
        aliveTeams = new ArrayList<>();
        tabListGroups = new ArrayList<>();
        statsWall = new ArrayList<>();
        bedWarsTeams = new ArrayList<>();
        spectators = new ArrayList<>();
        withGold = new ArrayList<>();
        randomMap = new ArrayList<>();
        noGold = new ArrayList<>();
        noItemDrop = new ArrayList<>();
        withItemDrop = new ArrayList<>();
        uuid = new ArrayList<>();

        setupStatsWall = new HashMap<>();
        maps = new HashMap<>();
        setupStatsWallMap = new HashMap<>();
        blocks = new HashMap<>();
        teamChest = new HashMap<>();
        lastHit = new HashMap<>();
        colorIds = new HashMap<>();
        colors = new HashMap<>();
        setupBedTop = new HashMap<>();
        setupBed = new HashMap<>();

        boarder = false;
        gold = true;
        itemDrop = false;
        nickEnable = false;
        forceMap = false;
        map = "Unknown";

        bedWarsConfig.loadConfig();
        if (EntityType.valueOf(bedWarsConfig.getString("settings.shopType")) == EntityType.PLAYER) {
            shopNPC = new ShopNPC(this);
            shopNPC.loadNPCLocations();
        }

        File file = new File("plugins/BedWars/mapBackup");
        int amount = 0;
        if (file.exists()) {
            for (File map : file.listFiles()) {
                amount++;
                World world = Bukkit.getWorld(map.getName().replace(".yml", ""));
                if (world == null) {
                    new File(map.getName().replace(".yml", "")).delete();
                    getGameHandler().copyFilesInDirectory(new File("plugins/BedWars/mapBackup/" + map.getName().replace(".yml", "")), new File(map.getName().replace(".yml", "")));
                    world = Bukkit.createWorld(WorldCreator.name(map.getName().replace(".yml", "")).type(WorldType.FLAT).generatorSettings("3;minecraft:air;2").generateStructures(false));
                }
                world.setTime(1200);
                world.setDifficulty(Difficulty.NORMAL);
                world.setGameRuleValue("doMobSpawning", "false");
                world.setGameRuleValue("doDaylightCycle", "false");
                randomMap.add(map.getName().replace(".yml", ""));
                maps.put(map.getName().replace(".yml", ""), new ArrayList<>());
            }
            getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §7Loaded maps");
            for (String string : randomMap) {
                getServer().getConsoleSender().sendMessage("§f§l- " + string);
            }
        }

        if (amount == 0) {
            getServer().getConsoleSender().sendMessage("§f§l- §cNo maps found...");
        }

        colors.put("AQUA", Color.AQUA);
        colors.put("BLACK", Color.BLACK);
        colors.put("BLUE", Color.BLUE);
        colors.put("FUCHSIA", Color.FUCHSIA);
        colors.put("GRAY", Color.GRAY);
        colors.put("GREEN", Color.GREEN);
        colors.put("LIME", Color.LIME);
        colors.put("MAROON", Color.MAROON);
        colors.put("NAVY", Color.NAVY);
        colors.put("OLIVE", Color.OLIVE);
        colors.put("ORANGE", Color.ORANGE);
        colors.put("PURPLE", Color.PURPLE);
        colors.put("RED", Color.RED);
        colors.put("SILVER", Color.SILVER);
        colors.put("TEAL", Color.TEAL);
        colors.put("WHITE", Color.WHITE);
        colors.put("YELLOW", Color.YELLOW);

        colorIds.put(Color.AQUA, 3);
        colorIds.put(Color.BLACK, 15);
        colorIds.put(Color.BLUE, 11);
        colorIds.put(Color.FUCHSIA, 6);
        colorIds.put(Color.GRAY, 7);
        colorIds.put(Color.GREEN, 13);
        colorIds.put(Color.LIME, 5);
        colorIds.put(Color.ORANGE, 1);
        colorIds.put(Color.PURPLE, 10);
        colorIds.put(Color.RED, 14);
        colorIds.put(Color.SILVER, 8);
        colorIds.put(Color.WHITE, 0);
        colorIds.put(Color.YELLOW, 4);

        if (getBedWarsConfig().getBoolean("module.cloudNet.v3.enable")) {
            for(IPermissionGroup iPermissionGroup : CloudNetDriver.getInstance().getPermissionManagement().getGroups()) {
                tabListGroups.add(
                        new TabListGroup(iPermissionGroup.getName(),
                                iPermissionGroup.getPrefix(),
                                iPermissionGroup.getSuffix(),
                                iPermissionGroup.getDisplay(),
                                iPermissionGroup.getSortId()));
            }
        }
        else {
            for (String tabList : getBedWarsConfig().getConfiguration().getStringList("nameTags.tabList")) {
                tabListGroups.add(
                        new TabListGroup(tabList,
                                bedWarsConfig.getString("nameTags." + tabList + ".prefix"),
                                bedWarsConfig.getString("nameTags." + tabList + ".suffix"),
                                bedWarsConfig.getString("nameTags." + tabList + ".display"),
                                bedWarsConfig.getInt("nameTags." + tabList + ".tagId"),
                                bedWarsConfig.getString("nameTags." + tabList + ".permission")));
            }
        }

        getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §7Loaded teams");
        for (BedWarsTeam team : getBedWarsTeams()) {
            getServer().getConsoleSender().sendMessage("§f§l- " + team.getColorCode() + team.getName());
        }
        getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §7Loaded NameTags");
        for (TabListGroup tabListGroup : getTabListGroups()) {
            getServer().getConsoleSender().sendMessage("§f§l- " + tabListGroup.getDisplay() + tabListGroup.getName());
        }

        register();
        mySQL.connect();
        if (mySQL.isConnected()) {
            mySQL.createTable();
        }
        loadGame();

        if (getServer().getPluginManager().getPlugin("NickAddon") != null) {
            nickEnable = true;
        }
        if (getServer().getPluginManager().getPlugin("Multiverse-Core") != null) {
            getServer().getPluginManager().disablePlugin(getServer().getPluginManager().getPlugin("Multiverse-Core"));
            getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §eDas Plugin Multiverse-Core wurde deaktiviert, dass BedWars Plugin lädt die welten selbst");
            getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §eUm dich auf die Welten zu teleportieren nutze /setup tp <Map>");
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            getGameHandler().sendToFallback(player);
        }
        if (getMySQL().isConnected()) {
            getMySQL().disconnect();
        }
        if (!getMap().equalsIgnoreCase("Unbekannt")) {
            Bukkit.unloadWorld(getMap(), false);
            new File(getMap()).delete();
        }
    }

    public void loadGame() {
        getScheduler().getBorder().stop();
        getScheduler().getEnding().stopCountdown();
        getScheduler().getGame().stopCountdown();
        getScheduler().getLobby().stopCountdown();
        getScheduler().getLobby().stopWaiting();
        getScheduler().getLobby().setSeconds(60);
        getGameHandler().stopSpawner();
        aliveTeams.clear();
        bedWarsTeams.clear();
        teamChest.clear();

        boarder = false;
        gold = true;
        itemDrop = false;
        forceMap = false;

        gameState = GameState.LOADGAME;
        setGameState(GameState.LOADGAME);

        int tagId = 0;
        for (String team : getBedWarsConfig().getConfiguration().getStringList("team.teams")) {
            bedWarsTeams.add(
                    new BedWarsTeam(team,
                            tagId,
                            bedWarsConfig.getString("team." + team + ".colorCode"),
                            getGameHandler().getColorFromString(bedWarsConfig.getString("team." + team + ".color")),
                            Integer.valueOf(bedWarsConfig.getString("settings.mode").split("x")[1]),
                            new ArrayList<>()));
            tagId++;
        }

        for (BedWarsTeam team : getBedWarsTeams()) {
            getAliveTeams().add(team);
        }
        for (String string : randomMap) {
            for (Entity entity : getServer().getWorld(string).getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                }
            }
        }
        String map = getMap();
        if (new File("plugins/BedWars/mapBackup/" + map).exists()) {
            Bukkit.unloadWorld(map, true);
            new File(map).delete();
            getGameHandler().copyFilesInDirectory(new File("plugins/BedWars/mapBackup/" + map), new File(map));
            if (getServer().getWorld(map) == null) {
                World world = Bukkit.createWorld(WorldCreator.name(map).type(WorldType.FLAT).generatorSettings("3;minecraft:air;2").generateStructures(false));
                world.setTime(1200);
                world.setDifficulty(Difficulty.NORMAL);
                world.setGameRuleValue("doMobSpawning", "false");
                world.setGameRuleValue("doDaylightCycle", "false");
            }
        }

        for (String string : getMaps().keySet()) {
            getMaps().get(string).clear();
        }

        players.clear();
        spectators.clear();
        getGameHandler().getSetupTeam().clear();
        getGameHandler().getSetup().clear();
        blocks.clear();
        if (!randomMap.isEmpty()) {
            int random = new Random().nextInt(randomMap.size());
            this.map = randomMap.get(random);
        }
        if (statsWall.isEmpty()) {
            for (int i = 1; i < BedWars.getInstance().getLocationAPI(getMap()).getCfg().getInt("statsWall") + 1; i++) {
                statsWall.add(getLocationAPI(getMap()).getLocation("statsSkull." + i));
            }
        }
        new StatsAPI().setStatsWall();
        for (String string : randomMap) {
            blocks.put(string, new ArrayList<>());
        }
        setGameState(GameState.LOBBY);
        if (getServer().getWorld(map) != null) {
            getLocationAPI(map).getLocation("lobby").getChunk().load();
        }
        if (!randomMap.isEmpty()) {
            getServer().getConsoleSender().sendMessage("§8[§e§lBedWars§8] §aDas Spiel ist bereit...");
        }
    }

    private void register() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerMoveListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerRespawnListener(), this);
        pluginManager.registerEvents(new ProtectionListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new EntityExplodeListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new ShopClickListener(), this);
        pluginManager.registerEvents(new EntityDamageByEntityListener(), this);
        pluginManager.registerEvents(new BlockPlaceListener(), this);
        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerInteractAtEntityListener(), this);
        pluginManager.registerEvents(new ServerPingListener(), this);

        getCommand("start").setExecutor(new Start());
        getCommand("setup").setExecutor(new Setup());
        getCommand("stats").setExecutor(new Stats());
        getCommand("forcemap").setExecutor(new ForceMap());
        getCommand("forcestart").setExecutor(new ForceStart());
    }

    public void setGameState(GameState gameState) {
        getServer().getPluginManager().callEvent(new GameStateChangeEvent(getGameState(), gameState));
       if (getBedWarsConfig().getBoolean("module.cloudNet.v3.enable")) {
            if (gameState == GameState.LOBBY || gameState == GameState.LOADGAME) {
                BukkitCloudNetHelper.setMaxPlayers(getGameHandler().getMaxPlayers());
                BukkitCloudNetHelper.setApiMotd(getMap());
                BukkitCloudNetHelper.setState("LOBBY");
                BridgeHelper.updateServiceInfo();
            }
            if (gameState == GameState.INGAME) {
                BukkitCloudNetHelper.setMaxPlayers(getGameHandler().getMaxPlayers() + 50);
                BukkitCloudNetHelper.setApiMotd(getMap());
                BukkitCloudNetHelper.setState("INGAME");
                BridgeHelper.updateServiceInfo();
            }
            if (gameState == GameState.ENDING) {
                BukkitCloudNetHelper.setMaxPlayers(getGameHandler().getMaxPlayers() + 50);
                BukkitCloudNetHelper.setApiMotd(getMap());
                BukkitCloudNetHelper.setState("INGAME");
                BridgeHelper.updateServiceInfo();
            }
        } else {
            if (gameState == GameState.LOADGAME) {
                setMotd(getBedWarsConfig().getString("settings.motd.lobby")
                        .replace("%map%", getMap()));
            } else {
                setMotd(getBedWarsConfig().getString("settings.motd." + gameState.toString().toLowerCase())
                        .replace("%map%", getMap()));
            }
        }
        this.gameState = gameState;
    }

    private void setMotd(String name) {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            Class<?> minecraftServerClazz = Class.forName("net.minecraft.server." + version + ".MinecraftServer");
            Method getServerMethod = minecraftServerClazz.getMethod("getServer");
            Object object = getServerMethod.invoke(null);
            Method setMotdMethod = object.getClass().getMethod("setMotd", String.class);
            setMotdMethod.invoke(object, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setItemDrop(boolean itemDrop) {
        this.itemDrop = itemDrop;
    }

    public boolean isItemDrop() {
        return itemDrop;
    }

    public ArrayList<UUID> getUuid() {
        return uuid;
    }

    public ArrayList<BedWarsTeam> getAliveTeams() {
        return aliveTeams;
    }

    public ArrayList<TabListGroup> getTabListGroups() {
        return tabListGroups;
    }

    public ArrayList<BedWarsTeam> getBedWarsTeams() {
        return bedWarsTeams;
    }

    public ArrayList<UUID> getNoGold() {
        return noGold;
    }

    public ArrayList<Player> getDeath() {
        return death;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public ArrayList<UUID> getSpectators() {
        return spectators;
    }

    public ArrayList<Location> getStatsWall() {
        return statsWall;
    }

    public ArrayList<UUID> getWithGold() {
        return withGold;
    }

    public void setBoarder(boolean boarder) {
        this.boarder = boarder;
    }

    public ArrayList<String> getRandomMap() {
        return randomMap;
    }

    public static BedWars getInstance() {
        return instance;
    }

    public BedWarsConfig getBedWarsConfig() {
        return bedWarsConfig;
    }

    public Board getBoard() {
        return board;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public GameState getGameState() {
        return gameState;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public HashMap<UUID, String> getSetupBed() {
        return setupBed;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public HashMap<UUID, String> getSetupBedTop() {
        return setupBedTop;
    }

    public ShopHandler getShopHandler() {
        return shopHandler;
    }

    public StatsHandler getStatsHandler() {
        return statsHandler;
    }

    public HashMap<String, ArrayList<UUID>> getMaps() {
        return maps;
    }

    public ArrayList<UUID> getNoItemDrop() {
        return noItemDrop;
    }

    public ArrayList<UUID> getWithItemDrop() {
        return withItemDrop;
    }

    public HashMap<String, Color> getColors() {
        return colors;
    }



    public HashMap<BedWarsTeam, Inventory> getTeamChest() {
        return teamChest;
    }

    public HashMap<UUID, String> getSetupStatsWallMap() {
        return setupStatsWallMap;
    }

    public HashMap<Color, Integer> getColorIds() {
        return colorIds;
    }

    public HashMap<Player, Player> getLastHit() {
        return lastHit;
    }

    public HashMap<String, List<Location>> getBlocks() {
        return blocks;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public void setForceMap(boolean forceMap) {
        this.forceMap = forceMap;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public LocationAPI getLocationAPI(String map) {
        return new LocationAPI(map);
    }
}
