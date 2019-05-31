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
	private CommandWords commands; // holds all valid command words!

	public Parser() {
		commands = new CommandWords();
	}

	public Command getCommand() {
		String inputLine = ""; // will hold the full input line...
		ArrayList<String> input = new ArrayList<String>(); // will duplicate input elements after separating them, and
															// then removes irrelevant information

		System.out.print("\n> "); // print prompt
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			inputLine = reader.readLine();
		} catch (java.io.IOException exc) {
			System.out.println("There was an error during reading: " + exc.getMessage());
		}
		String temp = inputLine;
		while (inputLine.length() > 0) {
			if (inputLine.indexOf(" ") > 0) {
				input.add(inputLine.substring(0, inputLine.indexOf(" ")));
			} else {
				input.add(inputLine);
				inputLine = "";
			}
			inputLine = inputLine.substring(inputLine.indexOf(" ") + 1);
			// find way to end with the last word
		}
		for (int i = 0; i < input.size(); i++) {
			if (isBadWord(input.get(i))) {
				input.remove(i);
				i--;
			}
		}
		String word1, word2, word3, word4, word5;
		while (input.size() == 0) {
			// if all the elements from input list are deleted, they were all irrelevant!
			System.out.println("There was an error executing: \"" + temp
					+ "\" because we could not find any useful information in it.");
			// repeat the ask for command process until you get something
			try {
				System.out.print("\n> "); // print prompt
				inputLine = reader.readLine();
			} catch (java.io.IOException exc) {
				System.out.println("There was an error during reading: " + exc.getMessage());
			}
		}
		word1 = input.get(0);
		word2 = null;
		word3 = null;
		word4 = null;
		word5 = null;
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
		// need return statement or else method will crash!
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
		} else {
			return false;
		}
	}

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

	public static boolean stringIsItem(String string) {
		// add names of items here
		String[] gameItems = { "deathnote", "death note", "television", "tv", "computer", "drawer", "front door", "pen",
				"apple", "bag of potato chips", "bag of chips", "potato chips", "chips", "task force employee list",
				"employee list", "flashlight", "stuffed bear", "bear", "kira case file", "kira file",
				"most wanted file", "taxes file", "task force key card", "newspaper", "mr. m's keycard",
				"slip of paper", "paper", "old key", "letter", "task force l's key", "l's key", "wanted poster",
				"poster" };
		string = string.toLowerCase();
		for (int i = 0; i < gameItems.length; i++) {
			if (string.equals(gameItems[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean stringIsCharacter(String string) {
		// add names of characters here
		String[] characterNames = { "Ryuk", "Arayoshi Hatori", "Shingo Mido", "Kiyomi Takada", "Reiji Namikawa",
				"Mashiko Kido", "Takeshi Ooi", "Touta Matsuda", "Raye Penber", "Naomi Misora", "Soichiro Yagami",
				"L Lawliet" };
		string = string.toLowerCase();
		for (int i = 0; i < characterNames.length; i++) {
			if (string.equals(characterNames[i].toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static boolean stringIsCommand(String string) {
		// add commands here
		// separate them on lines based on their function
		String[] commands = { "go", "walk", "proceed", "run", "write", "kill", "use", "read", "take", "pick up",
				"examine", "look", "watch", "unlock", "open", "give", "hand", "drop", "put down", "leave", "quit",
				"finish", "retire", "help", "eat", "consume" };
		string = string.toLowerCase();
		for (int i = 0; i < commands.length; i++) {
			if (string.equals(commands[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean stringIsDirection(String string) {
		// add directions here
		String[] directions = { "north", "east", "south", "west", "down", "up" };
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
