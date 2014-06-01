package com.me.cubejumper;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "CubeJumper";
		cfg.useGL20 = true;
		cfg.width = 1080;
		cfg.height = 720;
		cfg.backgroundFPS = -1;
		cfg.foregroundFPS = 60;
		
		new LwjglApplication(new CubeJumper(), cfg);
	}
}
