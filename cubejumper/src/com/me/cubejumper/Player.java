package com.me.cubejumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Player implements InputProcessor
{
//	private boolean canJump = false;
	int width = Gdx.graphics.getWidth() / 5;
	int height = Gdx.graphics.getHeight() / 5;
	Vector2 movement = new Vector2(0, 0);
	private float speed = 50;
	public static boolean canJump = true;
	int jumpSteps = 0;
	
	public boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
	public float currX, currY, currZ;
	public float posX, posY, posZ;
	private boolean isDevMode = false;
	
	PlayScreen play;
	
	World world;
	
	BodyDef playerDef;
	FixtureDef fixDef;
	Body body;
	
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
		System.out.println(canJump);
		
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
		if(body.getLinearVelocity().x > 60)
			body.setLinearVelocity(60, body.getLinearVelocity().y);
	}
	
	public BodyDef getBodyDef() {
		return playerDef;
	}
	
	public FixtureDef getFixtureDef() {
		return fixDef;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			if(isDevMode) {
				world.setGravity(new Vector2(0, 0));
				world.setContactListener(null);
				canJump = true;
			}else if(canJump) {
				float impulse = body.getMass() * 65;
				body.applyLinearImpulse(new Vector2(0, impulse), body.getWorldCenter(), true);
				body.applyAngularImpulse(-6500, true);
			}
			break;
		case Keys.A:
			movement.x = -speed;
			break;
		case Keys.S:
			isDevMode = true;
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
			}else
				canJump = false;
			break;
		case Keys.A:
			movement.x = 0;
			break;
		case Keys.S:
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
			float impulse = body.getMass() * 65;
			body.applyLinearImpulse(new Vector2(0, impulse), body.getWorldCenter(), true);
			body.applyAngularImpulse(-6500, true);
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
