package com.STRahman.SnakeGame.Utils;

// Only serves as a basic class to hold where the apple is located
public class Apple {

	private Location loc;
	
	public Apple(Location loc) {
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
}