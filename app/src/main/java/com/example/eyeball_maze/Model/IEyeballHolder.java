package com.example.eyeball_maze.Model;

public interface IEyeballHolder {
	public void addEyeball(int row, int column, Direction direction);
	public int getEyeballRow();
	public int getEyeballColumn();
	public Direction getEyeballDirection();
}
