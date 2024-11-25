package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Pig extends Actor {
    private Sprite sprite;
    private Body body;
    public static final float PPM = 100f; // Pixels per meter

    public Pig(World world, float x, float y) {
        // Initialize the sprite with the pig texture
        sprite = new Sprite(new Texture(Gdx.files.internal("Large.png")));
        sprite.setSize(1.0f * PPM, 1.0f * PPM); // Convert to pixel size
        sprite.setOriginCenter();

        // Create the Box2D body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM); // Scale to Box2D units

        // Create the body in the Box2D world
        body = world.createBody(bodyDef);

        // Define the shape for the pig (a circle)
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f); // Adjust radius in meters

        // Define the fixture for the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.7f; // Bounciness

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape
        shape.dispose();

        // Sync sprite position
        sprite.setPosition((body.getPosition().x * PPM) - sprite.getWidth() / 2,
                ((body.getPosition().y * PPM) - sprite.getHeight() / 2));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Sync the sprite's position and rotation with the body
        sprite.setPosition((body.getPosition().x * PPM) - sprite.getWidth() / 2,
                8+(body.getPosition().y * PPM) - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        // Draw the sprite
        sprite.draw(batch);
    }

    public Body getBody() {
        return body;
    }
}
