package com.bayviewglen.zork;

import java.util.concurrent.TimeUnit;
// https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java

public class Zork {
	private static int lapse = 75;

	public static void main(String[] args) {
		// Tutorial
		//preGame();
		// Type 'help' if you need help.
		Game game = new Game();
		game.play();
	}

	private static void preGame() {
		print("Welcome to Death Note!\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You are sitting in the classroom. There is a large window to your right.\r\nThe teacher is writing something on the chalkboard. \r\n",
				lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("Glancing outside the window, you can see the side of the school.\r\nA black blur suddenly falls from the sky.\r\n",
				lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("The bell rings and you exit the classroom. \r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("...\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You are standing at the front of the school. To the west is the side of the school. Green Odori St. is south. \r\n",
				lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You walk west, towards the field where you saw the black blur fall. \r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("...\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You are standing in a field of grass at the side of the school.\r\nThere is an apple tree in the center of the field. A mysterious black object lays next to it. \r\n",
				lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You walk towards the tree. Upon closer look, you are able to see that the object is a black notebook titled \"Death Note\".\r\n",
				lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You pick up the book.\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("...\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("It begins to rain. You decide to return home.\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("...\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You are inside your bedroom. Perhaps you should open the book to see what's inside…\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("The first page reads: \r\n" + "	Death Note: How To Use It\r\n"
				+ "	- the human whose name in written in this note shall die \r\n"
				+ "	- if the cause of death is not specified the person will simply die of a heart attack\r\n"
				+ "	- the human who uses this note can neither go to Heaven nor Hell\r\n"
				+ "	- this note shall become property of the human world once it touches the ground of (arrives in) the human world\r\n"
				+ "", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("Thunder and lightning interrupts your thoughts. You hear rustling behind you.\r\n", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("You turn around. A large dark figure looms over you, smiling sinisterly.\r\nIts red eyes appear to glow in the dark startling you, causing you to fall over.\r\n",
				lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("...\r\n\"No reason to be surprised. I am the Shinigami Ryuk. That used to be my notebook. \r\nAs you've probably noticed already, this is no ordinary notebook.\r\n"
				+ "As the notebook has found its way into your hands, you must take on my role. Test it out. \r\nUsing this notebook, you can eliminate evil and bring justice to the world. \r\nYour first task is to use the Death Note to kill 5 people.\r\n"
				+ "Report back to me when you have finished :) I'll be waiting right here\"\r\n" + "", lapse);
		try {
			TimeUnit.MILLISECONDS.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("\r\n3... 2... 1... Game start!\r\n\r\n", lapse);
	}

	public static void print(String temp, int lapse) {
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
