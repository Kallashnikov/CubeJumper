package com.me.cubejumper;

import com.badlogic.gdx.Game;

public class CubeJumper extends Game
{
	CubeJumper game;
	
	public final static String TITLE = "Cube Jumper";
	
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
