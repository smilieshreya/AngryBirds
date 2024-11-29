package com.mygame.AngryBirds;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.AngryBirds.Managers.BirdManager;
import com.mygame.AngryBirds.Objects.Bird;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AngryBirdsMainTest {

    private AngryBirdsMain game;

    @BeforeEach
    void setUp() {
        game = new AngryBirdsMain();
    }

    @Test
    void testConstants() {
        assertEquals(1920, AngryBirdsMain.WIDTH, "Game width should be 1920.");
        assertEquals(1080, AngryBirdsMain.HEIGHT, "Game height should be 1080.");
    }

    @Test
    void testAddBirdToQueue() {
        World world = new World(new Vector2(0, -9.8f), true);
        BirdManager birdManager = new BirdManager(world, new Vector2(100, 200));
        Bird bird = Mockito.mock(Bird.class);

        birdManager.addBird(bird);
        assertEquals(1, birdManager.getBirdCount(), "Bird count should be 1 after adding a bird.");
    }

    @Test
    void testGetNextBird() {
        World world = new World(new Vector2(0, -9.8f), true);
        BirdManager birdManager = new BirdManager(world, new Vector2(100, 200));
        Bird bird = Mockito.mock(Bird.class);

        birdManager.addBird(bird);
        Bird nextBird = birdManager.getNextBird();
        assertEquals(bird, nextBird, "Next bird should match the bird added.");
        assertEquals(0, birdManager.getBirdCount(), "Bird count should be 0 after retrieving the next bird.");
    }


    @Test
    void testHasBirdsLeft() {
        World world = new World(new Vector2(0, -9.8f), true);
        BirdManager birdManager = new BirdManager(world, new Vector2(100, 200));
        Bird bird = Mockito.mock(Bird.class);

        assertFalse(birdManager.hasBirdsLeft(), "There should be no birds initially.");
        birdManager.addBird(bird);
        assertTrue(birdManager.hasBirdsLeft(), "There should be birds after adding one.");
    }

    @Test
    void testGetBirdCount() {
        World world = new World(new Vector2(0, -9.8f), true);
        BirdManager birdManager = new BirdManager(world, new Vector2(100, 200));
        Bird bird1 = Mockito.mock(Bird.class);
        Bird bird2 = Mockito.mock(Bird.class);

        birdManager.addBird(bird1);
        birdManager.addBird(bird2);
        assertEquals(2, birdManager.getBirdCount(), "Bird count should be 2 after adding two birds.");
    }
}
