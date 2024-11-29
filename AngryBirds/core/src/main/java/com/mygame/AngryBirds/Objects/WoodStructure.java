package com.mygame.AngryBirds.Objects;


import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class WoodStructure extends Structure {

    public WoodStructure(float x, float y, String img, World world) {
        super(x, y, img, world);
    }

    @Override
    protected void defineFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.2f; //bounciness
    }
}