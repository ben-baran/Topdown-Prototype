package com.barantschik.game.anim;

public class Bezier
{
	public class BezierComp extends AnimFunc
	{
		private Bezier parent;
		private double value;
		private boolean done = false;
		
		public BezierComp(Bezier parent)
		{
			this.parent = parent;
		}
		
		public void update(double dt)
		{
			parent.update(dt);
		}

		public double output()
		{
			return value;
		}

		public boolean done()
		{
			return done;
		}

		public double detachVal()
		{
			return value;
		}

		public AnimD[] getDependencies()
		{
			AnimD[] arr = new AnimD[2 + points.length * points[0].length];
			for(int i = 0; i < points.length; i++) for(int j = 0; j < points[0].length; j++) arr[i * points[0].length + j] = points[i][j];
			arr[arr.length - 2] = t;
			arr[arr.length - 1] = duration;
			return arr;
		}
	}
	
	private BezierComp x = new BezierComp(this), y = new BezierComp(this), rot = new BezierComp(this);
	private AnimD[][] points;
	private AnimD t, duration;
	private int counter = 0;
	
	public Bezier(double[][] points, double start, double duration)
	{
		t = new AnimD(0);
		t.add(new LinearAnim(start, 1));
		this.points = new AnimD[points.length][points[0].length];
		for(int i = 0; i < points.length; i++) for(int j = 0; j < points[0].length; j++) this.points[i][j] = new AnimD(points[i][j]);
		this.duration = new AnimD(duration);
	}
	
	public Bezier(AnimD[][] points, double start, AnimD duration)
	{
		t = new AnimD(0);
		t.add(new LinearAnim(start, 1));
		this.points = points;
		this.duration = duration;
	}
	
	private void update(double dt)
	{
		if(++counter > 2)
		{
			counter = 0;
			t.update();
			duration.update();
			for(int i = 0; i < points.length; i++) for(int j = 0; j < points[0].length; j++) points[i][j].update();
			
			if(t.get() > duration.get())
			{
				x.done = true;
				y.done = true;
				rot.done = true;
			}
			else
			{
				double[][] rawPoints = new double[points.length][points[0].length];
				for(int i = 0; i < points.length; i++) for(int j = 0; j < points[0].length; j++) rawPoints[i][j] = points[i][j].get();
				double higherCoef = t.get() / duration.get(), lowerCoef = 1 - higherCoef;
				double[][] line = process(rawPoints, higherCoef, lowerCoef);
				x.value = line[0][0] * lowerCoef + line[1][0] * higherCoef;
				y.value = line[0][1] * lowerCoef + line[1][1] * higherCoef;
				rot.value = -Math.atan2(line[1][1] - line[0][1], line[1][0] - line[0][0]);
			}
		}
	}
	
	private double[][] process(double[][] rawPoints, double higherCoef, double lowerCoef)
	{
		if(rawPoints.length == 2) return rawPoints;
		double[][] newPoints = new double[rawPoints.length - 1][rawPoints[0].length];
		for(int i = 0; i < newPoints.length; i++) for(int j = 0; j < newPoints[0].length; j++) newPoints[i][j] = rawPoints[i + 1][j] * higherCoef + rawPoints[i][j] * lowerCoef;
		return process(newPoints, higherCoef, lowerCoef);
	}
	
	public BezierComp getX()
	{
		return x;
	}
	
	public BezierComp getY()
	{
		return y;
	}
	
	public BezierComp getRot()
	{
		return rot;
	}

	public AnimD[][] getPoints()
	{
		return points;
	}
	
	public void setPoints(AnimD[][] points)
	{
		this.points = points;
	}
	
	public AnimD getT()
	{
		return t;
	}
	
	public void setT(AnimD t)
	{
		this.t = t;
	}
	
	public AnimD getDuration()
	{
		return duration;
	}
	
	public void setDuration(AnimD duration)
	{
		this.duration = duration;
	}
	
}