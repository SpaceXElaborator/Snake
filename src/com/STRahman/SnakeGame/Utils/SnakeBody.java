package com.STRahman.SnakeGame.Utils;

// Class to store information pertaining to the Snake's individual body locations and directions
public class SnakeBody {

	private Location loc;
	private Direction dir;
	
	public SnakeBody(Location loc, Direction dir) {
		this.loc = loc;
		this.dir = dir;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Direction getDirection() {
		return dir;
	}
	
	public void setDirection(Direction dir) {
		this.dir = dir;
	}
	
}