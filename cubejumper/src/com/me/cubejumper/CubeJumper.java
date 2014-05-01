package com.me.cubejumper;

import com.badlogic.gdx.Game;
import com.me.cubejumper.screens.MainMenu;

/**
 * Main game class, creates the MainMenu and starts the game.<p>
 * 
 * @param TITLE - "Cube Jumper"
 * @param VERSION - "v1.1.2a" <p>
 * 
 * @author Jacob
 */
public class CubeJumper extends Game
{
	CubeJumper game;
	
	public final static String TITLE = "Cube Jumper";
	public final static String VERSION = "v1.2a";
	
	@Override
	public void create() {
		game = this;
		
		setScreen(new MainMenu(game));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {	
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
