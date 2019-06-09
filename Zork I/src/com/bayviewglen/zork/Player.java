package com.bayviewglen.zork;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Player {
	private static HashMap <String, Item> items = new HashMap <String, Item>();
	private static ArrayList <String> peopleKilled = new ArrayList <String>();
	private static int numKilled = peopleKilled.size();
	private static boolean sisterMission = false;

	public static void displayInventory() {
		String returnString = "Inventory: ";
		Set keys = items.keySet();
//		if (keys.size() >= 1)
//			returnString += " ";
		for (Iterator iter = keys.iterator(); iter.hasNext();) {

			String invItem = iter.next().toString();
			returnString += invItem.toUpperCase().substring(0, 1);
			invItem = invItem.substring(1);
			while (invItem.indexOf(" ")>0) {
				returnString += invItem.substring(0,invItem.indexOf(" ")+1);
				invItem = invItem.substring(invItem.indexOf(" ")+1);
				returnString += invItem.toUpperCase().substring(0, 1);
				invItem = invItem.substring(1);
			}
			returnString += invItem;	
			if (iter.hasNext())
				returnString += ", ";		} 
		Zork.print(returnString + "\n", 75);
	}
	
	//adds name to list of people killed 
	//increments number of people killed
	public static void addKill(String name) {
		peopleKilled.add(name);
		numKilled ++;
	}
	
	//prints out list of peopleKilled
	public static void peopleKilled() {
		for (String s : peopleKilled) {
			Zork.print(s +"\n", 75);
		}
		if (peopleKilled.isEmpty()) {
			Zork.print("You have not killed anyone.\n", 75);
		}
	}
	
	public static boolean isKilled (String name) {
		for (String p : peopleKilled) {
			if (p.equals(name)) {
				return true;
			}
		}
		return false;
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
			String putIn = item.getItemName();
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
		name = name.toLowerCase();
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
		String putIn = name.toLowerCase();
			items.remove(putIn);
	}
	
	public static int getNumKilled() {
		return numKilled;
	}
	
	public static boolean getSisterMission() {
		return sisterMission;
	}

	public static void setSisterMission(boolean b) {
		sisterMission = b;
	}
	
	
	
}

