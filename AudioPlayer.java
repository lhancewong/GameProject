/**
	@author Wilbert Meinard L. Chen (201153)
    @author Lhance Christian S. Wong (205467)
	@version May 31, 2021
**/

/*
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
*/

import javax.sound.sampled.*;
import java.io.*;

/**
 * This class is in charge of playing audio.
 */
public class AudioPlayer {
    private File audioFile;
    private Clip bossClip;

    public AudioPlayer() {}

    /**
     * Plays a wav file using the pathfile
     * 
     * @param filePath the path of the wav file
     * @param isMaster true if the one playing the sound is the server, false if otherwise
     */
    public void playSound(String filePath, boolean isMaster) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                if(!isMaster) {
                    try {
                        audioFile = new File(filePath);
                        if(filePath == "sounds/bossmusic.wav") {
                            AudioInputStream bossAudio = AudioSystem.getAudioInputStream(audioFile);
                            bossClip = AudioSystem.getClip();
                            bossClip.open(bossAudio);
                            bossClip.start();
                        } else {
                            AudioInputStream audio = AudioSystem.getAudioInputStream(audioFile);
                            Clip clip = AudioSystem.getClip();
                            clip.open(audio);
                            clip.start();
                        }
                    } catch(Exception e) {}
                } 
            }
        }; thread.start();
    }

    /**
     * Stops the boss music.
     */
    public void stopBossMusic() {
        bossClip.stop();
    }
}
