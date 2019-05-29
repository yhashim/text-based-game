package com.bayviewglen.zork;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	private static HashMap <String, Item> items = new HashMap <String, Item>(); // fix
	private static ArrayList <String> peopleKilled = new ArrayList <String>(); // fix
	private static int numKilled = peopleKilled.size();

	//adds name to list of people killed 
	//increments number of people killed
	public static void addKill(String name) {
		peopleKilled.add(name);
		numKilled ++;
	}
	
	//prints out list of peopleKilled
	public static void peopleKilled(ArrayList <String> peopleKilled) {
		for (String s : peopleKilled) {
			System.out.println(s);
		}
	}
	
	public static int numKilled() {
		return numKilled;
	}

	public static void printKilled() {
		for (String s: peopleKilled) {
			System.out.println(s);
		}
	}
	
	// adds an item to the inventory
	// if an item is already in the inventory, increments amount
	public static void addToInventory(Item item, int amount) {
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
	public static Item getItem(String name) {
		if (items.containsKey(name)) {
			return items.get(name);
		}
		
		return null;
	}
	*/
	
	// checks for an item in the inventory 
	// return true if it is, false if not
	public static boolean contains(String name) {
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
	
	public static void removeItem(String name, int amount) {
		if (items.get(name).getAmount() == 1) {
			items.remove(name);
		}
		else {
			items.get(name).setAmount(items.get(name).getAmount()-1); 
		}
	}
	
}

