package com.STRahman.SnakeGame.Utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.STRahman.SnakeGame.SnakeBoard;

// Class to listen in on keystrokes.
public class SnakeKeyListener extends KeyAdapter {

	private SnakeBoard snakeB;
	
	public SnakeKeyListener(SnakeBoard sb) {
		snakeB = sb;
	}
	
	// Picks up on the arrow keys and sets the direction accordingly
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) snakeB.setDirection("LEFT");
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) snakeB.setDirection("RIGHT");
		else if(e.getKeyCode() == KeyEvent.VK_UP) snakeB.setDirection("UP");
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) snakeB.setDirection("DOWN");
	}
	
}