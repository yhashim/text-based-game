package com.bayviewglen.zork;

/*
 * Class Room - a room in an adventure game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    August 2000
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Room {
	private String roomName;
	private String description;
	private HashMap<String, Room> exits; // stores exits of this room. // fix
	private HashMap <String, Item> items = new HashMap <String, Item>(); // stores item inventory of room. // fix
	private ArrayList<Room> rooms =  new ArrayList<Room>();
	
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	
	public HashMap <String, Item> getRoomItems(){
		return items;
	}

	private boolean isLocked;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 */
	public Room(String description) {
		this.description = description;
		exits = new HashMap<String, Room>();
		rooms.add(this);
	}

	public Room() {
		// default constructor.
		roomName = "DEFAULT ROOM";
		description = "DEFAULT DESCRIPTION";
		exits = new HashMap<String, Room>();
	}

	public void setExit(char direction, Room r) throws Exception {
		String dir = "";
		switch (direction) {
		case 'E':
			dir = "east";
			break;
		case 'W':
			dir = "west";
			break;
		case 'S':
			dir = "south";
			break;
		case 'N':
			dir = "north";
			break;
		case 'U':
			dir = "up";
			break;
		case 'D':
			dir = "down";
			break;
		default:
			throw new Exception("Invalid Direction");

		}

		exits.put(dir, r);
	}

	/**
	 * Define the exits of this room. Every direction either leads to another room
	 * or is null (no exit there).
	 */
	public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) {
		if (north != null)
			exits.put("north", north);
		if (east != null)
			exits.put("east", east);
		if (south != null)
			exits.put("south", south);
		if (west != null)
			exits.put("west", west);
		if (up != null)
			exits.put("up", up);
		if (down != null)
			exits.put("down", down);

	}

	/**
	 * Return the description of the room (the one that was defined in the
	 * constructor).
	 */
	public String shortDescription() {
		return "Room: " + roomName + "\n\n" + description;
	}

	/**
	 * Return a long description of this room, on the form: You are in the kitchen.
	 * Exits: north west
	 */
	public String longDescription() {

		return "Room: " + roomName + "\n\n" + description + "\n" + getItems() + "\n" + getCharacters() + "\n" + exitString();
	}

	/**
	 * Return a string describing the room's exits, for example "Exits: north west
	 * ".
	 */
	private String exitString() {
		String returnString = "Exits:";
		Set keys = exits.keySet();
		for (Iterator iter = keys.iterator(); iter.hasNext();)
			returnString += " " + iter.next();
		return returnString;
	}

	/**
	 * Return the room that is reached if we go from this room in direction
	 * "direction". If there is no room in that direction, return null.
	 */
	public Room nextRoom(String direction) {
		return (Room) exits.get(direction);
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getLock () {
		return isLocked;
	}
	
	public void setLock (Boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public void addToInventory(Item item, int amount) {
		if (items.containsKey(item.getItemName())) {
			items.get(item.getItemName()).addAmount(amount);
		}
		else {
			items.put(item.getItemName(), item);
		}
	}
	
	// checks for an item in the inventory 
	// return item if it is, null if not
	/*
	public Item getItem(String name) {
		if (items.containsKey(name)) {
			return items.get(name);
		}
		return null;
	}
	*/
	public String getItems() {
		String returnString = "Items:";
		Set keys = items.keySet();
		if (keys.size() == 0)
			returnString += " none";
		int numItems = 0;
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			if (numItems ==0)
				returnString += " " + iter.next();
			else
				returnString += ", " + iter.next();
			numItems ++;
		}
		return returnString;
	}
	
	public String getCharacters() {
		String returnString = "Characters:";
		Set values = Game.getMasterCharacterMap().valueSet();
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			Character n = (Character)iter;
			if (n.getStartingLocation().equals(roomName))
				returnString += " " + iter.next();
		}
		return returnString;
	}
	// checks for an item in the inventory 
		// return true if it is, false if not
		public boolean contains(Item item) {
			if (items.containsValue(item)) {
				return true;
			}	
			
			return false;
		}
	
	public void removeItem(String name, int amount) {
		//if (items.get(name).getAmount() == 1) {
			items.remove(name);
		//}
		//else {
		//	items.get(name).setAmount(items.get(name).getAmount()-1); 
		//}
	}
	
	
}