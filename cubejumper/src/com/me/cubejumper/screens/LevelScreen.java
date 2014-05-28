package com.me.cubejumper.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.bases.BaseScreen;
import com.me.cubejumper.levels.*;
import com.me.cubejumper.utilities.ActorAccessor;

public class LevelScreen extends BaseScreen
{
	private TextButton playButton, backButton;
	private List list;
	private ScrollPane scrollPane;
	
	public LevelScreen(CubeJumper game){
		this.game = game;
	}
	
	public void show(){
		super.show();
		
		playButton = new TextButton("Play", skin);
		playButton.pad(5);
		playButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				switch(list.getSelectedIndex()) {
				case 0:
					CubeJumper.currentLevel = 0;
					game.setScreen(new Level1(game));
					break;
				case 1:
					game.setScreen(new PlayScreen(game));
					break;
				}
				return true;
			}
		});
		
		backButton = new TextButton("Back", skin);
		backButton.pad(5);
		backButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MainMenu(game));
				return true;
			}
			@Override
			public void enter(InputEvent event, float x, float y, int pointer,
					Actor fromActor) {
				Tween.from(backButton, ActorAccessor.X, .75f).target(backButton.getX() - 10).start(tween);
				return;
			}
		});
		
		//The Labels
		heading = new Label("Select Level", skin);
		heading.setFontScale(2f);
		
		list = new List(new String[] {"Level 1", "Level 2", "Level 3"}, skin);
		scrollPane = new ScrollPane(list, skin);
		
		table.add().width(table.getWidth() / 3);
		table.add(heading).width(table.getWidth() / 3);
		table.add().width(table.getWidth() / 3).row();
		table.add(scrollPane).left().padLeft(5).expandY();
		table.add(playButton);
		table.add(backButton).bottom().right();
		table.debug();
		
		stage.addActor(table);
		
		//The table fade in Tween
		Tween.from(table, ActorAccessor.ALPHA, 2.5f).target(0).start(tween);
//		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tween);
		
		//The Heading fade in
		Timeline.createSequence().beginSequence()
		.push(Tween.from(heading, ActorAccessor.RGB, .75f).target(1, 0, 1))
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