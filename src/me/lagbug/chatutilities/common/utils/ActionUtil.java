package me.lagbug.chatutilities.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import me.clip.placeholderapi.PlaceholderAPI;

public class ActionUtil {

	private static JavaPlugin plugin;

	public static void setPlugin(JavaPlugin plugin) {
		ActionUtil.plugin = plugin;
	}

	public static void execute(Player p, String actions) {
		for (String r : actions.replace("[", "").replace("]", "").split(", ")) {
			r = r.replace("%player%", p.getName()).replace("%player_name%", p.getName());
			
			if (Utils.isPluginEnabled("PlaceholderAPI")) { r = PlaceholderAPI.setPlaceholders(p, r); }

			if (r.startsWith("consolecmd kick %player% ") || r.startsWith("kickplayer ")) {
				p.kickPlayer(r.replace("consolecmd kick %player% ", "").replace("kickplayer ", ""));
			} else if (r.startsWith("consolecmd")) {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), r.replace("consolecmd ", ""));
			} else if (r.startsWith("playercmd ")) {
				p.getPlayer().performCommand(r.replace("playercmd ", ""));

			} else if (r.startsWith("playermsg ")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', r.replace("playermsg ", "")));

			}else if (r.startsWith("sendtobungee")) {
				Messenger messenger = Bukkit.getMessenger();

				if (!messenger.isOutgoingChannelRegistered(plugin, "BungeeCord")) {
					messenger.registerOutgoingPluginChannel(plugin, "BungeeCord");
				}

				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(byteArray);

				try {
					out.writeUTF("Connect");
					out.writeUTF(r.replace("sendtobungee ", ""));
				} catch (IOException e) {
					e.printStackTrace();
				}

				p.sendPluginMessage(plugin, "BungeeCord", byteArray.toByteArray());
			} else if (r.startsWith("playsound ")) {
				String soundName = r.replace("playsound ", "");

				try {
					p.playSound(p.getLocation(), Sound.valueOf(soundName), 5.0F, 5.0F);
				} catch (IllegalArgumentException ex) {
					Utils.log("The sound '" + soundName + "' is not valid.");
				}

			}
		}
	}

}
