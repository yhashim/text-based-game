package com.bayviewglen.zork;

/*
 * Author:  Michael Kolling
 * Version: 1.0
 * Date:    July 1999
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * This parser reads user input and tries to interpret it as a "Zork"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Parser {
	// instance variable
	private CommandWords commands; // holds all valid command words!

	public Parser() {
		// how we set up the parser
		commands = new CommandWords();
	} 

	public Command getCommand() {
		String inputLine = ""; // will hold the full input line...
		ArrayList<String> input = new ArrayList<String>(); // will duplicate input elements after separating them, and
															// then removes irrelevant information

		Zork.print("\n> ", 75); // print prompt
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			inputLine = reader.readLine(); // takes in the user's input through the reader
		} catch (java.io.IOException exc) {
			Zork.print("There was an error during reading: " + exc.getMessage() + "\n", 75);
		}
		String temp = inputLine;
		while (inputLine.length() > 0) { 
			// we only look at the inputLine if it has info, otherwise we would get nullPointers
			if (inputLine.indexOf(" ") > 0) {
				input.add(inputLine.toLowerCase().substring(0, inputLine.indexOf(" ")));
				// separates by spacing
			} else {
				input.add(inputLine.toLowerCase());
				inputLine = "";
			}
			inputLine = inputLine.substring(inputLine.indexOf(" ") + 1);
			// find way to end with the last word
		}
		
		// if first input word is 'quit', no need to remove BadWords
		String firstInput = input.get(0);
		if (!firstInput.equals("quit")) {
			for (int i = 0; i < input.size(); i++) {
				if (isBadWord(input.get(i))) {
					input.remove(i);
					i--;
				}
			}
		}
		
		// there are only 5 types of words we need from the player
		// this is the info we need to do things
			// character (for interaction)
			// item (to use or interact)
			// command (to do something)
			// location (to go somewhere)
			// adjective (to make the game seem more interactive - the details you want matter kind of thing)
		String word1, word2, word3, word4, word5;
		while (input.size() == 0) {
			// if all the elements from input list are deleted, they were all irrelevant!
			Zork.print("There was an error executing: \"" + temp
					+ "\" because we could not find any useful information in it.\n", 75);
			// repeat the ask for command process until you get something
			try {
				Zork.print("\n> ", 75); // print prompt
				inputLine = reader.readLine();
				temp = inputLine;
				while (inputLine.length() > 0) {
					if (inputLine.indexOf(" ") > 0) {
						input.add(inputLine.toLowerCase().substring(0, inputLine.indexOf(" ")));
					} else {
						input.add(inputLine.toLowerCase());
						inputLine = "";
					}
					inputLine = inputLine.substring(inputLine.indexOf(" ") + 1);
					// find way to end with the last word
				}
				
				// if first input word is 'quit', no need to remove BadWords
				firstInput = input.get(0);
				if (!firstInput.equals("quit")) {
					for (int i = 0; i < input.size(); i++) {
						if (isBadWord(input.get(i))) {
							input.remove(i);
							i--;
						}
					}
				}	
				
			} catch (java.io.IOException exc) {
				Zork.print("There was an error during reading: " + exc.getMessage() + "\n", 75);
			}
		}
		// set the words with the information that remains
		word1 = input.get(0);
		word2 = null;
		word3 = null;
		word4 = null;
		word5 = null;
		// use if statements to ensure we do not throw any null pointers
		if (input.size() > 1) {
			word2 = input.get(1);
		}
		if (input.size() > 2) {
			word3 = input.get(2);
		}
		if (input.size() > 3) {
			word4 = input.get(3);
		}
		if (input.size() > 4) {
			word5 = input.get(4);
		}
		return new Command(word1, word2, word3, word4, word5);
	}

	/*
	 * Return true if a word in a player's input is irrelevant connector words such
	 * as "to" and "the" are irrelevant if a word is not an object that is important
	 * in the game it is irrelevant the important parts include: command, character,
	 * item
	 */
	private static boolean isBadWord(String string) {
		if (!stringIsCommand(string) && !stringIsCharacter(string) && !stringIsItem(string) && !stringIsAdverb(string)
				&& !stringIsDirection(string)) {
			return true;
			// if the word we have is not anything relevant, we don't need to consider it
		} else {
			return false;
		}
	}
	
	/*
	 * Return true if a word in a player's input is an adverb
	 */
	public static boolean stringIsAdverb(String string) {
		// a word is an adverb if it ends with "ly"
		string = string.toLowerCase();
		if (string.length() > 2) {
			if (string.substring(string.length() - 1).equals("ly")) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Return true if a word in a player's input is an item
	 */
	public static boolean stringIsItem(String string) {
		// all item names
		String[] gameItems = { "deathnote", "death note", "television1", "television2", 
				"computer1", "computer2", "computer3", "pen", 
				"mcintosh", "braeburn", "honeycrisp", "fuji", "chips", "employeelist",
				"flashlight", "teddy", "mw-file", "taxesfile", "keycard", "newspaper",
				"mkeycard", "paper", "oldkey", "letter", "lkey", "wantedposter"
				// testing by Teleportation
				,"1","2","3","4","5","6","7","8","9","10"
				,"11","12","13","14","15","16","17","18","19","20"
				,"21","22","23","24","25","26","27","28","29","30"
				,"31","32","33","34","35","36","37"
				};

		string = string.toLowerCase();
		for (int i = 0; i < gameItems.length; i++) {
			if (string.equals(gameItems[i])) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Return true if a word in a player's input is a character name
	 */
	public static boolean stringIsCharacter(String string) {
		// add names of characters here
		String[] characterNames = { "Ryuk", "Arayoshi_Hatori", "Shingo_Mido", "Kiyomi_Takada", "Reiji_Namikawa",
				"Masahiko_Kida", "Sayu", "Takeshi_Ooi", "Touta_Matsuda", "Raye_Penber", "Naomi_Misora", "Soichiro_Yagami",
				"L_Lawliet" };
		string = string.toLowerCase();
		for (int i = 0; i < characterNames.length; i++) {
			if (string.equals(characterNames[i].toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Return true if a word in a player's input is a valid command
	 */
	public static boolean stringIsCommand(String string) {
		// all valid commands
		String[] commands = { "listen", "write", "kill", "use", "read", "take", "seize",
				"examine", "look", "watch", "unlock", "open", "give", "hand", "drop", "put down", "leave", "quit",
				"finish", "retire", "help", "eat", "consume", "inventory" , "i", "teleport" };
		string = string.toLowerCase();
		for (int i = 0; i < commands.length; i++) {
			if (string.equals(commands[i])) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Return true if a word in a player's input is a valid direction
	 */
	public static boolean stringIsDirection(String string) {
		// all valid directions
		String[] directions = { "north", "east", "south", "west", "down", "up"};
		string = string.toLowerCase();
		for (int i = 0; i < directions.length; i++) {
			if (string.equals(directions[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Print out a list of valid command words.
	 */
	public void showCommands() {
		commands.showAll();
	}
}
