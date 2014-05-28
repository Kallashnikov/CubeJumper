package com.me.cubejumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseScreen;
import com.me.cubejumper.levels.PlayScreen;
import com.me.cubejumper.utilities.ActorAccessor;

public class WinScreen extends BaseScreen
{
	private TextButton buttonMenu;
	private Label time;
	
	public WinScreen(CubeJumper game) {
		this.game = game;
	}

	@Override
	public void show() {
		super.show();
		
		buttonMenu = new TextButton("Main Menu", skin);
		buttonMenu.pad(5);
		buttonMenu.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				return true;
			}
		});
		
		//The Labels
		heading = new Label("Level Completed!", skin);
		heading.setFontScale(2f);
		time = new Label("" + PlayScreen.highScore, skin);
		time.setFontScale(1.5f);
		
		table.add(heading);
		table.getCell(heading).padBottom(10).row();
		table.add(time).row();
		table.add(buttonMenu).padBottom(10);
		table.debug();
		
		stage.addActor(table);
		
		//The table fade in Tween
		Tween.from(table, ActorAccessor.ALPHA, 2.5f).target(0).start(tween);
//		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tween);
		
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