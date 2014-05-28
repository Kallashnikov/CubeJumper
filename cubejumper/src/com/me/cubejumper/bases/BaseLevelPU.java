package com.me.cubejumper.bases;

import com.me.cubejumper.CubeJumper;

public class BaseLevelPU extends BaseLevel
{
	public BaseLevelPU(CubeJumper game) {
		BaseLevel.game = game;
	}

	@Override
	public void show() {
		super.show();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void hide() {
		super.hide();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
