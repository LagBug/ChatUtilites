package me.lagbug.chatutilities.common.builders;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

	private ItemStack item;
	private ItemMeta meta;

	@SuppressWarnings("deprecation")
	public ItemBuilder(Material material, int amount, byte id) {
		item = new ItemStack(material, amount, id);
		meta = item.getItemMeta();
	}

	public ItemBuilder setDisplayName(String name) {
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		List<String> loreR = new ArrayList<>();
		for (String s : lore) {
			loreR.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		meta.setLore(loreR);
		return this;
	}

	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		meta.addEnchant(enchantment, level, true);
		return this;
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {
		meta.setUnbreakable(unbreakable);
		return this;
	}

	public ItemBuilder addItemFlags(ItemFlag... flags) {
		meta.addItemFlags(flags);
		return this;
	}

	@SuppressWarnings("deprecation")
	public void setSkullOwner(String skullOwner) {
		Material mat = Bukkit.getVersion().contains("v1_13_") ? Material.getMaterial("LEGACY_SKULL_ITEM") : Material.getMaterial("SKULL_ITEM");
		item = new ItemStack(mat, item.getAmount(), (short) 3);
		SkullMeta skullMeta = (SkullMeta) meta;
		skullMeta.setOwner(skullOwner);
		item.setItemMeta(skullMeta);
	}

	public void setLeatherArmorColor(Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);

		item.setItemMeta(meta);
	}

	public ItemStack build() {
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}
}