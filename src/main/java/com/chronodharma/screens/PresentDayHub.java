package com.chronodharma.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.chronodharma.MainGame;
import com.chronodharma.systems.TimeMachine;
import com.chronodharma.ui.TimeMachineUI;

public class PresentDayHub extends ScreenAdapter {
    private MainGame game;

    private Stage stage;
    private Skin skin;
    private TimeMachineUI timeMachineUI;
    private TimeMachine timeMachine;

    public PresentDayHub(MainGame game) {
        this.game = game;
        // Initialize TimeMachine with 100 energy for testing
        this.timeMachine = new TimeMachine(100);
    }

    @Override
    public void show() {
        System.out.println("Entered Present Day Hub");

        // Create Skin programmatically to avoid missing font files
        skin = new Skin();

        // Load Atlas
        try {
            skin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
        } catch (Exception e) {
            System.out.println("Error loading atlas: " + e.getMessage());
        }

        // Add Default Font
        skin.add("default-font", new BitmapFont());

        // Add Label Style
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default-font");
        skin.add("default", labelStyle);

        // Add Button Style
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = skin.getFont("default-font");
        buttonStyle.up = skin.getDrawable("button-up");
        buttonStyle.down = skin.getDrawable("button-down");
        skin.add("default", buttonStyle);

        // Create UI
        timeMachineUI = new TimeMachineUI(game, timeMachine, skin);
        stage = timeMachineUI.getStage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // Dark Gray for Lab
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (stage != null) {
            stage.act(delta);
            stage.draw();
        }
    }

    @Override
    public void dispose() {
        if (stage != null)
            stage.dispose();
        if (skin != null)
            skin.dispose();
    }
}
