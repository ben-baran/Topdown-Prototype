package com.barantschik.game.anim;

public class LinearAnim extends AnimFunc
{
	private double d, end = Double.POSITIVE_INFINITY;
	private AnimD coef;
	
	public LinearAnim(double start, double coef)
	{
		d = start;
		this.coef = new AnimD(coef);
	}
	
	public LinearAnim(double start, double coef, double end)
	{
		d = start;
		this.end = end;
		this.coef = new AnimD(coef);
	}
	
	public AnimD getCoef()
	{
		return coef;
	}
	
	public void setCoef(AnimD coef)
	{
		this.coef = coef;
	}
	
	public void update(double dt)
	{
		coef.update();
		d += dt * coef.get();
	}

	public double output()
	{
		return d;
	}

	public boolean done()
	{
		return d > end;
	}

	public double detachVal()
	{
		return d;
	}

	public AnimD[] getDependencies()
	{
		return new AnimD[]{coef};
	}

}
