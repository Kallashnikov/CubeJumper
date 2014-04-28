package com.me.cubejumper;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * Creates a menu, with the title, and 2 buttons for starting the game and exiting.
 * 
 * @author Jacob
 */
public class MainMenu implements Screen
{
	private CubeJumper game;
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonPlay, buttonExit;
	private BitmapFont white, black;
	private Label heading;
	private SpriteBatch batch;
	private TweenManager tween;
	
	private int width = Gdx.graphics.getWidth();
	private int height = Gdx.graphics.getHeight();
	
	public MainMenu(CubeJumper game) {
		this.game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Table.drawDebug(stage);
		
		tween.update(delta);
		
		stage.act(delta);
		
		batch.begin();
			stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		
		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("ui/bluebutton9.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, width, height);
		
		//font setup
		white = new BitmapFont(Gdx.files.internal("ui/white.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("ui/black.fnt"), false);
		
		//buttons
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("bluebutton");
		buttonStyle.down = skin.getDrawable("bluebutton_highlighted");
		buttonStyle.over = skin.getDrawable("bluebutton_highlighted");
		buttonStyle.pressedOffsetX = 1;
		buttonStyle.pressedOffsetY = -1;
		buttonStyle.font = white;
		
		//button instantiation
		buttonPlay = new TextButton("Play", buttonStyle);
		buttonPlay.pad(5);
		buttonPlay.setColor(1, 1, 1, 0);
		buttonPlay.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new Level1(game));
				return true;
			}
		});
		
		buttonExit = new TextButton("EXIT", buttonStyle);
		buttonExit.pad(5);
		buttonExit.setColor(1, 1, 1, 0);
		buttonExit.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				Gdx.app.exit();
				return true;
			}
		});
		
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		
		heading = new Label(CubeJumper.TITLE, headingStyle);
		heading.setFontScale(2.5f);
		
		table.add(heading);
		table.getCell(heading).padBottom(75);
		table.row();
		table.add(buttonPlay);
		table.getCell(buttonPlay).padBottom(10);
		table.row();
		table.add(buttonExit);
		table.getCell(buttonExit).padLeft(25);
		table.debug();
		
		stage.addActor(table);
		
		//animations
		tween = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
		//table fade in
		Tween.from(table, ActorAccessor.ALPHA, .5f).target(0).start(tween);
		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tween);
		
//		Timeline.createParallel().beginParallel()
//			.pushPause(1)
//			.push(Tween.from(buttonPlay, ActorAccessor.ALPHA, .5f).target(0))
//			.push(Tween.from(buttonPlay, ActorAccessor.X, 1f).target(Gdx.graphics.getWidth() + 100)).end().start(tween);
		
		Timeline.createSequence().beginParallel()
			.pushPause(1)
			.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
			.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(buttonPlay, ActorAccessor.X, 2f).target(Gdx.graphics.getWidth() / 2.76f)).end().start(tween);
		
		Timeline.createParallel().beginParallel()
			.pushPause(3)
			.push(Tween.to(buttonExit, ActorAccessor.ALPHA, .5f).target(1))
			.push(Tween.to(buttonExit, ActorAccessor.X, 2f).target(Gdx.graphics.getWidth() / 2.68f)).end().start(tween);
		
		//heading fade in
		Timeline.createSequence().beginSequence()
			.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
			.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
			.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
			.end().repeat(Tween.INFINITY, 0).start(tween);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		atlas.dispose();
		white.dispose();
		black.dispose();
		skin.dispose();
	}

}
