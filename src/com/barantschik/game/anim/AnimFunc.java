package com.barantschik.game.anim;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class AnimFunc
{
	private int numAttached = 0;
	
	public class FuncMods
	{
		private double delay = 0, speed = 1;
		
		public FuncMods(){}
		
		public FuncMods(double delay, double speed)
		{
			this.delay = delay;
			this.speed = speed;
		}
		
		public double process(double dt)
		{
			if(delay > dt)
			{
				delay -= dt * speed;
				return 0;
			}
			if(delay > 0)
			{
				delay = 0;
				return dt * speed - delay;
			}
			return dt * speed;
		}
		
		public double getDelay()
		{
			return delay;
		}
		
		public double getSpeed()
		{
			return speed;
		}
		
		public void setDelay(double delay, AnimFunc func)
		{
			this.delay = delay;
			AnimFunc.setFuncMods(func, this);
		}
		
		public void setSpeed(double speed, AnimFunc func)
		{
			this.speed = speed;
			AnimFunc.setFuncMods(func, this);
		}
	}
	
	private static Map<AnimFunc, FuncMods> functions = new HashMap<AnimFunc, FuncMods>();
	
	public static void delete(AnimFunc func)
	{
		functions.remove(func);
	}
	
	public static void updateAll(double dt)
	{
		Iterator<AnimFunc> funcs = functions.keySet().iterator();
		while(funcs.hasNext())
		{
			AnimFunc f = funcs.next();
			f.update(functions.get(f).process(dt));
			if(f.numAttached <= 0) funcs.remove();
		}
	}
	
	public static FuncMods getFuncMods(AnimFunc func)
	{
		return functions.get(func);
	}
	
	public static void setFuncMods(AnimFunc func, FuncMods mods)
	{
		if(functions.get(func) != mods)
		{			
			functions.put(func, mods);
			AnimD[] dependencies = func.getDependencies();
			for(AnimD dependency : dependencies) for(AnimFunc dependencyFunc: dependency.getFunctions()) setFuncMods(dependencyFunc, mods);
		}
	}
	
	public static FuncMods createMods(double delay, double speed)
	{
		AnimFunc container = new EmptyFunc();
		FuncMods mods = container.new FuncMods(delay, speed);
		delete(container);
		return mods;
	}
	
	public AnimFunc()
	{
		functions.put(this, new FuncMods());
	}
	
	public void attach()
	{
		numAttached++;
	}
	
	public void detach()
	{
		numAttached--;
	}
	
	public abstract void update(double dt);
	public abstract double output();
	public abstract boolean done();
	public abstract double detachVal();
	public abstract AnimD[] getDependencies();
}
