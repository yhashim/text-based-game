package com.bayviewglen.zork;

import java.util.ArrayList;

public class Hints {
	// arrayList to hold hints for when numKilled < 5
	static ArrayList<String> group1 = new ArrayList<String>();
	// arrayList to hold hints for when numKilled >= 5
	static ArrayList<String> group2 = new ArrayList<String>();

	/*
	 * Called when the player gives Ryuk an apple. Gives player a random hint
	 * depending on stage of game. They cannot access all the hints (we do not want
	 * to help the player too much or the game will be boring).
	 */
	public static String getHint() {
		String hint = "";
		if (Player.getNumKilled() < 5) {
			int index = (int) (Math.random() * group1.size());
			hint = group1.get(index);
			group1.remove(group1.get(index));
		} else {
			int index = (int) (Math.random() * group2.size());
			hint = group1.get(index);
			group1.remove(group1.get(index));
		}
		return hint;
	}

	/*
	 * This method is called by the GameClass at the beginning of the game.
	 * Initializes both arrayLists with hints.
	 */
	public static void populate() {
		group1.add("I heard that they're going to broadcast the newest murder spree on TV");
		group1.add("Where are announcements to a population given?");
		group1.add("Parents sometimes hide things from their children.");
		group1.add("Sometimes going for a walk outside can be nice.");
		group1.add("School can be a stressful time. Where do students go to hang out?");

		group2.add("What is black and white and red all over?");
		group2.add("In order for people to know who sent a letter, they will put their name on it.");
		group2.add("The best place to hide something is in plain sight.");
		group2.add(
				"Sometimes you must make sacrifices to get what you want. Sometimes these sacrifices cost the life of those close to you.");
		group2.add("Children are often afraid of the dark. They will often have a source of light in their room.");
	}
}
