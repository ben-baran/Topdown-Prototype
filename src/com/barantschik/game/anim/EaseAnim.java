package com.barantschik.game.anim;

public class EaseAnim extends AnimFunc
{
	public enum EaseType
	{
		LINEAR
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * t / timeTot;
			}
		},
		QUADRATIC_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * (t /= timeTot) * t;
			}
		},
		QUADRATIC_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return -target * ((t /= timeTot) * (t - 2));
			}
		},
		QUADRATIC_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if((t /= (timeTot / 2)) < 1) return target / 2 * t * t;
				return -target / 2 * ((--t) * (t - 2) - 1);
			}
		},
		CUBIC_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * (t /= timeTot) * t * t;
			}
		},
		CUBIC_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * ((t = (t / timeTot - 1)) * t * t + 1);
			}
		},
		CUBIC_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if((t /= (timeTot / 2)) < 1) return target / 2 * t * t * t;
				return target / 2 * ((t -= 2) * t * t + 2);
			}
		},
		QUARTIC_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * (t /= timeTot) * t * t * t;
			}
		},
		QUARTIC_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return -target * ((t = (t / timeTot - 1)) * t * t * t - 1);
			}
		},
		QUARTIC_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if((t /= (timeTot / 2)) < 1) return target / 2 * t * t * t * t;
				return -target / 2 * ((t -= 2) * t * t * t - 2);
			}
		},
		QUINTIC_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * (t /= timeTot) * t * t * t * t;
			}
		},
		QUINTIC_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * ((t = (t / timeTot - 1)) * t * t * t * t + 1);
			}
		},
		QUINTIC_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if((t /= (timeTot / 2)) < 1) return target / 2 * t * t * t * t * t;
				return target / 2 * ((t -= 2) * t * t * t * t + 2);
			}
		},
		SINE_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return -target * Math.cos(t / timeTot * Math.PI / 2) + target;
			}
		},
		SINE_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * Math.sin(t / timeTot * Math.PI / 2) + target;
			}
		},
		SINE_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return -target / 2 * (Math.cos(Math.PI * t / timeTot) - 1);
			}
		},
		EXPONENTIAL_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return t == 0 ? 0 : target * Math.pow(2, 10 * (t / timeTot - 1));
			}
		},
		EXPONENTIAL_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return t == timeTot ? target : target * (-Math.pow(2, -10 * t / timeTot) + 1);
			}
		},
		EXPONENTIAL_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if(t == 0) return 0;
				if(t == timeTot) return target;
				if((t /= (timeTot / 2)) < 1) return target / 2 * Math.pow(2, 10 * (t - 1));
				return target / 2 * (-Math.pow(2, -10 * (t - 1)) + 2);
			}
		},
		SQRT_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return -target * (Math.sqrt(1 - (t /= timeTot) * t) - 1);
			}
		},
		SQRT_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target * Math.sqrt(1 - (t = (t / timeTot - 1)) * t);
			}
		},
		SQRT_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if((t /= (timeTot / 2)) < 1) return -target / 2 * (Math.sqrt(1 - t * t) - 1);
				return target / 2 * (Math.sqrt(1 - (t -= 2) * t) + 1);
			}
		},
		BACK_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				double back = 1.70158;
				return target * (t /= timeTot) * t * ((back + 1) * t - back);
			}
		},
		BACK_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				double back = 1.70158;
				return target * ((t = (t / timeTot - 1)) * t * ((back + 1) * t + back) + 1);
			}
		},
		BACK_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				double back = 1.70158, norm = 1.525;
				if((t /= (timeTot / 2)) < 1) return target / 2 * (t * t * (((back *= norm) + 1) * t - back));
				return target / 2 * ((t -= 2) * t * (((back *= norm) + 1) * t + back) + 2);
			}
		},
		ELASTIC_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				if(t == 0) return 0;
				if((t /= timeTot) == 1) return target;
				double invDuration = 0.3 * timeTot, offset = invDuration / 4;
				return -(target * Math.pow(2, 10 * (--t)) * Math.sin((t * timeTot - offset) * 2 * Math.PI / invDuration));
			}
		},
		ELASTIC_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if(t == 0) return 0;
				if((t /= timeTot) == 1) return target;
				double invDuration = 0.3 * timeTot, offset = invDuration / 4;
				return (target * Math.pow(2, -10 * t) * Math.sin((t * timeTot - offset) * 2 * Math.PI / invDuration)) + target;
			}
		},
		ELASTIC_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if(t == 0) return 0;
				if((t /= (timeTot / 2)) == 2) return target;
				double invDuration = 0.45 * timeTot, offset = invDuration / 4;
				if(t < 1) return -0.5 * (target * Math.pow(2, 10 * (--t)) * Math.sin((t * timeTot - offset) * 2 * Math.PI / invDuration));
				return 0.5 * (target * Math.pow(2, -10 * (--t)) * Math.sin((t * timeTot - offset) * 2 * Math.PI / invDuration)) + target;
			}
		},
		BOUNCE_IN
		{
			public double getVal(double t, double timeTot, double target)
			{
				return target - BOUNCE_OUT.getVal(timeTot - t, timeTot, target);
			}
		},
		BOUNCE_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				double cycle = 2.75;
				if((t/= timeTot) < (1 / cycle)) return target * 7.5625 * t * t;
				if(t < (2 / cycle)) return target * (7.5625 * (t -= (1.5 / cycle)) * t + .75);
				if(t < (2.5 / cycle)) return target * (7.5625 * (t -= (2.25 / cycle)) * t + .9375);
				return target * (7.5625 * (t -= (2.625 / cycle)) * t + .984375);
			}
		},
		BOUNCE_IN_OUT
		{
			public double getVal(double t, double timeTot, double target)
			{
				if(t < (timeTot / 2)) return BOUNCE_IN.getVal(t * 2, timeTot, target) * 0.5;
				return BOUNCE_OUT.getVal(t * 2 - timeTot, timeTot, target) * 0.5 + target * 0.5;
			}
		};
		
		
		public abstract double getVal(double t, double timeTot, double target);
	}
	
	private static final EaseType DEFAULT_EASE = EaseType.CUBIC_IN_OUT;
	
	private EaseType ease = DEFAULT_EASE;
	private AnimD t, timeTot, target;
	private double val;
	private boolean done = false;
	
	public EaseAnim(double start, double timeTot, double target)
	{
		t = new AnimD(0);
		t.add(new LinearAnim(start, 1));
		this.timeTot = new AnimD(timeTot);
		this.target = new AnimD(target);
	}
	
	public EaseAnim(double start, double timeTot, double target, EaseType ease)
	{
		t = new AnimD(0);
		t.add(new LinearAnim(start, 1));
		this.timeTot = new AnimD(timeTot);
		this.target = new AnimD(target);
		this.ease = ease;
	}
	
	public EaseAnim(AnimD start, AnimD timeTot, AnimD target)
	{
		t = start;
		this.timeTot = timeTot;
		this.target = target;
	}
	
	public EaseAnim(AnimD start, AnimD timeTot, AnimD target, EaseType ease)
	{
		t = start;
		this.timeTot = timeTot;
		this.target = target;
		this.ease = ease;
	}
	
	public AnimD getT()
	{
		return t;
	}
	
	public void setT(AnimD t)
	{
		this.t = t;
	}
	
	public AnimD getTimeTot()
	{
		return timeTot;
	}
	
	public void setTimeTot(AnimD timeTot)
	{
		this.timeTot = timeTot;
	}
	
	public AnimD getTarget()
	{
		return target;
	}
	
	public void setTarget(AnimD target)
	{
		this.target = target;
	}
	
	public void update(double dt)
	{
		t.update();
		timeTot.update();
		target.update();
		
		if(t.get() > timeTot.get()) done = true;
		else val = ease.getVal(t.get(), timeTot.get(), target.get());
	}

	public double output()
	{
		return val;
	}

	public boolean done()
	{
		return done;
	}

	public double detachVal()
	{
		return target.get();
	}

	public EaseType getEase()
	{
		return ease;
	}
	
	public void setEase(EaseType ease)
	{
		this.ease = ease;
	}

	public AnimD[] getDependencies()
	{
		return new AnimD[]{t, timeTot, target};
	}
}