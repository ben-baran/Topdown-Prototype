package com.barantschik.game.anim;

public class CosAnim extends AnimFunc
{
	private AnimD t, cosCoef, tCoef;
	double val;
	
	public CosAnim(double start, double cosCoef, double tCoef)
	{
		t = new AnimD(0);
		t.add(new LinearAnim(start, 1));
		this.cosCoef = new AnimD(cosCoef);
		this.tCoef = new AnimD(tCoef);
	}
	
	public CosAnim(AnimD start, AnimD cosCoef, AnimD tCoef)
	{
		t = start;
		this.cosCoef = cosCoef;
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
	
	public AnimD getCosCoef()
	{
		return cosCoef;
	}
	
	public void setCosCoef(AnimD cosCoef)
	{
		this.cosCoef = cosCoef;
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
		cosCoef.update();
		tCoef.update();
		
		val = cosCoef.get() * Math.cos(t.get() * tCoef.get());
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
		return new AnimD[]{t, cosCoef, tCoef};
	}
}
