package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class scoreBoard extends Image {
    public scoreBoard(float x, float y,String img) {
        super(new Texture(Gdx.files.internal(img)));
        this.setPosition(x, y);
    }
}
