package com.barantschik.game.editor;

import com.barantschik.game.run.SceneObject;

public interface EditorObjectFactory
{
	public SceneObject create(double x, double y);
	public String getName();
}
