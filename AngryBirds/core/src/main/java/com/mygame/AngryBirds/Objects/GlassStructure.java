package com.mygame.AngryBirds.Objects;


import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class GlassStructure extends Structure {

    public GlassStructure(float x, float y, String img, World world) {
        super(x, y, img, world);
    }

    @Override
    protected void defineFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.1f; // bounciness
    }
}