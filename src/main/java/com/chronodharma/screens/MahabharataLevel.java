package com.chronodharma.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.chronodharma.MainGame;

public class MahabharataLevel extends ScreenAdapter {
    private MainGame game;

    public MahabharataLevel(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Entered Mahabharata Era (Dvapara Yuga)");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.3f, 0.1f, 1); // Dusty Battlefield
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render Battlefield and Enemies
    }
}
