package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Structure extends Image {
    private Body body;
    private World world;
    private static final float PPM = 100f; // Pixels per meter

    // Constructor for Structure
    public Structure(float x, float y, String img, World world) {
        super(new Texture(Gdx.files.internal(img)));
        this.world = world;

        // Set the position of the Image
        setPosition(x, y);

        // Create Box2D Body and Fixture for Structure
        createPhysicsBody(x / PPM, y / PPM);
    }

    // Initialize Box2D body for collision and physics
    private void createPhysicsBody(float x, float y) {
        // Define body definition for static body (as it won't move under physics)
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Change to StaticBody if the structure should not move

        // Create the body in the world
        body = world.createBody(bodyDef);

        // Define the shape (Rectangle or Polygon) for the Structure
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((((getWidth()) / 2) / PPM), (((getHeight()) / 2) / PPM)); // Box shape based on image size in meters

        // Create the fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f; // Density of the structure
        fixtureDef.friction = 0.5f; // Friction of the structure
        fixtureDef.restitution = 0.3f; // Restitution (bounciness) of the structure

        // Create the fixture for the body
        Fixture fixture = body.createFixture(fixtureDef);
        shape.dispose(); // Dispose shape to avoid memory leak

        // Optional: Set user data to reference the Image/Structure for handling
        body.setUserData(this);
    }

    // Update the position of the Image based on Box2D physics body
    public void update() {
        Vector2 position = body.getPosition();
        this.setPosition((position.x * PPM) - getWidth() / 2, (position.y * PPM) - getHeight() / 2);
    }

    // Handle collision logic, like breaking when a certain threshold is reached
    public void onCollision() {
        // Implement logic to break or destroy the structure
        // For example, check the velocity or force of the impact, then decide to destroy
        if (body.getLinearVelocity().len() > 5) {
            breakStructure();
        }
    }

    // Break the structure
    private void breakStructure() {
        // Logic to break or remove the structure
        world.destroyBody(body); // Removes the structure from the world
    }
}
