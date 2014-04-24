package com.me.cubejumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Spikes
{
	int width = Gdx.graphics.getWidth() / 5;
	int height = Gdx.graphics.getWidth() / 5;
	
	PlayScreen play;
	
	Body body;
	BodyDef spikeDef;
	FixtureDef fixDef;
	PolygonShape rect;
	
	public Spikes(World world, float x, float y) {
		//Body definition
		spikeDef = new BodyDef();
		spikeDef.type = BodyType.StaticBody;
		spikeDef.position.set(x, y);
		spikeDef.active = true;
				
		//Player's rectangle
		rect = new PolygonShape();
		rect.set(new Vector2[]{new Vector2(0,0), new Vector2(5, 10), new Vector2(10, 0)});
				
		//fixture definition
		fixDef = new FixtureDef();
		fixDef.shape = rect;
		fixDef.density = 1;
		fixDef.friction = 1;
		fixDef.restitution = 0;
		
		body = world.createBody(spikeDef);
		body.setUserData(2);
		body.createFixture(fixDef);
	}
	
	public Spikes(World world, float x, float y, float dens, float frict, float rest) {
		//Body definition
		spikeDef = new BodyDef();
		spikeDef.type = BodyType.DynamicBody;
		spikeDef.position.set(x, y);
		spikeDef.active = true;
		
		//Player's rectangle
		rect = new PolygonShape();
		rect.set(new Vector2[]{new Vector2(0,0), new Vector2(5, 10), new Vector2(10, 0)});
		
		//fixture definition
		fixDef = new FixtureDef();
		fixDef.shape = rect;
		fixDef.density = dens;
		fixDef.friction = frict;
		fixDef.restitution = rest;
		
		body = world.createBody(spikeDef);
		body.setUserData(2);
		body.createFixture(fixDef);
	}
	
	public void render() {
	}
	
	public void dispose() {
		rect.dispose();
	}
}
