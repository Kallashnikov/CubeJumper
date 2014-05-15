package com.me.cubejumper.bases;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.me.cubejumper.ContactHandler;
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.Player;
import com.me.cubejumper.screens.PauseScreen;

/**
 * Basis for all level classes, it contains:
 * <p>
 * 
 * @param Player
 *            player
 * @param ContactHandler
 *            conHandler
 * @param World
 *            world
 * @param OrthographicCamera
 *            camera
 *            <p>
 * @param startTime
 *            - beginning of the highscore count
 * @param endTime
 *            - end of the highscore count
 * @param highScore
 *            - takes endTime - startTime
 *            <p>
 * 
 * @author Jacob
 */
public class BaseLevel implements Screen {
	private static final float TIMESTEP = 1 / 60f;
	private static final int VELOCITYIT = 8;
	private static final int POSITIONIT = 3;

	private float currentBgx;
	private long lastTimeBg;
	
	public int width, height;
	public static float startTime, endTime;
	public static float highScore = 0;
	public static boolean isSlowMotion = false;
	public static float slow = 120f;

	private static final float SLOWMOTION = 1 / slow;

	protected static CubeJumper game;

	private Player player;
	private ContactHandler conHandler;

	public World world;
	private Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;

	private Body groundBody;
	private BodyDef groundBodyDef;
	private PolygonShape groundBox;

	protected SpriteBatch batch;
	protected ButtonStyle buttonStyle;
	protected Button pauseButton;
	protected Stage stage;
	protected TextureAtlas atlas;
	protected Texture background;
	protected Skin skin;
	protected Table table;
	protected LabelStyle headingStyle;
	protected BitmapFont white, black;
	protected Label heading;
	protected InputMultiplexer multiIn;

	@Override
	public void show() {
		width = Gdx.graphics.getWidth() / 5;
		height = Gdx.graphics.getHeight() / 5;
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("ui/background.png"));
		
		
		atlas = new TextureAtlas("ui/pausebutton.pack");
		
		currentBgx = 800;
		lastTimeBg = TimeUtils.nanoTime();
		
		stage = new Stage();
		skin = new Skin();
		skin.addRegions(atlas);

		table = new Table(skin);
		table.setBounds(0, 0, width, height);

		// buttons
		buttonStyle = new ButtonStyle();
		buttonStyle.up = skin.getDrawable("uiskin_button_up");
		buttonStyle.down = skin.getDrawable("uiskin_button_down");
		buttonStyle.over = skin.getDrawable("uiskin_button_down");
		buttonStyle.pressedOffsetX = 1;
		buttonStyle.pressedOffsetY = -1;
		pauseButton = new Button(buttonStyle);
		pauseButton.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new PauseScreen(game));
				return true;
			}
		});
		pauseButton.pad(1);
		table.add(pauseButton);
		//table.debug();
		stage.addActor(table);

		headingStyle = new LabelStyle(white, Color.WHITE);

		camera = new OrthographicCamera(width, height);
		world = new World(new Vector2(15f, -100f), true);
		debugRenderer = new Box2DDebugRenderer();
		conHandler = new ContactHandler(game, world);

		player = new Player(world);
		multiIn = new InputMultiplexer();
		multiIn.addProcessor(stage);
		multiIn.addProcessor(player);
		Gdx.input.setInputProcessor(multiIn);

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
	
		if(TimeUtils.nanoTime() - lastTimeBg > 100000000){
			// move the separator 50px
			currentBgx -= 50;
			// set the current time to lastTimeBg
			lastTimeBg = TimeUtils.nanoTime();
		}

		// if the seprator reaches the screen edge, move it back to the first position
		if(currentBgx == 0){
			currentBgx = 800;
		}
		//Table.drawDebug(stage);
		stage.act(delta);
		
		batch.begin();
		stage.draw();
		batch.draw(background, currentBgx - 800, 0);
		batch.draw(background, currentBgx, 0);
		batch.end();
		
		if (isSlowMotion)
			world.step(SLOWMOTION, VELOCITYIT, POSITIONIT);
		else
			world.step(TIMESTEP, VELOCITYIT, POSITIONIT);

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
		try {
			FileWriter file1 = new FileWriter("position.txt");
				file1.write(player.getPosition().toString());
				file1.close();
			FileWriter file2 = new FileWriter("velocity.txt");
				file2.write(player.getVelocity().toString());
				file2.close();
			FileWriter file3 = new FileWriter("angvelocity.txt");
				file3.write((int)player.getAngVelocity());
				file3.close();
			FileWriter file4 = new FileWriter("canjump.txt");
				file4.write(Player.isCanJump());
				file4.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
