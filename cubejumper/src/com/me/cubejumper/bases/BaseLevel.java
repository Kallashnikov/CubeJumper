package com.me.cubejumper.bases;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
 * @param TIMESTEP (float) - (1 / 60f)
 * @param VELOCITYIT (int) - 8
 * @param POSITIONIT (int) - 3
 * @param startTime - beginning of the highscore count
 * @param endTime - end of the highscore count
 * @param highScore - takes (endTime - startTime)<p>
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
	protected int spikePos = 0;
	protected int cubePos;
	
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

	public BaseLevel(){
	}
	
	public BaseLevel(int n) {
		resume();
	}
	
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
		
		player.update(camera, delta);
		camera.update();

		debugRenderer.render(world, camera.combined);
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param offset - starting x position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genSpikesSW(int times, int offset, float y, int pos){
		for(int x = 0 + pos; x < times; x++, spikePos++){
			spikeArray[spikePos] = new Spikes(world, (x * TEN) + offset, y);
		}
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param x - x position
	 * @param offset - starting y position in the world
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genSpikesUP(int times, int x, float offset, int pos){
		for(int y = 0 + pos; y < times; y++, spikePos++){
			spikeArray[spikePos] = new Spikes(world, x, (y * TEN) + offset);
		}
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param offset - starting x position in the world
	 * @param y - height position
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genCubesSW(int times, int offset, float y, int pos){
		for(int x = 0 + pos; x < times; x++, cubePos++){
			cubeArray[cubePos] = new Cubes(world, (x * TEN) + offset, y);
		}
	}
	
	/**
	 * @param times - number of times the loop will run
	 * @param x - x position
	 * @param offset - starting y position in the world
	 * @param pos - position in the array to start the loop <p>
	 * 
	 * @author Jacob
	 */
	public void genCubesUP(int times, int x, float offset, int pos){
		for(int y = 0 + pos; y < times; y++, cubePos++){
			cubeArray[cubePos] = new Cubes(world, x, (y * TEN) + offset);
		}
	}

	@Override
	public void resize(int width, int height) {
//		camera.viewportHeight = height;
//		camera.viewportWidth = width;
	}

	@Override
	public void hide() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void pause() {
//		posx = new BigDecimal(player.getPosition().x);
//			posx = dec.encrypt(posx);
//		posy = new BigDecimal(player.getPosition().y);
//			posy = dec.encrypt(posy);
//		velx = new BigDecimal(player.getPosition().x);
//			velx = dec.encrypt(velx);
//		vely = new BigDecimal(player.getPosition().y);
//			vely = dec.encrypt(vely);
//		ang = new BigDecimal(player.getAngVelocity());
//			ang = dec.encrypt(ang);
//		text.encrypt(Player.isCanJump());
			
		JSONObject obj = new JSONObject();
		obj.put("positionx", player.getPosition().x);
		obj.put("positiony", player.getPosition().y);
		obj.put("velocityx", player.getVelocity().x);
		obj.put("velocityy", player.getVelocity().y);
		obj.put("angvelocity", player.getAngVelocity());
		obj.put("canjump", Player.isCanJump());
		
		FileHandle file1 = Gdx.files.local("savedata/pause.json");
		file1.writeString(obj.toJSONString(), false);
		
//		new FileManager("savedata", "savedata/pause.json"
//				, "savedata/encrypted.json", 0);
//		FileManager.deleteFile("savedata/pause.json");
		
		game.setScreen(new PauseScreen(game));
	}

	@Override
	public void resume() {
		game.setScreen(this);
		
		JSONParser parser = new JSONParser();
		
//		new FileManager("savedata", "savedata/encrypted.json"
//				, "savedata/decrypted.json", 1);
//		FileManager.deleteFile("savedata/encrypted.json");
		
		try {
			FileHandle file1 = Gdx.files.local("savedata/pause.json");
			Object obj = parser.parse(new FileReader(file1.toString()));
			
			JSONObject jsonObject = (JSONObject) obj;
			
			//file1.delete();
			
			float positionx = Float.valueOf(jsonObject.get("positionx").toString());
			float positiony = Float.valueOf(jsonObject.get("positiony").toString());
			float angvel = Float.valueOf(jsonObject.get("angvelocity").toString());
			Vector2 coords = new Vector2(positionx, positiony);
			player.setPositionAndAngVelocity(coords, angvel);
			
			float velocityx = Float.valueOf(jsonObject.get("velocityx").toString());
			float velocityy = Float.valueOf(jsonObject.get("velocityy").toString());
			String canjump = (String) jsonObject.get("canjump");
			
			coords.set(velocityx, velocityy);
			player.setVelocity(coords);
			Player.setCanJump(canjump);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
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