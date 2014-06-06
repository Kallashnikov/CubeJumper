package com.me.cubejumper.levels;

import java.util.Random;

import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.objects.Spikes;

/**
 * Currently just level 2, just miss-named at the moment.
 * 
 * @author Jacob
 */
public class PlayScreen extends BaseLevel
{
	private static final int TEN = 10;
	
	Spikes[] spikeArray = new Spikes[100];
	Random random;
	
	public PlayScreen(CubeJumper game) {
		BaseLevel.game = game;
	}
	@Override
	public void render(float delta) {
		super.render(delta);
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void show() {
		super.show();
		
		random = new Random();
		
		for(int x = 0, y = 0, z = 0, offset = 0; x < 100; x++, y++) {
			int xPosi;
			if(y == 4){
				z = TEN * random.nextInt(5) + 20; //30
				if(z == 0)
					z = TEN;
				
				xPosi = x * TEN + z;
				
				if(xPosi < offset) {//x = 4, 70
					z = 0;
				}else{
					offset = z;
				}
				y = 0;
			}else{
				z = 0;
				xPosi = x * TEN;
			}
			
			spikeArray[x] = new Spikes(world, xPosi + offset, 1.5f);
		}
	}
	
	public World getWorld(){ 
		return world;
	}
	
	@Override
	public void hide() {
		super.hide();
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resume() {
		super.resume();
		
		CubeJumper.currentLevel = 2;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		for(int x = 0; x < 100; x++) {
			spikeArray[x].dispose();
		}
	}
}