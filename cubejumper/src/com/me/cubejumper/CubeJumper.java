package com.me.cubejumper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.me.cubejumper.levels.*;
import com.me.cubejumper.screens.MainMenu;

/**
 * Main game class, creates the MainMenu and starts the game.<p>
 * 
 * @param TITLE - "Cube Jumper"
 * @param VERSION - "v1.0.1b" <p>
 * 
 * @author Jacob
 */
public class CubeJumper extends Game
{
	static CubeJumper game;
	
	public final static String TITLE = "Cube Jumper";
	public final static String VERSION = "v1.0.1b";
	
	public static int currentLevel;

	public static Object[] levels = {Level1.class, Level2.class, PlayScreen.class};
	
	@Override
	public void create() {
		game = this;
		
		setScreen(new MainMenu(game));
	}
	
	public static void levelSel(){
		switch(CubeJumper.currentLevel) {
		case 0:
			new Level1(game).resume();
			break;
		case 1:
			new Level2(game).resume();
			break;
		case 2:
			new PlayScreen(game).resume();
			break;
		}
	}
	
	public static void levelSel(List list){
		switch(list.getSelectedIndex()) {
		case 0:
			CubeJumper.currentLevel = 0;
			game.setScreen(new Level1(game));;
			break;
		case 1:
			CubeJumper.currentLevel = 1;
			game.setScreen(new Level2(game));
			break;
		case 2:
			CubeJumper.currentLevel = 2;
			game.setScreen(new PlayScreen(game));
			break;
		}
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
