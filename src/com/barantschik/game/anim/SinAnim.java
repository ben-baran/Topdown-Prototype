package com.barantschik.game.anim;

public class SinAnim extends AnimFunc
{
	private AnimD t, sinCoef, tCoef;
	double val;
	
	public SinAnim(double start, double sinCoef, double tCoef)
	{
		t = new AnimD(0);
		t.add(new LinearAnim(start, 1));
		this.sinCoef = new AnimD(sinCoef);
		this.tCoef = new AnimD(tCoef);
	}
	
	public SinAnim(AnimD start, AnimD sinCoef, AnimD tCoef)
	{
		t = start;
		this.sinCoef = sinCoef;
		this.tCoef = tCoef;
	}
	
	public AnimD getT()
	{
		return t;
	}
	
	public void setT(AnimD t)
	{
		this.t = t;
	}
	
	public AnimD getSinCoef()
	{
		return sinCoef;
	}
	
	public void setSinCoef(AnimD sinCoef)
	{
		this.sinCoef = sinCoef;
	}
	
	public AnimD getTCoef()
	{
		return tCoef;
	}
	
	public void setTCoef(AnimD tCoef)
	{
		this.tCoef = tCoef;
	}
	
	public void update(double dt)
	{
		t.update();
		sinCoef.update();
		tCoef.update();
		
		val = sinCoef.get() * Math.sin(t.get() * tCoef.get());
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
		return 0;
	}


	public AnimD[] getDependencies()
	{
		return new AnimD[]{t, sinCoef, tCoef};
	}
}
