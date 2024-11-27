package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PigTest {

    private BigPig pig;
    private World world;

    @BeforeEach
    void setUp() {
        // Create a lightweight Box2D world
        world = new World(new Vector2(0, -9.8f), true);
        pig = new BigPig(world, 150, 300); // Adjusted constructor
    }

    @Test
    void testHealthReduction() {
        pig.takeDamage(25);
        assertEquals(75, pig.health, "Pig health should decrease correctly.");
        pig.takeDamage(80);
        assertTrue(pig.health <= 0, "Pig should have 0 or less health after fatal damage.");
    }

    @Test
    void testBodyCreation() {
        assertNotNull(pig.body, "Pig body should be created.");
    }

    @Test
    void testMarkForDestruction() {
        pig.takeDamage(100);
        assertTrue(pig.health <= 0, "Pig should be marked for removal after taking fatal damage.");
    }

    @Test
    void testDestroyMarkedBodies() {
        pig.takeDamage(100); // Mark pig for destruction
        Pig.destroyMarkedBodies(world);
        assertNull(pig.body.getWorld(), "Pig body should be removed from the world.");
    }

    @Test
    void testSpriteSyncWithBody() {
        pig.act(0.1f); // Simulate an act step
        assertEquals(
                pig.sprite.getX() + pig.sprite.getWidth() / 2,
                pig.body.getPosition().x * Pig.PPM,
                0.01f,
                "Pig sprite should sync with body position."
        );
    }
}
