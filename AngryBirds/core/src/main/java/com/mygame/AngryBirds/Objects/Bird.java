package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Bird extends Image {
    public Bird(float x, float y) {
        super(new Texture(Gdx.files.internal("Red_Bird.png")));
        this.setPosition(x, y);
    }
}
