package com.bayviewglen.zork;

import java.awt.ItemSelectable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
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
 * 
 */
class Game {
	private Parser parser;
	private Room currentRoom;
	private Room previousRoom;
	private Character currentCharacter;
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
				//String startingLocation = characterScanner.nextLine();
				//startingLocation = startingLocation.split(":")[1].trim();
				//character.setStartingLocation(startingLocation);
				String currentRoom = characterScanner.nextLine();
				if (!currentRoom.equals("Starting location:")) {
					currentRoom = currentRoom.split(": ")[1].trim();
					character.setCurrentRoom(currentRoom);
					Room accessRoom = masterRoomMap.get(currentRoom.toUpperCase().trim().replaceAll(" ", "_"));
					accessRoom.addToCharacterList(character);
				} else {
					currentRoom = null;
				}
				
				
				
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
				masterItemMap.put(itemName.toLowerCase(), item);
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
			currentCharacter = masterCharacterMap.get("RYUK");
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
		Player.addToInventory(masterItemMap.get("deathnote"), 1);
		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		// System.out.println("Thank you for playing.  Good bye.");
		Zork.print("Thank you for playing. Good bye.", 75);
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
//		System.out.println();
//		System.out.println("Welcome to Zork!");
//		System.out.println("Zork is a new, incredibly boring adventure game.");
		Zork.print("The game will now commence. Type 'help' if you ever need help!\n", 75);
		System.out.println();
		Zork.print(currentRoom.longDescription()+"\n", 75);
	}

	/**
	 * Given a command, process (that is: execute) the command. If this command ends
	 * the game, true is returned, otherwise false is returned.
	 */
	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			Zork.print("I don't know what you mean...\n", 75);
			return false;
		}
		String commandWord = command.getCommandWord();
		if (command.getDirection() != null) {
			goRoom(command);
			return false;
		}
		// testing
		else if (commandWord.equals("teleport")) {
			goToRoom(command);
		}
		//
		else if (commandWord.equals("p")) {
			goPreviousRoom();
		}
		else if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("inventory") || commandWord.equals("i")) {
			Player.displayInventory();
		} else if (commandWord.equals("go") || commandWord.equals("walk") || commandWord.equals("proceed")
				|| commandWord.equals("run")) {
			goRoom(command);
		} else if ((commandWord.equals("take") || commandWord.equals("seize")) && command.getObject() != null) {
			take(command);
		} else if (commandWord.equals("open") && command.getObject() != null) {
			open(command);
		} else if ((commandWord.equals("give") || commandWord.equals("hand")) && command.getObject() != null && command.getCharacter() != null) {
			give(command);
		} else if (commandWord.equals("unlock")) {
			unlock(command);
		} else if (commandWord.equals("read") && command.getObject() != null) {
			read(command);
		} else if (commandWord.equals("use") && command.getObject() !=null) {
			use(command);
		} else if ((commandWord.equals("write") || commandWord.equals("kill")) && (command.getCharacter() != null)) {
			write(command);
		} else if (commandWord.equals("watch") && command.getObject() != null) {
			watch(command);
		} else if ((commandWord.equals("drop") || commandWord.equals("put down") || commandWord.equals("leave")) && command.getObject() != null) {
			drop(command);
		} else if ((commandWord.equals("eat") || commandWord.equals("consume")) && (command.getObject() != null)) {
			eat(command);
		} else if (commandWord.equals("examine") && command.getObject() != null) {
			examine(command);
		} else if (commandWord.equals("quit")) {
			if  (command.hasSecondWord()) {
				Zork.print("If you would like to quit the game, simply type the word \"quit\"\n", 75);
			} else {
				return true; // signal that we want to quit
			}
				
				
//			if (command.hasSecondWord()) {
//				Zork.print("Quit what?\n", 75);
//			} else {
//				return true; // signal that we want to quit
//			}
//		} else if (commandWord.equals("eat")) {
//			if (command.hasSecondWord()) {
//				eat(command);
//			}
		} else {
			Zork.print("I don't know what you mean...\n", 75);
		}
		return false;
	}

	// implementations of user commands:      
	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */
	private void printHelp() {
		Zork.print("Your command words are:\n", 75);
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
				Zork.print("Sorry, I did not understand that. Please try again.\n", 75);
				return;
			} 
			else {
				Room previousRoom = currentRoom;
				Room nextRoom = currentRoom.nextRoom(direction);
				if (nextRoom == null)
					Zork.print("There is no door in that direction!\n", 75);
				else {
					if (Player.getEndGame() && Player.contains("oldkey") && currentRoom.getRoomName().toLowerCase().equals("forest pathway")) {
						Zork.print("You use your oldkey to enter the forest pathway!", 75);
						// goes to forest pathway
						currentRoom = nextRoom;
						Zork.print(currentRoom.longDescription()+"\n", 75);
						Zork.print("Ryuk: To enter the warehouse you must solve this riddle (the passcode is the answer).\nWhat begins and has no end? What is the ending of all that begins?", 75);
						riddleSolve();
						return;
					}
					if (Player.getEndGame() && currentRoom.getRoomName().toLowerCase().equals("mizuki-dori avenue")) {
						// ryuk will give you a key (oldkey)
						//Player.addToInventory(masterCharacterMap.get("ryuk").getItem("oldKey"), 1);
						// ryuk tells you it will give access to forest pathway
						Zork.print("Ryuk: Here is an oldkey - this will give you access to the forest pathway. Good luck!", 75);
					}
					currentRoom = nextRoom;
					String roomCharName = currentRoom.getRoomCharacter();
					if (roomCharName.equals("none")) {
						currentCharacter = null;
					}
					else {
						currentCharacter = masterCharacterMap.get(currentRoom.getRoomCharacter());
					}
					Zork.print(currentRoom.longDescription()+"\n", 75);
				}
				return;
			}
		}
		direction = command.getDirection();
		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);
		if (nextRoom == null)
			Zork.print("There is no door!\n", 75);
		else {
			currentRoom = nextRoom;
			Zork.print(currentRoom.longDescription()+"\n", 75);
		}
	}

	private void riddleSolve() {
		String inputLine = ""; // will hold the full input line...
		Zork.print("\n> ", 75); // print prompt
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			inputLine = reader.readLine();
		} catch (java.io.IOException exc) {
			Zork.print("There was an error during reading: " + exc.getMessage() + "\n", 75);
		}
		String temp = inputLine;
		if (temp.toLowerCase().equals("death")) {
			
		} else {
			
		}
	}

	private void goPreviousRoom() {
		currentRoom = previousRoom; 
	}
	
	// prints the description of the item
	private void examine(Command command) {
		String examinable = command.getObject();
		if ((Player.contains(examinable)) || (currentRoom.contains(masterItemMap.get(examinable)))) {
			String examinabledesc = masterItemMap.get(examinable).examine();
			Zork.print(examinabledesc + "\n", 75);
		} 
		else {
			Zork.print("You can not examine something not in the room or you don't have...\n", 75);
			return;
		}
			
		return;
	}

	// check if object is in currentRoom
	// if yes:
	// remove object from room's inventory
	// add object to player inventory
	// else
	// tell player that it is not there
	private void take(Command command) {
		String takeable = command.getObject();
		if (Player.contains(takeable)) {
			Zork.print("You already have that...\n", 75);
			return;
		}
		if (currentRoom.contains(masterItemMap.get(takeable)) && masterItemMap.get(takeable).take()) {
			//currentRoom.removeItem(takeable, 1);
			if (takeable.equals("flashlight") && (masterCharacterMap.get("SAYU").contains("teddy"))) {
				Player.setSisterMission(true);
			}
			if (takeable.equals("flashlight") && (!Player.getSisterMission())) {
				Zork.print("Sayu: You can't take the flashlight until you find my teddy!\n", 75);
				return;
			}
			currentRoom.removeItem(takeable, 1);
			Player.addToInventory(masterItemMap.get(takeable), 1);
			Zork.print("The " + takeable.toLowerCase() + " is now yours. Finders keepers!\n", 75);
		} else {
			Zork.print("Sorry, we can't do that.\n", 75);
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
					Zork.print("In the drawer you see a small slip of paper.\n", 75);
				}
			} else {
				Zork.print("Please enter the passcode: ", 75);
				// take input
				// if input == passcode for specific door
				// door condition is unlocked
				// else
				// say that's the wrong passcode! come again later
			}
		} else {
			Zork.print("There is no " + openable.toLowerCase() + " here.\n", 75);
		}

	}

	// check if item is giveable
	// if yes, give to character they stated
	// remove from player's inventory
	// add to character's inventory
	// else, tell character they probably don't want to give it away
	private void give(Command command) {
		String giveable = command.getObject();
		 
		if (!Player.contains(giveable)) {
			Zork.print("You can not give away what you don't have...\n", 75);
			return;
		}
		
		if (masterItemMap.get(giveable).give()) {
			boolean wantsItem = false;
			ArrayList<String> listOfWantedItems = currentCharacter.getWantedItems();
			for (int i = 0; i < listOfWantedItems.size(); i++) {
				if (listOfWantedItems.get(i).equals(giveable)) {
					wantsItem = true;
				}
			}
			String recipient = command.getCharacter();
			if (wantsItem) {

				Player.removeItem(giveable, 1);
				currentCharacter.addToInventory(masterItemMap.get(giveable));
				Zork.print("The " + giveable.toLowerCase() + " was given to " + recipient + "!\n", 75);
				
				if (giveable.toLowerCase().equals("flashlight")) {
					Player.setSisterMission(true);
				}
			}
			else {
				Zork.print("Sorry, " + recipient.toUpperCase().substring(0, 1) + recipient.substring(1) + " does not want that item.\n", 75);
			}
		} else {
			Zork.print("Sorry, we can't do that.\n", 75);
		}
		
		
		
		
//		if (masterItemMap.get(giveable).give()) {
//			Character.addToInventory(masterItemMap.get(giveable.toUpperCase()));
//			Player.removeItem(giveable, 1);
//		} else {
//			Zork.print("You wouldn't want to give " + giveable + " away!\n", 75);
//		}
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
		if (masterItemMap.get(readable).read()) {
			if (Player.contains(readable) || currentRoom.contains(masterItemMap.get(readable))) {
				// player has item or it is in the room
				switch(readable) {
					case "deathnote":
						Zork.print("List Of people killed: \n", 75);
						break;
					case "computer1":
						Zork.print("Dad's computer - keep out\n", 75);
						break;
					case "computer2":
						Zork.print("Matsuda's computer - keep out\n", 75);
						break;
					case "computer3":
						Zork.print("Meeting Room computer - keep out\n", 75);
						break;
					case "employeelist":
						Zork.print("List Of Employees: \nTsugami Ohaba \nWatari Tailor \nMello Ryga \nRoger Ruvie \nL Lawliet \nKiyomi Takada \nNate River \nMail Jeevas\n", 75);
						break;
					case "k-file":
						Zork.print("List of characters killed\n", 75);						
						break;
					case "mw-file":
						Zork.print("This is a long list of names. All of the names have been crossed out except for one: Kiyomi Takada\n", 75);
						break;
					case "t-file":
						Zork.print("List of ...\n", 75);						
						break;
					case "newspaper":
						Zork.print("On the front of the newpaper is an article: \nNew Mystery Killer Kira \nOver the past few weeks there have been a series of murders that seem to be connected to one person... see inside for full article\n", 75);						
						break;
					case "letter":
						Zork.print("The front of the letter reads: \nTo: L \nFrom: Naomi Misora\n", 75);						
						break;
					case "wantedposter":
						Zork.print("!Wanted! \nReiji Namikawa \n Crime: chlid kidnapping\n", 75);						
						break;
					default:
						Zork.print("There is nothing to read on the " + readable.toLowerCase() + ".\n", 75);
				}
			}
			else {
				Zork.print("You can not read something not in the room or you don't have...\n", 75);
			}
		}
		else {
			Zork.print("There is nothing to read on the " + readable.toLowerCase() + ".\n", 75);
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
		if (!Player.contains(usable)) {
			Zork.print("You can not use a " + usable.toLowerCase() + " if you don't have one\n", 75);	
		} 
		else
		{
			if (!masterItemMap.get(usable).use()) {
				Zork.print("Please specifiy how you would like to use the " + usable.toLowerCase() + ".\n", 75);
			}
			else
			{
				switch (usable) {
				case "flashlight":
					if (currentRoom.getRoomName().equals("Warehouse")) {
						Zork.print("The space in front of you lights up. To the left there are cabinets covered with tarps. In front of you, a desk sits in the middle of the room.\n", 75);
					}
					else
					{
						Zork.print("The space in front of you lights up.\n", 75);
					}
					break;
				case "keycard":
					Zork.print("The .....\n", 75);
					break;
				case "mkeycard":
					Zork.print("The .....\n", 75);
					break;
				case "oldkey":
					Zork.print("The .....\n", 75);
					break;
				case "lkey":
					Zork.print("The .....\n", 75);
					break;
				default:
					Zork.print("Please specifiy how you would like to use the " + usable.toLowerCase() + ".\n", 75);
				}
			}
		}
	}

	// check if character is killable
	// if true, remove character from its room
	// remove character from character array
	// +1 to killings
	// else, print - you cannot kill ___!
	private void write(Command command) {
		String killable = command.getCharacter();
		if (!Player.contains("pen")) {
			Zork.print("You cannot kill without a pen!\n", 75);
			return;
		}
		if (!killable.toLowerCase().equals("ryuk")) {
			Player.addKill(killable);
			Zork.print(
					"You let out a maniacal laugh. HAhAHaHA! \r\n", 75);
		} else {
			Zork.print("Ryuk: You abominable human! I thought you were smarter than this! I AM IMMORTAL!\n", 75);
		}
		if (Player.getNumKilled() == 10) {
			Player.setEndGame();
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
			Zork.print("You can't watch " + watchable.toLowerCase() + ", that would be boring!\n", 75);
		}
	}

	// check if object is in player's inventory
	// if yes, remove from inventory
	// add to currentRoom inventory
	// else, state they do not even have this object to put down
	private void drop(Command command) {
		String droppable = command.getObject();
		if (droppable.equals("deathnote") || droppable.equals("death note")) {
			Zork.print("You can't drop that.\n", 75);
		} else if (Player.contains(droppable)) {
			currentRoom.addToInventory(masterItemMap.get(droppable), 1);
			Player.removeItem(droppable, 1);
			Zork.print(droppable.toUpperCase().substring(0, 1) + droppable.substring(1) + " dropped.\n", 75);
		} else {
			Zork.print("You have no " + droppable.toLowerCase() + " to drop.\n", 75);
		}

	}

	// check if object is edible
	// if an apple, state - Ryuk wants that apple!
	// else, remove object from room inventory or personal inventory and say -
	// yummy!
	// else, print - dishonour on you! filthy human - you can't eat a ___!
	private void eat(Command command) {
		String consumable = command.getObject();
		if (masterItemMap.get(consumable).eat() && Player.contains(consumable)) {
			if (consumable.equals("mcintosh") || consumable.equals("fuji") || consumable.equals("honeycrisp") || consumable.equals("braeburn")) {
				Zork.print("Dont eat that! Ryuk wants that apple!\n", 75);
			} else {
				Player.removeItem(consumable, 1);
				Zork.print("Crunchity munchity you ate the " + consumable.toLowerCase() + ".\n", 75);
			}
		} else if (masterItemMap.get(consumable).eat()) {
			Zork.print("You dont have " + consumable.toLowerCase() + " to eat...\n", 75);
		} else {
			Zork.print("Dishonour on you! You filthy human - you can't eat the " + consumable.toLowerCase() + "!\n", 75);
		}

	}
	private HashMap <String, Character> getMasterCharacterMap() {
		return masterCharacterMap;
	}

	// teleport testing
	private void goToRoom(Command command) {
		String roomNum = command.getObject();
		if (roomNum != null)
		{
			switch(roomNum) {
			case "1":
				currentRoom = masterRoomMap.get("LIGHT'S_ROOM");
				currentCharacter = masterCharacterMap.get("RYUK");
				break;
			case "2":
				currentRoom = masterRoomMap.get("HALLWAY");
				currentCharacter = null;
				break;
			case "3":
				currentRoom = masterRoomMap.get("PARENT'S_ROOM");
				currentCharacter = null;
				break;
			case "4":
				currentRoom = masterRoomMap.get("FATHER'S_OFFICE");
				currentCharacter = null;
				break;
			case "5":
				currentRoom = masterRoomMap.get("SISTER'S_ROOM");
				currentCharacter = masterCharacterMap.get("SAYU");
				break;
			case "6":
				currentRoom = masterRoomMap.get("FOYER");
				currentCharacter = null;
				break;
			case "7":
				currentRoom = masterRoomMap.get("KITCHEN");
				currentCharacter = null;
				break;
			case "8":
				currentRoom = masterRoomMap.get("DINING_ROOM");
				currentCharacter = null;
				break;
			case "9":
				currentRoom = masterRoomMap.get("LIVING_ROOM");
				currentCharacter = null;
				break;
			case "10":
				currentRoom = masterRoomMap.get("BACK_YARD");
				currentCharacter = null;
				break;
			case "11":
				currentRoom = masterRoomMap.get("FRONT_YARD");
				currentCharacter = null;
				break;
			case "12":
				currentRoom = masterRoomMap.get("PAC_STREET_(EAST)");
				currentCharacter = null;
				break;
			case "13":
				currentRoom = masterRoomMap.get("PAC_STREET_(WEST)");
				currentCharacter = null;
				break;
			case "14":
				currentRoom = masterRoomMap.get("ANTEIKU_CAFE");
				currentCharacter = null;
				break;
			case "15":
				currentRoom = masterRoomMap.get("GREEN_ODORI_STREET");
				currentCharacter = null;
				break;
			case "16":
				currentRoom = masterRoomMap.get("FRONT_OF_SCHOOL");
				currentCharacter = null;
				break;
			case "17":
				currentRoom = masterRoomMap.get("SIDE_OF_SCHOOL");
				currentCharacter = null;
				break;
			case "18":
				currentRoom = masterRoomMap.get("MEIJI_DORI_AVENUE");
				currentCharacter = null;
				break;
			case "19":
				currentRoom = masterRoomMap.get("MIZUKI_DORI_AVENUE");
				currentCharacter = null;
				break;
			case "20":
				currentRoom = masterRoomMap.get("MAIN_SQUARE");
				currentCharacter = null;
				break;
			case "21":
				currentRoom = masterRoomMap.get("FOREST_PATHWAY");
				currentCharacter = null;
				break;
			case "22":
				currentRoom = masterRoomMap.get("FRONT_OF_WAREHOUSE");
				currentCharacter = null;
				break;
			case "23":
				currentRoom = masterRoomMap.get("WAREHOUSE");
				currentCharacter = null;
				break;
			case "24":
				currentRoom = masterRoomMap.get("LOBBY");
				currentCharacter = null;
				break;
			case "25":
				currentRoom = masterRoomMap.get("2ND_FLOOR_HALLWAY");
				currentCharacter = null;
				break;
			case "26":
				currentRoom = masterRoomMap.get("MEETING_ROOM_TWO");
				currentCharacter = null;
				break;
			case "27":
				currentRoom = masterRoomMap.get("2ND_FLOOR_HALLWAY_(NORTH)");
				currentCharacter = null;
				break;
			case "28":
				currentRoom = masterRoomMap.get("MEETING_ROOM_ONE");
				currentCharacter = null;
				break;
			case "29":
				currentRoom = masterRoomMap.get("2ND_FLOOR_HALLWAY_(SOUTH)");
				currentCharacter = null;
				break;
			case "30":
				currentRoom = masterRoomMap.get("MEETING_ROOM_THREE");
				currentCharacter = null;
				break;
			case "31":
				currentRoom = masterRoomMap.get("3RD_FLOOR_HALLWAY");
				currentCharacter = null;
				break;
			case "32":
				currentRoom = masterRoomMap.get("LARGE_MEETING_ROOM");
				currentCharacter = masterCharacterMap.get("NAOMI_MISORA");
				break;
			case "33":
				currentRoom = masterRoomMap.get("MR._MATSUDA'S_OFFICE");
				currentCharacter = null;
				break;
			case "34":
				currentRoom = masterRoomMap.get("MR._YAGAMI'S_OFFICE");
				currentCharacter = masterCharacterMap.get("SOICHIRO_YAGAMI");
				break;
			case "35":
				currentRoom = masterRoomMap.get("4TH_FLOOR_HALLWAY");
				currentCharacter = null;
				break;
			case "36":
				currentRoom = masterRoomMap.get("KIRA_INVESTIGATION_ROOM");
				currentCharacter = null;
				break;
			case "37":
				currentRoom = masterRoomMap.get("L'S_OFFICE");
				currentCharacter = null;
				break;
			default:
				currentRoom = masterRoomMap.get("LIGHT'S_ROOM");
				currentCharacter = null;
				break;
			}
		}
		else {
			currentRoom = masterRoomMap.get("LIGHT'S_ROOM");
		}
		Zork.print(currentRoom.longDescription()+"\n", 75);
	}
	
}