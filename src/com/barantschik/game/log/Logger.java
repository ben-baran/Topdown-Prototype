package com.barantschik.game.log;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import com.barantschik.game.draw.GUtil;

public abstract class Logger
{
	private static ArrayList<LogMessage> messages = new ArrayList<LogMessage>();
	
	public static void log(String s)
	{
		messages.add(0, new LogMessage(s));
	}
	
	public static void log(String s, int index)
	{
		messages.add(index, new LogMessage(s));
	}
	
	public static void log(LogMessage message)
	{
		messages.add(0, message);
	}
	
	public static void log(LogMessage message, int index)
	{
		messages.add(index, message);
	}
	
	public static void draw(TrueTypeFont font)
	{
		long curTime = System.currentTimeMillis();
		double height = 0;
		for(int i = 0; i < messages.size(); i++)
		{
			float opacity = messages.get(i).getOpacity(curTime);
			if(opacity == -1) messages.remove(i--);
			else
			{
				Color c = messages.get(i).getColor();
				GUtil.drawString(messages.get(i).getMessage(), font, 0, height, new Color(c.r, c.g, c.b, opacity));
				height += opacity * font.getHeight(messages.get(i).getMessage());
			}
		}
	}
	
	public static int getNumMessages()
	{
		return messages.size();
	}
	
	public static int getMessageIndex(LogMessage message)
	{
		return messages.indexOf(message);
	}
}