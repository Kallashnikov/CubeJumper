package com.me.cubejumper.levels;

import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.objects.Cubes;
import com.me.cubejumper.objects.Spikes;
import com.me.cubejumper.objects.powerups.PowerUpSloMo;

import box2dLight.RayHandler;

/**
 * Level 1 - starter level, extremely easy.
 * 
 * @author Jacob
 */
public class Level1 extends BaseLevel
{
	private static final int TEN = 10;
	
	private Spikes[] spikeArray = new Spikes[100];
	private Cubes[] cubeArray = new Cubes[100];
	private PowerUpSloMo sloMo;
	
	private RayHandler handler;
	
	public Level1(CubeJumper game) {
		BaseLevel.game = game;
	}
	
	public void show() {
		super.show();
		
		handler = new RayHandler(world);
		
		sloMo = new PowerUpSloMo(world, 75, 37.5f);
		
//		ConeLight light = new ConeLight(handler, 200, Color.BLUE, 1000, 20, 20, 30, 90);
//		light.setSoft(true);
//		light.setSoftnessLength(100f);
		
		genCubes(2, 50, 1.5f, 0);
		genCubes(3, 50, 11.5f, 2);
		genSpikes(4, 100, 1.5f, 0);
		genSpikes(6, 140, 1.5f, 4);
		genSpikes(8, 160, 11.5f, 6);
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param offset - start position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genSpikes(int times, int offset, float y, int pos){
		for(int x = 0 + pos; x < times; x++){
			spikeArray[x] = new Spikes(world, (x * TEN) + offset, y);
		}
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param offset - start position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genCubes(int times, int offset, float y, int pos){
		for(int x = 0 + pos; x < times; x++){
			cubeArray[x] = new Cubes(world, (x * TEN) + offset, y);
		}
	}
	
	public void render(float delta) {
		super.render(delta);
		
		if(isSlowMotion) {
			sloMo.activate(delta);
		}else
			world.step(TIMESTEP, VELOCITYIT, POSITIONIT);
		
//		handler.setCombinedMatrix(camera.combined);
//		handler.updateAndRender();
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public void hide() {
		super.hide();
	}

	public void pause() {
		super.pause();
	}

	public void resume() {
		super.resume();
	}

	public void dispose() {
		super.dispose();
		
		handler.dispose();
		for(int x = 0; x < 100; x++) {
			spikeArray[x].dispose();
		}
	}

}
