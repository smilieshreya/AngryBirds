package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.AngryBirds.Screen.GameScreen;
import com.mygame.AngryBirds.Screen.GameScreen2;
import com.mygame.AngryBirds.Screen.HUD;

public class CorporalPig extends Pig {
    public CorporalPig(World world, float x, float y) {
        super(world, x, y, "Corporal_Pig_Classic-Snicker.png");
        health = 140f;
    }

    @Override
    public Sprite createSprite(String texturePath) {
        Sprite newSprite = new Sprite(new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.internal(texturePath)));
        newSprite.setSize(1.24f * PPM, 1.1f * PPM);
        return newSprite;
    }
    @Override
    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            HUD.addScore(5500);
            markForDestruction();
        }
    }

    @Override
    public void markForDestruction() {
        if (body != null) {
            bodiesToDestroy.add(body);
            GameScreen2.pigs.remove(this);
            this.remove(); // Remove sprite from the stage
        }
    }

    @Override
    protected void defineFixture(FixtureDef fixtureDef) {
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f; //bounciness
    }
}
