package com.barantschik.game.run;

import com.barantschik.game.anim.AnimFunc;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.log.Logger;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.MouseHandler;
import com.barantschik.game.util.Resources;

public abstract class Run
{
	private long t1;
	
	public Run(String title)
	{
		GUtil.createDisplay(title, true, true);
		t1 = System.currentTimeMillis();
	}
	
	public abstract boolean runCondition();
	public abstract void update(double dt);
	public abstract void cleanUp();
	
	public void update()
	{
		double dt = Math.min((System.currentTimeMillis() - t1) * 60 / 1000.0, 2);
		GUtil.clear();
		MouseHandler.update();
		KeyHandler.update();
		AnimFunc.updateAll(dt);
		t1 = System.currentTimeMillis();
		update(dt);
		Logger.draw(Resources.getDefaultFont());
		GUtil.sync();
	}
}