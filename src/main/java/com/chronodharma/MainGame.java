package com.chronodharma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chronodharma.screens.ScreenManager;

/**
 * The main entry point for the game.
 * Manages the SpriteBatch and the ScreenManager.
 */
public class MainGame extends Game {
    public SpriteBatch batch;
    private ScreenManager screenManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        screenManager = new ScreenManager(this);

        // Start at the Present Day Hub
        screenManager.setScreen(ScreenManager.ScreenType.PRESENT_DAY_HUB);
    }

    @Override
    public void render() {
        super.render(); // Delegates to the active screen
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (screenManager != null) {
            // screenManager.dispose(); // If ScreenManager had resources
        }
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
