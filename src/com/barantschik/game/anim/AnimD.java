package com.barantschik.game.anim;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AnimD
{
	private static DecimalFormat formatter = new DecimalFormat("#.##");
	private double val, accum, sum;
	private Set<AnimFunc> functions = new HashSet<AnimFunc>();
	
	public AnimD(double val)
	{
		this.val = sum = val;
	}
	
	public AnimD add(AnimFunc... newF)
	{
		for(AnimFunc f : newF)
		{
			functions.add(f);
			f.attach();
		}
		return this;
	}
	
	public AnimD clear()
	{
		for(AnimFunc f : functions)
		{
			val += f.detachVal();
			f.detach();
		}
		functions = new HashSet<AnimFunc>();
		return this;
	}
	
	public void update()
	{
		accum = 0;
		for(Iterator<AnimFunc> i = functions.iterator(); i.hasNext();)
		{
			AnimFunc f = i.next();
			if(f.done())
			{
				val += f.detachVal();
				f.detach();
				i.remove();
			}
			else accum += f.output();
		}
		sum = val + accum;
	}
	
	public AnimD set(double val)
	{
		this.val = val;
		sum = val + accum;
		return this;
	}
	
	public AnimD adjust(double val)
	{
		return set(val + this.val);
	}
	
	public AnimD scale(double d)
	{
		return set(val * d);
	}
	
	public double get()
	{
		return sum;
	}
	
	public String toString()
	{
		return formatter.format(sum);
	}

	public Set<AnimFunc> getFunctions()
	{
		return functions;
	}
}