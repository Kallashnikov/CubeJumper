package com.me.cubejumper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * A triangular shape which is deadly to the touch.<p>
 * If the Player comes in contact, the Player dies.
 * 
 * @author Jacob
 */
public class Spikes extends ObjectBase
{	
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
		init(x, y);
		
		bodyDef.type = BodyType.StaticBody;
				
		//Player's rectangle
		shape.set(new Vector2[]{new Vector2(0,0), new Vector2(5, 10), new Vector2(10, 0)});
				
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
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.active = true;
		
		//Player's rectangle
		shape = new PolygonShape();
		shape.set(new Vector2[]{new Vector2(0,0), new Vector2(5, 10), new Vector2(10, 0)});
		
		//fixture definition
		fixDef = new FixtureDef();
		fixDef.shape = shape;
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
