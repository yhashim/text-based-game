package com.bayviewglen.zork;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
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
				//clip.start();
				//clip.loop(Clip.LOOP_CONTINUOUSLY);
				
				//JOptionPane.showMessageDialog(null, "Press OK to stop playing");
			}else {
				System.out.println("Can't find file");
				
			}
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
		}
		
	}
	public void play(DataLine clip){ //plays the music without looping
        clip.start();
    }
    public void loop(DataLine clip){ //loops the music
        ((Clip) clip).loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void stop(DataLine clip){ //stops the music 
        clip.close();
    }
   
	
}



