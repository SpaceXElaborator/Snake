package com.STRahman.SnakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.STRahman.SnakeGame.Utils.Apple;
import com.STRahman.SnakeGame.Utils.Direction;
import com.STRahman.SnakeGame.Utils.Location;
import com.STRahman.SnakeGame.Utils.Pivot;
import com.STRahman.SnakeGame.Utils.SnakeBody;
import com.STRahman.SnakeGame.Utils.SnakeKeyListener;

public class SnakeBoard extends JFrame implements ActionListener {

	// Needed for JFrame extension
	private static final long serialVersionUID = -8981320715881286924L;

	// Sets our timer for the game to be ran on. Creates the repeat at 1/10th of a
	// second
	Timer timer = new Timer(30, this);

	// Sets the initial player, location, and direction
	SnakeBody mainSnake = new SnakeBody(new Location(10, 30), Direction.DOWN);

	// Sets the size of the apples, and snake bodies
	private int blockSize = 20;

	// Boolean to check if the apple is on screen.
	private boolean appleAlive = false;
	private Apple apple = null;
	
	// Boolean to check if we need to delete a pivot or not
	private boolean pivotDel = false;

	// List to store snake bodies
	private List<SnakeBody> bodyLocations = new ArrayList<SnakeBody>();
	
	// List to store all pivots
	private List<Pivot> pivots = new ArrayList<Pivot>();

	// ------------- CLASS FUNCTIONS -------------
	
