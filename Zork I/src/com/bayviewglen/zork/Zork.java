package com.bayviewglen.zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
// https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java

public class Zork {
	private static int lapse = 75;

	public static void main(String[] args) {
		// Tutorial
		preGame();		
		Game game = new Game();
		Music.playMusic("LightTheme.wav");
		game.play();
	}

	private static void preGame() {
		ArrayList<String> preGameText = new ArrayList<String>();
		Scanner stringScanner;
		try {
			stringScanner = new Scanner(new File("data/TutorialText.dat"));
			while (stringScanner.hasNext()) {
				String string = stringScanner.nextLine();
				preGameText.add(string);
			}
			stringScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (String x : preGameText) {
			print(x, lapse);
			print("\r\n", lapse);
			try {
				TimeUnit.MILLISECONDS.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
