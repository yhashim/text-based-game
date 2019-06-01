package com.bayviewglen.zork;

import java.awt.ItemSelectable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	private HashMap<String, Character> masterCharacterMap;
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
				String[] rooms = roomExits.split(": ")[1].split(",");
				HashMap<String, String> temp = new HashMap<String, String>();
				for (String s : rooms) {
					temp.put(s.split("-")[0].trim(), s.split("-")[1]);
				}
				exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ", "_"), temp);
				// Read the Locked
				String isLocked = roomScanner.nextLine();
				room.setLock(isLocked.split(": ")[1].trim().equals("true"));
				// This puts the room we created (Without the exits in the masterMap)
				masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), room);
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

	private void initCharacters(String fileName) throws Exception {
		masterCharacterMap = new HashMap<String, Character>();
		Scanner characterScanner;
		try {
			characterScanner = new Scanner(new File(fileName));
			while (characterScanner.hasNext()) {
				Character character = new Character();
				// Read the Name
				String characterName = characterScanner.nextLine();
				characterName = characterName.split(":")[1].trim();
				character.setCharacterName(characterName);
				// Put in starting location
				String startingLocation = characterScanner.nextLine();
				startingLocation = startingLocation.split(":")[1].trim();
				character.setStartingLocation(startingLocation);
				// Assign its functions
				String[] functions = characterScanner.nextLine().split(":")[1].trim().split(", ");
				for (String x : functions) {
					character.addToFunctions(x);
				}
				// Tell them the items they want
				List<String> wantedItems2 = Arrays.asList(characterScanner.nextLine().split(":")[1].trim().split(", "));
				for (String x : wantedItems2) {
					character.addToWantedItems(x);
					// character.addToWantedItems(Item.getItem(x));
				}
				// Character's speech
				String[] speech = characterScanner.nextLine().split(":")[1].trim().split(", ");
				for (String x : speech) {
					character.addToSpeech(x);
				}
				// This puts the character we created in the masterCharacterMap
				masterCharacterMap.put(characterName.toUpperCase(), character);
			}
			characterScanner.close();
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
				itemName = itemName.split(": ")[1].trim();
				item.setItemName(itemName);
				// Read the Description
				String itemDescription = itemScanner.nextLine();
				itemDescription = itemDescription.split(":")[1].replaceAll("<br>", "\n").trim();
				item.setDescription(itemDescription);
				// Read the Weight
				String itemWeight = itemScanner.nextLine();
				itemWeight = itemWeight.split(":")[1].trim();
				item.setWeight(itemWeight);
				// Read the Functions
				String itemFunctions = itemScanner.nextLine();
				itemFunctions = itemFunctions.substring(itemFunctions.indexOf(": ") + 2);
				String[] items = itemFunctions.split(", ");
				for (String s : items) {
					item.addFunction(s);
					item.setBoolean(s);
				}
				// Read the Starting Room
				String currentRoom = itemScanner.nextLine();
				if (!currentRoom.equals("Starting Room:")) {
					currentRoom = currentRoom.split(": ")[1].trim();
					item.setCurrentRoom(currentRoom);
					Room accessRoom = masterRoomMap.get(currentRoom.toUpperCase().trim().replaceAll(" ", "_"));
					accessRoom.addToInventory(item, 1);
				} else {
					currentRoom = null;
				}
				// else {
					// Read the Starting Character
					String currentCharacter = itemScanner.nextLine();
					if (!currentCharacter.equals("Starting Character:")) {
						currentCharacter = currentCharacter.split(": ")[1].trim();
						item.setCurrentCharacter(currentCharacter);
						currentCharacter = currentCharacter.toUpperCase();
						Character accessCharacter = masterCharacterMap.get(currentCharacter);
						accessCharacter.addToInventory(item);
					} else {
						currentCharacter = null;
					}
				// }
				// This puts the item we created in the masterItemMap
				masterItemMap.put(itemName.toUpperCase(), item);
			}
			itemScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the game and initialize its internal map.
	 */
	public Game() {
		try {
			initRooms("data/Rooms.dat");
			currentRoom = masterRoomMap.get("LIGHT'S_ROOM");
			initCharacters("data/Characters.dat");
			initItems("data/Items.dat");
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
		} else if (commandWord.equals("inventory") || commandWord.equals("i")) {
			Player.displayInventory();
		} else if (commandWord.equals("go") || commandWord.equals("walk") || commandWord.equals("proceed")
				|| commandWord.equals("run")) {
			goRoom(command);
		} else if (commandWord.equals("take") || commandWord.equals("pick up")) {
			take(command);
		} else if (commandWord.equals("open")) {
			open(command);
		} else if (commandWord.equals("give") || commandWord.equals("hand")) {
			give(command);
		} else if (commandWord.equals("unlock")) {
			unlock(command);
		} else if (commandWord.equals("read")) {
			read(command);
		} else if (commandWord.equals("use")) {
			use(command);
		} else if (commandWord.equals("write") || commandWord.equals("kill")) {
			write(command);
		} else if (commandWord.equals("watch")) {
			watch(command);
		} else if (commandWord.equals("drop") || commandWord.equals("put down") || commandWord.equals("leave")) {
			drop(command);
		} else if (commandWord.equals("eat") || commandWord.equals("consume")) {
			eat(command);
		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord()) {
				System.out.println("Quit what?");
			} else {
				return true; // signal that we want to quit
			}
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

	// prints the description of the item
	private void examine(Command command) {
		System.out.println(masterItemMap.get(command.getObject()).examine());
	}

	// check if object is in currentRoom
	// if yes:
	// remove object from room's inventory
	// add object to player inventory
	// else
	// tell player that it is not there
	private void take(Command command) {
		String takeable = command.getObject();
		if (Player.contains(takeable.toUpperCase())) {
			System.out.println("You already have that...");
			return;
		}
		if (currentRoom.contains(masterItemMap.get(takeable.toUpperCase())) && masterItemMap.get(takeable.toUpperCase()).take()) {
			currentRoom.removeItem(takeable, 1);
			Player.addToInventory(masterItemMap.get(takeable.toUpperCase()), 1);
			System.out.println("The " + takeable + " is now yours. Finders keepers!");
		} else {
			System.out.println("Sorry, we can't do that.");
		}

	}

	// check if specified object is in room
	// if yes, check if it is openable
	// check if locked
	// if openable and unlocked, open and print out contents
	// else
	// ask for key
	// else, tell them it is not there
	private void open(Command command) {
		String openable = command.getObject();
		if (currentRoom.contains(masterItemMap.get(openable))) {
			if (masterItemMap.get(openable).open()) {
				if (openable.equals("drawer")) {
					System.out.println("In the drawer you see a small slip of paper");
				}
			} else {
				System.out.println("Please enter the passcode: ");
				// take input
				// if input == passcode for specific door
				// door condition is unlocked
				// else
				// say that's the wrong passcode! come again later
			}
		} else {
			System.out.println("There is no " + openable + " here.");
		}

	}

	// check if item is giveable
	// if yes, give to character they stated
	// remove from player's inventory
	// add to character's inventory
	// else, tell character they probably don't want to give it away
	private void give(Command command) {
		String giveable = command.getObject();
		if (masterItemMap.get(giveable).give()) {
			Character.addToInventory(masterItemMap.get(giveable.toUpperCase()));
			Player.removeItem(giveable, 1);
		} else {
			System.out.println("You wouldn't want to give " + giveable + " away!");
		}
	}

	// check if object is lockable
	// if true, check if key is in inventory
	// unlock
	// if no key
	// ask player to find key
	private void unlock(Command command) {
		String unlockable = command.getObject();
		if (masterItemMap.get(unlockable.toUpperCase()).unlock()) {

		}

	}

	// check if object is readable
	// if true, display text of specified object
	// else, tell player there is nothing to read on ___ object
	private void read(Command command) {
		String readable = command.getObject();
		if (masterItemMap.get(readable.toUpperCase()).read() && currentRoom.contains(masterItemMap.get(readable.toUpperCase()))) {
			if (readable.equals("task force employee list")) {
				System.out.println(
						"List Of Employees: \nTsugami Ohaba \nWatari Tailor \nMello Ryga \nRoger Ruvie \nL Lawliet \nKiyomi Takada \nNate River \nMail Jeevas");
			}
			if (readable.equals("the kira case file")) {
				// list of people you have killed
			}
			if (readable.equals("most wanted file")) {
				System.out.println(
						"This is a long list of names. All of the names have been crossed out except for one: Kiyomi Takada");
			}
			if (readable.equals("newspaper")) {
				System.out.println(
						"On the front of the newpaper is an article: \nNew Mystery Killer Kira \nOver the past few weeks there have been a series of murders that seem to be connected to one person... see inside for full article");
			}
			if (readable.equals("letter")) {
				System.out.println("The front of the letter reads: \nTo: L \nFrom: Naomi Misora");
			}
			if (readable.equals("wanted poster")) {
				System.out.println("!Wanted! \nReiji Namikawa \n Crime: chlid kidnapping");
			}
		} else {
			System.out.println("There is nothing to read on the " + readable + ".");
		}

	}

	// check if object is useable & if object is in inventory
	// check which useable object it is
	// if it is a flashlight, do flashlight thing (make sure that all other
	// parameters such as location are correct, etc.)
	// if... until all the useables are done
	// else, explain it cannot be used
	private void use(Command command) {
		String usable = command.getObject();
		if (masterItemMap.get(usable.toUpperCase()).use() && Player.contains(usable)) {
			if (masterItemMap.get(usable.toUpperCase()).equals("flashlight") && currentRoom.getRoomName().equals("warehouse")) {
				System.out.println(
						"The space in front of you lights up. To the left there are cabinets covered with tarps. In front of you, a desk sits in the middle of the room.");
			}
			if (masterItemMap.get(usable.toUpperCase()).equals("flashlight")) {
				System.out.println("The space in front of you lights up.");
			}
		} else {
			System.out.println("Please specifiy how you would like to use" + usable + ".");
		}
	}

	// check if character is killable
	// if true, remove character from its room
	// remove character from character array
	// +1 to killings
	// else, print - you cannot kill ___!
	private void write(Command command) {
		String killable = command.getCharacter();
		if (!killable.toLowerCase().equals("ryuk")) {
			Player.addKill(killable);
			System.out.println(
					"You let out a maniacal laugh. HAhAHaHA! \r\n");
		} else {
			System.out.println("Ryuk: You abominable human! I thought you were smarter than this! I AM IMMORTAL!");
		}
	}

	// check if object is watchable (basically tv) & in the currentRoom
	// if yes, display text of what you see on tv
	// else, say - you can't watch ___, that would be boring!
	private void watch(Command command) {
		String watchable = command.getObject();
		if (masterItemMap.get(watchable.toUpperCase()).watch() && currentRoom.contains(masterItemMap.get(watchable.toUpperCase()))) {
			// if killings < 5 display "Breaking News! \nSerial killer Arayoshi Hatori has
			// just gone on another murder spree, killing a total of 10 students from the
			// University of Tokyo and professor Miss Amane."
			// if killings >= 5 display "New Mystery Killer Kira - series of murders seem to
			// be connected to one killer"
		} else {
			System.out.println("You can't watch " + watchable + ", that would be boring!");
		}
	}

	// check if object is in player's inventory
	// if yes, remove from inventory
	// add to currentRoom inventory
	// else, state they do not even have this object to put down
	private void drop(Command command) {
		String droppable = command.getObject();
		if (droppable.toLowerCase().equals("deathnote") || droppable.toLowerCase().equals("death note")) {
			System.out.println("You can't drop that.");
		} else if (Player.contains(droppable)) {
			currentRoom.addToInventory(masterItemMap.get(droppable.toUpperCase()), 1);
			Player.removeItem(droppable, 1);
			System.out.println(droppable.toUpperCase().substring(0, 1) + droppable.toLowerCase().substring(1) + " dropped.");
		} else {
			System.out.println("You have no " + droppable + " to drop.");
		}

	}

	// check if object is edible
	// if an apple, state - Ryuk wants that apple!
	// else, remove object from room inventory or personal inventory and say -
	// yummy!
	// else, print - dishonour on you! filthy human - you can't eat a ___!
	private void eat(Command command) {
		String consumable = command.getObject();
		if (masterItemMap.get(consumable.toUpperCase()).eat() && Player.contains(consumable)) {
			if (consumable.equals("apple")) {
				System.out.println("Dont eat that! Ryuk wants that apple!");
			} else {
				Player.removeItem(consumable, 1);
				System.out.println("Crunchity munchity you ate the " + consumable + ".");
			}
		} else {
			System.out.println("Dishonour on you! You filthy human - you can't eat the " + consumable + "!");
		}

	}

}
