package com.barantschik.game.anim;

public class InvAnim extends AnimFunc
{
	private AnimD t, topCoef, bottomConst, tCoef;
	private double val;
	
	public InvAnim(double start, double topCoef, double bottomConst, double tCoef)
	{
		t = new AnimD(0);
		t.add(new LinearAnim(start, 1));
		this.topCoef = new AnimD(topCoef);
		this.bottomConst = new AnimD(bottomConst);
		this.tCoef = new AnimD(tCoef);
	}
	
	public InvAnim(AnimD t, AnimD topCoef, AnimD bottomConst, AnimD tCoef)
	{
		this.t = t;
		this.topCoef = topCoef;
		this.bottomConst = bottomConst;
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

	public AnimD getTopCoef()
	{
		return topCoef;
	}

	public void setTopCoef(AnimD topCoef)
	{
		this.topCoef = topCoef;
	}

	public AnimD getBottomConst()
	{
		return bottomConst;
	}

	public void setBottomConst(AnimD bottomConst)
	{
		this.bottomConst = bottomConst;
	}

	public AnimD gettCoef()
	{
		return tCoef;
	}

	public void settCoef(AnimD tCoef)
	{
		this.tCoef = tCoef;
	}
	
	public void update(double dt)
	{
		t.update();
		topCoef.update();
		bottomConst.update();
		tCoef.update();
		val = topCoef.get() / (bottomConst.get() + tCoef.get() * t.get());
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
		return new AnimD[]{t, topCoef, bottomConst, tCoef};
	}
}
