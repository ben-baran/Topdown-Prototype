package com.barantschik.game.editor;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.barantschik.game.draw.GUtil;
import com.barantschik.game.util.DynamicBag;
import com.barantschik.game.util.KeyHandler;
import com.barantschik.game.util.Resources;

public class PropertySectionStringUpdateList implements PropertySection
{
	private static final Color DEFAULT_COLOR = new Color(0.12f, 0.12f, 0.12f), CHOSEN_COLOR = new Color(0.1f, 0.6f, 0.3f);
	private static final double SELECTED_WIDTH = 30, TRIM = 10;
	
	private String title;
	private DynamicBag<String> bag;
	private ArrayList<PropertySectionStringUpdate> sections = new ArrayList<PropertySectionStringUpdate>();
	private int selected = 0;
	
	public PropertySectionStringUpdateList(String title, DynamicBag<String> bag)
	{
		this.title = title;
		this.bag = bag;
		
		for(int i = 0; i < bag.size(); i++) sections.add(new PropertySectionStringUpdate("    #" + (i + 1), bag.get(i)));
		sections.add(new PropertySectionStringUpdate("    #" + (bag.size() + 1), ""));
	}

	public int update()
	{
		String curString = sections.get(selected).getInput();
		int move = sections.get(selected).update();
		if(selected == bag.size())
		{
			if(!sections.get(selected).getInput().isEmpty())
			{
				bag.add(sections.get(selected).getInput());
				sections.add(new PropertySectionStringUpdate("    #" + (bag.size() + 1), ""));
			}
		}
		else
		{
			if(sections.get(selected).getInput().isEmpty())
			{
				bag.remove(selected);
				sections.remove(selected);
				for(int i = selected; i < sections.size(); i++)
				{
					sections.get(i).setName("    #" + (i + 1));
				}
			}
			else if(!curString.equals(sections.get(selected).getInput()))
			{			
				bag.remove(selected);
				bag.add(selected, sections.get(selected).getInput());
			}
		}
		
		if(!KeyHandler.has(Keyboard.KEY_LCONTROL)) selected += move;
		if(selected < 0)
		{
			selected = 0;
			return move;
		}
		if(selected > bag.size())
		{
			selected = bag.size();
			return move;
		}
		return KeyHandler.has(Keyboard.KEY_LCONTROL) ? move : 0;
	}

	public void draw(double x, double y, Color color)
	{
		GUtil.drawRect(x, y, x + PropertyEditor.WIDTH, y + PropertyEditor.LINE_HEIGHT * (1 + bag.size()), color);
		double yVal = y + PropertyEditor.LINE_HEIGHT / 2 - Resources.getDefaultFont().getHeight(title) / 2;
		GUtil.drawString(title, Resources.getDefaultFont(), x, yVal, PropertyEditor.TEXT_COLOR);
		for(int i = 0; i < sections.size(); i++)
		{
			y += PropertyEditor.LINE_HEIGHT;
			sections.get(i).draw(x, y, color);

			yVal = y + PropertyEditor.LINE_HEIGHT / 2;
			GUtil.drawRect(x + TRIM, yVal - TRIM, x + SELECTED_WIDTH, yVal + TRIM, i == selected ? CHOSEN_COLOR : DEFAULT_COLOR);
		}
	}

	public double getHeight()
	{
		return PropertyEditor.LINE_HEIGHT * (1 + sections.size());
	}
}