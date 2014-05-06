package com.me.cubejumper.bases;

public class BasePowerUp extends BaseObject
{
	protected float time = 0;
	
	public void init(float x, float y) {
		initCircle(x, y);
		
		circle.setRadius(2.5f);
		
		//Fixture definition
		fixDef.density = 1f;
		fixDef.friction = 0;
		fixDef.restitution = 0;
		fixDef.isSensor = true;
	}
	
	public void activate(float delta) {
		time += delta;
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		super.dispose();
	}
}
