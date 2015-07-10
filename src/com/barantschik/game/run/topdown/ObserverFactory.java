package com.barantschik.game.run.topdown;

import com.barantschik.game.editor.EditorObjectFactory;
import com.barantschik.game.run.SceneObject;

public class ObserverFactory implements EditorObjectFactory
{
	public SceneObject create(double x, double y)
	{
		return new Observer(x, y, 0);
	}

	public String getName()
	{
		return "observer";
	}
}