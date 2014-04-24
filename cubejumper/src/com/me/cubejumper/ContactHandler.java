package com.me.cubejumper;

import java.text.DecimalFormat;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ContactHandler implements ContactListener
{
	CubeJumper game;
	PlayScreen play;
	
	public ContactHandler(CubeJumper game) {
		this.game = game;
	}
	
	@Override
	public void beginContact(Contact contact) {
		//0 = ground, 1 = player, 2 = spikes, 3 = dynamic spikes
		Fixture a = contact.getFixtureA();
		Fixture b = contact.getFixtureB();
		
		if((returnData(a, 1) && returnData(b, 2))
				|| (returnData(a, 2) && returnData(b, 1))){
			System.out.println("The box has collided with the spike");
			DecimalFormat df = new DecimalFormat("$.$$");
			PlayScreen.endTime = System.nanoTime();
			PlayScreen.highScore = PlayScreen.endTime - PlayScreen.startTime;
			PlayScreen.highScore /= 1000000000;
			df.format(PlayScreen.highScore);
			game.setScreen(new DeathScreen(game));
		}else if((returnData(a, 1) && returnData(b, 3))
				|| (returnData(a, 3) && returnData(b, 1))){
			System.out.println("");
		}else if((returnData(a, 1) && returnData(b, 0))
				|| (returnData(a, 0) && returnData(b, 1))){
			Player.canJump = true;
		}
	}
	
	public Boolean returnData(Fixture fix, int num) {
		return fix.getBody().getUserData().equals(num);
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}