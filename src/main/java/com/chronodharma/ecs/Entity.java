package com.chronodharma.ecs;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a basic entity in the game (Player, Enemy, Item).
 * In a full ECS, this would be an ID with attached Components.
 * For this prototype, we'll use a simpler object structure.
 */
public class Entity {
    public enum Type {
        PLAYER,
        ENEMY,
        COLLECTIBLE
    }

    private int id;
    private Type type;
    private Vector2 position;
    private boolean active;

    public Entity(int id, Type type, float x, float y) {
        this.id = id;
        this.type = type;
        this.position = new Vector2(x, y);
        this.active = true;
    }

    public void update(float delta) {
        // Basic update logic
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
