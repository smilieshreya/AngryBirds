package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirdTest {

    private RedBird bird;
    private World world;

    @BeforeEach
    void setUp() {
        // Create a lightweight Box2D world
        world = new World(new Vector2(0, -9.8f), true);
        bird = new RedBird(world, 100, 200); // Instantiate RedBird at (100, 200)
    }

    @Test
    void testCreateBirdBody() {
        assertNotNull(bird.getBody(), "Bird body should be created and not null.");
    }

    @Test
    void testResetBird() {
        bird.resetBird();
        Vector2 position = bird.getBody().getPosition();
        assertEquals(3.4f, position.x, 0.01f, "Bird should reset to slingshot X position.");
        assertEquals(3.11f, position.y, 0.01f, "Bird should reset to slingshot Y position.");
        assertEquals(0, bird.getBody().getLinearVelocity().len(), "Bird's velocity should reset to zero.");
    }

    @Test
    void testLaunchBird() {
        bird.touchDown(340, 280, 0, 0); // Simulate touchDown at the slingshot
        bird.touchDragged(360, 300, 0); // Simulate dragging
        bird.touchUp(360, 300, 0, 0);   // Simulate releasing the drag
        assertFalse(bird.isReadyToFire, "Bird should not be ready to fire after launch.");
        assertTrue(bird.getBody().getLinearVelocity().len() > 0, "Bird should have a velocity after launch.");
    }

    @Test
    void testRendering() {
        assertDoesNotThrow(() -> bird.render(), "Rendering the bird should not throw any exceptions.");
    }
}
