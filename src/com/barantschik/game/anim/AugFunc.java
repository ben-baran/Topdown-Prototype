package com.barantschik.game.anim;

public class AugFunc
{
	public enum FType
	{
		ADD
		{
			public double output(double base, double modifier)
			{
				return base + modifier;
			}
		},
		SCALE
		{
			public double output(double base, double modifier)
			{
				return base * modifier;
			}
		},
		SINE
		{
			public double output(double base, double modifier)
			{
				return Math.sin(base);
			}
		},
		COS
		{
			public double output(double base, double modifier)
			{
				return Math.cos(base);
			}
		};
		
		public abstract double output(double base, double modifier);
	}
	
	public FType function;
	public double modifier;
	
	public AugFunc(FType function, double modifier)
	{
		this.function = function;
		this.modifier = modifier;
	}
	
	public double output(double input)
	{
		return function.output(input, modifier);
	}
}