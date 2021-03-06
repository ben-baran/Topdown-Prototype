package com.barantschik.game.run.topdown;

import com.barantschik.game.draw.Box;
import com.barantschik.game.run.Scene;

public class PointGesture extends GestureType
{
	private long duration;
	private double theta;
	
	public PointGesture(long duration)
	{
		setDuration(duration);
		setTheta(((Box) Scene.get("player")).getTheta().get());
	}

	public long getDuration()
	{
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public double getTheta()
	{
		return theta;
	}

	public void setTheta(double theta)
	{
		this.theta = theta;
	}
}
