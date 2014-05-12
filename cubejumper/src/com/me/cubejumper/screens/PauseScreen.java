package com.me.cubejumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseScreen;
import com.me.cubejumper.levels.Level1;
import com.me.cubejumper.utilities.ActorAccessor;

public class PauseScreen extends BaseScreen
{
	public static Vector2 playerPos;
	public static Vector2 playerXYVel;
	public static float playerRot;
	public static boolean pausedCanJump;
	
	Level1 level;
	
	private TextButton resumeButton;
	
	public PauseScreen(CubeJumper game){
		this.game = game;
	}
	
	public void show(){
		super.show();
		
		level = new Level1(game);
		
		resumeButton = new TextButton("Resume game", buttonStyle);
		resumeButton.pad(5);
		resumeButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				level.resume();
				return true;
			}
		});
		
		//The Labels
		heading = new Label("PAUSED", headingStyle);
		heading.setFontScale(2f);
		
		table.add(heading);
		table.getCell(heading).padBottom(10);
		table.row();
		table.add(resumeButton);
		table.debug();
		
		stage.addActor(table);
		
		//The table fade in Tween
		Tween.from(table, ActorAccessor.ALPHA, 1f).target(0).start(tween);
		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tween);
		
		//The Heading fade in
		Timeline.createSequence().beginSequence()
		.push(Tween.from(heading, ActorAccessor.RGB, .75f).target(1, 0, 0))
		.end().repeat(Tween.INFINITY, 0).start(tween);
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
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
