package com.barantschik.game.draw;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import com.barantschik.game.util.Resources;

public class Shader
{
	private int vertex, fragment;
	private int program;
	private FloatBuffer buff16;
	private Map<String, Integer> uniformLocations = new HashMap<String, Integer>();
	private int prevProgramUsed = 0;
	
	public Shader(String shaderName)
	{
		this(Resources.getShaderPath(shaderName));
	}
	
	public Shader(String[] paths)
	{
		this(paths[0], paths[1]);
	}
	
	public Shader(String vertPath, String fragPath)
	{
		vertex = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertex, Resources.read(vertPath));
		glCompileShader(vertex);
		
		fragment = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragment, Resources.read(fragPath));
		glCompileShader(fragment);
		
		program = glCreateProgram();
		glAttachShader(program, vertex);
		glAttachShader(program, fragment);
		glLinkProgram(program);
		glValidateProgram(program);
	}
	
	public void clean()
	{
		glDetachShader(vertex, program);
		glDetachShader(fragment, program);
		glDeleteShader(vertex);
		glDeleteShader(fragment);
		glDeleteProgram(program);
	}
	
	public void use()
	{
		prevProgramUsed = GL11.glGetInteger(GL_CURRENT_PROGRAM);
		glUseProgram(program);
	}
	
	private void set()
	{
		prevProgramUsed = GL11.glGetInteger(GL_CURRENT_PROGRAM);
		glUseProgram(program);
	}
	
	public void stop()
	{
		glUseProgram(prevProgramUsed);
	}
	
	public void delete()
	{
		glDeleteProgram(program);
	}
	
	public void cacheUniform(String name)
	{
		uniformLocations.put(name, glGetUniformLocation(program, name));
	}
	
	public int getUniformLoc(String name)
	{
		Integer loc = uniformLocations.get(name);
		if(loc == null)
		{
			loc = glGetUniformLocation(program, name);
			uniformLocations.put(name, loc);
			return loc;
		}
		return loc;
	}

	public void setUniformi(int loc, int val)
	{
		if(loc != -1)
		{
			set();
			glUniform1i(loc, val);
			stop();
		}
	}
	
	public void setUniformi(int loc, int val1, int val2)
	{
		if(loc != -1)
		{
			set();
			glUniform2i(loc, val1, val2);
			stop();
		}
	}
	
	public void setUniformi(int loc, int val1, int val2, int val3)
	{
		if(loc != -1)
		{
			set();
			glUniform3i(loc, val1, val2, val3);
			stop();
		}
	}
	
	public void setUniformi(int loc, int val1, int val2, int val3, int val4)
	{
		if(loc != -1)
		{
			set();
			glUniform4i(loc, val1, val2, val3, val4);
			stop();
		}
	}
	
	public void setUniformi(int loc, int[] vec)
	{
		if(vec.length == 1) setUniformi(loc, vec[0]);
		else if(vec.length == 2) setUniformi(loc, vec[0], vec[1]);
		else if(vec.length == 3) setUniformi(loc, vec[0], vec[1], vec[2]);
		else if(vec.length == 4) setUniformi(loc, vec[0], vec[1], vec[2], vec[3]);
	}
	
	public void setUniformf(int loc, float val)
	{
		if(loc != -1)
		{
			set();
			glUniform1f(loc, val);
			stop();
		}
	}
	
	public void setUniformf(int loc, float val1, float val2)
	{
		if(loc != -1)
		{
			set();
			glUniform2f(loc, val1, val2);
			stop();
		}
	}
	
	public void setUniformf(int loc, float val1, float val2, float val3)
	{
		if(loc != -1)
		{
			set();
			glUniform3f(loc, val1, val2, val3);
			stop();
		}
	}
	
	public void setUniformf(int loc, float val1, float val2, float val3, float val4)
	{
		if(loc != -1)
		{
			set();
			glUniform4f(loc, val1, val2, val3, val4);
			stop();
		}
	}
	
	public void setUniformf(int loc, float[] vec)
	{
		if(vec.length == 1) setUniformf(loc, vec[0]);
		else if(vec.length == 2) setUniformf(loc, vec[0], vec[1]);
		else if(vec.length == 3) setUniformf(loc, vec[0], vec[1], vec[2]);
		else if(vec.length == 4) setUniformf(loc, vec[0], vec[1], vec[2], vec[3]);
	}
	
	public void setUniformMat(int loc, boolean transposed, Matrix4f mat)
	{
		if(loc != -1)
		{
			set();
			if(buff16 == null) buff16 = BufferUtils.createFloatBuffer(16);
			buff16.clear();
			mat.store(buff16);
			buff16.flip();
			glUniformMatrix4(loc, transposed, buff16);
			stop();
		}
	}
}