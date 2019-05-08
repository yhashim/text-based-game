package com.bayviewglen.zork;

import java.util.concurrent.TimeUnit;
// https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java

public class Zork {
	private static int lapse = 75;

	public static void main(String[] args) {
		// Tutorial
		print("Welcome to Death Note!\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You are sitting in the classroom. There is a large window to your right.\r\nThe teacher is writing something on the chalkboard. \r\n" , lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("Glancing outside the window, you can see the side of the school.\r\nA black blur suddenly falls from the sky.\r\n" , lapse);

		// "Type 'help' if you need help.");
		// Game game = new Game();
		// game.play();
	}

	private static void print(String temp, int lapse) {
		for (int i = 0; i < temp.length(); i++) {
			try {
				TimeUnit.MILLISECONDS.sleep(lapse);
				System.out.print(temp.charAt(i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
