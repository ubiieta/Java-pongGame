package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;

public class PongJuego extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
    static final int ANCHO = 640, ALTO = 480;
    private Pelota pelota;
    private Pala palaIzquierda;
    private Pala palaDerecha;
    private int puntosIzquierda;
    private int puntosDerecha;
  
    private Font fuentePixelada;

    private static Sonido sonidoRebotar = new Sonido("plop.wav");
    private static Sonido sonidoGolpear = new Sonido("pongBlip.wav");
    private static Sonido sonidoMarcar = new Sonido("beeep.wav");

    // Variables de estado de teclas
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public PongJuego() {
        pelota = new Pelota(320, 220, 1, 1, 6, 10);
        palaIzquierda = new Pala(20, 200, 75, 3);
        palaDerecha = new Pala(610, 200, 75, 3);

        setFocusable(true);
        addKeyListener(this); // Añade el KeyListener al panel
        requestFocusInWindow(); // Solicita el foco para el panel

        // Cargar la fuente 8 bit
        try {
            fuentePixelada = Font.createFont(Font.TRUETYPE_FONT, new File("pixel.TTF"));
            fuentePixelada = fuentePixelada.deriveFont(48f); // Tamaño de fuente 48
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fuentePixelada = new Font("Monospaced", Font.BOLD, 48); // Fuente alternativa en caso de error
        }

        // Temporizador para actualizar la lógica del juego
        Timer timer = new Timer(10, e -> {
            logicaJuego();
            repaint();
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dibujar el fondo
        g2d.setColor(new Color(0x1B2227));
        g2d.fillRect(0, 0, ANCHO, ALTO);

        // Crear un trazo discontinuo
        float[] dashPattern = {13, 13};
        Stroke dashedStroke = new BasicStroke(6, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0);

        // Establecer el trazo para la línea discontinua
        g2d.setStroke(dashedStroke);

        // Establecer el color para la línea discontinua
        g2d.setColor(new Color(0xfffff2));

        // Dibujar la línea discontinua
        Line2D line = new Line2D.Float(ANCHO/2, 0, ANCHO/2, 480);
        g2d.draw(line);

        // Dibujar los elementos
       
        
        palaDerecha.paint(g);
        palaIzquierda.paint(g);

        // Dibujar el puntaje
        g2d.setFont(fuentePixelada); // Usar la fuente pixelada
        g2d.setColor(new Color(0xfffff2));
        g2d.drawString(String.valueOf(puntosIzquierda), 140, 80);
        g2d.drawString(String.valueOf(puntosDerecha),   460 , 80);
        pelota.paint(g);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            // Movimiento de la pala izquierda con W y S
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;

            // Movimiento de la pala derecha con flechas arriba y abajo
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            // Movimiento de la pala izquierda con W y S
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;

            // Movimiento de la pala derecha con flechas arriba y abajo
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No se utiliza
    }

    public static void sonidoRebotar() {
        sonidoRebotar.play();
    }

    public static void sonidoGolpear() {
        sonidoGolpear.play();
    }

    public static void sonidoMarcar() {
        sonidoMarcar.play();
    }
    
    public static int generateRandomNumberExcludingZero(int min, int max) {
        // Verifica que el rango es válido
        if (min >= max || min == 0 || max == 0) {
            throw new IllegalArgumentException("Rango inválido. Asegúrate de que min < max y ambos no sean cero.");
        }

        // Ajusta el rango para evitar el cero
        int range = max - min;
        int random = (int) (Math.random() * (range - 1)) + min;

        // Ajusta el número para excluir el cero
        if (random >= 0) {
            return random + (random == 0 ? 1 : 0); // Evita el cero
        } else {
            return random - (random == 0 ? -1 : 0); // Evita el cero
        }
    }
    public void logicaJuego() {
        pelota.moverPelota();
        pelota.rebotar(palaDerecha, palaIzquierda);

        // Mover pala izquierda
        if (wPressed) {
            palaIzquierda.setY(Math.max(0, palaIzquierda.getY() - palaIzquierda.getSpeed()));
        }
        if (sPressed) {
            palaIzquierda.setY(Math.min(ALTO - palaIzquierda.getHeight(), palaIzquierda.getY() + palaIzquierda.getSpeed()));
        }

        // Mover pala derecha
        if (upPressed) {
            palaDerecha.setY(Math.max(0, palaDerecha.getY() - palaDerecha.getSpeed()));
        }
        if (downPressed) {
            palaDerecha.setY(Math.min(ALTO - palaDerecha.getHeight(), palaDerecha.getY() + palaDerecha.getSpeed()));
        }

        if (pelota.getX() < 0) {
            pelota = new Pelota(320, 220, 1, generateRandomNumberExcludingZero(-1, 1), 6, 10);
            palaIzquierda = new Pala(20, 200, 75, 3);
            palaDerecha = new Pala(610, 200, 75, 3);
            sonidoMarcar();
            puntosDerecha++;
            
        }
        if (pelota.getX() > 640) {
            pelota = new Pelota(320, 220, -1, generateRandomNumberExcludingZero(-1, 1), 6, 10);
            palaIzquierda = new Pala(20, 200, 75, 3);
            palaDerecha = new Pala(610, 200, 75, 3);
            sonidoMarcar();
            puntosIzquierda++;
            
        }
        
        
    }

	 
}
