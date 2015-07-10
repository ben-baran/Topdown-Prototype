package com.barantschik.game.editor;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.barantschik.game.draw.GUtil;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.Resources;

public class PropertySectionText implements PropertySection
{
	private String message;
	
	public PropertySectionText(String message)
	{
		this.message = message;
	}
	
	public int update()
	{
		if(KeyHandler.pressed(Keyboard.KEY_UP)) return -1;
		if(KeyHandler.pressed(Keyboard.KEY_DOWN)) return 1;
		return 0;
	}
	
	@SuppressWarnings("deprecation")
	public void draw(double x, double y, Color color)
	{
		GUtil.drawRect(x, y, x + PropertyEditor.WIDTH, y + PropertyEditor.LINE_HEIGHT, color);
		double yVal = y + PropertyEditor.LINE_HEIGHT / 2 - Resources.getDefaultFont().getHeight(message) / 2;
		GUtil.drawString(message, Resources.getDefaultFont(), x, yVal, PropertyEditor.TEXT_COLOR);
	}
	
	public double getHeight()
	{
		return PropertyEditor.LINE_HEIGHT;
	}
}