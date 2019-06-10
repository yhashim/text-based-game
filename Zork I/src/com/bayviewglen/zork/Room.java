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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class Room {
	private String roomName;
	private String description;
	private HashMap<String, Room> exits; // stores exits of this room. // fix
	private HashMap<String, Item> items = new HashMap<String, Item>(); // stores item inventory of room. // fix
	private HashMap<String, Character> characters = new HashMap<String, Character>(); // stores item inventory of room.
																						// // fix
	private ArrayList<Room> rooms = new ArrayList<Room>();

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public HashMap<String, Item> getRoomItems() {
		return items;
	}

	private boolean isLocked;
	private boolean canUnlock;

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
		return "Room: " + roomName + "\n\n" + description + "\n" + getItems() + "\n" + exitString() + "\n"
				+ getCharacters();
	}

	public boolean isLocked() {
		return isLocked;
	}

	public boolean canUnlock(Room nextRoom) {
		if (nextRoom.getRoomName().equals("2nd Floor Hallway") && Player.contains("keycard")) {
			return true;
		} else if (nextRoom.getRoomName().equals("Mr. Matsuda's Office") && Player.contains("mkeycard")) {
			return true;
		} else if (nextRoom.getRoomName().equals("L's Office") && Player.contains("lkey")) {
			return true;
		} else if (nextRoom.getRoomName().equals("Forest Pathway") && Player.contains("oldkey")) {
			return true;
		} else if (nextRoom.getRoomName().equals("Warehouse")) {
			Zork.print("There is a security key pad on the door to the Warehouse.\n", 75);
			BufferedReader keyPadReader = new BufferedReader(new InputStreamReader(System.in));
			String inputPadNum;
			Zork.print("Enter a valid security password to unlock.\n\n> ", 75);
			try {
				inputPadNum = keyPadReader.readLine();
				if (inputPadNum.toLowerCase().equals("death")) {
					Zork.print("The Warehouse door is unlocked.\n", 75);
					return true;
				} else {
					Zork.print("Invalid security password!\n> ", 75);
					return false;
				}
			} catch (java.io.IOException exc) {
				Zork.print("There was an error during reading of Security Password.\n", 75);
			}
		}
		return false;
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

	public boolean getLock() {
		return isLocked;
	}

	public void setLock(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public void addToCharacterList(Character characterItem) {
		characters.put(characterItem.getCharacterName(), characterItem);
	}

	public void addToInventory(Item item, int amount) {
		if (items.containsKey(item.getItemName())) {
			Zork.print("The item already exists in the room\n", 75);
		} else {
			items.put(item.getItemName(), item);
		}
	}

	// checks for an item in the inventory
	// return item if it is, null if not
	/*
	 * public Item getItem(String name) { if (items.containsKey(name)) { return
	 * items.get(name); } return null; }
	 */
	public String getItems() {
		String returnString = "Items:";
		Set keys = items.keySet();
		if (keys.size() == 0)
			returnString += " none";
		int numItems = 0;
		for (Iterator iter = keys.iterator(); iter.hasNext();) {

			if (numItems == 0)
				returnString += " ";
			else
				returnString += ", ";

			// possible function
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

			numItems++;
		}
		return returnString;
	}

	/*
	 * public Character getCurrentCharacters(Room room) { return
	 * characters.get(masterCharacterMap.get(.getStartingLocation().equals(room)));
	 * }
	 */
	public String getCharacters() {
		String returnString = "Characters:";
		Set keys = characters.keySet();
//		getMasterCharacterMap().valueSet();

		if (keys.size() == 0)
			returnString += " none";
		int numCharacters = 0;
		for (Iterator iter = keys.iterator(); iter.hasNext();) {

			if (numCharacters == 0)
				returnString += " ";
			else
				returnString += ", ";

			// possible function

			String roomChar = iter.next().toString();

			if (roomChar.equals("Naomi Misora")) {
				roomChar = "Task Force Lady";
			}

			returnString += roomChar.toUpperCase().substring(0, 1);
			roomChar = roomChar.substring(1);
			while (roomChar.indexOf(" ") > 0) {
				returnString += roomChar.substring(0, roomChar.indexOf(" ") + 1);
				roomChar = roomChar.substring(roomChar.indexOf(" ") + 1);
				returnString += roomChar.toUpperCase().substring(0, 1);
				roomChar = roomChar.substring(1);
			}
			returnString += roomChar;

			numCharacters++;
		}
		return returnString;
	}

	public String getRoomCharacter() {
		String roomChar = "none";
		Set keys = characters.keySet();
		Iterator iter = keys.iterator();
		if (iter.hasNext())
			roomChar = iter.next().toString().toUpperCase();
		return roomChar;
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
		items.remove(name.toLowerCase());
	}
}