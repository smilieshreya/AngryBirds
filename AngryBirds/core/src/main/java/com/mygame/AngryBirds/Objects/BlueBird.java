package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.physics.box2d.*;

public class BlueBird extends Bird {

    public BlueBird(World world, float x, float y) {
        super(world, x, y);
    }
    @Override
    protected String getTexturePath() {
        return "blueBird.png";
    }
}
