package com.bayviewglen.zork;

import java.util.ArrayList;

/**
 * Class Command - Part of the "Zork" game.
 * 
 * author: Michael Kolling version: 1.0 date: July 1999
 *
 * This class holds information about a command that was issued by the user. A
 * command currently consists of two strings: a command word and a second word
 * (for example, if the command was "take map", then the two strings obviously
 * are "take" and "map").
 * 
 * The way this is used is: Commands are already checked for being valid command
 * words. If the user entered an invalid command (a word that is not known) then
 * the command word is <null>.
 *
 * If the command had only one word, then the second word is <null>.
 *
 * The second word is not checked at the moment. It can be anything. If this
 * game is extended to deal with items, then the second part of the command
 * should probably be changed to be an item rather than a String.
 */
class Command {
	// variables
	private ArrayList<String> commandWords;
	private String commandWord;
	private String character;
	private String object;
	private String adverb;
	private String direction;
	private String firstWord;

	/**
	 * Create a command object. The command word should be null to indicate that
	 * this was a command that is not recognized by this game
	 */
	public Command(String word1, String word2, String word3, String word4, String word5) {
		commandWords = new ArrayList<String>();
		commandWords.add(word1);
		commandWords.add(word2);
		commandWords.add(word3);
		commandWords.add(word4);
		commandWords.add(word5);
		
		for (String w : commandWords) {
			if (w != null) {
				if (Parser.stringIsCommand(w)) {
					commandWord = w;
				} else if (Parser.stringIsCharacter(w)) {
					character = w;
				} else if (Parser.stringIsItem(w)) {
					object = w;
				} else if (Parser.stringIsAdverb(w)){
					adverb = w;
				} else if (Parser.stringIsDirection(w)) {
					direction = w;
				}
			}
		}
		
		cleanupCommandWords(commandWords);
	}
	
	private void cleanupCommandWords(ArrayList<String> commandWords2) {
		// TODO Auto-generated method stub
		for (int i = 0; i < commandWords.size(); i++) {
			if (commandWords.get(i) == null) {
				commandWords.remove(i);
				i--;
			}
		}
	}

	/**
	 * Return the command word of this command. If the command was not understood,
	 * the result is null.
	 */
	public String getCommandWord() {
		return commandWord;
	}

	/**
	 * Return the command word of this command. If the command was not understood,
	 * the result is null.
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * Return the object of this command. If the object was not understood,
	 * the result is null.
	 */
	public String getObject() {
		return object;
	}

	/**
	 * Return the adjective of how this command should execute. If the command was
	 * not understood, the result is null.
	 */
	public String getAdverb() {
		return adverb;
	}
	
	/**
	 * Return the direction. If the command was
	 * not understood, the result is null.
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * Return true if this command was not understood.
	 */
	public boolean isUnknown() {
		return ((commandWord == null) && (direction == null) && (adverb == null) && (object == null) && (character == null));
	}
	
	public boolean hasSecondWord() {
		String secondWord = commandWords.get(1);
		if (secondWord != null)
			return true;
		else 
			return false;
		//return (commandWords.size()>=2);
	}
	
	public String toString() {
		return commandWord.substring(0,1).toUpperCase() + commandWord.substring(1).toLowerCase();
	}

}
