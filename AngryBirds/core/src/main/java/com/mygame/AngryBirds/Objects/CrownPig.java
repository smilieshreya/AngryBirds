package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.AngryBirds.Screen.GameScreen;
import com.mygame.AngryBirds.Screen.GameScreen2;
import com.mygame.AngryBirds.Screen.GameScreen3;
import com.mygame.AngryBirds.Screen.HUD;

public class CrownPig extends Pig {
    public CrownPig(World world, float x, float y) {
        super(world, x, y, "Smoothcheeks.jpg");
        health = 140f;
    }

    @Override
    public Sprite createSprite(String texturePath) {
        Sprite newSprite = new Sprite(new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.internal(texturePath)));
        newSprite.setSize(1.25f * PPM, 1.25f * PPM);
        return newSprite;
    }
    @Override
    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            HUD.addScore(6000);
            markForDestruction();
        }
    }

    @Override
    public void markForDestruction() {
        if (body != null) {
            bodiesToDestroy.add(body);
            GameScreen3.pigs.remove(this);
            this.remove(); // Remove sprite from the stage
        }
    }

    @Override
    protected void defineFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 1.1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.4f;
    }
}