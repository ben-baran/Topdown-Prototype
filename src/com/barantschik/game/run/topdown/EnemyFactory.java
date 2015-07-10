package com.barantschik.game.run.topdown;

import com.barantschik.game.draw.GUtil;
import com.barantschik.game.editor.EditorObjectFactory;
import com.barantschik.game.run.SceneObject;

public class EnemyFactory implements EditorObjectFactory
{
	public SceneObject create(double x, double y)
	{
		Enemy enemy = new Enemy(x, y, 12, 4, 0);
		enemy.setShader(GUtil.getColorShader());
		enemy.shaderPut("color", new float[]{(float) Math.random(), (float) Math.random(), (float) Math.random()});
		enemy.getTags().add("enemy");
		return enemy;
	}

	public String getName()
	{
		return "enemy";
	}
}
