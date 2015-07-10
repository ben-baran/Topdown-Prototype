package com.barantschik.game.anim;

public class CyclicChaseAnim extends AnimFunc
{
	private AnimD origin;
	private final double cycleSize;
	private double val;
	
	public CyclicChaseAnim(double origin, double cycleSize)
	{
		this(new AnimD(origin), cycleSize);
	}
	
	public CyclicChaseAnim(AnimD origin, double cycleSize)
	{
		this.origin = origin;
		this.cycleSize = cycleSize;
		val = origin.get();
	}

	public void update(double dt)
	{
		origin.update();
		
		double dif = origin.get() - val, absDif = Math.abs(dif);
		if(absDif < cycleSize / 2) val -= Math.signum(dif) * Math.log(1 + absDif);
		else val += Math.signum(dif) * Math.log(1 + absDif);
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
		return new AnimD[]{origin};
	}
}
