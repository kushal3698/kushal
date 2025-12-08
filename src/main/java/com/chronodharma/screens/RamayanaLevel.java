package com.chronodharma.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.chronodharma.MainGame;

public class RamayanaLevel extends ScreenAdapter {
    private MainGame game;

    public RamayanaLevel(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Entered Ramayana Era (Treta Yuga)");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.1f, 1); // Forest Green
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render Forest Tilemap and Enemies
    }
}
