import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffect {
    Clip clip;

    public SoundEffect(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR
            URL url = this.getClass().getResource("/sounds/" + soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip == null) return;
        // Stop the player if it is still running
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0); // rewind to the beginning
        clip.start();     // Start playing
    }
}
