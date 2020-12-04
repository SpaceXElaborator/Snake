package com.STRahman.SnakeGame;

public class SnakeGame {

	// Serves only as the bus to create the gameboard
	// Might move this into the SnakeBoard.java class
	public static void main(String[] args) {
		SnakeBoard sb = new SnakeBoard();
		sb.setVisible(true);
	}

}