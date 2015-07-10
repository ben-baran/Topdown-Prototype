package com.barantschik.game.editor;

import org.newdawn.slick.Color;

public interface PropertySection
{
	public int update();
	public void draw(double x, double y, Color color);
	public double getHeight();
}