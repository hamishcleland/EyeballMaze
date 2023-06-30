package com.example.eyeball_maze.Model;

import android.graphics.Point;

public class Position {
	//This class holds information about the position of the square with get functions
	final private Point position;
	public Position (int x, int y) {
		Point eyeballPos = new Point(y,x);
		this.position = eyeballPos;
	}
	
	public int getRow () {
		return (int)position.y;
	}
	
	public int getColumn () {
		return (int)position.x;
	}
}
