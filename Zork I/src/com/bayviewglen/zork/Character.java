package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Character {
	private String characterName;
	private String startingLocation;
	// private static HashMap <String, Item> startingItems = new HashMap <String, Item>();
	private static HashMap <String, Item> wantedItems = new HashMap <String, Item>(); // fix
	private static HashMap <String, Item> items = new HashMap<String, Item>(); // fix
	private ArrayList<String> speech;
	private static String[] functions; // stores functions you can do to this character. //fix
	
	public Character() {
		
	}
	
//	public Character(String characterName, String startingLocation, HashMap <String, Item> startingItems, HashMap <String, Item> wantedItems, ArrayList<String> speech, String[] functions) {
//		this.characterName = characterName;
//		this.startingLocation = startingLocation;
//		this.startingItems = startingItems;
//		this.wantedItems = wantedItems;
//		this.speech = speech;
//		this.functions = functions;
//	}
	
	// adds an item to the inventory
	// if an item is already 
	public static void addToInventory(Item item, int amount) {
		if (items.containsKey(item.getItemName())) {
			items.get(item.getItemName()).addAmount(amount);
		}
		else {
			items.put(item.getItemName(), item);
		}
	}
	
	// checks for an item in the inventory 
	// return item if it is, null if not
	public boolean contains(String name) {
		if (items.containsKey(name)) {
			return true;
		}	
		
		return false;
	}
	
	public ArrayList<String> speech() {
		return speech;
	}
	

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	
	public String getStartingLocation() {
		return startingLocation;
	}

	public void setStartingLocation(String startingLocation) {
		this.startingLocation = startingLocation;
	}
	
	public HashMap <String, Item> getStartingItems() {
		return startingItems;
	}

	public void setStartingItems(HashMap <String, Item> startingItems) {
		this.startingItems = startingItems;
	}
	
	public HashMap <String, Item> getWantedItems() {
		return wantedItems;
	}

	public void setWantedItems(HashMap <String, Item> wantedItems) {
		this.wantedItems = wantedItems;
	}
	
	public ArrayList<String> getSpeech() {
		return speech;
	}

	public void setSpeech(ArrayList<String> speech) {
		this.speech = speech;
	}
}
