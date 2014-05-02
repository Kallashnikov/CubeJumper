package com.me.cubejumper;

import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseObject;

public class PowerUpSloMo extends BaseObject
{
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
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		super.dispose();
	}
}
