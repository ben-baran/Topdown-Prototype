package com.barantschik.game.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.barantschik.game.util.Pair;

public abstract class Scene
{
	private static HashMap<String, SceneObject> objects = new HashMap<String, SceneObject>();
	private static HashMap<String, Set<SceneObject>> tags = new HashMap<String, Set<SceneObject>>();
	
	private static HashMap<String, SceneObject> putBuffer = new HashMap<String, SceneObject>(), 
												shallowPutBuffer = new HashMap<String, SceneObject>(),
												removeBuffer = new HashMap<String, SceneObject>(),
												shallowRemoveBuffer = new HashMap<String, SceneObject>();
	
	private static Comparator<Entry<String, SceneObject>> updateCompare = new Comparator<Entry<String, SceneObject>>()
	{
		public int compare(Entry<String, SceneObject> o1, Entry<String, SceneObject> o2)
		{
			return ((Double) o1.getValue().getImportance()).compareTo(o2.getValue().getImportance());
		}
	};
	
	private static Comparator<Entry<String, SceneObject>> drawCompare = new Comparator<Entry<String, SceneObject>>()
	{
		public int compare(Entry<String, SceneObject> o1, Entry<String, SceneObject> o2)
		{
			return ((Double) o1.getValue().getLayer()).compareTo(o2.getValue().getLayer());
		}
	};
	
	public static void update()
	{
		clearBuffers();
		List<Entry<String, SceneObject>> updateList = new ArrayList<Entry<String, SceneObject>>();
		updateList.addAll(objects.entrySet());
		Collections.sort(updateList, updateCompare);
		for(Entry<String, SceneObject> e : updateList)
		{
			if(e.getValue() != null && e.getValue().isActive()) e.getValue().update();
		}
	}
	
	private static void clearBuffers()
	{
		for(String name : objects.keySet())
		{
			SceneObject o = objects.get(name);
			if(o.getPreviousNames().size() != 0) shallowPut(o.getName(), o);
			for(String previousName : o.getPreviousNames())
			{
				shallowRemove(previousName, o);
			}
			o.getPreviousNames().clear();
			for(Pair<Boolean, String> tagChange : o.getTagChanges())
			{
				if(tags.get(tagChange.b) == null) tags.put(tagChange.b, new HashSet<SceneObject>());
				if(tagChange.a) tags.get(tagChange.b).add(o);
				else tags.get(tagChange.b).remove(o);
			}
			o.getTagChanges().clear();
		}
		
		for(String s : shallowRemoveBuffer.keySet())
		{
			objects.remove(s);
		}
		shallowRemoveBuffer.clear();
		
		for(String s : shallowPutBuffer.keySet())
		{
			SceneObject object = shallowPutBuffer.get(s);
			object.setName(s);
			objects.put(s, object);
		}
		shallowPutBuffer.clear();
		
		for(String s : removeBuffer.keySet())
		{
			SceneObject object = removeBuffer.get(s);
			objects.remove(object.getName());
			for(String tag : object.getTags()) tags.get(tag).remove(object);
		}
		removeBuffer.clear();
		
		for(String s : putBuffer.keySet())
		{
			SceneObject object = putBuffer.get(s);
			object.setName(s);
			objects.put(s, object);
			for(String tag : object.getTags())
			{
				if(tags.get(tag) == null) tags.put(tag, new HashSet<SceneObject>());
				tags.get(tag).add(object);
			}
		}
		putBuffer.clear();
	}
	
	public static void put(String s, SceneObject object)
	{
		putBuffer.put(s, object);
	}
	
	public static void shallowPut(String s, SceneObject object)
	{
		shallowPutBuffer.put(s, object);
	}
	
	public static void remove(SceneObject object)
	{
		removeBuffer.put(object.getName(), object);
	}
	
	public static void remove(String name, SceneObject object)
	{
		removeBuffer.put(name, object);
	}
	
	public static void shallowRemove(SceneObject object)
	{
		shallowRemoveBuffer.put(object.getName(), object);
	}
	
	public static void shallowRemove(String name, SceneObject object)
	{
		shallowRemoveBuffer.put(name, object);
	}
	
	public static SceneObject get(String name)
	{
		return objects.get(name);
	}
	
	public static Set<SceneObject> getByTag(String name)
	{
		Set<SceneObject> objects = tags.get(name);
		if(objects == null)
		{
			tags.put(name, new HashSet<SceneObject>());
			return tags.get(name);
		}
		return objects;
	}
	
	public static Set<SceneObject> getByTag(String... names)
	{
		Set<SceneObject> objects = new HashSet<SceneObject>();
		for(String name : names) objects.addAll(getByTag(name));
		return objects;
	}
	
	public static Map<String, SceneObject> getAll()
	{
		return objects;
	}
	
	public static void draw()
	{
		clearBuffers();
		List<Entry<String, SceneObject>> drawList = new ArrayList<Entry<String, SceneObject>>();
		drawList.addAll(objects.entrySet());
		Collections.sort(drawList, drawCompare);
		for(Entry<String, SceneObject> e : drawList) e.getValue().draw();
	}

	public static void setAllActive(boolean active)
	{
		for(String name : objects.keySet()) objects.get(name).setActive(active);
	}
}