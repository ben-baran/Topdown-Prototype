package com.barantschik.game.anim;

public class SpringAnim extends AnimFunc
{
	private AnimD origin, velCoef, stiffness;
	private double val, vel;
	
	public SpringAnim(double origin, double velCoef, double stiffness)
	{
		this(new AnimD(origin), new AnimD(velCoef), new AnimD(stiffness));
	}
	
	public SpringAnim(AnimD origin, AnimD velCoef, AnimD stiffness)
	{
		this.origin = origin;
		this.velCoef = velCoef;
		this.stiffness = stiffness;
		val = origin.get();
	}

	public void update(double dt)
	{
		origin.update();
		velCoef.update();
		stiffness.update();
		
		vel *= velCoef.get();
		vel += stiffness.get() * (origin.get() - val);
		val += vel;
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
		return new AnimD[]{origin, velCoef, stiffness};
	}
}