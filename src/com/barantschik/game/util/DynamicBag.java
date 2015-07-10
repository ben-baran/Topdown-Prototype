package com.barantschik.game.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class DynamicBag<T> implements Iterable<T>
{
	private Set<Listener<DynamicBag<T>, String>> listeners = new HashSet<Listener<DynamicBag<T>, String>>();
	private List<T> list = new ArrayList<T>();
	
	public boolean contains(T t)
	{
		return list.contains(t);
	}
	
	public T get(int i)
	{
		return list.get(i);
	}
	
	public void add(T t)
	{
		list.add(t);
		sendMessage("Added " + t);
	}
	
	public void add(int i, T t)
	{
		list.add(i, t);
		sendMessage("Added " + t);
	}
	
	public void remove(int i)
	{
		T removed = list.get(i);
		list.remove(i);
		sendMessage("Removed " + removed);
	}
	
	public void remove(T t)
	{
		list.remove(t);
		sendMessage("Removed " + t);
	}
	
	public int size()
	{
		return list.size();
	}
	
	public Iterator<T> iterator()
	{
		return new DynamicBagIterator<T>(this, list);
	}
	
	public void addListener(Listener<DynamicBag<T>, String> l)
	{
		listeners.add(l);
	}
	
	public void sendMessage(String message)
	{
		for(Listener<DynamicBag<T>, String> l : listeners) l.inform(this, message);
	}
}