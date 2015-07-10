package com.barantschik.game.anim;

public class CyclicChaseAnim extends AnimFunc
{
	private AnimD origin, stiffness;
	private final double cycleSize;
	private double val;
	
	public CyclicChaseAnim(double origin, double cycleSize, double stiffness)
	{
		this(new AnimD(origin), new AnimD(stiffness), cycleSize);
	}
	
	public CyclicChaseAnim(AnimD origin, AnimD stiffness, double cycleSize)
	{
		this.origin = origin;
		this.stiffness = stiffness;
		this.cycleSize = cycleSize;
		val = origin.get();
	}

	public void update(double dt)
	{
		origin.update();
		stiffness.update();
		
		double originD = origin.get();
		if(val - originD > cycleSize / 2) originD += cycleSize;
		else if(originD - val > cycleSize / 2) originD -= cycleSize;
		
		val += stiffness.get() * (originD - val);
	}

	public double output()
	{
		return val;
	}

	public boolean done()
	{
		return false;
	}

	public double detachVal()
	{
		return origin.get();
	}

	public AnimD[] getDependencies()
	{
		return new AnimD[]{origin, stiffness};
	}
}
