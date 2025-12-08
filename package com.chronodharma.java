package com.chronodharma.desktop;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.chronodharma.MainGame;
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Chrono-Dharma: The Past Heals");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new MainGame(), config);
    }
}
