package com.chronodharma.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.chronodharma.MainGame;

/**
 * The Void Level: A punishment level for attempting to time travel without
 * enough energy.
 */
public class VoidLevel extends ScreenAdapter {
    private MainGame game;

    public VoidLevel(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("CRITICAL ERROR: STUCK IN THE VOID");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1); // Pitch Black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render glitch effects and "Time Eater" enemies here
    }
}
