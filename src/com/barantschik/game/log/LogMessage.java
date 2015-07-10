package com.barantschik.game.log;

import org.newdawn.slick.Color;

public class LogMessage
{
	public final static Color DEFAULT_COLOR = new Color(255, 255, 255);
	public final static long DEFAULT_FADEIN = 500, DEFAULT_LIFESPAN = 3000, DEFAULT_FADEOUT = 500;
	
	private String s;
	private long startTime, fadein = DEFAULT_FADEIN, lifespan = DEFAULT_LIFESPAN, fadeout = DEFAULT_FADEOUT;
	private Color color = DEFAULT_COLOR;
	private boolean permanent = false;
	
	public LogMessage(String s)
	{
		this.s = s;
		startTime = System.currentTimeMillis();
	}
	
	public LogMessage(String s, boolean permanent)
	{
		this.s = s;
		this.permanent = permanent;
		startTime = System.currentTimeMillis();
	}
	
	public LogMessage(String s, Color color)
	{
		this.s = s;
		this.color = color;
		startTime = System.currentTimeMillis();
	}
	
	public LogMessage(String s, Color color, boolean permanent)
	{
		this.s = s;
		this.color = color;
		this.permanent = permanent;
		startTime = System.currentTimeMillis();
	}
	
	public LogMessage(String s, long lifespan, long fadeout)
	{
		this.s = s;
		this.lifespan = lifespan;
		this.fadeout = fadeout;
		startTime = System.currentTimeMillis();
	}
	
	public LogMessage(String s, long lifespan, long fadeout, Color color)
	{
		this.s = s;
		this.lifespan = lifespan;
		this.fadeout = fadeout;
		this.color = color;
		startTime = System.currentTimeMillis();
	}
	
	public String getMessage()
	{
		return s;
	}
	
	public void setMessage(String s)
	{
		this.s = s;
	}
	
	public float getOpacity(long time)
	{
		if(fadein + startTime > time) return ((float) (time - startTime) / fadein);
		if(permanent || fadein + lifespan + startTime > time) return 1;
		if(fadein + lifespan + fadeout + startTime > time) return 1 - ((float) (time - fadein - lifespan - startTime) / fadeout);
		return -1;
	}

	public long getStartTime()
	{
		return startTime;
	}
	
	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}
	
	public long getLifespan()
	{
		return lifespan;
	}
	
	public void setLifespan(long lifespan)
	{
		this.lifespan = lifespan;
	}
	
	public long getFadein()
	{
		return fadein;
	}
	
	public void setFadein(long fadein)
	{
		this.fadein = fadein;
	}
	
	public long getFadeout()
	{
		return fadeout;
	}
	
	public void setFadeout(long fadeout)
	{
		this.fadeout = fadeout;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

	public boolean isPermanent()
	{
		return permanent;
	}
	
	public void setPermanent(boolean permanent)
	{
		this.permanent = permanent;
	}
}