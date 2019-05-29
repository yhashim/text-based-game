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
	private boolean isUnlockable;
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
	
	public boolean take() {
		return isTakeable; 
	}
	
	public boolean open() {
		return isOpenable;
	}
	
	public boolean give() {
		return isGiveable;
	}
	
	public boolean unlock() {
		return isUnlockable;
	}
	
	public boolean read() {
		return isReadable;
	}
	
	public boolean use() {
		return isUsable;
	}
	
	public boolean write() {
		return isDeathNote;
	}
	
	public boolean watch() {
		return isWatchable;
	}
	
	public boolean drop() {
		return isTakeable;
	}
	
	public boolean eat() {
		return isConsumable;
	}
	
}
