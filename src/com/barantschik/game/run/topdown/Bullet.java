package com.barantschik.game.run.topdown;

import com.barantschik.game.anim.LinearAnim;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.GUtil;
import com.barantschik.game.draw.Polygon;
import com.barantschik.game.log.Logger;
import com.barantschik.game.physics.Physics;
import com.barantschik.game.run.Scene;
import com.barantschik.game.run.SceneObject;

public class Bullet extends Box
{
	private static final double BULLET_X = 30, BULLET_Y = 4.5, BULLET_SPEED = 48;
	private static final int LIFE_SPAN = 120;
	
	private int age = 1;
	
	public Bullet(double centerX, double centerY, double theta, double speedX, double speedY)
	{
		super(centerX, centerY, BULLET_X, BULLET_Y, theta);
		getTags().add("player_bullet");
		getCenterX().add(new LinearAnim(0, speedX + BULLET_SPEED * Math.sin(Math.PI / 2 + theta)));
		getCenterY().add(new LinearAnim(0, speedY + BULLET_SPEED * Math.cos(Math.PI / 2 + theta)));
		setShader(GUtil.getWhiteShader());
		setLayer(-1);
	}
	
	public void update()
	{
		if(age > LIFE_SPAN) Scene.remove(this);
			
		if(!getTags().contains("deactivated"))
		{			
			++age;
			super.update();
			
			for(SceneObject object : Scene.getByTag("wall"))
			{
				double[] intersection = null;
				if(object instanceof Box) intersection = Physics.intersecting(this, (Box) object);
				else if(object instanceof Polygon) intersection = Physics.intersecting(getCoords(), ((Polygon) object).getCoords());
				if(intersection != null)
				{
					getTags().add("deactivated");
					getCenterX().clear();
					getCenterY().clear();
				}
			}
		}
	}
	
	public double getAttrD(String name)
	{
		if(name.equals("BULLET_X")) return BULLET_X;
		if(name.equals("BULLET_Y")) return BULLET_Y;
		if(name.equals("BULLET_SPEED")) return BULLET_SPEED;
		if(name.equals("LIFE_SPAN")) return LIFE_SPAN;
		if(name.equals("age")) return age;
		return super.getAttrD(name);
	}
}