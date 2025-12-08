package com.chronodharma.screens;

import com.chronodharma.MainGame;
import com.badlogic.gdx.Screen;

/**
 * Manages screen transitions.
 */
public class ScreenManager {
    private MainGame game;

    // Cache screens to avoid recreating them constantly
    private PresentDayHub presentDayHub;
    private RamayanaLevel ramayanaLevel;
    private MahabharataLevel mahabharataLevel;

    public enum ScreenType {
        PRESENT_DAY_HUB,
        RAMAYANA_LEVEL,
        MAHABHARATA_LEVEL,
        VOID_LEVEL
    }

    public ScreenManager(MainGame game) {
        this.game = game;
    }

    public void setScreen(ScreenType type) {
        Screen screen = null;
        switch (type) {
            case PRESENT_DAY_HUB:
                if (presentDayHub == null)
                    presentDayHub = new PresentDayHub(game);
                screen = presentDayHub;
                break;
            case RAMAYANA_LEVEL:
                if (ramayanaLevel == null)
                    ramayanaLevel = new RamayanaLevel(game);
                screen = ramayanaLevel;
                break;
            case MAHABHARATA_LEVEL:
                if (mahabharataLevel == null)
                    mahabharataLevel = new MahabharataLevel(game);
                screen = mahabharataLevel;
                break;
            case VOID_LEVEL:
                screen = new VoidLevel(game); // Always new instance for the Void
                break;
        }
        game.setScreen(screen);
    }
}
