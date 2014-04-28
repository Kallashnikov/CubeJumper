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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/** 
 * On the player's death(i.e. makes contact with a spike), this screen
 * is created and displayed.<p>
 *
 *	@author Jacob
 */
public class DeathScreen implements Screen 
{
	private int width = Gdx.graphics.getWidth() / 5;
	private int height = Gdx.graphics.getHeight() / 5;
	
	private CubeJumper game;
	private PlayScreen play;
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton buttonRestart;
	private BitmapFont white, black;
	private Label heading, time;
	private SpriteBatch batch;
	private TweenManager tween;
	
	public DeathScreen(CubeJumper game) {
		this.game = game;
	}
	
	@Override
	public void show() {
		batch = new SpriteBatch();
		
		stage = new Stage();
		
		atlas = new TextureAtlas("ui/bluebutton9.pack");
		skin = new Skin();
		skin.addRegions(atlas);
		
		table = new Table(skin);
		table.setBounds(0, 0, width, height);
		
		//font
		white = new BitmapFont(Gdx.files.internal("ui/white.fnt"), false);
		
		//buttons
		TextButtonStyle buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("bluebutton");
		buttonStyle.down = skin.getDrawable("bluebutton_highlighted");
		buttonStyle.over = skin.getDrawable("bluebutton_highlighted");
		buttonStyle.pressedOffsetX = 1;
		buttonStyle.pressedOffsetY = -1;
		buttonStyle.font = white;
		
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		
		//labels
		heading = new Label("GAME OVER!", headingStyle);
		heading.setFontScale(2f);
		time = new Label("" + PlayScreen.highScore, headingStyle);
		time.setFontScale(1.5f);
		
		table.add(heading);
		table.getCell(heading).padBottom(10);
		table.row();
		table.add(time);
		table.debug();
		
		stage.addActor(table);
		
		tween = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());
		
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
		stage.dispose();
		batch.dispose();
	}

}
