package com.me.cubejumper.levels;

import com.badlogic.gdx.graphics.Color;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.Player;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.objects.FinishFlag;
import com.me.cubejumper.objects.powerups.PUSloMo;
import com.me.cubejumper.objects.powerups.PUSuperJump;
import box2dLight.ConeLight;
import box2dLight.RayHandler;

/**
 * Level 1 - starter level, slighty difficult.
 * 
 * @author Jacob
 */
public class Level1 extends BaseLevel
{
	private PUSloMo sloMo;
	private PUSuperJump spJump;
	private RayHandler handler;
	private static ConeLight light;
	
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
		
		light = new ConeLight(handler, 500, Color.WHITE, 500, 0, 0, 270, 60);
		light.setSoft(true);
		light.setSoftnessLength(100f);
		
		genCubesSW(2, 50, 1.5f, 0);
		genCubesSW(3, 50, 11.5f, 2);
		
		genSpikesSW(4, 100, 1.5f, 0);
		genSpikesSW(6, 130, 1.5f, 4);
		
		genCubesSW(4, 180, 11.7f, 3);
		
		genSpikesSW(9, 160, 21.7f, 6);
		
		genCubesSW(5, 250, 1.5f, 4);
		
		genSpikesSW(12, 210, 1.5f, 9);
		
		genCubesSW(10, 280, 11.5f, 5);
		
		genSpikesSW(13, 250, 21.5f, 12);
		
		genCubesSW(12, 310, 21.5f, 10);
		
		genSpikesSW(14, 290, 31.5f, 13);
		
		genCubesSW(15, 360, 11.5f, 12);
		
		genSpikesSW(16, 350, 21.5f, 15);
		genSpikesSW(26, 230, 1.5f, 15);
		
		new FinishFlag(world, 350, 1.5f);
	}
	
	public void render(float delta) {
		super.render(delta);
		
		if(isSlowMotion) {
			sloMo.activate(delta);
		}
		
		if(isSuperJump) {
			spJump.activate(delta);
		}else{
			Player.superJump = 1;
			Player.yLimit = 55;
			PUSuperJump.count = 0;
		}
		
		light.setPosition(player.getPosition().x, ((int)player.getPosition().y) + 50);
		
		sloMo.color(75, 37.5f, Color.BLUE, camera);
		spJump.color(25, 10, Color.ORANGE, camera);
		
		handler.updateAndRender();
		handler.setCombinedMatrix(camera.combined);
		handler.setAmbientLight(.1f);
		
		batch.begin();
		stage.draw();
		batch.end();
	}
	
	public static Color getLightColor() {
		return	light.getColor();
	}
	
	public static void setLightColor(Color color) {
		light.setColor(color);
	}
	
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	public void hide() {
		super.hide();
	}

	public void pause() {
		super.pause();
		
		CubeJumper.currentLevel = 0;
	}

	public void resume() {
		super.resume();
	}

	public void dispose() {
		super.dispose();
		
		handler.dispose();
	}
}