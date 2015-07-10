package com.barantschik.game.run.topdown;

public abstract class GestureType
{
	private long time;
	
	public GestureType()
	{
		time = System.currentTimeMillis();
	}
	
	public long getTime()
	{
		return time;
	}
}