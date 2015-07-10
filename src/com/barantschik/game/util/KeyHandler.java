package com.barantschik.game.util;

import org.lwjgl.input.Keyboard;

public abstract class KeyHandler
{
	private static Locker<Integer> keys = new Locker<Integer>();
	
	public static void update()
	{
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState()) keys.add(Keyboard.getEventKey());
			else keys.delete(Keyboard.getEventKey());
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