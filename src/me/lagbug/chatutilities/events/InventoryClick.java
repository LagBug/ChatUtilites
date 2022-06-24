package me.lagbug.chatutilities.events;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.lagbug.chatutilities.ChatUtils;
import me.lagbug.chatutilities.common.utils.Utils;

public class InventoryClick implements Listener {
	
    private final ChatUtils plugin = ChatUtils.getPlugin(ChatUtils.class);
	private FileConfiguration config, messages;

	public InventoryClick() {
		config = plugin.getConfigFile();
		messages = plugin.getLangFile();
	}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getClickedInventory() == null || e.getInventory() == null || e.getCurrentItem().getType().equals(Material.AIR) || !(e.getWhoClicked() instanceof Player)) {
            return;
        }
        
        if (!getInventoryName(e).equals(ChatColor.translateAlternateColorCodes('&', config.getString("spy.gui.title")))) {
        	return;
        }
       
        
        Player player = (Player) e.getWhoClicked();
        
        if (!plugin.getSpiers().contains(player)) {
            plugin.getSpiers().add(player);
            player.sendMessage(Utils.replace(messages.getString("spy.enabled")));
        } else {
            plugin.getSpiers().remove(player);
            player.sendMessage(Utils.replace(messages.getString("spy.disabled")));
        }
        player.closeInventory();
    }
    
	private String getInventoryName(InventoryClickEvent e) {
		if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18") || Bukkit.getVersion().contains("1.19")) {
			return e.getView().getTitle();
		} else {
			try {
				return (String) Class.forName("org.bukkit.inventory.Inventory")
						.getMethod("getName", (Class<?>[]) new Class[0]).invoke(e.getInventory(), new Object[0]);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
				return "N/A";
			}
		}
	}
}