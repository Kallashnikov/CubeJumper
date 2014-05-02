package com.me.cubejumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.levels.PlayScreen;

/**
 * Controls movement, updating, rotation, and the player body.
 * 
 * @param Vector2 movement<p>
 * @param speed (float)
 * @param canJump (boolean) - can the player jump
 * @param xLimit (int) - limits x velocity
 * @param yLimit (int) - limits y velocity
 * @param available (boolean) - checks if there is an Accelerometer
 * @param currX (float)
 * @param currY	(float)
 * @param currZ	(float)
 * @param posX (float)
 * @param posY (float)
 * @param posZ (float)
 * @param isDevMode (boolean)
 * 
 * @author Jacob
 */
public class Player implements InputProcessor
{
	private int width = Gdx.graphics.getWidth() / 5;
	private int height = Gdx.graphics.getHeight() / 5;
	private Vector2 movement = new Vector2(0, 0);
	private float speed = 50;
	public static boolean canJump = true;
	public static int xLimit = 60, yLimit = 55;
	
	public boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
	public float currX, currY, currZ;
	public float posX, posY, posZ;
	private boolean isDevMode = false;
	
	private PlayScreen play;
	
	private World world;
	
	private BodyDef playerDef;
	private FixtureDef fixDef;
	private Body body;
	
	public Player(World world) {
		this.world = world;
		
		//Body definition
		playerDef = new BodyDef();
		playerDef.type = BodyType.DynamicBody;
		playerDef.position.set(-50, 10);
		
		//Player's rectangle
		PolygonShape rect = new PolygonShape();
		rect.setAsBox(5, 5);
		
		//fixture definition
		fixDef = new FixtureDef();
		fixDef.shape = rect;
		fixDef.density = 0.8f;
		fixDef.friction = 0;
		fixDef.restitution = 0;
		
		body = world.createBody(playerDef);
		body.setUserData(1);
		body.createFixture(fixDef);
		
		posX = Gdx.input.getAccelerometerX();
		posY = Gdx.input.getAccelerometerY();
		posZ = Gdx.input.getAccelerometerZ();
	}
	
	public void update(Camera camera, float delta) {	
		body.applyForceToCenter(movement, true);
		
		camera.position.set(body.getPosition().x, body.getPosition().y, 0);
		//System.out.println(canJump);
		
		currX = Gdx.input.getAccelerometerX();
		currY = Gdx.input.getAccelerometerY();
		currZ = Gdx.input.getAccelerometerZ();
		
		if(available){
			//walk left
			if(currY - posY <= -1.5){
				movement.x = -speed;
			}else //walk right
				if(currY - posY >= 1.5){
					movement.x = speed;
			}else{
				movement.x = 0;
			}
		}
		
		//limits the acceleration of the body
		//to prevent it from traveling infinitely faster
		if(body.getLinearVelocity().x > xLimit) {
			if(body.getLinearVelocity().y > yLimit) {
				body.setLinearVelocity(xLimit, yLimit);
			}else{
				body.setLinearVelocity(xLimit, body.getLinearVelocity().y);
			}
		}else if(body.getLinearVelocity().y > yLimit) {
			if(body.getLinearVelocity().x > xLimit) {
				body.setLinearVelocity(xLimit, yLimit);
			}else{
				body.setLinearVelocity(body.getLinearVelocity().x, yLimit);
			}
		}else{
			body.setLinearVelocity(body.getLinearVelocity());
		}
		
		if(body.getAngularVelocity() < -5.89f) {
			body.setAngularVelocity(-5.89f);
		}else{
			body.setAngularVelocity(body.getAngularVelocity());
		}
	}
	
	public void jump() {
		float impulse = body.getMass() * 65;
		body.applyLinearImpulse(new Vector2(0, impulse), body.getWorldCenter(), true);
		body.applyAngularImpulse(-7300, true);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			if(isDevMode) {
				world.setGravity(new Vector2(0, 0));
				jump();
			}else if(canJump) {
				jump();
			}
			break;
		case Keys.A:
			movement.x = -speed;
			break;
		case Keys.S:
			isDevMode = true;
			BaseLevel.isSlowMotion = true;
			break;
		case Keys.D: 
			movement.x = speed;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.W:
			if(isDevMode){
				canJump = false;
			}else
				canJump = false;
			break;
		case Keys.A:
			movement.x = 0;
			break;
		case Keys.S:
			isDevMode = false;
			BaseLevel.isSlowMotion = false;
			break;
		case Keys.D:
			movement.x = 0;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int button) {
		if(canJump) {
			jump();
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		canJump = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
