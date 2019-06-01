package com.bayviewglen.zork;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Music{
	 
	void playMusic (String musicLocation) {
		
		try {
			
			File musicPath =  new File (musicLocation);
			
			if(musicPath.exists()) {
				
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				// brings the music from the file to eclipse 
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				
				JOptionPane.showMessageDialog(null, "Press OK to stop playing");
			}else {
				System.out.println("Can't find file");
				
			}
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}
}






/*
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.player.Player;

import sun.audio.*;




public class Music {
	
	 // play the MP3 file to the sound card
    public static void play() {
    	String filename = "C:\\Users\\ksharma2\\git\\Zork\\Music";
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



/*
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class Music {
	
	public static void main (String[] args) {
		
		play("C:\\Users\\ksharma2\\git\\Zork\\Music");
	}
	
   public static void play(String filepath) {
	   InputStream music;
	   try {
		   music = new FileInputStream(new File(filepath));
		   AudioStream audios = new AudioStream(music);
		   AudioPlayer.player.start(audios);
	   }
	   catch(Exception e) {
		   JOptionPane.showMessageDialog(null,"Error");
		   
	   }
	     
	   
   }
  
}
*/

