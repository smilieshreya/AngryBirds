package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Structure extends Image {
    public Structure(float x, float y) {
        super(new Texture(Gdx.files.internal("Wooden_Box.png")));
        this.setPosition(x, y);
    }
}
