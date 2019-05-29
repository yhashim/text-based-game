package com.bayviewglen.zork;
import java.util.HashMap;

public class Player {
	private HashMap <String, Item> items = new HashMap <String, Item>();
	
	// adds an item to the inventory
	// if an item is already 
	public void addToInventory(Item item, int amount) {
		if (items.containsKey(item.getItemName())) {
			items.get(item.getItemName()).addAmount(amount);
		}
		else {
			items.put(item.getItemName(), item);
		}
	}
	
	/*
	// checks for an item in the inventory 
	// return item if it is, null if not
	public Item getItem(String name) {
		if (items.containsKey(name)) {
			return items.get(name);
		}
		
		return null;
	}
	*/
	
	// checks for an item in the inventory 
	// return item if it is, null if not
	public boolean contains(String name) {
		if (items.containsKey(name)) {
			return true;
		}	
		
		return false;
	}
	
	public String toString() {
		String inv = "";
		for (Item i: items.values()) {
			inv += "\n " + i;
		}
		if (inv.length() <= 0) {
			return "There is nothing in your inventory";
		}
		return inv;
	}
	
	public void removeItem(String name, int amount) {
		if (items.get(name).getAmount() == 1) {
			items.remove(name);
		}
		else {
			items.get(name).setAmount(items.get(name).getAmount()-1); 
		}
	}
	
}

