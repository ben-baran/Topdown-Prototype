package com.barantschik.game.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public abstract class Resources
{
	public static final String DEFAULT_FONT = "./res/fonts/LiberationMono-Regular.ttf";
	public static final float DEFAULT_FONT_SIZE = 24;
	
	private static HashMap<String, TrueTypeFont> fonts = new HashMap<String, TrueTypeFont>();
	
	public static TrueTypeFont getDefaultFont()
	{
		return getFont(DEFAULT_FONT);
	}
	
	public static TrueTypeFont getFont(String path)
	{
		if(fonts.get(path) == null)
		{			
			try
			{
				fonts.put(path, new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(DEFAULT_FONT_SIZE), true));
			}
			catch (FontFormatException | IOException e)
			{
				e.printStackTrace();
			}
		}
		return fonts.get(path);
	}
	
	public static String[] getShaderPath(String name)
	{
		String base = "./res/shaders/" + name + "/" + name + ".";
		return new String[]{base + "v", base + "f"};
	}
	
	public static String read(String path)
	{
		try
		{
			StringBuilder str = new StringBuilder();
			List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			for(String s : lines) str.append(s).append("\n");
			return str.toString();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Texture getTexture(String path, String format)
	{
		try
		{
			return TextureLoader.getTexture(format, new FileInputStream(new File(path)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}