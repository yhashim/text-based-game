package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Character {
	private String characterName;
	private String startingLocation;
	private String currentRoom;
	private HashMap <String, Item> startingItems;
	private ArrayList<String> wantedItems;
	private HashMap <String, Item> items;
	private ArrayList<String> functions;
	private ArrayList<String> speech;
	
	// character constructor 
	public Character() {
		items = new HashMap<String, Item>();
		startingItems = new HashMap <String, Item>();
		wantedItems = new ArrayList<String>();
		speech = new ArrayList<String>();
		functions = new ArrayList<String>(); 
	}
	
	public void addToFunctions(String function) {
		functions.add(function);
	}
	
	// adds an item to the inventory
	public void addToInventory(Item item) {
		if (items.containsKey(item.getItemName())) {
			Zork.print("The character already has the item\n", 75);
		}
		else {
			items.put(item.getItemName(), item);
		}
	}
	
	public void removeFromInventory(String item) {
		if (items.containsKey(item)) {
			items.remove(item);
		}
	}
	
	public void addToWantedItems(String x) {
		wantedItems.add(x);	
	}
	
	public void addToSpeech(String x) {
		speech.add(x);	
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
	
	public void setCurrentRoom(String currentRoom) {
		this.currentRoom = currentRoom;
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
	
	public ArrayList<String> getWantedItems() {
		return wantedItems;
	}

	public void setWantedItems(ArrayList<String> wantedItems) {
		this.wantedItems = wantedItems;
	}
	
	public ArrayList<String> getSpeech() {
		return speech;
	}

	public void setSpeech(ArrayList<String> speech) {
		this.speech = speech;
	}
}