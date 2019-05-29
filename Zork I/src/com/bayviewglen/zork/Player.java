package com.bayviewglen.zork;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	public static HashMap <String, Item> items = new HashMap <String, Item>();
	private static ArrayList <String> peopleKilled = new ArrayList <String>();
	private static HashMap <String, Character> validNames = new HashMap <String, Character>(); 
	private static int numKilled = 0;
	
	public boolean checkKill(String name, HashMap validNames) {
		//returns true if name is in ArrayList
		// false if name is not 
	}

	public void addKill(String name) {
		// check to see if valid name (using checkKill method)
			// if yes
				//add name and increment numKilled
				peopleKilled.add(name);
				numKilled ++;
			// if no, 
				//print "As you finish writing the name down you hear thunder and lightning outside. You hear a loud bang behind you."
				//move Ryuk to currentRoom 
				//print "At the entrance of the *room* you see Ryuk floating there. His red eyes are gleaming in the dark."
				//print "I though you were smarter than this, Light. I must have overestimated you. Oh well. You failed, that means I must kill you."
	}
	
	public void peopleKilled(ArrayList <String> peopleKilled) {
		for (String s : peopleKilled) {
			System.out.println(s);
		}
	}
	
	public int numKilled() {
		return numKilled;
	}

	public void printKilled() {
		for (String s: peopleKilled) {
			System.out.println(s);
		}
	}
	
	// adds an item to the inventory
	// if an item is already in the inventory, increments amount
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
	// return true if it is, false if not
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

