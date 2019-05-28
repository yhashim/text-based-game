package com.bayviewglen.zork;

import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;

class Character {
	private String characterName;
	private String startingLocation;
	private String startingItems;
	private String wantedItems;
	private String speech;
	private ArrayList<String> functions; // stores functions you can do to this character.
	
	
	/**
	 * Return what the character is saying within ""
	 */
	
	public String speech() {
		return speech;
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
	
	public String getStartingItems() {
		return startingItems;
	}

	public void setStartingItems(String startingItems) {
		this.startingItems = startingItems;
	}
	
	public String getWantedItems() {
		return wantedItems;
	}

	public void setWantedItems(String wantedItems) {
		this.wantedItems = wantedItems;
	}
	
	public String getSpeech() {
		return speech;
	}

	public void setSpeech(String speech) {
		this.speech = speech;
	}
}
