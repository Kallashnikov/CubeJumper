package com.me.cubejumper.bases;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Powerup object base class.
 * 
 * @author Jacob
 */
public class BasePowerUp extends BaseObject
{
	public static float WAIT_TIME = 5f;
	public static int count = 0;
	protected float time = 0;
	
	protected void init(float x, float y) {
		initCircle(x, y);
		
		circle.setRadius(2.5f);
		
		//Fixture definition
		fixDef.density = 1f;
		fixDef.friction = 0;
		fixDef.restitution = 0;
		fixDef.isSensor = true;
		
		shapeRender = new ShapeRenderer();
	}
	
	protected void activate(float delta) {
		time += delta;
	}
	
	protected void color(float x, float y, Color color, Camera cam) { 
		shapeRender.setProjectionMatrix(cam.combined);
		
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(color);
		shapeRender.circle(x, y, 2.5f);
		shapeRender.end();
	}
	
	protected void destroy() {
		if(!world.isLocked())
			world.destroyBody(body);
	}
	
	protected void render() {
		super.render();
	}
	
	protected void dispose() {
		super.dispose();
	}
}
