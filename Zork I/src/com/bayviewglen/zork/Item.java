package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Item {
	private String itemName;
	private String description;
	private int amount;
	private double weight;
	private ArrayList<String> functions; // stores functions of this item.
	
	private boolean isDeathNote;
	private boolean isGiveable;
	private boolean isTakeable;
	private boolean isConsumable;
	private boolean isKey;
	private boolean isReadable;
	private boolean isWatchable;
	private boolean isOpenable;
	private boolean isUsable; 
	
	public Item (String description, String weight) {
		this.description = description;
		this.weight = Double.parseDouble(weight);
		functions = new ArrayList<String>();
	}
	
	public Item() {
		// default constructor.
		itemName = "DEFAULT ITEM";
		description = "DEFAULT DESCRIPTION";
		functions = new ArrayList<String>();
	}
	
	public void addFunction(String function) throws Exception {
		functions.add(function);
	}

	/**
	 * Return the description of the item (the one that was defined in the
	 * constructor).
	 */
	public String shortDescription() {
		return "Item: " + itemName + "\n\n" + description;
	}

	/**
	 * Return a long description of this item, on the form: You are in the kitchen.
	 * Exits: north west
	 */
	public String longDescription() {
		return "Item: " + itemName + "\n\n" + description + "\n" + functionString();
	}

	/**
	 * Return a string describing the item's functions
	 * ".
	 */
	private String functionString() {
		String returnString = "Functions:";
//		Set keys = functions.keySet();
//		for (Iterator iter = keys.iterator(); iter.hasNext();)
//			returnString += " " + iter.next();
		return returnString;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = Double.parseDouble(weight);;
	}
	
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int newAmount) {
		this.amount = newAmount; 
	}
	
	public void addAmount(int amount) {
		this.amount += amount;
	}
	
	public void examine() {
		System.out.println(description);
	}
	
	public void take() {
		if (isTakeable == true) {
			// calls remove method from room inventory
			// calls add method from player inventory
		}
	}
	
	public void open() {
		if (isOpenable == true) {
			// returns text that contains more game info 
		}
	}
	
	public void give() {
		if (isGiveable == true) {
			// calls remove method from player inventory 
			// calls add method from character inventory
		}
	}
	
	public void unlock() {
		if (isKey == true) {
			// calls change isLocked method from room class
		}
	}
	
	public void read() {
		if (isReadable == true) {
			// returns text that contains more game info
		}
	}
	
	public void use() {
		if (isUsable == true) {
			// returns text that contains more game info
		}
	}
	
	public void write() {
		if (isDeathNote == true) {
			// returns what is written in DeathNote
			// calls add method in DeathNote class (will increment num people killed) 
		}
		
	}
	
	public void watch() {
		if (isWatchable == true) {
			// returns text the contains more game info
		}
		
	}
	
	public void drop() {
		if (isTakeable == true) {
			// calls removeItem from player class (removes item from inventory)
		}
		
	}
	
	public void eat () {
		if (isConsumable == true) {
			// returns text "you eat the ___, yum"
		}
	}
	
}
