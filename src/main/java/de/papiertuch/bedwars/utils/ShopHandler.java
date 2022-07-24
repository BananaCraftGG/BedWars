package de.papiertuch.bedwars.utils;

import de.papiertuch.bedwars.BedWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ShopHandler {


    private void setMainItems(Inventory inventory) {
        inventory.setItem(0, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bricks.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bricks.name"))
                .build());
        inventory.setItem(1, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.armor.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.armor.name"))
                .build());
        inventory.setItem(2, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.tools.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.tools.name"))
                .build());
        inventory.setItem(3, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.swords.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.swords.name"))
                .build());
        inventory.setItem(4, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bows.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bows.name"))
                .build());
        inventory.setItem(5, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.chests.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.chests.name"))
                .build());
        inventory.setItem(6, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.potions.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.potions.name"))
                .build());
        inventory.setItem(7, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.specials.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.specials.name"))
                .build());
        inventory.setItem(8, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.food.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.food.name"))
                .build());
    }

    private void fillInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                        .setName("§7")
                        .build());
            }
        }
    }

    public Inventory getMainInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.title") + " §8┃ §aMenü");
        Material material = Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bricks.item"));
        inventory.setItem(9, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bricks.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bricks.name"))
                .setData(material == Material.SANDSTONE ? 0 : BedWars.getInstance().getColorIds().get(BedWars.getInstance().getGameHandler().getTeam(player).getColorasColor()))
                .build());
        inventory.setItem(10, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.armor.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.armor.name"))
                .build());
        inventory.setItem(11, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.tools.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.tools.name"))
                .build());
        inventory.setItem(12, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.swords.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.swords.name"))
                .build());
        inventory.setItem(13, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bows.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bows.name"))
                .build());
        inventory.setItem(14, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.chests.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.chests.name"))
                .build());
        inventory.setItem(15, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.potions.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.potions.name"))
                .build());
        inventory.setItem(16, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.specials.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.specials.name"))
                .build());
        inventory.setItem(17, new ItemBuilder(Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.food.item")), 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.food.name"))
                .build());
        fillInventory(inventory);
        return inventory;
    }

    public Inventory getSpecialInventory(String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(18, new ItemBuilder(Material.LADDER, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.ladder.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ladder.name"))
                .setLore("§8» " + getPrice("ladder"))
                .build());
        inventory.setItem(19, new ItemBuilder(Material.WEB, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.web.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.web.name"))
                .setLore("§8» " + getPrice("web"))
                .build());
        inventory.setItem(20, new ItemBuilder(Material.FIREWORK, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.warp.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.warp.name"))
                .setLore("§8» " + getPrice("warp"))
                .build());
        inventory.setItem(21, new ItemBuilder(Material.ARMOR_STAND, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.shop.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.shop.name"))
                .setLore("§8» " + getPrice("shop"))
                .build());

        inventory.setItem(23, new ItemBuilder(Material.TNT, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.tnt.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.tnt.name"))
                .setLore("§8» " + getPrice("tnt"))
                .build());
        inventory.setItem(24, new ItemBuilder(Material.EGG, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.egg.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.egg.name"))
                .setLore("§8» " + getPrice("egg"))
                .build());
        inventory.setItem(25, new ItemBuilder(Material.BLAZE_ROD, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.rescue.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.rescue.name"))
                .setLore("§8» " + getPrice("rescue"))
                .build());
        inventory.setItem(26, new ItemBuilder(Material.ENDER_PEARL, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.pearl.amount"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.pearl.name"))
                .setLore("§8» " + getPrice("pearl"))
                .build());

        fillInventory(inventory);
        return inventory;
    }

    public Inventory getPotionsInventory(String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(18, new ItemBuilder(Material.POTION, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.healing1.amount"))
                .setData(8261)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.healing1.name"))
                .setLore("§8» " + getPrice("healing1"))
                .build());
        inventory.setItem(20, new ItemBuilder(Material.POTION, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.healing2.amount"))
                .setData(8229)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.healing2.name"))
                .setLore("§8» " + getPrice("healing2"))
                .build());
        inventory.setItem(22, new ItemBuilder(Material.POTION, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.strength.amount"))
                .setData(8201)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.strength.name"))
                .setLore("§8» " + getPrice("strength"))
                .build());
        inventory.setItem(24, new ItemBuilder(Material.POTION, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.regeneration.amount"))
                .setData(8193)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.regeneration.name"))
                .setLore("§8» " + getPrice("regeneration"))
                .build());
        inventory.setItem(26, new ItemBuilder(Material.POTION, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.speed.amount"))
                .setData(8194)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.speed.name"))
                .setLore("§8» " + getPrice("speed"))
                .build());

        fillInventory(inventory);
        return inventory;
    }

    public Inventory getChestInventory(String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(21, new ItemBuilder(Material.CHEST, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chest.amount"))
                .setLore("§8» " + getPrice("chest"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chest.name"))
                .build());
        inventory.setItem(23, new ItemBuilder(Material.ENDER_CHEST, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.endChest.amount"))
                .setLore("§8» " + getPrice("endChest"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.endChest.name"))
                .build());

        fillInventory(inventory);
        return inventory;
    }

    public Inventory getFoodInventory(String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(20, new ItemBuilder(Material.APPLE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.apple.amount"))
                .setLore("§8» " + getPrice("apple"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.apple.name"))
                .build());
        inventory.setItem(21, new ItemBuilder(Material.COOKED_BEEF, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.beef.amount"))
                .setLore("§8» " + getPrice("beef"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.beef.name"))
                .build());
        inventory.setItem(22, new ItemBuilder(Material.CAKE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.cake.amount"))
                .setLore("§8» " + getPrice("cake"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.cake.name"))
                .build());

        inventory.setItem(24, new ItemBuilder(Material.GOLDEN_APPLE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.goldenApple.amount"))
                .setLore("§8» " + getPrice("goldenApple"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.goldenApple.name"))
                .build());

        fillInventory(inventory);
        return inventory;
    }

    public Inventory getBowInventory(String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(20, new ItemBuilder(Material.BOW, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bow1.amount"))
                .setLore("§8» " + getPrice("bow1"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow1.name"))
                .enchant(Enchantment.ARROW_INFINITE, 1)
                .setDurability(Material.BOW.getMaxDurability() - ((Material.BOW.getMaxDurability() / 100) * 15))
                .build());
        inventory.setItem(21, new ItemBuilder(Material.BOW, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bow2.amount"))
                .enchant(Enchantment.ARROW_DAMAGE, 1)
                .enchant(Enchantment.ARROW_INFINITE, 1)
                .setLore("§8» " + getPrice("bow2"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow2.name"))
                .setDurability(Material.BOW.getMaxDurability() - ((Material.BOW.getMaxDurability() / 100) * 15))
                .build());
        inventory.setItem(22, new ItemBuilder(Material.BOW, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bow3.amount"))
                .setLore("§8» " + getPrice("bow3"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow3.name"))
                .enchant(Enchantment.ARROW_DAMAGE, 2)
                .enchant(Enchantment.ARROW_INFINITE, 1)
                .setDurability(Material.BOW.getMaxDurability() - ((Material.BOW.getMaxDurability() / 100) * 15))
                .build());

        inventory.setItem(24, new ItemBuilder(Material.ARROW, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.arrow.amount"))
                .setLore("§8» " + getPrice("arrow"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.arrow.name"))
                .build());

        fillInventory(inventory);
        return inventory;
    }

    public Inventory getWeaponsInventory(String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(20, new ItemBuilder(Material.STICK, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.stick.amount"))
                .setLore("§8» " + getPrice("stick"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.stick.name"))
                .enchant(Enchantment.KNOCKBACK, 1)
                .build());
        inventory.setItem(21, new ItemBuilder(Material.WOOD_SWORD, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword1.amount"))
                .setLore("§8» " + getPrice("sword1"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword1.name"))
                .build());
        inventory.setItem(22, new ItemBuilder(Material.WOOD_SWORD, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword2.amount"))
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword2.name"))
                .setLore("§8» " + getPrice("sword2"))
                .build());
        inventory.setItem(23, new ItemBuilder(Material.WOOD_SWORD, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword3.amount"))
                .enchant(Enchantment.DAMAGE_ALL, 2)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword3.name"))
                .setLore("§8» " + getPrice("sword3"))
                .build());
        inventory.setItem(24, new ItemBuilder(Material.IRON_SWORD, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword4.amount"))
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword4.name"))
                .enchant(Enchantment.KNOCKBACK, 1)
                .setLore("§8» " + getPrice("sword4"))
                .build());

        fillInventory(inventory);
        return inventory;
    }

    public Inventory getToolsInventory(String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(21, new ItemBuilder(Material.WOOD_PICKAXE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.woodPickAxe.amount"))
                .setLore("§8» " + getPrice("woodPickAxe"))
                .enchant(Enchantment.DIG_SPEED, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.woodPickAxe.name"))
                .build());
        inventory.setItem(22, new ItemBuilder(Material.STONE_PICKAXE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.stonePickAxe.amount"))
                .setLore("§8» " + getPrice("stonePickAxe"))
                .enchant(Enchantment.DIG_SPEED, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.stonePickAxe.name"))
                .build());
        inventory.setItem(23, new ItemBuilder(Material.IRON_PICKAXE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.ironPickAxe.amount"))
                .setLore("§8» " + getPrice("ironPickAxe"))
                .enchant(Enchantment.DIG_SPEED, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ironPickAxe.name"))
                .build());

        fillInventory(inventory);
        return inventory;
    }

    public Inventory getArmorInventory(Player player, String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        inventory.setItem(19, new ItemBuilder(Material.LEATHER_HELMET, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.helmet.amount"))
                .setLeatherColor(BedWars.getInstance().getGameHandler().getTeam(player).getColorasColor())
                .setLore("§8» " + getPrice("helmet"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.helmet.name"))
                .build());
        inventory.setItem(20, new ItemBuilder(Material.LEATHER_LEGGINGS, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.leggings.amount"))
                .setLeatherColor(BedWars.getInstance().getGameHandler().getTeam(player).getColorasColor())
                .setLore("§8» " + getPrice("leggings"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.leggings.name"))
                .build());
        inventory.setItem(21, new ItemBuilder(Material.LEATHER_BOOTS, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.boots.amount"))
                .setLeatherColor(BedWars.getInstance().getGameHandler().getTeam(player).getColorasColor())
                .setLore("§8» " + getPrice("boots"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.boots.name"))
                .build());

        inventory.setItem(23, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate1.amount"))
                .setLore("§8» " + getPrice("chestPlate1"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate1.name"))
                .build());
        inventory.setItem(24, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate2.amount"))
                .setLore("§8» " + getPrice("chestPlate2"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate2.name"))
                .build());
        inventory.setItem(25, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate3.amount"))
                .setLore("§8» " + getPrice("chestPlate3"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate3.name"))
                .build());
        inventory.setItem(26, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate4.amount"))
                .setLore("§8» " + getPrice("chestPlate4"))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .enchant(Enchantment.THORNS, 1)
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate4.name"))
                .build());
        fillInventory(inventory);
        return inventory;
    }

    public Inventory getBricksInventory(Player player, String title) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, title + " §8┃ §aMenü");
        setMainItems(inventory);
        Material material = Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bricks.item"));
        inventory.setItem(20, new ItemBuilder(material, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bricks.amount"))
                .setData(material == Material.SANDSTONE ? 0 : BedWars.getInstance().getColorIds().get(BedWars.getInstance().getGameHandler().getTeam(player).getColorasColor()))
                .setLore("§8» " + getPrice("bricks"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bricks.name"))
                .build());
        inventory.setItem(21, new ItemBuilder(Material.ENDER_STONE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.endStone.amount"))
                .setLore("§8» " + getPrice("endStone"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.endStone.name"))
                .build());
        inventory.setItem(22, new ItemBuilder(Material.IRON_BLOCK, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.ironBlock.amount"))
                .setLore("§8» " + getPrice("ironBlock"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ironBlock.name"))
                .build());
        inventory.setItem(23, new ItemBuilder(Material.STAINED_GLASS, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.glass.amount"))
                .setData(BedWars.getInstance().getColorIds().get(BedWars.getInstance().getGameHandler().getTeam(player).getColorasColor()))
                .setLore("§8» " + getPrice("glass"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.glass.name"))
                .build());
        inventory.setItem(24, new ItemBuilder(Material.GLOWSTONE, BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.glowStone.amount"))
                .setLore("§8» " + getPrice("glowStone"))
                .setName(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.glowStone.name"))
                .build());
        fillInventory(inventory);
        return inventory;
    }

    private String getPrice(String item) {
        String material = BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item." + item + ".price.material");
        int price = BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item." + item + ".price.price");
        if (material.equals("CLAY_BRICK")) {
            return "§c" + price + " Bronze";
        }
        if (material.equals("IRON_INGOT")) {
            return "§f" + price + " Eisen";
        }
        if (material.equals("GOLD_INGOT")) {
            return "§6" + price + " Gold";
        }
        return "none";
    }

    public void buyItem(InventoryClickEvent event, Player player, ItemStack itemStack, Material material, int price) {
        if (event.isShiftClick()) {
            Integer amount = price;
            if (!player.getInventory().contains(material, amount)) {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.notEnoughResources"));
                player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("NOTE_BASS"), 10.0F, 10.0F);
                return;
            }
            for (int i = 0; i != 64; i++) {
                if (!player.getInventory().contains(material, amount)) {
                    return;
                }
                removeItems(player, material, amount);
                player.getInventory().addItem(event.getCurrentItem());
            }
        } else {
            Integer amount = price;
            if (!player.getInventory().contains(material, amount)) {
                player.sendMessage(BedWars.getInstance().getBedWarsConfig().getString("message.notEnoughResources"));
                player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound("NOTE_BASS"), 10.0F, 10.0F);
                return;
            }
            removeItems(player, material, amount);
            player.getInventory().addItem(event.getCurrentItem());
        }
    }


    public void removeItems(Player player, Material material, int amount) {
        int removeItem = amount;

        for (int index = 0; index < player.getInventory().getSize() || removeItem <= 0; ++index) {
            ItemStack i = player.getInventory().getItem(index);
            if (removeItem <= 0) break;
            if (i == null || i.getType() != material) continue;
            int size = i.getAmount();
            if (size <= removeItem) {
                removeItem -= size;
                player.getInventory().setItem(index, new ItemStack(Material.AIR));
                continue;
            }
            i.setAmount(size - removeItem);
            player.getInventory().setItem(index, i);
            removeItem = 0;
        }
        player.updateInventory();
    }
}
