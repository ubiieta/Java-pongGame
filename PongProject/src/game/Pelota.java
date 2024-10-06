package game;

import java.awt.*;

public class Pelota {

	// Declarar variables
	private int x, y, cx, cy, speed, size, turno;
	private Color color;
	

	// Constructor
	public Pelota(int x, int y, int cx, int cy, int speed, int size) {
		this.x = x;
		this.y = y;
		this.cx = cx;
		this.cy = cy;
		this.speed = speed;
		this.color = Color.red;
		this.size = size;
	}

	// Métodos getters y setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCx() {
		return cx;
	}

	public void setCx(int cx) {
		this.cx = cx;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	// Dibujar la pelota
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, size, size);
	}

	// Mover la pelota
	public void moverPelota() {
		x += cx;
		y += cy;
	}

	// Comprobar colisión con una pala
	public boolean colisionPala(Pala palaDerecha, Pala palaIzquierda) {
		
		// Comprobar colisión con pala izquierda
		if (cx < 0 && x <= palaIzquierda.getX() + palaIzquierda.getPaddleWidth() && 
				x + size >= palaIzquierda.getX() &&
				y + size >= palaIzquierda.getY() && y <= palaIzquierda.getY() + palaIzquierda.getHeight()) {

			if(Math.abs(cx) <4 && Math.abs(cy) < 4 ) {
				cx = cx/Math.abs(cx)*(Math.abs(cx)+1);
				cy = cy/Math.abs(cy)*(Math.abs(cy)+1);
				

			}

			turno++;
			System.out.println("Turno: "+ turno + " (cx: "+cx+", cy: "+cy+")");

			return true;

		}
		
		// Comprobar colisión con pala derecha
		if (cx > 0 && x + size >= palaDerecha.getX() && 
				x <= palaDerecha.getX() + palaDerecha.getPaddleWidth() &&
				y + size >= palaDerecha.getY() && y <= palaDerecha.getY() + palaDerecha.getHeight()) {

			if(Math.abs(cx) <4 && Math.abs(cy) < 4 ) {
				cx = cx/Math.abs(cx)*(Math.abs(cx)+1);
				cy = cy/Math.abs(cy)*(Math.abs(cy)+1);

			}



			return true;

		}
		return false;
	}

	// Manejar el rebote de la pelota
	public void rebotar(Pala palaDerecha, Pala palaIzquierda) {
		if (colisionPala(palaDerecha, palaIzquierda)) {
			cx *= -1;
			PongJuego.sonidoGolpear();
		}

		// Colisiones con los bordes superior e inferior
		if (y + size >= PongJuego.ALTO || y <= 0) {
			cy *= -1;
			PongJuego.sonidoRebotar();
		}
	}
}
