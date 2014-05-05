package com.me.cubejumper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.bases.BaseObject;

public class PowerUpSloMo extends BaseObject
{
	private static final int VELOCITYIT = 8;
	private static final int POSITIONIT = 3;
	
	public static float slow = 120f;
	
	private static final float SLOWMOTION = 1 / slow;
	
	private static final float WAIT_TIME = 5f;
	private float time = 0;
	
	private Vector2 pos;
	//pentagon
	//private Vector2[] vertices = {new Vector2(0,0), new Vector2(1.667f * 2, -1.667f * 2), new Vector2(3.334f * 2, -1.667f * 2), new Vector2(5 * 2, 0), new Vector2(5 * 2, 1.667f * 2), new Vector2(3.334f * 2, 3.334f * 2), new Vector2(1.667f * 2, 3.334f * 2), new Vector2(0, 1.667f * 2)};
	/**
	 * The slo-mo power up, which slows down the timestep of the world to simulate slo-mo.
	 * @param world
	 * @param x - world x position
	 * @param y - world y position
	 * 
	 * @author Jacob
	 */
	public PowerUpSloMo(World world){
		this.world = world;
	}
	
	public PowerUpSloMo(World world, float x, float y) {
		this.world = world;
		
		initCircle(x, y);
		
		//Circle
		circle.setRadius(2.5f);
		
		//Fixture definition
		fixDef.density = 1f;
		fixDef.friction = 0;
		fixDef.restitution = 0;
		fixDef.isSensor = true;
		
		body = world.createBody(bodyDef);
		body.setUserData(5);
		body.createFixture(fixDef);
	}
	
	public void activate(float delta) {
		time += delta;
		if(time <= WAIT_TIME) {
			world.step(SLOWMOTION, VELOCITYIT, POSITIONIT);
		}else{
			BaseLevel.isSlowMotion = false;
		}
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		super.dispose();
	}
}
