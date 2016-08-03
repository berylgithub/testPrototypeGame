package com.beryl.testgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.beryl.testgame.MainTestGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title="Mini Touhou";
                config.width=1300;
                config.height=720;
		new LwjglApplication(new MainTestGame(), config);
	}
}
