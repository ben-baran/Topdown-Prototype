package com.barantschik.game.log;

import org.newdawn.slick.Color;

import com.barantschik.game.anim.AnimD;

public class AnimDLogMessage extends LogMessage
{
	private String prepend = "";
	private AnimD d;
	
	public AnimDLogMessage(AnimD d)
	{
		super(d.toString());
		this.d = d;
	}
	
	public AnimDLogMessage(AnimD d, boolean permanent)
	{
		super(d.toString(), permanent);
		this.d = d;
	}
	
	public AnimDLogMessage(AnimD d, Color color)
	{
		super(d.toString(), color);
		this.d = d;
	}
	
	public AnimDLogMessage(AnimD d, Color color, boolean permanent)
	{
		super(d.toString(), color, permanent);
		this.d = d;
	}
	
	public AnimDLogMessage(AnimD d, long lifespan, long fadeout)
	{
		super(d.toString(), lifespan, fadeout);
		this.d = d;
	}
	
	public AnimDLogMessage(AnimD d, long lifespan, long fadeout, Color color)
	{
		super(d.toString(), lifespan, fadeout, color);
		this.d = d;
	}
	
	public AnimDLogMessage prepend(String prepend)
	{
		this.prepend = prepend;
		return this;
	}
	
	public AnimD getD()
	{
		return d;
	}
	
	public void setD(AnimD d)
	{
		this.d = d;
	}
	
	public String getMessage()
	{
		return prepend + d.toString();
	}
}