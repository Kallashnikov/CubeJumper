package com.me.cubejumper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Cubes, for placing spikes on or being used as platforms.
 * 
 * @author Jacob
 */
public class Cubes extends ObjectBase
{
	/**
	 * A static and non-moving Cube.
	 * @param world
	 * @param x - world x position
	 * @param y - world y position
	 * 
	 * @author Jacob
	 */
	public Cubes(World world, float x, float y) {
		this.world = world;
		
		init(x, y);
		
		//Rectangle
		//shape.setAsBox(5, 5);
		shape.set(new Vector2[]{new Vector2(0,0), new Vector2(0, 10), new Vector2(10, 0), new Vector2(10, 10)});
		
		//Fixture definition
		fixDef.density = 1f;
		fixDef.friction = 0;
		fixDef.restitution = 0;
		
		body = world.createBody(bodyDef);
		body.setUserData(4);
		body.createFixture(fixDef);
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		super.dispose();
	}
}
