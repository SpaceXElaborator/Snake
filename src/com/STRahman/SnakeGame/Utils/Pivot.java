package com.STRahman.SnakeGame.Utils;

// The class to tell which way a body part will need to turn when the player presses one of the arrow keys
public class Pivot {

	private Location loc;
	private Direction dir;
	
	public Pivot(Location loc, Direction dir) {
		this.loc = loc;
		this.dir = dir;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Direction getDirection() {
		return dir;
	}
	
}