package com.chronodharma.ecs;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a Rakshasa Demon Guard that patrols a specific area.
 */
public class DemonGuard extends Entity {
    private Vector2 patrolStart;
    private Vector2 patrolEnd;
    private boolean movingToEnd;
    private float speed = 2.0f;

    public DemonGuard(int id, float x, float y, Vector2 patrolEnd) {
        super(id, Type.ENEMY, x, y);
        this.patrolStart = new Vector2(x, y);
        this.patrolEnd = patrolEnd;
        this.movingToEnd = true;
    }

    @Override
    public void update(float delta) {
        if (!isActive())
            return;

        Vector2 target = movingToEnd ? patrolEnd : patrolStart;
        Vector2 current = getPosition();

        // Simple movement logic towards target
        Vector2 direction = new Vector2(target).sub(current).nor();
        current.mulAdd(direction, speed * delta);

        // Check if reached target (within small threshold)
        if (current.dst(target) < 0.1f) {
            movingToEnd = !movingToEnd; // Switch direction
        }
    }
}
