package com.mygame.AngryBirds.Objects;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class TrajectoryHelper {
    public static void drawTrajectory(ShapeRenderer shapeRenderer, Vector2 start, Vector2 velocity, float gravity, int steps) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        float timeStep = 0.1f;
        Vector2 current = new Vector2(start);

        for (int i = 0; i < steps; i++) {
            float time = i * timeStep;
            Vector2 next = new Vector2(
                    start.x + velocity.x * time,
                    start.y + velocity.y * time - 0.5f * gravity * time * time
            );
            shapeRenderer.line(current, next);
            current.set(next);
        }

        shapeRenderer.end();
    }
}

