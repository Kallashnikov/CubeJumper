package com.me.cubejumper.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.me.cubejumper.bases.BaseObject;

/**
 * A triangular shape which is deadly to the touch.<p>
 * If the Player comes in contact, the Player dies.
 * 
 * @author Jacob
 */
public class Spikes extends BaseObject
{	
	Vector2[] vertices = new Vector2[]{new Vector2(0,0), new Vector2(5, 10), new Vector2(10, 0)};
	/**
	 * A static and non-moving Spike.
	 * @param world
	 * @param x - world x position
	 * @param y - world y position
	 * 
	 * @author Jacob
	 */
	public Spikes(World world, float x, float y) {
		//Body definition
		initPoly(x, y);
		
		bodyDef.type = BodyType.StaticBody;
				
		//Spike points
		poly.set(vertices);
				
		//fixture definition
		fixDef.density = 1;
		fixDef.friction = 1;
		fixDef.restitution = 0;
		
		body = world.createBody(bodyDef);
		body.setUserData(2);
		body.createFixture(fixDef);
	}
	
	/**
	 * A dynamic Spike, which can move around.
	 * @param world
	 * @param x - world x position
	 * @param y - world y position
	 * @param dens - density
	 * @param frict - friction
	 * @param rest - restitution
	 * 
	 * @author Jacob
	 */
	public Spikes(World world, float x, float y, float dens, float frict, float rest) {
		//Body definition
		initPoly(x, y);
		
		bodyDef.type = BodyType.DynamicBody;
		
		//Player's rectangle
		poly.set(new Vector2[]{new Vector2(0,0), new Vector2(5, 10), new Vector2(10, 0)});
		
		//fixture definition
		fixDef.density = dens;
		fixDef.friction = frict;
		fixDef.restitution = rest;
		
		body = world.createBody(bodyDef);
		body.setUserData(2);
		body.createFixture(fixDef);
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		super.dispose();
	}
}
