package com.barantschik.game.run.topdown;

import java.util.ArrayList;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Shader;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.MouseHandler;

public class GestureTracker extends SceneObject
{
	private static int MAX_SAMPLES_LINE = 60;
	private static double MAX_DISTANCE_LINE = 25;
	
	private ArrayList<double[]> points = new ArrayList<double[]>();
	private Shader redShader = new Shader("uniformColor");
	
	public GestureTracker()
	{
		redShader.setUniformf(redShader.getUniformLoc("color"), 1.0f, 0.5f, 0.5f);
	}
	
	public void update()
	{
		if(MouseHandler.has(0)) points.add(GUtil.getCamera().mouseOnScreen());
		else
		{
			if(points.size() > 0)
			{
				//Check if matches line
				if(points.size() <= MAX_SAMPLES_LINE)
				{
					double x1 = points.get(points.size() - 1)[0], x2 = points.get(0)[0];
					double y1 = points.get(points.size() - 1)[1], y2 = points.get(0)[1];
					double dy = y2 - y1, dx = x2 - x1, term = x2 * y1 - y2 * x1, distance = Math.sqrt(dy * dy + dx * dx);
					
					boolean good = true;
					for(double[] point : points) if(Math.abs(dy * point[0]  -dx * point[1] + term) / distance > MAX_DISTANCE_LINE)
					{
						good = false;
						Logger.log("Not a line: " + Math.abs(dy * point[0]  -dx * point[1] + term) / distance);
						break;
					}
					
					if(good) Logger.log("It's a line");
				}
			}
			points = new ArrayList<double[]>();
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
