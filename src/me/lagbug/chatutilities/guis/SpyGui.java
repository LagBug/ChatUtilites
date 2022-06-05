package me.lagbug.chatutilities.guis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lagbug.chatutilities.ChatUtils;

public class SpyGui {
	
    private final ChatUtils plugin = ChatUtils.getPlugin(ChatUtils.class);
    private FileConfiguration config;
    
    public SpyGui(Player player) {
    	config = plugin.getConfigFile();
    	
        Inventory inv = Bukkit.createInventory(null, config.getInt("spy.gui.slots"), colorize(config.getString("spy.gui.title")));
        ItemStack item = !plugin.getSpiers().contains(player) ? create("enable") : create("disable");
        inv.setItem(config.getInt("spy.gui.item.slot"), item);
        
        player.openInventory(inv);
    }

    public ItemStack create(String type) {
        ItemStack item = new ItemStack(Material.valueOf(config.getString("spy.gui.item." + type + ".material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("spy.gui.item." + type + ".name")));
        List<String> lore = new ArrayList<>();
        config.getStringList("gui.item." + type + ".lore").forEach(l -> lore.add(ChatColor.translateAlternateColorCodes('&', l)));
        meta.setLore(lore);
        item.setItemMeta(meta);
        
        return item;
    }

    private String colorize(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}