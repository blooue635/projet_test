package com.mb.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import com.mb.main.render.Camera;
import com.mb.main.io.Window;
import com.mb.main.render.Model;
import com.mb.main.render.Shader;
import com.mb.main.render.Texture;
import com.mb.main.io.Timer;
import com.mb.main.world.TileRenderer;
import com.mb.main.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

public class Main {

	public Main(){
		Window.setCallbacks();
		
		if(!glfwInit()){
			System.err.println("GLFW failed to initilize!");
			System.exit(1);
		}

		Window win = new Window();
		win.setSize(800, 600);
		win.createWindow("Game");

        World world = new World();

		GL.createCapabilities();
		
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		glEnable(GL_TEXTURE_2D);


        TileRenderer tiles = new TileRenderer();


		float[] vertices = new float[]{
				-0.5f , 0.5f, 0,	//TOP LEFT		0
				0.5f, 0.5f, 0,		//TOP RIGHT		1
				0.5f, -0.5f, 0,		//BOTTON RIGHT	2
				-0.5f, -0.5f, 0,	//BOTTOM LEFT	3
		};

		float[] texture = new float[]{
				0,0,
				1,0,
				1,1,
				0,1,
		};

		int[] indices = new int[]{
				0,1,2,
				2,3,0
		};

		Model model = new Model(vertices,texture,indices);
		Shader shader = new Shader("shader");
		
		Texture tex = new Texture("./res/star.png");
		
		Matrix4f projection = new Matrix4f().ortho2D(-640/2, 640/2, -480/2, 480/2);


		Matrix4f scale = new Matrix4f()
                .translate(new Vector3f(150, 0, 0))
                .scale(16);
		
		Matrix4f target = new Matrix4f();
		
		camera.setPosition(new Vector3f(-150,0,0));
		
		double frameCap = 1.0/60.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
				
		while(!win.shouldClose()){
			boolean can_render = false;
			
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time+=passed;
			time = time_2;
			
			while(unprocessed >= frameCap){
				unprocessed -= frameCap;
				can_render = true;
				
				target = scale;
				
				
				
				if(win.getInput().isKeyReleased(GLFW_KEY_ESCAPE))
					System.out.println("escape");
					glfwSetWindowShouldClose(win.getWindow(), true);
				
				
				win.update();
				if(frame_time >= 1.0){
					frame_time = 0;
					System.out.println("FPS => "+frames);
					frames =0;
				}
					
			}
			
			if(can_render){
				glClear(GL_COLOR_BUFFER_BIT);

				shader.bind();
				shader.setUniform("sampler", 0);
				shader.setUniform("projection", camera.getProjection().mul(target));
				tex.bind(0);
				model.render();

                world.render(tiles, shader, camera);

				win.swapBuffer();
				frames++;
			}
		}
		glfwTerminate();
	}
	
	public static void main(String[] args) {
		new Main();

	}

}
