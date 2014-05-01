package com.me.cubejumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.me.cubejumper.ActorAccessor;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseScreen;
import com.me.cubejumper.levels.Level1;
import com.me.cubejumper.levels.PlayScreen;

/** 
 * On the player's death(i.e. makes contact with a spike), this screen
 * is created and displayed.<p>
 *
 *	@author Jacob
 */
public class DeathScreen extends BaseScreen
{
	private Level1 level1;

	private TextButton buttonRestart;
	private Label time;
	
	public DeathScreen(CubeJumper game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		super.show();
		
		buttonRestart = new TextButton("Retry?", buttonStyle);
		buttonRestart.pad(5);
		buttonRestart.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new Level1(game));
				return true;
			}
		});
		
		//labels
		heading = new Label("GAME OVER!", headingStyle);
		heading.setFontScale(2f);
		time = new Label("" + PlayScreen.highScore, headingStyle);
		time.setFontScale(1.5f);
		
		table.add(heading);
		table.getCell(heading).padBottom(10);
		table.row();
		table.add(time);
		table.getCell(time).padBottom(10);
		table.row();
		table.add(buttonRestart);
		table.debug();
		
		stage.addActor(table);
		
		//table fade in
		Tween.from(table, ActorAccessor.ALPHA, 1f).target(0).start(tween);
		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tween);
		
		//heading fade in
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