package com.barantschik.game.util;

import java.util.ArrayList;

public class Locker<T> extends ArrayList<T>
{
	private ArrayList<T> locks = new ArrayList<T>();
	
	public int indexLocked(T part)
	{
		for(int i = 0; i < locks.size(); i++) if(locks.get(i) == part) return i;
		return -1;
	}
	
	public boolean hasLocked(T part)
	{
		return indexLocked(part) != -1;
	}
	
	public boolean add(T part)
	{
		if(!super.contains(part) && !hasLocked(part)) return super.add(part);
		return false;
	}
	
	public T remove(int index)
	{
		return null;
	}
	
	public void delete(T part)
	{
		int index = super.indexOf(part);
		if(index != -1) super.remove(index);
		else
		{
			int indexLocked = indexLocked(part);
			if(indexLocked != -1) locks.remove(indexLocked);
		}
	}
	
	public void putLockUntilRemoved(T part)
	{
		if(!super.contains(part) && !hasLocked(part)) locks.add(part);
	}
	
	public void clear()
	{
		super.clear();
		locks.clear();
	}

	public boolean lockedContains(T part)
	{
		if(super.contains(part))
		{
			delete(part);
			putLockUntilRemoved(part);
			return true;
		}
		return false;
	}
}