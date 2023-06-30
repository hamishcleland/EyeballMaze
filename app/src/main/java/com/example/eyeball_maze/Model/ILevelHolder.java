package com.example.eyeball_maze.Model;

public interface ILevelHolder {
	public void addLevel(int height, int width);
	public int getLevelWidth();
	public int getLevelHeight();
	public void setLevel(int levelNumber);
	public int getLevelCount();
}