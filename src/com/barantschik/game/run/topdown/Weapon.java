package com.barantschik.game.run.topdown;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.run.Scene;

public class Weapon extends Box
{
	private static final double WEAPON_X = 80, WEAPON_Y = 10;
	private boolean switched = false;
	
	public Weapon()
	{
		setCenterX(new AnimD(0));
		setCenterY(new AnimD(0));
		setSizeX(new AnimD(WEAPON_X));
		setSizeY(new AnimD(WEAPON_Y));
		setTheta(new AnimD(0));
		setupBox();
		
		setLayer(-3);
		setImportance(3);
		setShader(GUtil.getWhiteShader());
	}
	
	public void update()
	{
		Box player = (Box) Scene.get("player");
		
		double sin = Math.sin(player.getTheta().get()), cos = Math.cos(player.getTheta().get());
		double[] v1 = {cos, -sin}, v2 = {sin, cos};
		
		switched = !switched;
		
		if(switched)
		{
			getCenterX().set(player.getCenterX().get() + 50 * v1[0] - 70 * v2[0]);
			getCenterY().set(player.getCenterY().get() + 50 * v1[1] - 70 * v2[1]);
			getTheta().set(player.getTheta().get() + 0.8);
		}
		else
		{
			getCenterX().set(player.getCenterX().get() + 50 * v1[0] + 70 * v2[0]);
			getCenterY().set(player.getCenterY().get() + 50 * v1[1] + 70 * v2[1]);
			getTheta().set(player.getTheta().get() - 0.8);
		}
		
		super.update();
	}
}
