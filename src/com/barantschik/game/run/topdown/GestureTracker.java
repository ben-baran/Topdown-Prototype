package com.barantschik.game.run.topdown;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Shader;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.MouseHandler;
import com.barantschik.game.util.VecOp;

public class GestureTracker extends SceneObject
{
	private static double MIN_SAMPLE_DIFFERENCE = 2;
	private static double MAX_DISTANCE_POINT = 30;
	private static int MAX_SAMPLES_POINT = 6;
	private static int MAX_SAMPLES_CHECK_LINE = 60;
	private static double MIN_LINE_LENGTH = 100, MAX_ERROR_LINE = 25;
	
	private double[] lastPoint = {Double.NaN, Double.NaN};
	private long gestureBegin = 0;
	private ArrayList<double[]> points = new ArrayList<double[]>();
	private Shader redShader = new Shader("uniformColor");
	
	private Queue<GestureType> gestures = new LinkedList<GestureType>();
	
	public GestureTracker()
	{
		redShader.setUniformf(redShader.getUniformLoc("color"), 1.0f, 0.5f, 0.5f);
	}
	
	public Queue<GestureType> getGestures()
	{
		return gestures;
	}
	
	public void update()
	{
		if(MouseHandler.has(0))
		{
			double[] newPoints = GUtil.getCamera().mouseOnScreen();
			if(points.size() == 0) gestureBegin = System.currentTimeMillis();
			if(Double.isNaN(lastPoint[0]) || VecOp.magnitude(new double[]{newPoints[0] - lastPoint[0], newPoints[1] - lastPoint[1]}) > MIN_SAMPLE_DIFFERENCE)	
			{
				points.add(newPoints);
				lastPoint = newPoints;
			}
		}
		else
		{
			if(points.size() > 0)
			{
				double x1 = points.get(points.size() - 1)[0], x2 = points.get(0)[0];
				double y1 = points.get(points.size() - 1)[1], y2 = points.get(0)[1];
				double dy = y2 - y1, dx = x2 - x1, term = x2 * y1 - y2 * x1, distance = Math.sqrt(dy * dy + dx * dx);
				
				if(points.size() <= MAX_SAMPLES_POINT && distance < MAX_DISTANCE_POINT)
				{
					Logger.log("It's a point");
					gestures.add(new PointGesture(System.currentTimeMillis() - gestureBegin));
				}
				else if(distance > MIN_LINE_LENGTH && points.size() <= MAX_SAMPLES_CHECK_LINE)
				{	
					boolean good = true;
					double inaccuracy = 0;
					for(double[] point : points)
					{
						double pointLineDistance = Math.abs(dy * point[0] - dx * point[1] + term) / distance;
						if(pointLineDistance > MAX_ERROR_LINE)
						{							
							good = false;
							Logger.log("Not a line: " + pointLineDistance);
							break;
						}
						else inaccuracy += pointLineDistance;
					}
					
					if(good)
					{
						Logger.log("It's a line");
						gestures.add(new LineGesture(System.currentTimeMillis() - gestureBegin, inaccuracy));
					}
				}
			}
			
			points = new ArrayList<double[]>();
			lastPoint = new double[]{Double.NaN, Double.NaN};
		}
	}

	public void draw()
	{
		for(int i = 0; i < points.size() - 1; i++) GUtil.drawLine(points.get(i)[0], points.get(i)[1], points.get(i + 1)[0], points.get(i + 1)[1]);
		if(points.size() > 1) GUtil.drawLine(points.get(0)[0], points.get(0)[1], points.get(points.size() - 1)[0], points.get(points.size() - 1)[1], redShader);
	}

	public int getAttrI(String name)
	{
		return 0;
	}

	public Object getAttr(String name)
	{
		return null;
	}

	public double getAttrD(String name)
	{
		return 0;
	}

	public AnimD getAttrAD(String name)
	{
		return null;
	}
}
