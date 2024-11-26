package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class BigPig extends Pig {

    public BigPig(World world, float x, float y) {
        super(world, x, y, "Large.png");
    }

    @Override
    protected void defineFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 1.5f; // BigPig is heavier
        fixtureDef.friction = 0.4f; // Slightly less friction
        fixtureDef.restitution = 0.5f; // Moderate bounciness
    }
}
