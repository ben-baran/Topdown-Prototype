package com.barantschik.game.editor;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.barantschik.game.draw.GUtil;
import com.barantschik.game.log.Logger;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.Locker;
import com.barantschik.game.util.Resources;

public class PropertySectionStringUpdate implements PropertySection
{
	private final static int DEFAULT_MAX_STRING_LENGTH = 50;
	
	private int maxStringLength = DEFAULT_MAX_STRING_LENGTH;
	private String name, input = "";
	
	public PropertySectionStringUpdate(String name)
	{
		this.name = name;
	}
	
	public PropertySectionStringUpdate(String name, String input)
	{
		this.name = name;
		this.input = input;
	}
	
	public int update()
	{
		Locker<Integer> keys = KeyHandler.keys();
		for(int i = 0; i < keys.size() && input.length() <= maxStringLength; i++)
		{
			int curKey = keys.get(i);
			String name = Keyboard.getKeyName(curKey);
			if(name.length() == 1 && KeyHandler.pressed(curKey))
			{
				i--;
				if(!KeyHandler.has(Keyboard.KEY_LSHIFT)) name = name.toLowerCase();
				input += name;
			}
			else if(name.equals("MINUS") && KeyHandler.has(Keyboard.KEY_LSHIFT) && KeyHandler.pressed(curKey))
			{
				i--;
				input += "_";
			}
		}
		if(KeyHandler.has(Keyboard.KEY_LSHIFT) && KeyHandler.pressed(Keyboard.KEY_BACK)) input = "";
		else if(KeyHandler.pressed(Keyboard.KEY_BACK) && input.length() > 0) input = input.substring(0, input.length() - 1);
		
		if(KeyHandler.pressed(Keyboard.KEY_UP)) return -1;
		if(KeyHandler.pressed(Keyboard.KEY_DOWN)) return 1;
		return 0;
	}

	public void draw(double x, double y, Color color)
	{
		GUtil.drawRect(x, y, x + PropertyEditor.WIDTH, y + PropertyEditor.LINE_HEIGHT, color);
		double yVal = y + PropertyEditor.LINE_HEIGHT / 2 - Resources.getDefaultFont().getHeight(name + ": " + input) / 2;
		GUtil.drawString(name + ": ", Resources.getDefaultFont(), x, yVal, PropertyEditor.TEXT_COLOR);
		double editX = x + Resources.getDefaultFont().getWidth(name + ": ");
		GUtil.drawString(input, Resources.getDefaultFont(), editX, yVal, PropertyEditor.EDIT_COLOR);
	}

	public double getHeight()
	{
		return PropertyEditor.LINE_HEIGHT;
	}

	public String getInput()
	{
		return input;
	}


	public String getName()
	{
		return name;
	}
	

	public void setName(String name)
	{
		this.name = name;
	}
}