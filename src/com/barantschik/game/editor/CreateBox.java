package com.barantschik.game.editor;

import org.lwjgl.input.Keyboard;

import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.log.Logger;
import com.barantschik.game.run.Scene;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.MouseHandler;

public class CreateBox extends Tool
{
	private double[] clicked;
	
	public CreateBox(Editor parent)
	{
		super(parent);
	}
	
	public void update()
	{
		if(KeyHandler.has(Keyboard.KEY_ESCAPE))
		{
			Logger.log("Canceled box creation");
			setDone(true);
		}
		else if(clicked == null && MouseHandler.has(0)) clicked = getParent().mouse();
		else if(clicked != null && !MouseHandler.has(0))
		{
			double[] curCoords = getParent().mouse(),
					 average = {(curCoords[0] + clicked[0]) / 2, (curCoords[1] + clicked[1]) / 2},
					 size = {(curCoords[0] - clicked[0]) / 2, (curCoords[1] - clicked[1]) / 2};
			if(size[0] != 0 && size[1] != 0)
			{				
				Box b = new Box(average[0], average[1], size[0], size[1], 0);
				b.getTags().add("wall");
				b.setShader(GUtil.getWhiteShader());
				b.setActive(false);
				Scene.put(b.toString(), b);
				Logger.log("Added new box");
				setDone(true);
			}
			else clicked = null;
		}
	}

	public void draw()
	{
		if(clicked != null)
		{
			double[] curCoords = getParent().mouse(),
					 average = {(curCoords[0] + clicked[0]) / 2, (curCoords[1] + clicked[1]) / 2},
					 size = {(curCoords[0] - clicked[0]) / 2, (curCoords[1] - clicked[1]) / 2};
			Box b = new Box(average[0], average[1], size[0], size[1], 0);
			b.setShader(GUtil.getWhiteShader());
			b.draw();
		}
	}
}