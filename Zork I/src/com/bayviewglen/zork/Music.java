package com.bayviewglen.zork;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.Player;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;




public class Music {
	
	 // play the MP3 file to the sound card
   public static void play() {
   	String filename = "test/Arthur.mp3";
   	try {
       	
           FileInputStream fis     = new FileInputStream(filename);
           BufferedInputStream bis = new BufferedInputStream(fis);
           Player player = new Player(bis);
           player.play();
       }
       catch (Exception e) {
           System.out.println("Problem playing file " + filename);
           System.out.println(e);
       }
   }

	public static void main(String[] args) {
		
		play();

	}


}
