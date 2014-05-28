package com.me.cubejumper.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.me.cubejumper.CubeJumper;
import com.me.cubejumper.Player;
import com.me.cubejumper.objects.Cubes;
import com.me.cubejumper.objects.Spikes;
import com.me.cubejumper.screens.PauseScreen;
import com.me.cubejumper.utilities.ContactHandler;
import com.me.cubejumper.utilities.InputHandler;

/**
 * Basis for all level classes, it contains:
 * <p>
 * 
 * @param Player player
 * @param ContactHandler conHandler
 * @param World world
 * @param OrthographicCamera camera<p>
 * @param startTime - beginning of the highscore count
 * @param endTime - end of the highscore count
 * @param highScore - takes endTime - startTime<p>
 * 
 * @author Jacob
 */
public class BaseLevel implements Screen {
	protected static final float TIMESTEP = 1 / 60f;
	protected static final int VELOCITYIT = 8;
	protected static final int POSITIONIT = 3;
	private static final int TEN = 10;
	
//	private float currentBgx;
//	private long lastTimeBg;
	
	public int width, height;
	public static float startTime, endTime;
	public static float highScore = 0;
	public static boolean isPaused = false;
	public static boolean isSlowMotion = false;
	public static boolean isSuperJump = false;
	public static boolean playing = true;
	private Spikes[] spikeArray = new Spikes[100];
	private Cubes[] cubeArray = new Cubes[100];
	
	protected static CubeJumper game;
	
	protected Player player;
	private ContactHandler conHandler;
	private InputHandler inputHandler;
	
	protected World world;
	
	private Box2DDebugRenderer debugRenderer;
	protected OrthographicCamera camera;
	public ShapeRenderer shapeRender;
	
	private Body groundBody;
	private BodyDef groundBodyDef;
	private PolygonShape groundBox;
	
	protected SpriteBatch batch;
	protected ButtonStyle buttonStyle;
	protected Button pauseButton;
	protected Stage stage;
	protected TextureAtlas atlas;
	protected Texture background;
	protected Sprite bg;
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
		
		isSlowMotion = false;
		isSuperJump = false;
		BasePowerUp.count = 0;
		
		batch = new SpriteBatch();
		//background = new Texture(Gdx.files.internal("ui/background.png"));
		//bg = new Sprite(background);
		atlas = new TextureAtlas("ui/pausebutton.pack");
		
		//currentBgx = 800;
		//lastTimeBg = TimeUtils.nanoTime();
		
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
				pause();
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
		inputHandler = new InputHandler(world, player);
		
		multiIn = new InputMultiplexer();
		multiIn.addProcessor(stage);
		multiIn.addProcessor(inputHandler);
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
		
//		if(TimeUtils.nanoTime() - lastTimeBg > 100000000){
//			// move the separator 50px
//			currentBgx -= 50;
//			// set the current time to lastTimeBg
//			lastTimeBg = TimeUtils.nanoTime();
//		}

//		// if the seprator reaches the screen edge, move it back to the first position
//		if(currentBgx == 0){
//			currentBgx = 800;
//		}
		//Table.drawDebug(stage);
		stage.act(delta);
		
		batch.begin();
		stage.draw();
	//	batch.draw(bg, currentBgx - 800, 0);
	//	batch.draw(bg, currentBgx, 0);
		batch.end();
		
		player.update(camera, delta);
		camera.update();

		debugRenderer.render(world, camera.combined);
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param offset - start position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genSpikes(int times, int offset, float y, int pos){
		for(int x = 0 + pos; x < times; x++){
			spikeArray[x] = new Spikes(world, (x * TEN) + offset, y);
		}
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param offset - start position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genCubes(int times, int offset, float y, int pos){
		for(int x = 0 + pos; x < times; x++){
			cubeArray[x] = new Cubes(world, (x * TEN) + offset, y);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
		FileHandle file1 = Gdx.files.local("positionx.txt");
			file1.writeString(Float.toString(player.getPosition().x), false);
		FileHandle file2 = Gdx.files.local("positiony.txt");
			file2.writeString(Float.toString(player.getPosition().y), false);
		FileHandle file3 = Gdx.files.local("velocityx.txt");
			file3.writeString(Float.toString(player.getVelocity().x), false);
		FileHandle file4 = Gdx.files.local("velocityy.txt");
			file4.writeString(Float.toString(player.getVelocity().y), false);
		FileHandle file5 = Gdx.files.local("angvelocity.txt");
			file5.writeString(player.getAngVelocity(), false);
		FileHandle file6 = Gdx.files.local("canjump.txt");
			file6.writeString(Player.isCanJump(), false);
		
//		SaveData.playerPos = player.getPosition();
//		SaveData.playerXYVel = player.getVelocity();
//		SaveData.playerRot = player.getAngVelocity();
//		SaveData.pausedCanJump = Player.isCanJump();
		game.setScreen(new PauseScreen(game));
	}

	@Override
	public void resume() {
		game.setScreen(this);
		float x, y;
		Vector2 vec = new Vector2(0, 0);
		
		FileHandle file4 = Gdx.files.local("velocityx.txt");
		x = Float.parseFloat(file4.readString());
		FileHandle file5 = Gdx.files.local("velocityy.txt");
		y = Float.parseFloat(file5.readString());
		vec.set(x, y);
		player.setVelocity(vec);
	
		FileHandle file6 = Gdx.files.local("canjump.txt");
		Player.setCanJump(file6.readString());
	
		FileHandle file1 = Gdx.files.local("positionx.txt");
			x = Float.parseFloat(file1.readString());
		FileHandle file2 = Gdx.files.local("positiony.txt");
			y = Float.parseFloat(file2.readString());
		vec.set(x, y);
		FileHandle file3 = Gdx.files.local("angvelocity.txt");
			y = Float.parseFloat(file3.readString());
		player.setPositionAndAngVelocity(vec, y);
	}

	@Override
	public void dispose() {
		groundBox.dispose();
		debugRenderer.dispose();
		world.dispose();
		for(int x = 0; x < 100; x++) {
			spikeArray[x].dispose();
		}
		for(int x = 0; x < 100; x++) {
			cubeArray[x].dispose();
		}
	}

}
