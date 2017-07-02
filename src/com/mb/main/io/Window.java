package com.mb.main.io;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;


public class Window {
	private long window;
		
	private int width,height;
	private boolean fullscreen;
	
	private Input input;
	
	public static void setCallbacks(){
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));

	}
	
	public Window() {
		setSize(640, 480);
		this.fullscreen = false;

	}
	
	
	public void createWindow(String title){
			window = glfwCreateWindow(width,
					height,
					title,
					fullscreen ? glfwGetPrimaryMonitor() : 0,
					0);

		
		if(window == 0)
			throw new IllegalStateException("Failed to create window!");
		
		if(!fullscreen){
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(window, 
					(vid.width()-width)/2, 
					(vid.height()-height)/2);
			glfwShowWindow(window);
		}
		
		glfwMakeContextCurrent(window);
		this.input = new Input(window);
	}

	public void update(){
		input.update();
		glfwPollEvents();
	}
	
	
	
	public boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}

	public void swapBuffer(){
		glfwSwapBuffers(window);
	}
	
	public void setSize(int width,int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}


	public long getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
	
	
	

}
