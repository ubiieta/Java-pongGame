package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Pala {
	//declare instance variables
	private int height, x, y, speed;
	private Color color;

	//constant
	static final int PADDLE_WIDTH = 10;

	public Pala(int x, int y, int height, int speed) {
		super();

		this.x = x;
		this.y = y;
		this.height = height;
		this.speed = speed;
		this.color = new Color(0xfffff2);
	}



	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, PADDLE_WIDTH, height);
	}



	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public static int getPaddleWidth() {
		return PADDLE_WIDTH;
	}
	
	


}
