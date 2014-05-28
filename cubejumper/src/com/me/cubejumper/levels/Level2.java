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
 * Level 2 - climb the wall.
 * 
 * @author Jacob
 */
public class Level2 extends BaseLevel
{
	private PUSloMo sloMo;
	private PUSuperJump spJump;
	private FinishFlag flag;

	private RayHandler handler;
	private static ConeLight light;

	public Level2(CubeJumper game) {
		BaseLevel.game = game;
	}

	public Level2(CubeJumper game, int x) {
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

		genCubesUP(10, 50, 50, 0);

		flag = new FinishFlag(world, 25, 1.5f);
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