	// Creates the initial screen for the game
	public SnakeBoard() {
		super("Snake Game");
		timer.start();

		setSize(800, 800);
		addKeyListener(new SnakeKeyListener(this));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		// Each time the timer hits 500 ms, run repaint (Which calls paint())
		if (ev.getSource() == timer) {
			repaint();
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		gameLoop(g);

		// Make sure to move the head in the direction it is going
		if (mainSnake.getDirection() == Direction.UP)
			mainSnake.getLocation().setY(mainSnake.getLocation().getY() - 20);
		if (mainSnake.getDirection() == Direction.DOWN)
			mainSnake.getLocation().setY(mainSnake.getLocation().getY() + 20);
		if (mainSnake.getDirection() == Direction.LEFT)
			mainSnake.getLocation().setX(mainSnake.getLocation().getX() - 20);
		if (mainSnake.getDirection() == Direction.RIGHT)
			mainSnake.getLocation().setX(mainSnake.getLocation().getX() + 20);
		
		// Delete the first pivot only if the last tail has gone through the area
		if(pivotDel) {
			pivots.remove(0);
			pivotDel = false;
		}
		
		if(!bodyLocations.isEmpty()) {
			for(SnakeBody sb : bodyLocations) {
				// Check if any of the body parts overlap a pivot set by the head. If so, change direction
				for(Pivot p : pivots) {
					Location loc = sb.getLocation();
					Location piv = p.getLocation();
					if(loc.getX() == piv.getX() && loc.getY() == piv.getY()) {
						sb.setDirection(p.getDirection());
					}
				}
				
				if(!pivots.isEmpty()) {
					// If the last body part overlaps a pivot, set the first pivot to be deleted
					SnakeBody lastBody = bodyLocations.get(bodyLocations.size() - 1);
					Location last = lastBody.getLocation();
					Pivot p = pivots.get(0);
					if(last.getX() == p.getLocation().getX() && last.getY() == p.getLocation().getY()) {
						pivotDel = true;
					}
				}
				
				// Make sure to move the body parts in the direction they are going
				if (sb.getDirection() == Direction.UP)
					sb.getLocation().setY(sb.getLocation().getY() - 20);
				if (sb.getDirection() == Direction.DOWN)
					sb.getLocation().setY(sb.getLocation().getY() + 20);
				if (sb.getDirection() == Direction.LEFT)
					sb.getLocation().setX(sb.getLocation().getX() - 20);
				if (sb.getDirection() == Direction.RIGHT)
					sb.getLocation().setX(sb.getLocation().getX() + 20);
			}
		}
		
		// Check if the head overlaps with the apple, if it does, get rid of it and add a new body part
		if(appleAlive) {
			Location loc = mainSnake.getLocation();
			Location appleLoc = apple.getLocation();
			if(loc.getX() == appleLoc.getX() && loc.getY() == appleLoc.getY()) {
				addBody();
				appleAlive = false;
			}
		}
		
	}

	// ------------- PUBLIC FUNCTIONS -------------
	
	// Takes the SnakeKeyListener and sets the direction based on the arrow key
	// presses
	public void setDirection(String s) {
		int x = mainSnake.getLocation().getX();
		int y = mainSnake.getLocation().getY();
		
		// Set the direction of the main head according to SnakeKeyListener
		// Each instance will add a pivot if is not just the head.
		switch (s) {
		case "UP":
			if (mainSnake.getDirection() == Direction.DOWN)
				return;
			if(!bodyLocations.isEmpty()) pivots.add(new Pivot(new Location(x, y), Direction.UP));
			mainSnake.setDirection(Direction.UP);
			break;
		case "DOWN":
			if (mainSnake.getDirection() == Direction.UP)
				return;
			if(!bodyLocations.isEmpty()) pivots.add(new Pivot(new Location(x, y), Direction.DOWN));
			mainSnake.setDirection(Direction.DOWN);
			break;
		case "LEFT":
			if (mainSnake.getDirection() == Direction.RIGHT)
				return;
			if(!bodyLocations.isEmpty()) pivots.add(new Pivot(new Location(x, y), Direction.LEFT));
			mainSnake.setDirection(Direction.LEFT);
			break;
		case "RIGHT":
			if (mainSnake.getDirection() == Direction.LEFT)
				return;
			if(!bodyLocations.isEmpty()) pivots.add(new Pivot(new Location(x, y), Direction.RIGHT));
			mainSnake.setDirection(Direction.RIGHT);
			break;
		}
	}
	
	// ------------- PRIVATE FUNCTIONS -------------
	
	private void gameLoop(Graphics g) {
		// Instantiates the graphics to be used for drawing
		Graphics2D g2d = (Graphics2D) g;

		// Sets the color as black and draws the player wherever they are on the
		// coordinate plane. Fills
		g2d.setColor(Color.BLACK);
		g2d.fillRect(mainSnake.getLocation().getX(), mainSnake.getLocation().getY(), blockSize, blockSize);
		g2d.drawRect(mainSnake.getLocation().getX(), mainSnake.getLocation().getY(), blockSize - 1, blockSize - 1);

		// Draw the bodies if there are some
		if (!bodyLocations.isEmpty()) {
			for (SnakeBody sb : bodyLocations) {
				g2d.setColor(Color.BLACK);
				g2d.fillRect(sb.getLocation().getX(), sb.getLocation().getY(), blockSize, blockSize);
				g2d.drawRect(sb.getLocation().getX(), sb.getLocation().getY(), blockSize - 1, blockSize - 1);
			}
		}
		
		if(!appleAlive) {
			// Spawn a new apple if there is none
			spawnRandomApple();
		} else {
			// Get the apples location and draw a red square at its X,Y
			Location appleLoc = apple.getLocation();
			g2d.setColor(Color.RED);
			g2d.fillRect(appleLoc.getX(), appleLoc.getY(), blockSize, blockSize);
			g2d.drawRect(appleLoc.getX(), appleLoc.getY(), blockSize - 1, blockSize - 1);
		}
		
		// ------------- LOSE CONDITIONS -------------

		// Lose if the player moves off-screen
		if (mainSnake.getLocation().getY() <= 0 || mainSnake.getLocation().getY() >= 790)
			kill();
		if (mainSnake.getLocation().getX() <= 0 || mainSnake.getLocation().getX() >= 800)
			kill();
		
		// Loop through each of the snake body pieces and check if the snake head overlaps its body.
		for(SnakeBody sb : bodyLocations) {
			Location loc = mainSnake.getLocation();
			Location bodyLoc = sb.getLocation();
			if(loc.getX() == bodyLoc.getX() && loc.getY() == bodyLoc.getY()) {
				kill();
			}
		}
	}
	
	private void addBody() {
		Direction dir = null;
		Location loc = null;
		
		// If the body location is empty, use the mainSnake, otherwise get the last snake in the list and spawn the new piece there
		if(bodyLocations.isEmpty()) { 
			dir = mainSnake.getDirection();
			loc = mainSnake.getLocation();
		} else {
			SnakeBody sb = bodyLocations.get(bodyLocations.size() - 1);
			dir = sb.getDirection();
			loc = sb.getLocation();
		}
		
		Location newLoc = null;
		
		// Depending on the direction determines which face we will be adding the new body too
		switch(dir) {
		case DOWN:
			newLoc = new Location(loc.getX(), loc.getY() - 20);
			break;
		case LEFT:
			newLoc = new Location(loc.getX() + 20, loc.getY());
			break;
		case RIGHT:
			newLoc = new Location(loc.getX() - 20, loc.getY());
			break;
		case UP:
			newLoc = new Location(loc.getX(), loc.getY() + 20);
			break;
		}
		
		// Create the snake body and add it to the list
		SnakeBody newBody = new SnakeBody(newLoc, dir);
		bodyLocations.add(newBody);
	}
	
	private void spawnRandomApple() {
		appleAlive = true;
		
		// Create the possible X and Y values the apple can spawn at
		List<Integer> x = Arrays.asList(10, 30, 50, 70, 90, 110, 130, 150, 170, 190, 210, 230, 250, 270, 290, 310, 330, 350, 370, 390, 410, 430, 450, 470, 490, 510, 530, 550, 570, 590, 610, 630, 650, 670, 690, 710, 730, 750, 770, 790);
		List<Integer> y = Arrays.asList(30, 50, 70, 90, 110, 130, 150, 170, 190, 210, 230, 250, 270, 290, 310, 330, 350, 370, 390, 410, 430, 450, 470, 490, 510, 530, 550, 570, 590, 610, 630, 650, 670, 690, 710, 730, 750, 770);
		
		// Get a random X and Y to create a new location with
		Random rand = new Random();
		Location loc = new Location(x.get(rand.nextInt(x.size())), y.get(rand.nextInt(y.size())));
		
		boolean sameLoc = false;
		
		// Go through each of the body locations and make sure that the apple is not spawning on a body piece
		for(SnakeBody sb : bodyLocations) {
			Location sbLoc = sb.getLocation();
			if(sbLoc.getX() == loc.getX() && sbLoc.getY() == loc.getY()) {
				sameLoc = true;
				break;
			}
		}
		
		// Make sure the apple is not spawning on the head
		Location mainLoc = mainSnake.getLocation();
		
		if(mainLoc.getX() == loc.getX() && mainLoc.getY() == loc.getY()) {
			sameLoc = true;
		}
		
		// If it did happen to spawn on the head or body, spawn a new apple
		if(sameLoc) {
			spawnRandomApple();
		} else {
			apple = new Apple(loc);
		}
	}

	// Kills the game
	private void kill() {
		timer.stop();
	}

}