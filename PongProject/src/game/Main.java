package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main { 

	public static void main(String[] args) {
		    

		JFrame frame = new JFrame("Pong");	

		// Cerrar la aplicacion al salir
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Parametros de la ventana
		frame.setSize(654,517);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);


		// Cambiar el logo
		ImageIcon logo = new ImageIcon("pongLogo.png");
		frame.setIconImage(logo.getImage());
 


		// Crear juego
		PongJuego juego = new PongJuego();
		frame.add(juego);

		// Cambiar visibilidad
		frame.setVisible(true);

		// Empezar el reloj 
		Timer timer = new Timer(33, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				juego.repaint();
				juego.logicaJuego();
				 

			}
		});
		timer.start();
	}


}


