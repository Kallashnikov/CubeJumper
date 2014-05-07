package com.me.cubejumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.me.cubejumper.bases.BaseLevel;
import com.me.cubejumper.bases.BasePowerUp;

public class InputHandler implements InputProcessor
{
	private Player player;
	
	private World world;
	
	public InputHandler(World world, Player player){
		this.player = player;
		this.world = world;
		
		Gdx.input.setCatchBackKey(true);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			if(player.isDevMode) {
				world.setGravity(new Vector2(0, 0));
				player.jump();
			}else if(Player.canJump) {
				player.jump();
			}
			break;
		case Keys.A:
			player.movement.x = -Player.speed;
			break;
		case Keys.S:
			player.isDevMode = true;
			BaseLevel.isSlowMotion = true;
			break;
		case Keys.D: 
			player.movement.x = Player.speed;
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.W:
			if(player.isDevMode){
				Player.canJump = false;
			}else
				Player.canJump = false;
			break;
		case Keys.A:
			player.movement.x = 0;
			break;
		case Keys.S:
			player.isDevMode = false;
			BaseLevel.isSlowMotion = false;
			break;
		case Keys.D:
			player.movement.x = 0;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer,
			int button) {
		if(Player.canJump)
			player.jump();
		if(BaseLevel.isSuperJump)
			++BasePowerUp.count;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		Player.canJump = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
