package me.lagbug.chatutilities.common.builders;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBuilder {

	private Inventory inventory;
	private String title;
	private int slots;
	private	Map<Integer, ItemStack> toAdd = new HashMap<>();

	public InventoryBuilder(String title, int slots) {
		this.title = title;
		this.slots = slots;
	}

	public InventoryBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public InventoryBuilder setSlots(int slots) {
		this.slots = slots;
		return this;
	}
	
	public InventoryBuilder addItem(ItemStack item, int slot) {
		toAdd.put(slot, item);
		return this;
	}
	
	public ItemStack[] getItems() {
		return toAdd.values().toArray(new ItemStack[toAdd.size()]);
	}
	
	public Inventory build() {
		inventory = Bukkit.createInventory(null, slots, title);
		for (Integer i : toAdd.keySet()) {
			inventory.setItem(i, toAdd.get(i));
		}
		
		return inventory;
	}
}