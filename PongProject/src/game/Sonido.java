package game;

import javax.sound.sampled.*;
import java.io.*;


public class Sonido {
    private Clip clip;

    public Sonido(String filePath) {
        try {
            // Cargar un archivo de sonido desde la ruta dada
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            // Reproduce el sonido desde el principio
            clip.stop(); // Detener cualquier reproducción actual
            clip.setFramePosition(0); // Volver al inicio del clip
            clip.start();
        }
    }
}