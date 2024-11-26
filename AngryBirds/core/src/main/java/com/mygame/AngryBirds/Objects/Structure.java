// Abstract Structure class
package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Structure extends Image {
    protected Body body;
    protected World world;
    protected static final float PPM = 100f; // Pixels per meter

    // Constructor for Structure
    public Structure(float x, float y, String img, World world) {
        super(new Texture(Gdx.files.internal(img)));
        this.world = world;

        // Set the position of the Image
        setPosition(x, y);

        // Create Box2D Body and Fixture for Structure
        createPhysicsBody(x / PPM, y / PPM);
    }

    // Abstract method to define structure-specific properties
    protected abstract void defineFixture(FixtureDef fixtureDef);

    // Initialize Box2D body for collision and physics
    private void createPhysicsBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(((getWidth() / 2) / PPM), ((getHeight() / 2) / PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        // Call the abstract method to allow subclasses to customize fixture properties
        defineFixture(fixtureDef);

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }

    public void update() {
        Vector2 position = body.getPosition();
        setPosition((position.x * PPM) - getWidth() / 2, (position.y * PPM) - getHeight() / 2);
    }

    public Body getBody() {
        return body;
    }
}
