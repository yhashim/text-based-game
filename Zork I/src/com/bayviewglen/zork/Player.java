package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Player {
	// instance variables
	// hold information for the player that we need to access throughout the
	// gameplay
	private static HashMap<String, Item> items = new HashMap<String, Item>();
	private static ArrayList<String> peopleKilled = new ArrayList<String>();
	private static int numKilled = peopleKilled.size();
	private static boolean sisterMission = false;

	// displays character's inventory when command "inventory"/"i" called
	public static void displayInventory() {
		String returnString = "Inventory: ";
		Set keys = items.keySet();
		// iterate through, add to returnString
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			String invItem = iter.next().toString();
			returnString += invItem.toUpperCase().substring(0, 1);
			invItem = invItem.substring(1);
			while (invItem.indexOf(" ") > 0) {
				returnString += invItem.substring(0, invItem.indexOf(" ") + 1);
				invItem = invItem.substring(invItem.indexOf(" ") + 1);
				returnString += invItem.toUpperCase().substring(0, 1);
				invItem = invItem.substring(1);
			}
			returnString += invItem;
			if (iter.hasNext())
				returnString += ", ";
		}
		// print out the returnString
		Zork.print(returnString + "\n", 75);
	}

	// adds name to list of people killed
	// increments number of people killed
	public static void addKill(String name) {
		peopleKilled.add(name);
		numKilled++;
	}

	// iterates through peopleKilled
	// prints out list of peopleKilled
	public static void peopleKilled() {
		for (String s : peopleKilled) {
			Zork.print(s + "\n", 75);
		}
		if (peopleKilled.isEmpty()) {
			Zork.print("You have not killed anyone.\n", 75);
		}
	}

	// returns true if name has been killed
	// false otherwise
	public static boolean isKilled(String name) {
		for (String p : peopleKilled) {
			if (p.equals(name)) {
				return true;
			}
		}
		return false;
	}

	// returns numKilled (imporant for game milestones)
	public static int numKilled() {
		return numKilled;
	}

	// prints peopleKilled
	public static void printKilled() {
		for (String s : peopleKilled) {
			Zork.print(s + "\n", 75);
		}
	}

	// adds an item to the inventory
	public static void addToInventory(Item item) {
		if (items.containsKey(item.getItemName())) {
			Zork.print("The item already exists in inventory\n", 75);
		} else {
			String putIn = item.getItemName();
			items.put(putIn, item);
		}
	}

	// checks for an item in the inventory
	// return true if it is, false if not
	public static boolean contains(String name) {
		name = name.toLowerCase();
		if (items.containsKey(name)) {
			return true;
		}
		return false;
	}

	// overwriting the toString method for our own purposes
	public String toString() {
		String inv = "";
		for (Item i : items.values()) {
			inv += "\n " + i;
		}
		if (inv.length() <= 0) {
			return "There is nothing in your inventory";
		}
		return inv;
	}

	// removes an item from inventory
	public static void removeItem(String name) {
		String putIn = name.toLowerCase();
		items.remove(putIn);
	}

	// numKilled getter
	public static int getNumKilled() {
		return numKilled;
	}

	// milestone boolean getter
	public static boolean getSisterMission() {
		return sisterMission;
	}

	// milestone boolean setter
	public static void setSisterMission(boolean b) {
		sisterMission = b;
	}

}
