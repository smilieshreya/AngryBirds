package com.mygame.AngryBirds;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

}
