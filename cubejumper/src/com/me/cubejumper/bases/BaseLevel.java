package com.me.cubejumper.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.ContactHandler;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.InputHandler;
import com.me.cubejumper.Player;

/** 
 * Basis for all level classes, it contains:<p>
 * 
 *	@param Player player
 *	@param ContactHandler conHandler
 *	@param World world
 *	@param OrthographicCamera camera<p>
 *	@param startTime - beginning of the highscore count
 *	@param endTime - end of the highscore count
 *	@param highScore - takes endTime - startTime<p>
 *
 *	@author Jacob
 */
public class BaseLevel implements Screen
{
	protected static final float TIMESTEP = 1 / 60f;
	protected static final int VELOCITYIT = 8;
	protected static final int POSITIONIT = 3;
	
	public int width, height;
	public static float startTime, endTime;
	public static float highScore = 0;
	public static boolean isSlowMotion = false;
	public static boolean isSuperJump = false;
	
	protected static CubeJumper game;
	
	private Player player;
	private ContactHandler conHandler;
	private InputHandler inputHandler;
	
	protected World world;
	private Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;
	public ShapeRenderer shapeRender;
	
	private Body groundBody;
	private BodyDef groundBodyDef;
	private PolygonShape groundBox;
	
	@Override
	public void show() {
		width = Gdx.graphics.getWidth() / 5;
		height = Gdx.graphics.getHeight() / 5;
		
		isSlowMotion = false;
		isSuperJump = false;
		BasePowerUp.count = 0;
		
		camera = new OrthographicCamera(width, height);
		
		world = new World(new Vector2(15f, -100f), true);
		debugRenderer = new Box2DDebugRenderer();
		conHandler = new ContactHandler(game, world);
		
		player = new Player(world);
		inputHandler = new InputHandler(world, player);
		Gdx.input.setInputProcessor(inputHandler);
		
		world.setContactListener(conHandler);
		
		startTime = System.nanoTime();
		
		groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0, 1);
		
		groundBody = world.createBody(groundBodyDef);
		groundBody.setUserData(0);
		
		groundBox = new PolygonShape();
		groundBox.setAsBox((camera.viewportWidth) * 100, .5f);
		
		groundBody.createFixture(groundBox, 0.0f);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		player.update(camera, delta);
		camera.update();
		
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		groundBox.dispose();
		debugRenderer.dispose();
		world.dispose();
	}

}
