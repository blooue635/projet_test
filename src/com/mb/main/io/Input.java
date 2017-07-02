package com.mb.main.io;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
	private long window;
	
	private boolean keys[];
	private boolean buttons[];
	
	public Input(long win) {
		this.window = win;
		
		keys = new boolean[GLFW_KEY_LAST];
		
		for(int i = 0;i < GLFW_KEY_LAST;i++){
			keys[i] = false;
		}

	}
	
	

	public boolean isKeyDown(int key){
		return (glfwGetKey(window, key) == 1);
	}
	public boolean isKeyPressed(int key){
		return isKeyDown(key) && !keys[key];
	}
	public boolean isKeyReleased(int key){
		return !isKeyDown(key) && keys[key];
	}
	
	
	public boolean isMouseButtonDown(int button){
		return glfwGetMouseButton(window, button) == 1;
	}
	public boolean isButtonPressed(int button){
		return isMouseButtonDown(button) && !buttons[button];
	}
	public boolean isButtonReleased(int button){
		return !isMouseButtonDown(button) && buttons[button];
	}

	public void update() {
		for(int i = 32;i < GLFW_KEY_LAST;i++){
			keys[i] = isKeyDown(i);
		}
	}
	

}
