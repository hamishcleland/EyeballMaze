package com.example.eyeball_maze.Model;

public class PlayableSquare extends Square {
	
	//COnstructor for adding a Playable Square, must have color and shape
	public PlayableSquare(Color newColor, Shape newShape) {
		this.setColour(newColor);
		this.setShape(newShape);
	}

}
