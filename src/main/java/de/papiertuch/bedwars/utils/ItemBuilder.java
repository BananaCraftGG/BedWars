package de.papiertuch.bedwars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Leon on 14.06.2019.
 * development with love.
 * © Copyright by Papiertuch
 */

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(Material material, int amount ) {
        item = new ItemStack(material, amount);
    }

    public ItemBuilder( Material material, int amount, int data ) {
        item = new ItemStack(material, amount, (short) data);
    }

    public ItemBuilder( ItemStack item ) {
        this.item = item;
    }

    public ItemBuilder setData( int data ) {
        item.setDurability((short) data);
        return this;
    }

    public ItemBuilder removeLore() {
        ItemMeta m = item.getItemMeta();
        if (m.hasLore()) {
            m.setLore(new ArrayList<>());
        }
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder removeName() {
        ItemMeta m = item.getItemMeta();
        if (m.hasDisplayName()) {
            m.setDisplayName(null);
        }
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setDurability( int durability ) {
        item.setDurability((short) durability);
        return this;
    }

    public ItemBuilder setMaterial( Material m ) {
        item.setType(m);
        return this;
    }

    public ItemBuilder setAmount( int amount ) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName( String name ) {
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(name);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLore( String... lore ) {
        ItemMeta m = item.getItemMeta();
        m.setLore(Arrays.asList(lore));
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLore( ArrayList<String> lore ) {
        ItemMeta m = item.getItemMeta();
        m.setLore(lore);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setUnbreakable( ) {
        ItemMeta m = item.getItemMeta();
        m.spigot().setUnbreakable(true);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder enchant(Enchantment ench, int lvl ) {
        item.addUnsafeEnchantment(ench, lvl);
        return this;
    }

    public ItemBuilder addFlags( ItemFlag... flag ) {
        ItemMeta m = item.getItemMeta();
        m.addItemFlags(flag);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setLeatherColor( Color color ) {
        LeatherArmorMeta m = (LeatherArmorMeta) item.getItemMeta();
        m.setColor(color);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setSkullOwner( String owner ) {
        SkullMeta m = (SkullMeta) item.getItemMeta();
        m.setOwner(owner);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setPotionType( PotionEffectType type ) {
        PotionMeta m = (PotionMeta) item.getItemMeta();
        m.setMainEffect(type);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookAuthor( String author ) {
        BookMeta m = (BookMeta) item.getItemMeta();
        m.setAuthor(author);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookContent( String... pages ) {
        BookMeta m = (BookMeta) item.getItemMeta();
        m.setPages(pages);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookTitle( String title ) {
        BookMeta m = (BookMeta) item.getItemMeta();
        m.setTitle(title);
        item.setItemMeta(m);
        return this;
    }

    public ItemBuilder setBookMeta( String title, String author, String... pages ) {
        BookMeta m = (BookMeta) item.getItemMeta();
        m.setTitle(title);
        m.setAuthor(author);
        m.setPages(pages);
        item.setItemMeta(m);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setEggType( EntityType type ) {
        if ((item != null) && (item.getType() == Material.MONSTER_EGG) && (type != null) && (type.getName() != null)) {
            try {
                String version = Bukkit.getServer().getClass().toString().split("\\.")[3];
                Class<?> craftItemStack = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack");

                Object nmsItemStack = craftItemStack.getDeclaredMethod("asNMSCopy", ItemStack.class).invoke(null, item);
                Object nbtTagCompound = Class.forName("net.minecraft.server." + version + ".NBTTagCompound").newInstance();

                Field nbtTagCompoundField = nmsItemStack.getClass().getDeclaredField("tag");
                nbtTagCompoundField.setAccessible(true);

                nbtTagCompound.getClass().getMethod("setString", String.class, String.class).invoke(nbtTagCompound, "id", type.getName());
                nbtTagCompound.getClass().getMethod("set", String.class, Class.forName("net.minecraft.server." + version + ".NBTBase")).invoke(nbtTagCompoundField.get(nmsItemStack), "EntityTag", nbtTagCompound);

                item = (ItemStack) craftItemStack.getDeclaredMethod("asCraftMirror", nmsItemStack.getClass()).invoke(null, nmsItemStack);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }

    public ItemStack build( ) {
        return item;
    }
}
