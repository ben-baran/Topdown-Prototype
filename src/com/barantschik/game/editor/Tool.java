package com.barantschik.game.editor;

public abstract class Tool
{
	private boolean done = false;
	private Editor parent;
	
	public abstract void update();
	public abstract void draw();
	
	public Tool(Editor parent)
	{
		this.parent = parent;
	}
	
	public boolean isDone()
	{
		return done;
	}
	
	public void setDone(boolean done)
	{
		this.done = done;
	}
	
	public Editor getParent()
	{
		return parent;
	}
	
	public void setParent(Editor parent)
	{
		this.parent = parent;
	}
}
