package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Item {
	private String itemName;
	private String description;
	private String weight;
	private ArrayList<String> functions; // stores functions of this item.
	
	public Item (String description, String weight) {
		this.description = description;
		this.setWeight(weight);
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

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
}
