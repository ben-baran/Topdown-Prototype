package com.barantschik.game.run.topdown;

import java.util.Iterator;

import org.newdawn.slick.Color;

import com.barantschik.game.anim.AnimD;
import com.barantschik.game.draw.Box;
import com.barantschik.game.draw.Polygon;
import com.barantschik.game.log.LogMessage;
import com.barantschik.game.log.Logger;
import com.barantschik.game.physics.Physics;
import com.barantschik.game.run.Scene;
import com.barantschik.game.run.SceneObject;

public class Enemy extends Box
{
	private final double ENEMY_SPEED = 3, FLOCK_DIST = 200, FLOCK_COEF = .2;
	private int health = 1;
	
	public Enemy(double centerX, double centerY, double sizeX, double sizeY, double theta)
	{
		super(centerX, centerY, sizeX, sizeY, theta);
		setImportance(3);
	}
	
	public Enemy(AnimD centerX, AnimD centerY, AnimD sizeX, AnimD sizeY, AnimD theta)
	{
		super(centerX, centerY, sizeX, sizeY, theta);
		setImportance(3);
	}
	
	public void update()
	{
		SceneObject player = Scene.get("player");
		double angle = -Math.atan2(player.getAttrAD("centerY").get() - getCenterY().get(), player.getAttrAD("centerX").get() - getCenterX().get());
		getTheta().set(angle);
		getCenterX().adjust(ENEMY_SPEED * Math.sin(Math.PI / 2 + angle));
		getCenterY().adjust(ENEMY_SPEED * Math.cos(Math.PI / 2 + angle));
		
		boolean hit = false;
		Iterator<SceneObject> bullets = Scene.getByTag("player_bullet").iterator();
		while(bullets.hasNext())
		{
			Box bullet = (Box) bullets.next();
			if(!bullet.getTags().contains("deactivated") && Physics.intersecting(bullet, this) != null)
			{
				Logger.log(new LogMessage("Hit enemy!", new Color(0.8f, 0.8f, 0.4f)));
				hit = true;
				health--;
				bullets.remove();
				Scene.remove(bullet);
			}
			
		}
		
		if(health <= 0)
		{
			Scene.remove(this);
			Logger.log(new LogMessage("Killed enemy!", new Color(0.8f, 0.4f, 0.8f)));
		}
		
		for(SceneObject object : Scene.getByTag("enemy")) if(object != this)
		{
			Enemy e = (Enemy) object;
			double dx = getCenterX().get() - e.getCenterX().get(), dy = getCenterY().get() - e.getCenterY().get();
			if(dx * dx + dy * dy < FLOCK_DIST)
			{
				getCenterX().adjust(dx * FLOCK_COEF);
				getCenterY().adjust(dy * FLOCK_COEF);
			}
		}
		
		super.update();
		for(int i = 0; i < Physics.NUM_ITERATIONS_LOW; i++) for(SceneObject object : Scene.getByTag("wall", "player"))
		{
			double[] intersection = null;
			if(object instanceof Box) intersection = Physics.intersecting(this, (Box) object);
			else if(object instanceof Polygon) intersection = Physics.intersecting(getCoords(), ((Polygon) object).getCoords());
			if(intersection != null)
			{
				getCenterX().adjust(intersection[0]);
				getCenterY().adjust(intersection[1]);
				super.update();
			}
		}
		
		super.update();
	}
	
	public double getAttrD(String name)
	{
		if(name.equals("ENEMY_SPEED")) return ENEMY_SPEED;
		return super.getAttrD(name);
	}
}
