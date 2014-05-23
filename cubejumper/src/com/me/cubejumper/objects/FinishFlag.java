package com.me.cubejumper.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseObject;

public class FinishFlag extends BaseObject
{
	public FinishFlag(World world, float x, float y) {
		this.world = world;
		
		initPoly(x * 2, y);
		
		//Rectangle
		//shape.setAsBox(5, 5);
		poly.set(new Vector2[]{new Vector2(0,0), new Vector2(0, 25), new Vector2(2, 0), new Vector2(7, 22.5f), new Vector2(2, 25)});
				
		//Fixture definition
		fixDef.density = 1f;
		fixDef.friction = 0;
		fixDef.restitution = 0;
		fixDef.isSensor = true;
				
		body = world.createBody(bodyDef);
		body.setUserData(7);
		body.createFixture(fixDef);
	}
	
	@Override
	protected void render() {
		// TODO Auto-generated method stub
		super.render();
	}
	
	@Override
	protected void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
}
