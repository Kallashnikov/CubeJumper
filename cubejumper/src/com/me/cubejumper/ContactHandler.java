package com.me.cubejumper;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.levels.PlayScreen;
import com.me.cubejumper.screens.DeathScreen;

/**
 * Handles all contacts in the world.
 * 
 * @author Jacob
 */
public class ContactHandler implements ContactListener
{
	private CubeJumper game;
	
	public ContactHandler(CubeJumper game, World world) {
		this.game = game;
	}
	
	@Override
	public void beginContact(Contact contact) {
		//0 = ground, 1 = player, 2 = spikes, 3 = dynamic spikes, 4 = cubes, 5 = powerups
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if((returnData(a, 1) && returnData(b, 2))
				|| (returnData(a, 2) && returnData(b, 1))){
			PlayScreen.endTime = System.nanoTime();
			PlayScreen.highScore = PlayScreen.endTime - PlayScreen.startTime;
			PlayScreen.highScore /= 1000000000;
			game.setScreen(new DeathScreen(game));
		}else if((returnData(a, 1) && returnData(b, 3))
				|| (returnData(a, 3) && returnData(b, 1))){
		}else if((returnData(a, 1) && returnData(b, 0))
				|| (returnData(a, 0) && returnData(b, 1))){
			Player.canJump = true;
		}else if((returnData(a, 1) && returnData(b, 4))
				|| (returnData(a, 4) && returnData(b, 1))){
			Player.canJump = true;
		}else if((returnData(a, 1) && returnData(b, 5))
				|| (returnData(a, 5) && returnData(b, 1))){
			BaseLevel.isSlowMotion = true;
		}else if((returnData(a, 1) && returnData(b, 6))
				|| (returnData(a, 6) && returnData(b, 1))){
			BaseLevel.isSuperJump = true;
		}
	}
	
	public Boolean returnData(Fixture fix, int num) {
		return fix.getBody().getUserData().equals(num);
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
