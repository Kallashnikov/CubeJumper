package com.me.cubejumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Controls movement, updating, rotation, and the player body.
 * 
 * @param Vector2 movement<p>
 * @param speed (float) - 50
 * @param canJump (boolean) - can the player jump
 * @param xLimit (int) - limits x velocity, 60
 * @param yLimit (int) - limits y velocity, 55
 * @param angVel (float) - limits angular velocity, 5.89f
 * @param superJump (int) - changes when to 2 when Player has SuperJump, 1
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
public class Player
{
	public Vector2 movement = new Vector2(0, 0);
	public static float speed = 50;
	public static boolean canJump = true;
	public static int xLimit = 60, yLimit = 55;
	public static float angVel = -5.89f;
	public static int superJump = 1;
	
	public boolean available = Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer);
	public float currX, currY, currZ;
	public float posX, posY, posZ;
	public boolean isDevMode = false;
	
	private BodyDef playerDef;
	private FixtureDef fixDef;
	private Body body;
	
	public Player(World world) {
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
		
//		posX = Gdx.input.getAccelerometerX();
//		posY = Gdx.input.getAccelerometerY();
//		posZ = Gdx.input.getAccelerometerZ();
	}
	
	public void update(Camera camera, float delta) {	
		//body.applyForceToCenter(movement, true);
		
		camera.position.set(body.getPosition().x, body.getPosition().y, 0);
		
		/*
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
		**/

		
		//limits the acceleration of the body
		//to prevent it from traveling infinitely faster
		if(body.getLinearVelocity().x > xLimit) {
			if(body.getLinearVelocity().y > yLimit) {
				body.setLinearVelocity(xLimit, yLimit);
			}else{
				body.setLinearVelocity(xLimit, body.getLinearVelocity().y);
			}
		}else if(body.getLinearVelocity().x <= -1) {
			body.setLinearVelocity(-1, body.getLinearVelocity().y);
		}else if(body.getLinearVelocity().y > yLimit) {
			if(body.getLinearVelocity().x > xLimit) {
				body.setLinearVelocity(xLimit, yLimit);
			}else{
				body.setLinearVelocity(body.getLinearVelocity().x, yLimit);
			}
		}else{
			body.setLinearVelocity(body.getLinearVelocity());
		}
		
		if(body.getAngularVelocity() < angVel) {
			body.setAngularVelocity(angVel);
		}else{
			body.setAngularVelocity(body.getAngularVelocity());
		}
	}
	
	public void jump() {
		float impulse = body.getMass() * 65 * superJump;
		body.applyLinearImpulse(new Vector2(0, impulse), body.getWorldCenter(), true);
		body.applyAngularImpulse(-7300, true);
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}
	
	public void setPositionAndAngVelocity(Vector2 position, float playerAngVel) {
		body.setTransform(position, playerAngVel);
		body.setAngularVelocity(playerAngVel);
	}
	
	public Vector2 getVelocity() {
		return body.getLinearVelocity();
	}
	
	public void setVelocity(Vector2 velocity) {
		this.body.setLinearVelocity(velocity);
	}
	
	public String getAngVelocity() {
		String flo = Float.toString(body.getAngularVelocity());
		return flo;
	}

	public static String isCanJump() {
		String num;
		if(canJump){
			num = "true";
		}else{
			num = "false";
		}
		return num;
	}

	public static void setCanJump(String canJump) {
		if(canJump.equals("true")){
			Player.canJump = true;
		}else{
			Player.canJump = false;
		}
	}
}
