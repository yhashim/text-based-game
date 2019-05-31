package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Item {
	private String itemName;
	private String description;
	private String currentRoom;
	private String currentCharacter;
	private int amount;
	private double weight;
	private ArrayList<String> functions; // stores functions of this item.

	private boolean isGiveable;
	private boolean isTakeable;
	private boolean isConsumable;
	private boolean isUsable; 
	private boolean isUnlockable;
	private boolean isReadable;
	private boolean isWatchable;
	private boolean isOpenable;
	
	private static ArrayList<Item> allItems;
	
	public Item (String description, String weight) {
		this.description = description;
		this.weight = Double.parseDouble(weight);
		functions = new ArrayList<String>();
		if (allItems == null) {
			allItems = new ArrayList<Item>();
		}
		allItems.add(this);
	}
	
	public Item() {
		// default constructor.
		itemName = "DEFAULT ITEM";
		description = "DEFAULT DESCRIPTION";
		functions = new ArrayList<String>();
	}
	
	public void addFunction(String function) throws Exception {
		functions.add(function);
		this.setBoolean(function);
	}
	
	public void setBoolean(String function) {
		// for (int i = 0; i < functions.size(); i++) {
			if (functions.equals("give")){
				isGiveable = true;
			}
			else if (functions.equals("take")){
				isTakeable = true;
			}
			else if (functions.equals("eat")) {
				isConsumable = true;
			}
			else if (functions.equals("use")) {
				isUsable = true;
			}
			else if (functions.equals("unlock")) {
				isUnlockable = true;
			}
			else if (functions.equals("read")) {
				isReadable = true;
			}
			else if (functions.equals("watch")) {
				isWatchable = true;
			}
			else if (functions.equals("open")) {
				isOpenable = true;
			}
			
		//}
	}
	
	public static Item getItem(String string) {
		for (Item i : allItems) {
			if (i.getItemName().toLowerCase().equals(string)) {
				return i;
			}
		}
		return null;
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
	
	public String examine() {
		return description;
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
	
	public boolean watch() {
		return isWatchable;
	}
	
	public boolean drop() {
		return isTakeable;
	}
	
	public boolean eat() {
		return isConsumable;
	}

	public String getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(String currentRoom) {
		this.currentRoom = currentRoom;
	}
	
	public String getCurrentCharacter() {
		return currentCharacter;
	}
	
	public void setCurrentCharacter(String currentCharacter) {
		this.currentCharacter = currentCharacter;
	}
}
