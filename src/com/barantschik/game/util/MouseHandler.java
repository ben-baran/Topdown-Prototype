package com.barantschik.game.util;

import org.lwjgl.input.Mouse;

public abstract class MouseHandler
{
	private static Locker<Integer> keys = new Locker<Integer>();
	
	public static void update()
	{
		while(Mouse.next())
		{
			if(Mouse.getEventButtonState()) keys.add(Mouse.getEventButton());
			else keys.delete(Mouse.getEventButton());
		}
	}
	
	public static boolean has(int key)
	{
		return keys.contains(key);
	}
	
	public static boolean pressed(int key)
	{
		return keys.lockedContains(key);
	}
	
	public static Locker<Integer> keys()
	{
		return keys;
	}
	
	public static boolean cameBefore(int key1, int key2)
	{
		return keys.indexOf(key1) < keys.indexOf(key2);
	}
}
