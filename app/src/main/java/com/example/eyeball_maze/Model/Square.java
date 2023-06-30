package com.example.eyeball_maze.Model;

public abstract class Square {
	
	private Position position;
	private Color color;
	private Shape shape;
	
	protected void setLocation (int xPos, int yPos) {
		this.position = new Position(xPos,yPos);
	}
	
	protected void setShape (Shape shape) {
		this.shape = shape;
	}
	
	protected Shape getShape() {
		return this.shape;
	}
	
	protected void setColour (Color colour) {
		this.color = colour;
	}
	
	protected Color getColour () {
		return this.color;
	}

	protected Position getPosition() {
		return position;
	}
}
