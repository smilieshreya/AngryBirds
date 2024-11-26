package com.mygame.AngryBirds.Objects;


import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class WoodStructure extends Structure {

    // Constructor for WoodStructure
    public WoodStructure(float x, float y, String img, World world) {
        super(x, y, img, world);
    }

    @Override
    protected void defineFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 0.8f; // Wood is less dense
        fixtureDef.friction = 0.6f; // Slightly higher friction
        fixtureDef.restitution = 0.2f; // Lower bounciness
    }
}