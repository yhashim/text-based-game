package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Character {
	// instance variables
	// hold important info we receive from the character.dat file during initialization
	private String characterName;
	private String startingLocation;
	private String currentRoom;
	private HashMap<String, Item> startingItems;
	private ArrayList<String> wantedItems;
	private HashMap<String, Item> items;
	private ArrayList<String> functions;
	private ArrayList<String> speech;

	// character constructor
	public Character() {
		items = new HashMap<String, Item>();
		// each character has an item hashmap for all the items they have at any point of time
		startingItems = new HashMap<String, Item>();
		// this hashmap holds the items the character starts off with (we get this info from the .dat file)
		wantedItems = new ArrayList<String>();
		// this hashmap holds the items the character can accept from the player
		speech = new ArrayList<String>();
		// holds all character's speech from .dat file
		functions = new ArrayList<String>();
		// holds functions the player can do to the character (read from .dat file)
	}
	
	// adds functions we read from the .dat file to this character's functions arrayList
	public void addToFunctions(String function) {
		functions.add(function);
	}

	// adds an item to the inventory
	public void addToInventory(Item item) {
		if (items.containsKey(item.getItemName())) {
			Zork.print("The character already has the item\n", 75);
		} else {
			items.put(item.getItemName(), item);
		}
	}
	
	// removes an item from the inventory
	public void removeFromInventory(String item) {
		if (items.containsKey(item)) {
			items.remove(item);
		}
	}
	
	// adds an item we read from the .dat file to this character's wantedItems arrayList
	public void addToWantedItems(String x) {
		wantedItems.add(x);
	}
	
	// adds a string we read from the .dat file to this character's speech arrayList
	public void addToSpeech(String x) {
		speech.add(x);
	}

	// checks for an item in the inventory
	// return true if it is, false if not
	public boolean contains(String name) {
		if (items.containsKey(name)) {
			return true;
		}
		return false;
	}
	
	// getter (speech arrayList)
	public ArrayList<String> speech() {
		return speech;
	}
	
	// current room setter
	public void setCurrentRoom(String currentRoom) {
		this.currentRoom = currentRoom;
	}
	
	// name getter
	public String getCharacterName() {
		return characterName;
	}
	
	// name setter
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	// location getter
	public String getStartingLocation() {
		return startingLocation;
	}
	
	// location setter
	public void setStartingLocation(String startingLocation) {
		this.startingLocation = startingLocation;
	}
	
	// items getter
	public HashMap<String, Item> getStartingItems() {
		return startingItems;
	}

	// items setter
	public void setStartingItems(HashMap<String, Item> startingItems) {
		this.startingItems = startingItems;
	}
	
	// wanted items getter
	public ArrayList<String> getWantedItems() {
		return wantedItems;
	}

	// wanted items setter
	public void setWantedItems(ArrayList<String> wantedItems) {
		this.wantedItems = wantedItems;
	}

	// speech getter
	public ArrayList<String> getSpeech() {
		return speech;
	}

	// speech setter
	public void setSpeech(ArrayList<String> speech) {
		this.speech = speech;
	}
}