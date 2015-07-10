package com.barantschik.game.run.topdown;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.run.Scene;

public class Weapon extends Box
{
	private static final double WEAPON_X = 80, WEAPON_Y = 10;
	
	public Weapon()
	{
		setCenterX(new AnimD(0));
		setCenterY(new AnimD(0));
		setSizeX(new AnimD(WEAPON_X));
		setSizeY(new AnimD(WEAPON_Y));
		setTheta(new AnimD(0));
		setupBox();
		
		setLayer(-3);
		setShader(GUtil.getWhiteShader());
	}
	
	public void update()
	{
		Box player = (Box) Scene.get("player");
		
		getCenterX().set(player.getCenterX().get() + );
		getCenterY().set(player.getCenterY().get());
		getTheta().set(player.getTheta().get());
		super.update();
	}
}
