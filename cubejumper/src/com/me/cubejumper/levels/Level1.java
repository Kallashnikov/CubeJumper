package com.me.cubejumper.levels;

import com.badlogic.gdx.graphics.Color;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.Player;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.objects.Cubes;
import com.me.cubejumper.objects.Spikes;
import com.me.cubejumper.objects.powerups.PUSloMo;
import com.me.cubejumper.objects.powerups.PUSuperJump;
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
	private PUSloMo sloMo;
	private PUSuperJump spJump;
	
	private RayHandler handler;
	
	public Level1(CubeJumper game) {
		BaseLevel.game = game;
	}
	
	public Level1(CubeJumper game, int x) {
		super.show();
	}
	
	public void show() {
		super.show();
		
		handler = new RayHandler(world);
		
		sloMo = new PUSloMo(world, 75, 37.5f);
		spJump = new PUSuperJump(world, 25, 10);
		
//		ConeLight light = new ConeLight(handler, 200, Color.BLUE, 1000, 20, 20, 30, 90);
//		light.setSoft(true);
//		light.setSoftnessLength(100f);
		
		genCubes(2, 50, 1.5f, 0);
		genCubes(3, 50, 11.5f, 2);
		genSpikes(4, 100, 1.5f, 0);
		genSpikes(6, 130, 1.5f, 4);
		genCubes(4, 180, 11.7f, 3);
		genSpikes(9, 160, 21.7f, 6);
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
		
		if(isSuperJump) {
			spJump.activate(delta);
		}else{
			Player.superJump = 1;
			Player.yLimit = 55;
			PUSuperJump.count = 0;
		}
		
		//System.out.println(100 >> 1);
		
		sloMo.color(75, 37.5f, Color.BLUE, camera);
		spJump.color(25, 10, Color.ORANGE, camera);
		
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
