package com.barantschik.game.anim;

public class ConstAnim extends AnimFunc
{
	private AnimD val;
	
	public ConstAnim(AnimD val)
	{
		this.val = val;
	}
	
	public AnimD getVal()
	{
		return val;
	}
	
	public void setVal(AnimD val)
	{
		this.val = val;
	}
	
	public void update(double dt)
	{
		val.update();
	}
	
	public double output()
	{
		return val.get();
	}
	
	public boolean done()
	{
		return false;
	}
	
	public double detachVal()
	{
		return val.get();
	}
	
	public AnimD[] getDependencies()
	{
		return new AnimD[]{};
	}
}