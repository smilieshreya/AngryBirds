package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class Structure extends Image implements ContactListener {
    private Body body;
    private World world;
    private static final float PPM = 100f; // Pixels per meter
    private static final List<Body> bodiesToDestroy = new ArrayList<>();

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
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(((getWidth() / 2) / PPM), ((getHeight() / 2) / PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }

    public void update() {
        Vector2 position = body.getPosition();
        setPosition((position.x * PPM) - getWidth() / 2, (position.y * PPM) - getHeight() / 2);
    }

    public void onCollision() {
        if (body.getLinearVelocity().len() > 5) {
            breakStructure();
        }
    }

    private void breakStructure() {
        // Mark the body for destruction
        if (body != null) {
            bodiesToDestroy.add(body);
            this.remove();
        }

        // Remove the sprite (this actor) from the stage
//        if (this.getParent() != null) {
//            this.remove(); // Ensures the sprite is no longer rendered
//        }
    }

    public static void destroyMarkedBodies(World world) {
        for (Body body : bodiesToDestroy) {
            world.destroyBody(body);
        }
        bodiesToDestroy.clear();
    }

    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA == body || bodyB == body) {
            Body otherBody = (bodyA == body) ? bodyB : bodyA;
            float impactVelocity = otherBody.getLinearVelocity().len();
            if (impactVelocity > 2.5f) {
                breakStructure();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}
}
