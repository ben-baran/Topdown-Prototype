package com.barantschik.game.anim;

public class EmptyFunc extends AnimFunc
{
	public void update(double dt){}
	
	public double output()
	{
		return 0;
	}
	
	public boolean done()
	{
		return false;
	}
	
	public double detachVal()
	{
		return 0;
	}
	
	public AnimD[] getDependencies()
	{
		return null;
	}
}