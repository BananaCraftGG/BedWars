package de.papiertuch.bedwars.listener;

import de.papiertuch.bedwars.BedWars;
import de.papiertuch.bedwars.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Leon on 15.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ShopClickListener implements Listener {

    @EventHandler
    public void onShopClick(InventoryClickEvent event) {
        try {
            Player player = (Player) event.getWhoClicked();
            if (event.getClickedInventory().getName().contains(" §8┃ §aMenü")) {
                String name = event.getCurrentItem().getItemMeta().getDisplayName();
                event.setCancelled(true);
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bricks.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getBricksInventory(player, name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.armor.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.tools.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getToolsInventory(name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.swords.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getWeaponsInventory(name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.bows.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getBowInventory(name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.food.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getFoodInventory(name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.chests.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getChestInventory(name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.potions.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getPotionsInventory(name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.specials.name"))) {
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(name));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bricks.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeName().removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bricks.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bricks.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getBricksInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.endStone.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeName().removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.endStone.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.endStone.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getBricksInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ironBlock.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeName().removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ironBlock.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.ironBlock.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getBricksInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.glass.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeName().removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.glass.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.glass.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getBricksInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.glowStone.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeName().removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.glowStone.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.glowStone.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getBricksInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.woodPickAxe.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.woodPickAxe.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.woodPickAxe.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getToolsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.stonePickAxe.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.stonePickAxe.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.stonePickAxe.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getToolsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ironPickAxe.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ironPickAxe.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.ironPickAxe.price.price"));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    player.openInventory(BedWars.getInstance().getShopHandler().getToolsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.stick.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.stick.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.stick.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getWeaponsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword1.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword1.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword1.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getWeaponsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword2.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword2.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword2.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getWeaponsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword3.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword3.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword3.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getWeaponsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword4.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.sword4.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.sword4.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getWeaponsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow1.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow1.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bow1.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getBowInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow2.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow2.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bow2.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getBowInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow3.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.bow3.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.bow3.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getBowInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.arrow.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.arrow.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.arrow.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getBowInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.endChest.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.endChest.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.endChest.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getChestInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chest.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chest.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chest.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getChestInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.healing1.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.healing1.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.healing1.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getPotionsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.healing2.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.healing2.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.healing2.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getPotionsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.strength.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.strength.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.strength.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getPotionsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.regeneration.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.regeneration.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.regeneration.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getPotionsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.speed.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.speed.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.speed.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getPotionsInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.apple.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.apple.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.apple.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getFoodInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.beef.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.beef.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.beef.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getFoodInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.cake.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.cake.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.cake.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getFoodInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.goldenApple.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.goldenApple.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.goldenApple.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getFoodInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ladder.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.ladder.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.ladder.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.web.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.web.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.web.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.warp.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.warp.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.warp.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.shop.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.shop.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.shop.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.tnt.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.tnt.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.tnt.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.egg.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.egg.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.egg.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.rescue.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.rescue.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.rescue.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.pearl.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.pearl.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.pearl.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getSpecialInventory(event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.helmet.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.helmet.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.helmet.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.leggings.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.leggings.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.leggings.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.boots.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.boots.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.boots.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate1.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate1.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate1.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate2.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate2.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate2.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate3.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate3.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate3.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
                if (name.equalsIgnoreCase(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate4.name"))) {
                    BedWars.getInstance().getShopHandler().buyItem(event,
                            player, new ItemBuilder(event.getCurrentItem()).removeLore().build(),
                            Material.valueOf(BedWars.getInstance().getBedWarsConfig().getString("inventory.shop.item.chestPlate4.price.material")),
                            BedWars.getInstance().getBedWarsConfig().getInt("inventory.shop.item.chestPlate4.price.price"));
                    player.openInventory(BedWars.getInstance().getShopHandler().getArmorInventory(player, event.getClickedInventory().getName().replace(" §8┃ §aMenü", "")));
                    player.playSound(player.getLocation(), BedWars.getInstance().getGameHandler().getSound(BedWars.getInstance().getBedWarsConfig().getString("sound.click")), 1, 1);
                    return;
                }
            }
        } catch (Exception ex) {
        }
    }
}
