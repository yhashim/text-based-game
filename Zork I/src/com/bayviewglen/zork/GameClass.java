package com.bayviewglen.zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author: Michael Kolling Version: 1.1 Date: March 2000
 * 
 * This class is the main class of the "Zork" application. Zork is a very
 * simple, text based adventure game. Users can walk around some scenery. That's
 * all. It should really be extended to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * routine.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates the commands that
 * the parser returns.
 */
class Game {
	private Parser parser;
	private Room currentRoom;
	// This is a MASTER object that contains all of the rooms and is easily
	// accessible.
	// The key will be the name of the room -> no spaces (Use all caps and
	// underscore -> Great Room would have a key of GREAT_ROOM
	// In a hashmap keys are case sensitive.
	// masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the Great
	// Room (assuming you have one)
	private HashMap<String, Room> masterRoomMap;
	private HashMap<String, Item> masterItemMap;

	private void initRooms(String fileName) throws Exception {
		masterRoomMap = new HashMap<String, Room>();
		Scanner roomScanner;
		try {
			HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();
			roomScanner = new Scanner(new File(fileName));
			while (roomScanner.hasNext()) {
				Room room = new Room();
				// Read the Name
				String roomName = roomScanner.nextLine();
				room.setRoomName(roomName.split(":")[1].trim());
				// Read the Description
				String roomDescription = roomScanner.nextLine();
				room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());
				// Read the Exits
				String roomExits = roomScanner.nextLine();
				// An array of strings in the format E-RoomName
				String[] rooms = roomExits.split(":")[1].split(",");
				HashMap<String, String> temp = new HashMap<String, String>();
				for (String s : rooms) {
					temp.put(s.split("-")[0].trim(), s.split("-")[1]);
				}
				exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ", "_"), temp);
				// This puts the room we created (Without the exits in the masterMap)
				masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), room);
				// Now we better set the exits.
			}

			for (String key : masterRoomMap.keySet()) {
				Room roomTemp = masterRoomMap.get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room.

					String roomName2 = tempExits.get(s.trim());
					Room exitRoom = masterRoomMap.get(roomName2.toUpperCase().replaceAll(" ", "_"));
					roomTemp.setExit(s.trim().charAt(0), exitRoom);
				}
			}
			roomScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initItems(String fileName) throws Exception {
		masterItemMap = new HashMap<String, Item>();
		Scanner itemScanner;
		try {
			itemScanner = new Scanner(new File(fileName));
			while (itemScanner.hasNext()) {
				Item item = new Item();
				// Read the Name
				String itemName = itemScanner.nextLine();
				item.setItemName(itemName.split(":")[1].trim());
				// Read the Description
				String itemDescription = itemScanner.nextLine();
				item.setDescription(itemDescription.split(":")[1].replaceAll("<br>", "\n").trim());
				// Read the Weight
				String itemWeight = itemScanner.nextLine();
				item.setWeight(itemWeight.split(":")[1].trim());
				// Read the Functions
				String itemFunctions = itemScanner.nextLine();
				itemFunctions = itemFunctions.substring(itemFunctions.indexOf(":") + 1);
				String[] items = itemFunctions.split(",| ");
				for (String s : items) {
					item.addFunction(s);
				}
//				// An array of strings in the format F-ItemName
//				String[] items = itemFunctions.split(":")[1].split(",");
//				HashMap<String, String> temp = new HashMap<String, String>();
//				for (String s : items) {
//					temp.put(s.split("-")[0].trim(), s.split("-")[1]);
//				}
//				functions.put(itemName.substring(10).trim().toUpperCase().replaceAll(" ", "_"), temp);
//				// This puts the item we created (Without the functions in the masterMap)
//				masterItemMap.put(itemName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), item);
//				// Now we better set the functions.
			}
			itemScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		try {
			initRooms("data/Rooms.dat");
			initItems("data/Items.dat");
			currentRoom = masterRoomMap.get("LIGHT'S_ROOM");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser = new Parser();
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();
		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
//		System.out.println();
//		System.out.println("Welcome to Zork!");
//		System.out.println("Zork is a new, incredibly boring adventure game.");
		System.out.println("The game will now commence. Type 'help' if you ever need help!");
		System.out.println();
		System.out.println(currentRoom.longDescription());
	}

	/**
	 * Given a command, process (that is: execute) the command. If this command ends
	 * the game, true is returned, otherwise false is returned.
	 */
	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}
		String commandWord = command.getCommandWord();
		if (command.getDirection() != null) {
			goRoom(command);
			return false;
		}
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("go") || commandWord.equals("walk") || commandWord.equals("proceed")
				|| commandWord.equals("run")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord())
				System.out.println("Quit what?");
			else
				return true; // signal that we want to quit
		} else if (commandWord.equals("eat")) {
			if (command.hasSecondWord()) {
				eat(command);
			}
		}
		return false;
	}

	// implementations of user commands:
	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
		String direction = command.getDirection();
		if (!command.hasSecondWord()) {
			if (direction == null) {
				// we don't know where to go...
				System.out.println("Sorry, I did not understand that. Please try again.");
				return;
			} else {
				Room nextRoom = currentRoom.nextRoom(direction);
				if (nextRoom == null)
					System.out.println("There is no door!");
				else {
					currentRoom = nextRoom;
					System.out.println(currentRoom.longDescription());
				}
				return;
			}
		}
		direction = command.getDirection();
		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);
		if (nextRoom == null)
			System.out.println("There is no door!");
		else {
			currentRoom = nextRoom;
			System.out.println(currentRoom.longDescription());
		}
	}

	private void eat(Command command) {
		// TODO Auto-generated method stub

	}

}
