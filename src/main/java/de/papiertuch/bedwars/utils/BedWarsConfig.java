package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class BedWarsConfig {

    private File file;
    private FileConfiguration configuration;

    private HashMap<String, Object> cache;
    private ArrayList<ConfigInput> sortedList;

    public BedWarsConfig() {
        file = new File("plugins/BedWars", "config.yml");
        configuration = YamlConfiguration.loadConfiguration(file);

        cache = new HashMap<>();
        sortedList = new ArrayList<>();
    }

    public void loadConfig() {
        init();
        if (configuration.get("settings.mysql.host") != null) {
            return;
        }
        configuration.options().copyDefaults(true);

        for (ConfigInput configInput : sortedList) {
            if (configuration.get(configInput.getPath()) == null) {
                configuration.set(configInput.getPath(), configInput.getValue());
            }
        }
        save();
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    private void init() {
        new ConfigInput("settings.mysql.host", "host");
        new ConfigInput("settings.mysql.dataBase", "dataBase");
        new ConfigInput("settings.mysql.user", "user");
        new ConfigInput("settings.mysql.password", "password");
        new ConfigInput("settings.mysql.port", 3306);
        new ConfigInput("settings.mode", "4x2");
        new ConfigInput("settings.minPlayers", 2);
        new ConfigInput("settings.shopType", "VILLAGER");
        new ConfigInput("settings.border", true);
        new ConfigInput("settings.enableStats", true);
        new ConfigInput("settings.stopServerAfterRound", false);
        new ConfigInput("settings.sendTitle", true);
        new ConfigInput("settings.coinCommand.enable", false);
        new ConfigInput("settings.coinCommand.command", "coins add %player% %value%");
        new ConfigInput("settings.coinCommand.killValue", 3);
        new ConfigInput("settings.coinCommand.playedValue", 5);
        new ConfigInput("settings.coinCommand.winValue", 20);
        new ConfigInput("settings.coinCommand.bedValue", 10);
        new ConfigInput("settings.premiumKick.enable", true);
        new ConfigInput("settings.premiumKick.permission", "bedwars.premium");

        new ConfigInput("settings.motd.lobby", "&a&lLOBBY &f%map%");
        new ConfigInput("settings.motd.ingame", "&6&lINGAME &f%map%");
        new ConfigInput("settings.motd.ending", "&c&lENDING &f%map%");

        new ConfigInput("countDown.lobbyDuration", 60);
        new ConfigInput("countDown.endingDuration", 15);
        new ConfigInput("countDown.gameTime", 3600);
        new ConfigInput("countDown.bronzeSpawnRate", 1);
        new ConfigInput("countDown.ironSpawnRate", 15);
        new ConfigInput("countDown.goldSpawnRate", 40);
        new ConfigInput("module.cloudNet.v2.enable", false);
        new ConfigInput("module.cloudNet.v2.nameTagPattern", "%prefix%");
        new ConfigInput("module.cloudNet.v3.enable", false);

        List<String> teams = new ArrayList<>();
        teams.add("Blau");
        teams.add("Rot");
        teams.add("Lila");
        teams.add("Gelb");
        new ConfigInput("team.teams", teams);

        new ConfigInput("team.Blau.colorCode", "&9");
        new ConfigInput("team.Blau.color", "BLUE");

        new ConfigInput("team.Rot.colorCode", "&c");
        new ConfigInput("team.Rot.color", "RED");

        new ConfigInput("team.Lila.colorCode", "&5");
        new ConfigInput("team.Lila.color", "PURPLE");

        new ConfigInput("team.Gelb.colorCode", "&e");
        new ConfigInput("team.Gelb.color", "YELLOW");


        List<String> tabList = new ArrayList<>();
        tabList.add("Admin");
        tabList.add("default");
        new ConfigInput("nameTags.tabList", tabList);

        new ConfigInput("nameTags.Admin.prefix", "&8[&4Admin&8] &4");
        new ConfigInput("nameTags.Admin.suffix", "&4");
        new ConfigInput("nameTags.Admin.display", "&4");
        new ConfigInput("nameTags.Admin.tagId", 9998);
        new ConfigInput("nameTags.Admin.permission", "bedwars.admin");

        new ConfigInput("nameTags.default.prefix", "&7");
        new ConfigInput("nameTags.default.suffix", "&7");
        new ConfigInput("nameTags.default.display", "&7");
        new ConfigInput("nameTags.default.tagId", 9999);
        new ConfigInput("nameTags.default.permission", "bedwars.default");

        new ConfigInput("command.setup.permission", "bedwars.setup");
        new ConfigInput("command.forceMap.permission", "bedwars.forcemap");
        new ConfigInput("command.start.permission", "bedwars.start");
        new ConfigInput("command.start.seconds", 5);

        new ConfigInput("sound.click", "WOOD_CLICK");
        new ConfigInput("sound.interact", "PISTON_EXTEND");
        new ConfigInput("sound.kill", "LEVEL_UP");
        new ConfigInput("sound.countdown", "CLICK");
        new ConfigInput("sound.error", "NOTE_PLING");
        new ConfigInput("sound.destroyBed", "WITHER_DEATH");
        new ConfigInput("sound.endingCountdown", "LAVA_POP");

        new ConfigInput("message.prefix", "&8[&e&lBedWars&8]&7");
        new ConfigInput("message.locationNotFound", "%prefix% &cDie Location &f%loc% &cvon der Map &f%map% &cwurde nicht gefunden");
        new ConfigInput("message.pathNotFound", "%prefix% &cDer Wert &f%value% &cwurde nicht gefunden");
        new ConfigInput("message.gameStop", "%prefix% &cDie Runde wird neugestartet...");
        new ConfigInput("message.forceMap.changeMap", "%prefix% &7Du hast die Map zu &e&l%map% &7ge\u00E4ndert");
        new ConfigInput("message.forceMap.mapNotExists", "%prefix% &cDiese Map gibt es nicht");
        new ConfigInput("message.gameStarting", "%prefix% &aDas Spiel wird gestartet...");
        new ConfigInput("message.gameStartingIn", "%prefix% &7Das &a&lSpiel &7startet in &a&l%seconds% &7Sekunden");
        new ConfigInput("message.gameStartingInOneSecond", "%prefix% &7Das &a&lSpiel &7startet in &a&leiner &7Sekunde");
        new ConfigInput("message.goldStatus", "%prefix% &7Gold &8\u00BB &e&l%state%");
        new ConfigInput("message.itemDropStatus", "%prefix% &7ItemDrops &8\u00BB &e&l%state%");
        new ConfigInput("message.mapStatus", "%prefix% &7Aktuelle Map &8\u00BB &e&l%map%");
        new ConfigInput("message.yourInTeam", "%prefix% &7Du bist nun im Team %team%");
        new ConfigInput("message.teamWin", " %prefix% &7Das Team %team% &7hat gewonnen!");
        new ConfigInput("message.borderIn", "%prefix% &7Die &cBorder &7verkleinert sich in &c&l%minutes% &7Minuten");
        new ConfigInput("message.borderInOneMinute", "%prefix% &7Die &cBorder &7verkleinert sich in &c&leiner Minute");
        new ConfigInput("message.border", "%prefix% &7Die &cBorder &7verkleinert sich nun!");
        new ConfigInput("message.roundStarting", "%prefix% &7Die Runde wurde gestartet");
        new ConfigInput("message.destroyAllBeds", "%prefix% &cEs wurden &4&lAlle &cBetten entfernt");
        new ConfigInput("message.smallBoarder", "%prefix% &cDie Border ist nun ganz klein!");
        new ConfigInput("message.roundEnds", "%prefix% &cDie Runde ist zuende");
        new ConfigInput("message.notEnoughResources", "%prefix% &cDu hast nicht genug Ressourcen");
        new ConfigInput("message.serverStopIn", "%prefix% &cDer Server stoppt in &a&l%seconds% &cSekunden");
        new ConfigInput("message.serverStopInOneSecond", "%prefix% &cDer Server stoppt in &a&leiner &cSekunde");
        new ConfigInput("message.serverStop", "%prefix% &cDer Server stoppt jetzt!");
        new ConfigInput("message.start.startRound", "%prefix% &aDu hast die Runde gestartet");
        new ConfigInput("message.start.countDownUnderSeconds", "%prefix% &cDer Countdown ist bereits unter &a&l%seconds% &cSekunden!");
        new ConfigInput("message.start.notEnoughPlayers", "%prefix% &cEs sind nicht genug Spieler online");
        new ConfigInput("message.start.roundAlreadyStarting", "%prefix% &cDie Runde startet bereits...");
        new ConfigInput("message.noPerms", "%prefix% &cDazu hast du keine Rechte...");
        new ConfigInput("message.destroyOwnBed", "%prefix% &cDu kannst dein eigenes Bett nicht abbauen!");
        new ConfigInput("message.destroyBed", "%prefix% &7Das Bett vom Team %team% &7wurde von %player% &7abgebaut!");
        new ConfigInput("message.teamFull", "%prefix% &cDieses Team ist bereits voll!");
        new ConfigInput("message.teamEmpty", "%prefix% &cEs wurden alle Spieler neuverteilt da die Teams unfair belegt sind...");
        new ConfigInput("message.otherTeamsEmpty", "%prefix% &cNehme ein anderes Team da diese leer sind");
        new ConfigInput("message.choseTeam", "%prefix% &7Du wirst nun im Team %team% &7sein");
        new ConfigInput("message.teleportToPlayer", "%prefix% &7Du bist nun bei %player%");
        new ConfigInput("message.killMessage", "%prefix% %player% &7wurde von %killer% &7get\u00F6tet");
        new ConfigInput("message.killerLife", "%prefix% &7Leben von %killer% &8\u00BB &c%live%");
        new ConfigInput("message.death", "%prefix% %player% &7ist gestorben");
        new ConfigInput("message.leaveGameWithItem", "%prefix% &cDu hast den Server verlassen...");
        new ConfigInput("message.teamDeath", "%prefix% &7Das Team %team% &7ist ausgeschieden");
        new ConfigInput("message.teamReamingPlayers", "%prefix% &7Das Team %team% &7hat noch &a&l%players% Spieler");
        new ConfigInput("message.joinGame", "%prefix% %player% &7hat das Spiel betreten");
        new ConfigInput("message.spectator", "%prefix% &7Du bist ist ein Spectator");
        new ConfigInput("message.leaveGame", "%prefix% %player% &7hat das Spiel verlassen");
        new ConfigInput("message.premiumKick.full", "%prefix% &cDu benötigst mindestens den &6&lPremium &cRang, um diesen Server betreten zu können");
        new ConfigInput("message.premiumKick.fullPremium", "%prefix% &cDieser Server ist komplett voll, jeder hat mindestenes einen &6&lPremium &cRang");
        new ConfigInput("message.premiumKick.kickPlayer", "%prefix% &cDu wurdest von einem &6&lhöherrängigen &cSpieler gekickt");
        new ConfigInput("message.actionBar.lobbyWaitingOnes", "%prefix% &fWarte auf &c&leinen &fSpieler...");
        new ConfigInput("message.actionBar.lobbyWaiting", "%prefix% &fWarte auf &c&l%players% &fSpieler...");
        new ConfigInput("message.actionBar.lobby", "%team%");
        new ConfigInput("message.actionBar.game", "&e&lGold &8» %goldVote% &8┃ &e&lItemDrop &8» %itemDropVote% &8┃ &e&lKarte §8» &f&l%map%");
        new ConfigInput("message.actionBar.ones", "eine");
        new ConfigInput("message.setup.addMap", "%prefix% &7Das Setup für die Map &a&l%map% §7wurde gestartet");
        new ConfigInput("message.setup.addTeam", "%prefix% &7Du hast das Team &a&l%team% &7erstellt");
        new ConfigInput("message.setup.addNameTag", "%prefix% &7Du hast den NameTag &a&l%nameTag% &7erstellt");
        new ConfigInput("message.setup.loadMap", "%prefix% &aDie Welt wird geladen...");
        new ConfigInput("message.setup.loadFinish", "%prefix% &7Die Map wurde geladen");
        new ConfigInput("message.setup.setLobby", "%prefix% &7Du hast den &a&lLobby &7Spawn gesetzt");
        new ConfigInput("message.setup.setSpectator", "%prefix% &7Du hast den &a&lSpectator &7Spawn gesetzt");
        new ConfigInput("message.setup.setTeamSpawn", "%prefix% &7Du hast den Spawn von %team% &7gesetzt");
        new ConfigInput("message.setup.noBackup", "%prefix% &cDu musst erst die Map als Backup speichern");
        new ConfigInput("message.setup.finishSetup", "%prefix% &fDas Setup wurde beendet, dass Spiel kann beginnen");
        new ConfigInput("message.setup.saveMap", "%prefix% &7Du hast die Map &a&l%map% &7gespeichert");
        new ConfigInput("message.setup.teleport", "%prefix% &7Du wurdest zur Map &a&l%map% &7teleportiert");
        new ConfigInput("message.setup.mapNotExists", "%prefix% &cDiese Map gibt es nicht");
        new ConfigInput("message.setup.setupTeam", "%prefix% &7Du richtest nun das Team %team% &7ein");
        new ConfigInput("message.voting.voteEnable", "&aAktiviert");
        new ConfigInput("message.voting.voteDisable", "&cDeaktiviert");
        new ConfigInput("message.voting.withGold", "&8» &aMit Gold");
        new ConfigInput("message.voting.withOutGold", "&8» &cOhne Gold");
        new ConfigInput("message.voting.withItemDrop", "&8» &aMit ItemDrop");
        new ConfigInput("message.voting.withOutItemDrop", "&8» &cOhne ItemDrop");
        new ConfigInput("message.voting.mapVote", "%prefix% &7Du hast für die Map &a&l%map% &7gestimmt");
        new ConfigInput("message.voting.alreadyForeMap", "%prefix% &cEs wurde bereits durch einen ForceMap eine Map ausgesucht");
        new ConfigInput("message.voting.yes", "%prefix% &7Du hast für &aJa &7gestimmt");
        new ConfigInput("message.voting.no", "%prefix% &7Du hast für &cNein &7gestimmt");
        new ConfigInput("message.title.win.one", "%teamColor% Team %team%");
        new ConfigInput("message.title.win.two", "&ahat gewonnen");
        new ConfigInput("message.title.bedDestroy.one", "&cDein Bett");
        new ConfigInput("message.title.bedDestroy.two", "&cwurde zerstört");
        new ConfigInput("message.drops.bronze", "&cBronze");
        new ConfigInput("message.drops.iron", "&fEisen");
        new ConfigInput("message.drops.gold", "&6Gold");
        new ConfigInput("message.inventory.teamChose", "&8\u00BB &f%count%");
        new ConfigInput("message.inventory.votingAmount", "&8\u00BB &f%votes% Stimmen");


        List<String> list = new ArrayList<>();
        list.add("%prefix% &7Stats von %player%");
        list.add("&8» &f&lPlatz &8» &e%ranking%");
        list.add("&8» &f&lPunkte &8» &e%points%");
        list.add("&8» &f&lKills &8» &e%kills%");
        list.add("&8» &f&lDeaths &8» &e%deaths%");
        list.add("&8» &f&lGespielt &8» &e%played%");
        list.add("&8» &f&lGewonnen &8» &e%wins%");
        list.add("&8» &f&lVerloren &8» &e%loses%");
        list.add("&8» &f&lBetten &8» &e%beds%");

        new ConfigInput("message.stats.lines", list);

        new ConfigInput("chat.format.spectators", "&8[&4\u2716&8] %player% &8\u00BB &7%message%");
        new ConfigInput("chat.format.normal", "%player% &8\u00BB &7%message%");
        new ConfigInput("chat.format.team", "%player% &8\u00BB &7%message%");
        new ConfigInput("chat.format.all", "&8[&f&lGlobal&8] %player% &8\u00BB&7%message%");

        new ConfigInput("scoreboard.title", "%prefix% &f%time%");

        new ConfigInput("scoreboard.line1.title", "&f&lSpieler");
        new ConfigInput("scoreboard.line1.input", " &8\u00BB &e%players%");
        new ConfigInput("scoreboard.line2.title", "&f&lVariante");
        new ConfigInput("scoreboard.line2.input", " &8\u00BB &e%mode%");
        new ConfigInput("scoreboard.line3.title", "&f&lKarte");
        new ConfigInput("scoreboard.line3.input", " &8\u00BB &e%map%");
        new ConfigInput("scoreboard.line4.title", "&f&lTeam");
        new ConfigInput("scoreboard.line4.input", " &8\u00BB %team%");
        new ConfigInput("scoreboard.line5.title", "&f&lTeams");

        new ConfigInput("scoreboard.line.teamHasBed.one", " &8\u00BB &2\u2714 %team%");
        new ConfigInput("scoreboard.line.teamHasBed.two", " &8\u2503 &f%players%");
        new ConfigInput("scoreboard.line.teamHasNoBed.one", " &8\u00BB &4\u2716 %team%");
        new ConfigInput("scoreboard.line.teamHasNoBed.two", " &8\u2503 &f%players%");
        new ConfigInput("scoreboard.line.teamDeath.one", " &r&8\u00BB &4\u2716 %team%&m");
        new ConfigInput("scoreboard.line.teamDeath.two", "&r");

        new ConfigInput("scoreboard.teams.prefix", "%team%");
        new ConfigInput("scoreboard.spectator.prefix", "&7");
        new ConfigInput("scoreboard.spectator.display", "&7");

        new ConfigInput("item.team.material", "ARMOR_STAND");
        new ConfigInput("item.team.name", "&8\u00BB &e§lTeams");
        new ConfigInput("item.team.slot", 1);
        new ConfigInput("item.leave.material", "MAGMA_CREAM");
        new ConfigInput("item.leave.name", "&8\u00BB &e&lVerlassen");
        new ConfigInput("item.leave.slot", 7);
        new ConfigInput("item.compass.material", "COMPASS");
        new ConfigInput("item.compass.name", "&8\u00BB &e&lSpieler");
        new ConfigInput("item.compass.slot", 4);
        new ConfigInput("item.vote.material", "PAPER");
        new ConfigInput("item.vote.name", "&8\u00BB &e&lVoting");
        new ConfigInput("item.vote.slot", 3);
        new ConfigInput("item.start.material", "REDSTONE_TORCH_ON");
        new ConfigInput("item.start.name", "&8\u00BB &e&lSpiel starten");
        new ConfigInput("item.start.slot", 5);
        new ConfigInput("item.voting.mapVote", "&8\u00BB &e&lMapvoting");
        new ConfigInput("item.voting.goldVote", "&8\u00BB &e&lGoldVoting");
        new ConfigInput("item.voting.itemDropVote", "&8\u00BB &e&lItemDrop Voting");
        new ConfigInput("item.setup.setLobby.name", "&8» &7&lLobby Spawn setzen (Rechtsklick)");
        new ConfigInput("item.setup.setLobby.lore", "&8» &7Bitte auf Position stellen und Rechtsklick");
        new ConfigInput("item.setup.setSpectator.name", "&8» &7&lSpectator Spawn setzen (Rechtsklick)");
        new ConfigInput("item.setup.setSpectator.lore", "&8» &7Bitte auf Position stellen und Rechtsklick");
        new ConfigInput("item.setup.setTeamSpawn.name", "&8» &7&lSpawn setzen von %team% (Rechtsklick)");
        new ConfigInput("item.setup.setTeamSpawn.lore", "&8» &7Bitte auf Position stellen und Rechtsklick");
        new ConfigInput("item.setup.setBed.name", "&8» &7&lUnteres Teil des Bettes von %team% (Zerschlagen)");
        new ConfigInput("item.setup.setBed.lore", "&8» &7Bitte auf das Bett schlagen");
        new ConfigInput("item.setup.setBedTop.name", "&8» &7&lOberes Teil des Bettes von %team% (Zerschlagen)");
        new ConfigInput("item.setup.setBedTop.lore", "&8» &7Bitte auf das Bett schlagen");
        new ConfigInput("item.setup.setStatsWall.name", "&8» &7&lStats Wand setzen (Zerschlagen)");
        new ConfigInput("item.setup.setStatsWall.lore", "&8» &7Bitte auf den Kopf klicken");
        new ConfigInput("item.setup.setBronzeSpawner.name", "&8» &c&lBronze Spawner setzen (Zerschlagen)");
        new ConfigInput("item.setup.setBronzeSpawner.lore", "&8» &7Bitte auf den Block klicken");
        new ConfigInput("item.setup.setIronSpawner.name", "&8» &f&lEisen Spawner setzen (Zerschlagen)");
        new ConfigInput("item.setup.setIronSpawner.lore", "&8» &7Bitte auf den Block klicken");
        new ConfigInput("item.setup.setGoldSpawner.name", "&8» &6&lGold Spawner setzen (Zerschlagen)");
        new ConfigInput("item.setup.setGoldSpawner.lore", "&8» &7Bitte auf den Block klicken");
        new ConfigInput("item.setup.saveMap.name", "&8» &a&lMap speichern (Nicht Wartelobby)");
        new ConfigInput("item.setup.back.name", "&8» &c&lZurück");
        new ConfigInput("item.setup.finish.name", "&8» &b&lSetup beenden (Rechtsklick)");
        new ConfigInput("item.setup.options.name", "&8» &7&lTeams einrichten (Rechtsklick)");

        new ConfigInput("inventory.shop.title", "&8\u00BB &6Haupt");
        new ConfigInput("inventory.shop.bricks.name", "&8\u00BB &6Steine");
        new ConfigInput("inventory.shop.bricks.item", "STAINED_CLAY");
        new ConfigInput("inventory.shop.armor.name", "&8\u00BB &6R\u00FCstung");
        new ConfigInput("inventory.shop.armor.item", "CHAINMAIL_CHESTPLATE");
        new ConfigInput("inventory.shop.tools.name", "&8\u00BB &6Spitzhacken");
        new ConfigInput("inventory.shop.tools.item", "STONE_PICKAXE");
        new ConfigInput("inventory.shop.swords.name", "&8\u00BB &6Schwerter");
        new ConfigInput("inventory.shop.swords.item", "WOOD_SWORD");
        new ConfigInput("inventory.shop.bows.name", "&8\u00BB &6B\u00F6gen");
        new ConfigInput("inventory.shop.bows.item", "BOW");
        new ConfigInput("inventory.shop.food.name", "&8\u00BB &6Essen");
        new ConfigInput("inventory.shop.food.item", "COOKED_BEEF");
        new ConfigInput("inventory.shop.chests.name", "&8\u00BB &6Kisten");
        new ConfigInput("inventory.shop.chests.item", "CHEST");
        new ConfigInput("inventory.shop.potions.name", "&8\u00BB &6Tr\u00E4nke");
        new ConfigInput("inventory.shop.potions.item", "GLASS_BOTTLE");
        new ConfigInput("inventory.shop.specials.name", "&8\u00BB &6Extras");
        new ConfigInput("inventory.shop.specials.item", "EMERALD");
        new ConfigInput("inventory.shop.item.bricks.name", "&8\u00BB &aBl\u00F6cke");
        new ConfigInput("inventory.shop.item.bricks.amount", 2);
        new ConfigInput("inventory.shop.item.bricks.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.bricks.price.price", 1);
        new ConfigInput("inventory.shop.item.endStone.name", "&8\u00BB &aEndsteine");
        new ConfigInput("inventory.shop.item.endStone.amount", 1);
        new ConfigInput("inventory.shop.item.endStone.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.endStone.price.price", 8);
        new ConfigInput("inventory.shop.item.ironBlock.name", "&8\u00BB &aEisenblock");
        new ConfigInput("inventory.shop.item.ironBlock.amount", 1);
        new ConfigInput("inventory.shop.item.ironBlock.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.ironBlock.price.price", 3);
        new ConfigInput("inventory.shop.item.glass.name", "&8\u00BB &aGlass");
        new ConfigInput("inventory.shop.item.glass.amount", 1);
        new ConfigInput("inventory.shop.item.glass.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.glass.price.price", 4);
        new ConfigInput("inventory.shop.item.glowStone.name", "&8\u00BB &aLicht");
        new ConfigInput("inventory.shop.item.glowStone.amount", 1);
        new ConfigInput("inventory.shop.item.glowStone.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.glowStone.price.price", 4);
        new ConfigInput("inventory.shop.item.helmet.name", "&8\u00BB &aHelm");
        new ConfigInput("inventory.shop.item.helmet.amount", 1);
        new ConfigInput("inventory.shop.item.helmet.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.helmet.price.price", 1);
        new ConfigInput("inventory.shop.item.leggings.name", "&8\u00BB &aHose");
        new ConfigInput("inventory.shop.item.leggings.amount", 1);
        new ConfigInput("inventory.shop.item.leggings.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.leggings.price.price", 1);
        new ConfigInput("inventory.shop.item.boots.name", "&8\u00BB &aSchuhe");
        new ConfigInput("inventory.shop.item.boots.amount", 1);
        new ConfigInput("inventory.shop.item.boots.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.boots.price.price", 1);
        new ConfigInput("inventory.shop.item.chestPlate1.name", "&8\u00BB &aBrustpanzer I");
        new ConfigInput("inventory.shop.item.chestPlate1.amount", 1);
        new ConfigInput("inventory.shop.item.chestPlate1.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.chestPlate1.price.price", 1);
        new ConfigInput("inventory.shop.item.chestPlate2.name", "&8\u00BB &aBrustpanzer II");
        new ConfigInput("inventory.shop.item.chestPlate2.amount", 1);
        new ConfigInput("inventory.shop.item.chestPlate2.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.chestPlate2.price.price", 3);
        new ConfigInput("inventory.shop.item.chestPlate3.name", "&8\u00BB &aBrustpanzer III");
        new ConfigInput("inventory.shop.item.chestPlate3.amount", 1);
        new ConfigInput("inventory.shop.item.chestPlate3.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.chestPlate3.price.price", 5);
        new ConfigInput("inventory.shop.item.chestPlate4.name", "&8\u00BB &aBrustpanzer IV");
        new ConfigInput("inventory.shop.item.chestPlate4.amount", 1);
        new ConfigInput("inventory.shop.item.chestPlate4.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.chestPlate4.price.price", 7);
        new ConfigInput("inventory.shop.item.woodPickAxe.name", "&8\u00BB &aHolzspitzhacke");
        new ConfigInput("inventory.shop.item.woodPickAxe.amount", 1);
        new ConfigInput("inventory.shop.item.woodPickAxe.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.woodPickAxe.price.price", 4);
        new ConfigInput("inventory.shop.item.stonePickAxe.name", "&8\u00BB &aSteinspitzhacke");
        new ConfigInput("inventory.shop.item.stonePickAxe.amount", 1);
        new ConfigInput("inventory.shop.item.stonePickAxe.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.stonePickAxe.price.price", 2);
        new ConfigInput("inventory.shop.item.ironPickAxe.name", "&8\u00BB &aEisenspitzhacke");
        new ConfigInput("inventory.shop.item.ironPickAxe.amount", 1);
        new ConfigInput("inventory.shop.item.ironPickAxe.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.ironPickAxe.price.price", 1);
        new ConfigInput("inventory.shop.item.stick.name", "&8\u00BB &aStock");
        new ConfigInput("inventory.shop.item.stick.amount", 1);
        new ConfigInput("inventory.shop.item.stick.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.stick.price.price", 8);
        new ConfigInput("inventory.shop.item.sword1.name", "&8\u00BB &aHolzschwert I");
        new ConfigInput("inventory.shop.item.sword1.amount", 1);
        new ConfigInput("inventory.shop.item.sword1.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.sword1.price.price", 1);
        new ConfigInput("inventory.shop.item.sword2.name", "&8\u00BB &aHolzschwert II");
        new ConfigInput("inventory.shop.item.sword2.amount", 1);
        new ConfigInput("inventory.shop.item.sword2.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.sword2.price.price", 3);
        new ConfigInput("inventory.shop.item.sword3.name", "&8\u00BB &aHolzschwert III");
        new ConfigInput("inventory.shop.item.sword3.amount", 1);
        new ConfigInput("inventory.shop.item.sword3.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.sword3.price.price", 5);
        new ConfigInput("inventory.shop.item.sword4.name", "&8\u00BB &aEisenschwert");
        new ConfigInput("inventory.shop.item.sword4.amount", 1);
        new ConfigInput("inventory.shop.item.sword4.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.sword4.price.price", 5);
        new ConfigInput("inventory.shop.item.bow1.name", "&8\u00BB &aBogen I");
        new ConfigInput("inventory.shop.item.bow1.amount", 1);
        new ConfigInput("inventory.shop.item.bow1.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.bow1.price.price", 3);
        new ConfigInput("inventory.shop.item.bow2.name", "&8\u00BB &aBogen II");
        new ConfigInput("inventory.shop.item.bow2.amount", 1);
        new ConfigInput("inventory.shop.item.bow2.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.bow2.price.price", 6);
        new ConfigInput("inventory.shop.item.bow3.name", "&8\u00BB &aBogen III");
        new ConfigInput("inventory.shop.item.bow3.amount", 1);
        new ConfigInput("inventory.shop.item.bow3.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.bow3.price.price", 9);
        new ConfigInput("inventory.shop.item.arrow.name", "&8\u00BB &aPfeil");
        new ConfigInput("inventory.shop.item.arrow.amount", 1);
        new ConfigInput("inventory.shop.item.arrow.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.arrow.price.price", 1);
        new ConfigInput("inventory.shop.item.apple.name", "&8\u00BB &aApfel");
        new ConfigInput("inventory.shop.item.apple.amount", 1);
        new ConfigInput("inventory.shop.item.apple.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.apple.price.price", 1);
        new ConfigInput("inventory.shop.item.beef.name", "&8\u00BB &aFleisch");
        new ConfigInput("inventory.shop.item.beef.amount", 1);
        new ConfigInput("inventory.shop.item.beef.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.beef.price.price", 2);
        new ConfigInput("inventory.shop.item.cake.name", "&8\u00BB &aKuchen");
        new ConfigInput("inventory.shop.item.cake.amount", 1);
        new ConfigInput("inventory.shop.item.cake.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.cake.price.price", 1);
        new ConfigInput("inventory.shop.item.goldenApple.name", "&8\u00BB &aGold Apfel");
        new ConfigInput("inventory.shop.item.goldenApple.amount", 1);
        new ConfigInput("inventory.shop.item.goldenApple.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.goldenApple.price.price", 2);
        new ConfigInput("inventory.shop.item.chest.name", "&8\u00BB &aKiste");
        new ConfigInput("inventory.shop.item.chest.amount", 1);
        new ConfigInput("inventory.shop.item.chest.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.chest.price.price", 1);
        new ConfigInput("inventory.shop.item.endChest.name", "&8\u00BB &aEnderkiste");
        new ConfigInput("inventory.shop.item.endChest.amount", 1);
        new ConfigInput("inventory.shop.item.endChest.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.endChest.price.price", 1);
        new ConfigInput("inventory.shop.item.healing1.name", "&8\u00BB &aHeltrank I");
        new ConfigInput("inventory.shop.item.healing1.amount", 1);
        new ConfigInput("inventory.shop.item.healing1.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.healing1.price.price", 3);
        new ConfigInput("inventory.shop.item.healing2.name", "&8\u00BB &aHeiltrank II");
        new ConfigInput("inventory.shop.item.healing2.amount", 1);
        new ConfigInput("inventory.shop.item.healing2.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.healing2.price.price", 6);
        new ConfigInput("inventory.shop.item.strength.name", "&8\u00BB &aSt\u00E4rketrank");
        new ConfigInput("inventory.shop.item.strength.amount", 1);
        new ConfigInput("inventory.shop.item.strength.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.strength.price.price", 3);
        new ConfigInput("inventory.shop.item.regeneration.name", "&8\u00BB &aRegenerationstrank");
        new ConfigInput("inventory.shop.item.regeneration.amount", 1);
        new ConfigInput("inventory.shop.item.regeneration.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.regeneration.price.price", 3);
        new ConfigInput("inventory.shop.item.speed.name", "&8\u00BB &aSchnelligkeitstrank");
        new ConfigInput("inventory.shop.item.speed.amount", 1);
        new ConfigInput("inventory.shop.item.speed.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.speed.price.price", 4);
        new ConfigInput("inventory.shop.item.ladder.name", "&8\u00BB &aLeiter");
        new ConfigInput("inventory.shop.item.ladder.amount", 1);
        new ConfigInput("inventory.shop.item.ladder.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.ladder.price.price", 4);
        new ConfigInput("inventory.shop.item.web.name", "&8\u00BB &aSpinnennetz");
        new ConfigInput("inventory.shop.item.web.amount", 1);
        new ConfigInput("inventory.shop.item.web.price.material", "CLAY_BRICK");
        new ConfigInput("inventory.shop.item.web.price.price", 16);
        new ConfigInput("inventory.shop.item.warp.name", "&8\u00BB &aTeleporter");
        new ConfigInput("inventory.shop.item.warp.amount", 1);
        new ConfigInput("inventory.shop.item.warp.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.warp.price.price", 5);
        new ConfigInput("inventory.shop.item.shop.name", "&8\u00BB &aMobiler Shop");
        new ConfigInput("inventory.shop.item.shop.amount", 1);
        new ConfigInput("inventory.shop.item.shop.price.material", "IRON_INGOT");
        new ConfigInput("inventory.shop.item.shop.price.price", 7);
        new ConfigInput("inventory.shop.item.tnt.name", "&8\u00BB &aTNT");
        new ConfigInput("inventory.shop.item.tnt.amount", 1);
        new ConfigInput("inventory.shop.item.tnt.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.tnt.price.price", 3);
        new ConfigInput("inventory.shop.item.egg.name", "&8\u00BB &aFallschirm");
        new ConfigInput("inventory.shop.item.egg.amount", 1);
        new ConfigInput("inventory.shop.item.egg.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.egg.price.price", 3);
        new ConfigInput("inventory.shop.item.rescue.name", "&8\u00BB &aRettungsplatform");
        new ConfigInput("inventory.shop.item.rescue.amount", 1);
        new ConfigInput("inventory.shop.item.rescue.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.rescue.price.price", 4);
        new ConfigInput("inventory.shop.item.pearl.name", "&8\u00BB &aEnderperle");
        new ConfigInput("inventory.shop.item.pearl.amount", 1);
        new ConfigInput("inventory.shop.item.pearl.price.material", "GOLD_INGOT");
        new ConfigInput("inventory.shop.item.pearl.price.price", 13);
    }

    public Integer getInt(String string) {
        try {
            if (!cache.containsKey(string)) {
                cache.put(string, configuration.getInt(string));
            }
            return (Integer) cache.get(string);
        } catch (Exception e) {
            configuration.set(string, getConfigInput(string).getValue());
            save();
            return (Integer) getConfigInput(string).getValue();
        }
    }

    public Boolean getBoolean(String string) {
        try {
            if (!cache.containsKey(string)) {
                cache.put(string, configuration.getBoolean(string));
            }
            return (Boolean) cache.get(string);
        } catch (Exception e) {
            configuration.set(string, getConfigInput(string).getValue());
            save();
            return (Boolean) getConfigInput(string).getValue();
        }
    }

    public String getString(String string) {
        try {
            if (!cache.containsKey(string)) {
                cache.put(string, ChatColor.translateAlternateColorCodes('&', configuration.getString(string)
                        .replace("%prefix%", configuration.getString("message.prefix"))));
            }
            return (String) cache.get(string);
        } catch (Exception e) {
            System.out.print(string);
            System.out.print(getConfigInput(string).getValue());
            configuration.set(string, getConfigInput(string).getValue());
            save();
            return (String) getConfigInput(string).getValue();
        }
    }

    private ConfigInput getConfigInput(String string) {
        for (ConfigInput configInput : sortedList) {
            if (configInput.getPath().equalsIgnoreCase(string)) {
                return configInput;
            }
        }
        return null;
    }

    public ArrayList<ConfigInput> getSortedList() {
        return sortedList;
    }
}
