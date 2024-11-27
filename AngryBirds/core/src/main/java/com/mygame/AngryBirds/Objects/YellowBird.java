package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird {

    public YellowBird(World world, float x, float y) {
        super(world, x, y);
    }

    @Override
    protected String getTexturePath() {
        return "yellowBird.png";
    }
}

