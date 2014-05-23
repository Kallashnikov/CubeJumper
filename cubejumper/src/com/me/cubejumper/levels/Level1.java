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
 * Level 1 - starter level, extremely easy.
 * 
 * @author Jacob
 */
public class Level1 extends BaseLevel
{
	private PUSloMo sloMo;
	private PUSuperJump spJump;
	private FinishFlag flag;
	
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
		
		genCubes(2, 50, 1.5f, 0);
		genCubes(3, 50, 11.5f, 2);
		
		genSpikes(4, 100, 1.5f, 0);
		genSpikes(6, 130, 1.5f, 4);
		
		genCubes(4, 180, 11.7f, 3);
		
		genSpikes(9, 160, 21.7f, 6);
		
		genCubes(5, 250, 1.5f, 4);
		
		genSpikes(12, 210, 1.5f, 9);
		
		genCubes(10, 280, 11.5f, 5);
		
		genSpikes(13, 250, 21.5f, 12);
		
		genCubes(12, 310, 21.5f, 10);
		
		genSpikes(14, 290, 31.5f, 13);
		
		genCubes(15, 360, 11.5f, 12);
		
		genSpikes(16, 350, 21.5f, 15);
		genSpikes(26, 230, 1.5f, 15);
		
		flag = new FinishFlag(world, 350, 1.5f);
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
		
		light.setPosition(player.getPosition().x, ((int)player.getPosition().y) + 50);
		
		sloMo.color(75, 37.5f, Color.BLUE, camera);
		spJump.color(25, 10, Color.ORANGE, camera);
		
		handler.updateAndRender();
		handler.setCombinedMatrix(camera.combined);
		handler.setAmbientLight(.1f);
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
	}

	public void resume() {
		super.resume();
	}

	public void dispose() {
		super.dispose();
		
		handler.dispose();
	}
}