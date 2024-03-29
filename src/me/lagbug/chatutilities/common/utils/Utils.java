package me.lagbug.chatutilities.common.utils;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;

import me.lagbug.chatutilities.ChatUtils;

public class Utils {
	
	private static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final ConsoleCommandSender cs = Bukkit.getConsoleSender();

	public static String randomString(int length) {
		String result = ""; Random r = new Random();

		for (int i = 0; i < length; i++) {
			result = result + alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return result;
	}
	
	public static int randomInteger(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return new Random().nextInt((max - min) + 1) + min;
	}
	
	public static String listToString(List<?> list) {
		return list.toString().replace("[", "").replace("]", "").replace(",", "");
	}
	
	public static Color colorFromString(String colorName) {
		Color color;

		try {
			Field field = Class.forName("java.awt.Color").getField(colorName);
			color = (Color) field.get(null);
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			return Color.BLACK;
		}
		
		return color;
	}
	
	public static String materialToString(Material material) {
		String result = "";

		for (String s : material.name().split("_")) {
			result += s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " ";
		}
		return result.substring(0, result.length() - 1);
	}
	
	public static boolean isPluginEnabled(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin) != null && Bukkit.getPluginManager().getPlugin(plugin).isEnabled();
	}

	private static final ChatUtils plugin = ChatUtils.getPlugin(ChatUtils.class);
	
	public static String replace(String s) {
		return ChatColor.translateAlternateColorCodes('&', s.replace("%prefix%", plugin.getConfigFile().getString("prefix")));
	}
	
	public static void log(String text) {
		cs.sendMessage("[" + "ChatUtils" + "] " + text + ".");
	}
}