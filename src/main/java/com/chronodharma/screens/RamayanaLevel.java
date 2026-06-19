package com.chronodharma.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.chronodharma.MainGame;

public class RamayanaLevel extends ScreenAdapter {
    private static final int TILE_SIZE = 64;
    private MainGame game;
    private BitmapFont font;
    private Texture canopyTile;
    private Texture pathTile;
    private Texture shrineTile;

    public RamayanaLevel(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Entered Ramayana Era (Treta Yuga)");
        this.font = new BitmapFont();
        this.canopyTile = createTile(new Color(0.07f, 0.24f, 0.07f, 1f), new Color(0.11f, 0.35f, 0.12f, 1f));
        this.pathTile = createPathTile();
        this.shrineTile = createShrineTile();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.1f, 1); // Forest Green
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = game.batch;
        batch.begin();

        int cols = MathUtils.ceil((float) Gdx.graphics.getWidth() / TILE_SIZE) + 1;
        int rows = MathUtils.ceil((float) Gdx.graphics.getHeight() / TILE_SIZE) + 1;
        int pathCenter = cols / 2;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Texture tile = canopyTile;
                if (Math.abs(x - pathCenter) <= 1) {
                    tile = pathTile;
                }
                batch.draw(tile, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Shrine marker near the top of the path
        float shrineX = (pathCenter - 1) * TILE_SIZE;
        float shrineY = (rows - 3) * TILE_SIZE;
        batch.draw(shrineTile, shrineX, shrineY, TILE_SIZE * 2, TILE_SIZE * 2);

        font.draw(batch, "Treta Yuga Sanctuary", 30, Gdx.graphics.getHeight() - 30);
        font.draw(batch, "Use ESC to exit the level", 30, 40);
        batch.end();
    }

    @Override
    public void dispose() {
        if (font != null)
            font.dispose();
        if (canopyTile != null)
            canopyTile.dispose();
        if (pathTile != null)
            pathTile.dispose();
        if (shrineTile != null)
            shrineTile.dispose();
    }

    private Texture createTile(Color base, Color accent) {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        pixmap.setColor(base);
        pixmap.fill();
        pixmap.setColor(accent);
        pixmap.fillCircle(TILE_SIZE / 4, TILE_SIZE / 2, TILE_SIZE / 6);
        pixmap.fillCircle((TILE_SIZE * 3) / 4, TILE_SIZE / 3, TILE_SIZE / 5);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createPathTile() {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.35f, 0.23f, 0.12f, 1f);
        pixmap.fill();
        pixmap.setColor(0.55f, 0.42f, 0.28f, 1f);
        for (int i = 0; i < TILE_SIZE; i += 8) {
            pixmap.fillRectangle(0, i, TILE_SIZE, 4);
        }
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createShrineTile() {
        Pixmap pixmap = new Pixmap(TILE_SIZE * 2, TILE_SIZE * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.6f, 0.52f, 0.35f, 1f);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        pixmap.setColor(0.9f, 0.85f, 0.65f, 1f);
        pixmap.fillRectangle(10, 10, pixmap.getWidth() - 20, pixmap.getHeight() - 20);
        pixmap.setColor(0.8f, 0.2f, 0.2f, 1f);
        pixmap.fillTriangle(pixmap.getWidth() / 2, pixmap.getHeight() - 10, 10, pixmap.getHeight() / 2, pixmap.getWidth() - 10, pixmap.getHeight() / 2);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
