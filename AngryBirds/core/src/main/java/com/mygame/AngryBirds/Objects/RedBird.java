package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {

    public RedBird(World world, float x, float y) {
        super(world, x, y);
    }

    @Override
    protected String getTexturePath() {
        return "Red_Bird.png";
    }
}
