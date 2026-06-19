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

public class MahabharataLevel extends ScreenAdapter {
    private static final int TILE_SIZE = 64;
    private MainGame game;
    private BitmapFont font;
    private Texture sandTile;
    private Texture trenchTile;
    private Texture bannerTile;

    public MahabharataLevel(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("Entered Mahabharata Era (Dvapara Yuga)");
        this.font = new BitmapFont();
        this.sandTile = createSandTile();
        this.trenchTile = createTrenchTile();
        this.bannerTile = createBannerTile();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.3f, 0.1f, 1); // Dusty Battlefield
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = game.batch;
        batch.begin();

        int cols = MathUtils.ceil((float) Gdx.graphics.getWidth() / TILE_SIZE) + 1;
        int rows = MathUtils.ceil((float) Gdx.graphics.getHeight() / TILE_SIZE) + 1;
        int trenchRow = rows / 2;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Texture tile = (y == trenchRow || y == trenchRow - 1) ? trenchTile : sandTile;
                batch.draw(tile, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // Place opposing banners on each side of the trench
        batch.draw(bannerTile, TILE_SIZE, (trenchRow + 1) * TILE_SIZE, TILE_SIZE, TILE_SIZE * 2);
        batch.draw(bannerTile, (cols - 2) * TILE_SIZE, (trenchRow - 3) * TILE_SIZE, TILE_SIZE, TILE_SIZE * 2);

        font.draw(batch, "Dvapara Yuga Battlefield", 30, Gdx.graphics.getHeight() - 30);
        font.draw(batch, "Trenches divide rival armies", 30, Gdx.graphics.getHeight() - 60);
        batch.end();
    }

    @Override
    public void dispose() {
        if (font != null)
            font.dispose();
        if (sandTile != null)
            sandTile.dispose();
        if (trenchTile != null)
            trenchTile.dispose();
        if (bannerTile != null)
            bannerTile.dispose();
    }

    private Texture createSandTile() {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.58f, 0.42f, 0.24f, 1f);
        pixmap.fill();
        pixmap.setColor(0.67f, 0.5f, 0.32f, 1f);
        for (int i = 0; i < TILE_SIZE; i += 6) {
            pixmap.drawLine(0, i, TILE_SIZE, i);
        }
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createTrenchTile() {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.25f, 0.18f, 0.1f, 1f);
        pixmap.fill();
        pixmap.setColor(0.12f, 0.09f, 0.05f, 1f);
        pixmap.fillRectangle(10, 10, TILE_SIZE - 20, TILE_SIZE - 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    private Texture createBannerTile() {
        Pixmap pixmap = new Pixmap(TILE_SIZE, TILE_SIZE * 2, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        pixmap.setColor(Color.GOLD);
        pixmap.fillRectangle(10, 10, pixmap.getWidth() - 20, pixmap.getHeight() - 20);
        pixmap.setColor(new Color(0.7f, 0.05f, 0.05f, 1f));
        pixmap.fillRectangle(10, pixmap.getHeight() / 2, pixmap.getWidth() - 20, 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
