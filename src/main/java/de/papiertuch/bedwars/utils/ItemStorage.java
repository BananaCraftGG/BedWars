package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ItemStorage {

    private ItemStack teams = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.team.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.team.name")
            ).build();

    private ItemStack leave = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.leave.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.leave.name"))
            .build();

    private ItemStack startItem = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.start.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.start.name"))
            .build();

    private ItemStack vote = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.vote.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.vote.name"))
            .build();


    private ItemStack compass = new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("item.compass.material")), 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.compass.name"))
            .build();


    private ItemStack setLobby = new ItemBuilder(Material.WATCH, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setLobby.name"))
            .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setLobby.lore"))
            .build();

    private ItemStack saveMap = new ItemBuilder(Material.INK_SACK, 1, 10)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.saveMap.name"))
            .build();

    private ItemStack setSpectator = new ItemBuilder(Material.ARROW, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setSpectator.name"))
            .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setSpectator.lore"))
            .build();

    public ItemStack getSaveMap() {
        return saveMap;
    }

    public ItemStack getSetSpectator() {
        return setSpectator;
    }

    public ItemStack getTeam(BedWarsTeam team) {
        return new ItemBuilder(Material.LEATHER_BOOTS, 1)
                .setLeatherColor(team.getColorasColor())
                .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setTeamSpawn.name")
                .replace("%team%", team.getColor() + team.getName()))
                .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setTeamSpawn.lore"))
                .build();
    }

    public ItemStack getBed(BedWarsTeam team) {
        return new ItemBuilder(Material.STONE_AXE, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setBed.name")
                        .replace("%team%", team.getColor() + team.getName()))
                .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setBed.lore"))
                .build();
    }

    public ItemStack getBedTop(BedWarsTeam team) {
        return new ItemBuilder(Material.STONE_AXE, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setBedTop.name")
                        .replace("%team%", team.getColor() + team.getName()))
                .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setBedTop.lore"))
                .build();
    }


    private ItemStack setStatsWall = new ItemBuilder(Material.SKULL_ITEM, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setStatsWall.name"))
            .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setStatsWall.lore"))
            .build();


    private ItemStack setBronzeSpawner = new ItemBuilder(Material.WOOD_AXE, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setBronzeSpawner.name"))
            .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setBronzeSpawner.lore"))
            .build();


    private ItemStack setGoldSpawner = new ItemBuilder(Material.GOLD_AXE, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setGoldSpawner.name"))
            .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setGoldSpawner.lore"))
            .build();


    private ItemStack setIronSpawner = new ItemBuilder(Material.IRON_AXE, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setIronSpawner.name"))
            .setLore(BedWars.getInstance().getBedWarsConfig().getString("item.setup.setIronSpawner.lore"))
            .build();

    private ItemStack finish = new ItemBuilder(Material.MAGMA_CREAM, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.finish.name"))
            .build();

    private ItemStack options = new ItemBuilder(Material.PAPER, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.options.name"))
            .build();

    private ItemStack back = new ItemBuilder(Material.MAGMA_CREAM, 1)
            .setName(BedWars.getInstance().getBedWarsConfig().getString("item.setup.back.name"))
            .build();

    public ItemStack getBack() {
        return back;
    }

    public ItemStack getFinish() {
        return finish;
    }

    public ItemStack getCompass() {
        return compass;
    }

    public ItemStack getLeave() {
        return leave;
    }

    public ItemStack getOptions() {
        return options;
    }

    public ItemStack getSetBronzeSpawner() {
        return setBronzeSpawner;
    }

    public ItemStack getSetGoldSpawner() {
        return setGoldSpawner;
    }

    public ItemStack getSetIronSpawner() {
        return setIronSpawner;
    }

    public ItemStack getSetLobby() {
        return setLobby;
    }

    public ItemStack getSetStatsWall() {
        return setStatsWall;
    }

    public ItemStack getStartItem() {
        return startItem;
    }

    public ItemStack getTeams() {
        return teams;
    }

    public ItemStack getVote() {
        return vote;
    }


}
