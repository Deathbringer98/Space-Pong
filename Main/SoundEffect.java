import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffect {
    Clip clip;

    public SoundEffect(String soundFileName) {
        try {
            // Ensure the URL is correctly formed and the file exists
            URL url = this.getClass().getResource("/sounds/score.wav");
            if (url == null) {
                throw new RuntimeException("Resource not found: score.wav");
            }
            


            // Open an audio input stream from the URL
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file: " + soundFileName);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading audio file: " + soundFileName);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + soundFileName);
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null) {
            System.err.println("Audio clip not initialized");
            return;
        }
        if (clip.isRunning()) {
            clip.stop(); // Stop the player if it is still running
        }
        clip.setFramePosition(0); // rewind to the beginning
        clip.start(); // Start playing
    }
}
