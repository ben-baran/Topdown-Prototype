package com.barantschik.game.editor;

import org.lwjgl.input.Keyboard;

import com.barantschik.game.log.Logger;
import com.barantschik.game.run.Scene;
import com.barantschik.game.run.SceneObject;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.MouseHandler;

public class CreateObject extends Tool
{
	private EditorObjectFactory factory;
	
	public CreateObject(Editor parent, EditorObjectFactory factory)
	{
		super(parent);
		this.factory = factory;
	}
	
	public void update()
	{
		if(KeyHandler.has(Keyboard.KEY_ESCAPE))
		{
			Logger.log("Canceled " + factory.getName() + " creation");
			setDone(true);
		}
		else if(MouseHandler.pressed(0))
		{
			double[] curCoords = getParent().mouse();
			SceneObject s = factory.create(curCoords[0], curCoords[1]);
			s.setActive(false);
			Scene.put(s.toString(), s);
			Logger.log("Added new " + factory.getName());
			setDone(true);
		}
	}

	public void draw()
	{
		double[] curCoords = getParent().mouse();
		factory.create(curCoords[0], curCoords[1]).draw();
	}
}