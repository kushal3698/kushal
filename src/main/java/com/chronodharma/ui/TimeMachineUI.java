package com.chronodharma.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.chronodharma.MainGame;
import com.chronodharma.screens.ScreenManager;
import com.chronodharma.systems.TimeMachine;

/**
 * The UI overlay for the Time Machine control panel.
 */
public class TimeMachineUI {
    private Stage stage;
    private TimeMachine timeMachine;
    private MainGame game;
    private Label energyLabel;
    private Label statusLabel;

    public TimeMachineUI(MainGame game, TimeMachine timeMachine, Skin skin) {
        this.game = game;
        this.timeMachine = timeMachine;
        this.stage = new Stage();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // UI Elements
        Label titleLabel = new Label("TIME MACHINE CONTROL", skin);
        energyLabel = new Label("Flux Energy: " + timeMachine.getFluxEnergy() + "%", skin);
        statusLabel = new Label("System: READY", skin);

        TextButton jumpRamayanaBtn = new TextButton("JUMP: RAMAYANA (Treta Yuga)", skin);
        TextButton jumpMahabharataBtn = new TextButton("JUMP: MAHABHARATA (Dvapara Yuga)", skin);

        // Layout
        table.add(titleLabel).pad(10).row();
        table.add(energyLabel).pad(10).row();
        table.add(statusLabel).pad(10).row();
        table.add(jumpRamayanaBtn).pad(10).row();
        table.add(jumpMahabharataBtn).pad(10).row();

        // Listeners
        jumpRamayanaBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attemptTimeJump(ScreenManager.ScreenType.RAMAYANA_LEVEL);
            }
        });

        jumpMahabharataBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attemptTimeJump(ScreenManager.ScreenType.MAHABHARATA_LEVEL);
            }
        });
    }

    private void attemptTimeJump(ScreenManager.ScreenType targetEra) {
        if (timeMachine.attemptJump()) {
            statusLabel.setText("Status: JUMPING...");
            game.getScreenManager().setScreen(targetEra);
        } else {
            statusLabel.setText("Status: CRITICAL FAILURE - INSUFFICIENT ENERGY");
            // Logic for "Void" level
            game.getScreenManager().setScreen(ScreenManager.ScreenType.VOID_LEVEL);
        }
        updateEnergyDisplay();
    }

    public void updateEnergyDisplay() {
        energyLabel.setText("Flux Energy: " + timeMachine.getFluxEnergy() + "%");
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
    }
}
