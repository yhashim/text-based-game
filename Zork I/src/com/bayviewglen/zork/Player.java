package com.bayviewglen.zork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Player {
	private static HashMap <String, Item> items = new HashMap <String, Item>();
	private static ArrayList <String> peopleKilled = new ArrayList <String>();
	private static int numKilled = peopleKilled.size();

	public static void displayInventory() {
		String returnString = "Inventory: Death Note";
		Set keys = items.keySet();
		if (keys.size() >= 1)
			returnString += " ";
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
				returnString += ", " + iter.next().toString().toLowerCase();
		}
		Zork.print(returnString, 75);
	}
	
	//adds name to list of people killed 
	//increments number of people killed
	public static void addKill(String name) {
		peopleKilled.add(name);
		numKilled ++;
	}
	
	//prints out list of peopleKilled
	public static void peopleKilled(ArrayList <String> peopleKilled) {
		for (String s : peopleKilled) {
			Zork.print(s+"\n", 75);
		}
	}
	
	public static int numKilled() {
		return numKilled;
	}

	public static void printKilled() {
		for (String s: peopleKilled) {
			Zork.print(s+"\n", 75);
		}
	}
	
	// adds an item to the inventory
	// if an item is already in the inventory, increments amount
	public static void addToInventory(Item item, int amount) {
		if (items.containsKey(item.getItemName())) {
			//items.get(item.getItemName()).addAmount(amount);
			Zork.print("The item already exists in inventory\n", 75);
		}
		else {
			String putIn = item.getItemName().toUpperCase();
			items.put(putIn, item);

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
		name = name.toUpperCase();
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
		String putIn = name.toUpperCase();
		//if (items.get(putIn).getAmount() == 1) {
			items.remove(putIn);
		//}
		//else {
		//	items.get(putIn).setAmount(items.get(putIn).getAmount()-1); 
		//}
	}
	
}