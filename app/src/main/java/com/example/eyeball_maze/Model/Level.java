package com.example.eyeball_maze.Model;

import android.graphics.Point;
import java.util.ArrayList;

public class Level {
	protected Level (int height, int width) {
		Square[][] newLevel = new Square[height][width];
		this.levelLayout = newLevel;
		ArrayList<Point> goalList = new ArrayList<Point>();
		this.goalList = goalList;
		ArrayList<Point> completedGoalList = new ArrayList<Point>();
		this.completedGoalList = completedGoalList;
		Point eyeballPos = new Point(0,0);
		this.eyeballPos = eyeballPos;
	}
	
	private Square[][] levelLayout;
	private ArrayList<Point> goalList;
	private ArrayList<Point> completedGoalList;
	private Point eyeballPos;
	private Direction eyeballDirection;

	// Getter
	protected Square getSquare(int yPos, int xPos) {
		return this.levelLayout[yPos][xPos];
	}

	// Setter
	protected void setSquare(int yPos, int xPos, Square square) {
		this.levelLayout[yPos][xPos] = square;
	}
	  
	// Getter
	protected ArrayList<Point> getGoalList() {
		return this.goalList;
	}
	  
	// Removes a completed goal from the current goal list
	protected void removeCompletedGoal(Point goal) {
		this.goalList.remove(goal);
	}
	  
	// Adds a current goal to the goal list
	protected void addCompletedGoal(Point goal) {
		this.completedGoalList.add(goal);
	}
	  
	// Getter
	protected ArrayList<Point> getCompletedGoalList() {
	  	return this.completedGoalList;
	}
	
	// Getter
	protected Point getEyeballPos() {
	    return this.eyeballPos;
	}

	// Setter
	protected void setEyeballPos(int xPos, int yPos) {
	    this.eyeballPos = new Point(xPos,yPos);
	}	
	
	// Getter
	protected Direction getEyeballDir() {
		return this.eyeballDirection;
	}

	// Setter
	protected void setEyeballDir(Direction dir) {
		this.eyeballDirection = dir;
	}
	
	// Getter
	protected int getLevelWidth() {
		return this.levelLayout[0].length;
	}
	
	// Getter
	protected int getLevelHeight() {
		return this.levelLayout.length;
	}
		  
	//Adds a goal to the level
	protected void addGoal(int yPos, int xPos) {
		if (levelLayout[0].length < xPos || levelLayout.length < yPos) {
            throw new IllegalArgumentException("The goal is outside the map");
        } else {
        	Point goal = new Point(xPos,yPos);
    		this.goalList.add(goal);
        }
	}
	
	//Adds a square to the level
	protected void addSquare(Square square, int yPos, int xPos) {
		if (levelLayout[0].length < xPos || levelLayout.length < yPos) {
            throw new IllegalArgumentException("The square is outside the level");
        } else {
        	square.setLocation(yPos,xPos);
    		this.levelLayout[yPos][xPos] = square;
        }
	}
	
	//Adds an eyeball to the level
	protected void addEyeball(int yPos, int xPos, Direction direction) {
		if (levelLayout[0].length < xPos || levelLayout.length < yPos) {
            throw new IllegalArgumentException("The eyeball is outside the map");
        } else {
        	eyeballPos.set(xPos, yPos);
    		this.eyeballDirection = direction;
        }
	}
}
