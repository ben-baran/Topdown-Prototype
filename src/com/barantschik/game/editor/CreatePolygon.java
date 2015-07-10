package com.barantschik.game.editor;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Polygon;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.Scene;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.MouseHandler;

public class CreatePolygon extends Tool
{
	private final static int POINT_SIZE = 20;
	private ArrayList<double[]> points = new ArrayList<double[]>();
	
	public CreatePolygon(Editor parent)
	{
		super(parent);
	}
	
	public void update()
	{
		if(MouseHandler.pressed(0))
		{
			double[] mouseCoords = getParent().mouse();
			points.add(mouseCoords);
		}
		if(KeyHandler.pressed(Keyboard.KEY_GRAVE) && points.size() > 2)
		{
			Polygon p = new Polygon(points);
			p.getTags().add("wall");
			p.setShader(GUtil.getWhiteShader());
			p.setActive(false);
			Scene.put(p.toString(), p);
			Logger.log("Added new polygon");
			setDone(true);
		}
		if(KeyHandler.pressed(Keyboard.KEY_ESCAPE))
		{
			Logger.log("Canceled polygon creation");
			setDone(true);
		}
	}

	public void draw()
	{
		if(points.size() > 2)
		{
			Polygon p = new Polygon(points);
			p.setShader(GUtil.getWhiteShader());
			p.draw();
		}
		for(double[] d : points)
		{
			double[] t = GUtil.getCamera().process(new double[]{d[0], d[1], 1});
			GUtil.drawPoint(t[0] + GUtil.getX() / 2, t[1] + GUtil.getY() / 2, POINT_SIZE);
		}
	}
}
