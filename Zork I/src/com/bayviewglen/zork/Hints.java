package com.bayviewglen.zork;

public class Hints {
	public String getHint() {
		String hint = "";
		if (Player.getNumKilled() < 5) {
			// hint = get random hint from group 1
		} else {
			// hint = get random hint from group 2
		}
		return hint;
	}
}
