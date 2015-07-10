package com.barantschik.game.util;

import java.util.Iterator;
import java.util.List;

public class DynamicBagIterator<T> implements Iterator<T>
{
	private DynamicBag<T> bag;
	private Iterator<T> iterator;
	private T last;
	
	public DynamicBagIterator(DynamicBag<T> bag, List<T> list)
	{
		this.bag = bag;
		iterator = list.iterator();
	}

	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	public T next()
	{
		last = iterator.next();
		return last;
	}

	public void remove()
	{
		if(last != null) bag.sendMessage("Removed " + last);
		iterator.remove();
	}
}
