package com.example.eyeball_maze.Model;

import android.graphics.Point;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class Game implements IEyeballHolder,IGoalHolder,ILevelHolder,IMoving,ISquareHolder {
	
	//Instaniates game object
	public Game() {
		ArrayList<Level> newLevelList = new ArrayList<Level>();
		this.levelList = newLevelList;
		ArrayList<Point>newMoveList = new ArrayList<>();
		this.moveList = newMoveList;
		ArrayList<Direction>newDirectionList = new ArrayList<>();
		this.directionList = newDirectionList;
	}

	private ArrayList<Level> levelList;

	private ArrayList<Point>moveList;

	private ArrayList<Direction>directionList;
	private Level currentLevel;

	private int moveCount = 0;
	
	//Adds a new level to the game
	public void addLevel(int height, int width) {
		Level newLevel = new Level(height,width);
		this.levelList.add(newLevel);
		this.currentLevel = newLevel;
	}
	
	//Returns level width
	public int getLevelWidth() {
		return currentLevel.getLevelWidth();
	}
	
	//Returns level height
	public int getLevelHeight() {
		return currentLevel.getLevelHeight();
	}
	
	//Returns the number of levels
	public int getLevelCount() {
		return this.levelList.size();
	}
	
	//Sets a level in the list at a specific index to make the current level in game
	public void setLevel(int levelNumber) {
		if (levelNumber < 0 || levelNumber > levelList.size() -1) {
            throw new IllegalArgumentException("You have selected a level outside the valid range");
        } else {
        	this.currentLevel = levelList.get(levelNumber);
        }
	}
	
	//Adds a goal to the level
	public void addGoal(int xPos, int yPos) {
		currentLevel.addGoal(xPos, yPos);
	}
	
	//Checks if goal at a point
	public boolean hasGoalAt (int height, int width) {
		boolean result = false;
		for (Point item : currentLevel.getGoalList()) {
			if (height == item.y && width == item.x) {
				result = true;
			}
		}
		return result;
	}
	
	//Returns the number of goals
	public int getGoalCount() {
		return currentLevel.getGoalList().size();
	}

	public List<Point> getGoalList()  {
		return currentLevel.getGoalList();
	}

	public List<Point> getCompletedGoalList()  {
		return currentLevel.getCompletedGoalList();
	}
	//Returns completed goal count
	public int getCompletedGoalCount() {
		return currentLevel.getCompletedGoalList().size();
	}

	public int getMoveCount() {
		return this.moveCount;
	}
	
	//Adds a square to the level
	public void addSquare(Square square, int yPos, int xPos) {
		currentLevel.addSquare(square, yPos, xPos);
	}
	
	//Returns the colour at a x,y coordinate in the level
	public Color getColorAt(int yPos, int xPos) {
		Square theSquare = currentLevel.getSquare(yPos,xPos);
		if (theSquare == null) {
			return Color.BLANK;
		} else {
		return theSquare.getColour();}
	}
	
	//Returns the shape at a x,y coordinate in the level
	public Shape getShapeAt(int yPos, int xPos) {
		Square theSquare = currentLevel.getSquare(yPos,xPos);
		if (theSquare == null) {
			return Shape.BLANK;
		} else {
		return theSquare.getShape();}
	}
	
	//Adds an eyeball to the level
	public void addEyeball(int yPos, int xPos, Direction direction) {
		currentLevel.addEyeball(yPos, xPos, direction);
		moveList.add(new Point(xPos,yPos));
		directionList.add(direction);
	}
	
	//returns the row of the eyeball (y)
	public int getEyeballRow() {
		return (int)currentLevel.getEyeballPos().y;
	}
	
	//returns the column of the eyeball (x)
	public int getEyeballColumn() {
		return (int)currentLevel.getEyeballPos().x;
	}
	
	//Returns the current direction the eyeball is facing
	public Direction getEyeballDirection() {
		return currentLevel.getEyeballDir();
	}
	
	//Checks if the move is a valid one
	public boolean canMoveTo(int yPos, int xPos) {
		Boolean result = false;
		Shape destShape = this.getShapeAt(xPos, yPos);
		Shape currentShape = this.getShapeAt(this.getEyeballRow(), this.getEyeballColumn());
		Color destColor = this.getColorAt(xPos, yPos);
		Color currentColor = this.getColorAt(this.getEyeballRow(), this.getEyeballColumn());
		if (destShape == currentShape || destColor == currentColor) {
			if (this.returnMoveDir(yPos, xPos) != null && this.isBackwardsMove(yPos, xPos) == false) {
				result = true;
			}
		}
		return result;
	}
	
	//Returns the direction of the move if it were to happen
	private Direction returnMoveDir (int yPos, int xPos) {
		Direction movingDir = null;
		int xCoord = this.getEyeballRow();
		int yCoord = this.getEyeballColumn();
		if (xPos > xCoord && yPos == yCoord) {
			movingDir = Direction.RIGHT;
		} else if (xPos < xCoord && yPos == yCoord){
			movingDir = Direction.LEFT;
		} else if (yPos > yCoord && xPos == xCoord){
			movingDir = Direction.UP;
		} else if (yPos < yCoord && xPos == xCoord) {
			movingDir = Direction.DOWN;
		}
		return movingDir;
	}
	
	//Determines if a move is backwards
	private boolean isBackwardsMove (int yPos, int xPos) {
		Direction moveDir = this.returnMoveDir(yPos, xPos);
		boolean result = false;
		if (moveDir == Direction.LEFT && currentLevel.getEyeballDir() == Direction.RIGHT) {
			result = true;
		} else if (moveDir == Direction.RIGHT && currentLevel.getEyeballDir() == Direction.LEFT) {
			result = true;
		} else if (moveDir == Direction.UP && currentLevel.getEyeballDir() == Direction.DOWN) {
			result = true;
		} else if (moveDir == Direction.DOWN && currentLevel.getEyeballDir() == Direction.UP) {
			result = true;
		}
		return result;
	}

	//Determines the message if you were to move to a square
	public Message MessageIfMovingTo(int yPos, int xPos) {
		if (this.canMoveTo(yPos, xPos) == true) {
			if (this.hasBlankFreePathTo(yPos,xPos) == false) {
				return Message.MOVING_OVER_BLANK; }
			 else {
					return Message.OK;
			}
		} else if (this.returnMoveDir(yPos, xPos) == null) {
			return Message.MOVING_DIAGONALLY;
		} else if (this.isBackwardsMove(yPos, xPos) == true) {
			return Message.BACKWARDS_MOVE;
		}
		else {
				return Message.DIFFERENT_SHAPE_OR_COLOR;
			} 
	}
	
	//Checks if a direction is ok to moved in
	public boolean isDirectionOK(int yPos, int xPos) {
		Direction movingDir = this.returnMoveDir(yPos, xPos);
		Boolean backwardsMove = this.isBackwardsMove(yPos, xPos);
		Boolean result = false;
		if (movingDir != null && backwardsMove == false) {
			result = true;
		}
		return result;
	}
	
	//Returns the message if moving to a square
	public Message checkDirectionMessage (int yPos, int xPos) {
		Message result = this.MessageIfMovingTo(yPos, xPos);
		return result;
	}
	
	//Checks if the path is blank in a direction
	public boolean hasBlankFreePathTo (int yPos, int xPos) {
		int y = 0;
		int x = 0;
		boolean result = true;
		Direction moveDir = this.returnMoveDir(yPos, xPos);
		if (moveDir == Direction.LEFT) {
			while (xPos != this.getEyeballRow() - x)
			{
				Shape nextShape = this.getShapeAt(this.getEyeballRow() - x, this.getEyeballColumn());
				if (nextShape == Shape.BLANK) {
					result = false;
					return result;
				} else {
					x++;
				}
			}
			} else if (moveDir == Direction.RIGHT) {
				while (xPos != this.getEyeballRow() + x)
				{
					Shape nextShape = this.getShapeAt(this.getEyeballRow() + x, this.getEyeballColumn());
					if (nextShape == Shape.BLANK) {
						result = false;
						return result;
					} else {
						x++;
					}
				}
				
			} else if (moveDir == Direction.UP) {
				while (yPos != this.getEyeballColumn() + y)
				{
					Shape nextShape = this.getShapeAt(this.getEyeballRow(), this.getEyeballColumn() + y);
					if (nextShape == Shape.BLANK) {
						result = false;
						return result;
					} else {
						y++;
					}
				}
			} else if (moveDir == Direction.DOWN) {
					while (yPos != this.getEyeballColumn() - y)
					{
						Shape nextShape = this.getShapeAt(this.getEyeballRow(), this.getEyeballColumn() - y);
						if (nextShape == Shape.BLANK) {
							result = false;
							return result;
						} else {
							y++;
						}
					}
			}
		return result;}
	
	//Checks for the message if a path is blank or not
	public Message checkMessageForBlankOnPathTo(int yPos, int xPos) {
		 return this.MessageIfMovingTo(yPos,xPos);
	}
	
	//Logic to make a move
	public void moveTo (int yPos, int xPos) {
		Point setBlank = null;
		if (currentLevel.getCompletedGoalList().size() > 0) {
			for (Point completeGoal : currentLevel.getCompletedGoalList()) {
				if ((completeGoal.x == currentLevel.getEyeballPos().x) && (completeGoal.y == currentLevel.getEyeballPos().y)) {
					setBlank = completeGoal;
				}
			}
		}
		if (this.MessageIfMovingTo(yPos,xPos) == Message.OK) {
			Direction moveDir = this.returnMoveDir(yPos, xPos);
			currentLevel.setEyeballDir(moveDir);
			currentLevel.setEyeballPos(yPos,xPos);
			this.moveCount++;
			moveList.add(currentLevel.getEyeballPos());
			directionList.add(currentLevel.getEyeballDir());
			Point completedGoal = null;
			for (Point goal : currentLevel.getGoalList()) {
				  if ((goal.x == currentLevel.getEyeballPos().x) && (goal.y == currentLevel.getEyeballPos().y)) {
					  currentLevel.addCompletedGoal(goal);
					  completedGoal = goal;
				  }
				}
			if (completedGoal != null) {
				currentLevel.removeCompletedGoal(completedGoal);
			}
			}
		if (setBlank != null) {
			currentLevel.setSquare((int)setBlank.y,(int)setBlank.x,new BlankSquare());
		}
	}

	public void undoMove () {
		if (this.moveList.size() >  1) {
			int index = moveList.size() - 2;
			currentLevel.setEyeballPos(moveList.get(index).x,moveList.get(index).y);
			currentLevel.setEyeballDir(directionList.get(index));
			moveList.remove(index + 1);
			directionList.remove(index + 1);
			this.moveCount--;
		}
	}
}
