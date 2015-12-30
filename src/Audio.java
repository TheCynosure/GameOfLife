import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by john_bachman on 12/16/15.
 */
public class Audio extends Thread implements Runnable {
    public void run() {
        try {
            InputStream in = new FileInputStream(new File("Song.wav"));
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
