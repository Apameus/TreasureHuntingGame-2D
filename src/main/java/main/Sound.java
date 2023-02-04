package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("/sounds/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/sounds/coin.wav");
        soundURL[2] = getClass().getResource("/sounds/fanfare.wav");
        soundURL[3] = getClass().getResource("/sounds/powerup.wav");
        soundURL[4] = getClass().getResource("/sounds/unlock.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioIS);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void play(){
        clip.start();
    }

    public void stop(){
        clip.stop();
    }
}
