package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Ground {

    private Body groundBody;

    public Ground(World world, float startX, float endX, float yPosition) {
        // Define the ground body
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        groundBodyDef.position.set(0, 0); // The body is static, position doesn't matter
        // Create the ground body in the Box2D world
        groundBody = world.createBody(groundBodyDef);

        // Define the ground shape as an edge
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vector2(startX, yPosition), new Vector2(endX, yPosition));

        // Create a fixture for the ground
        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 1.0f;

        groundBody.createFixture(groundFixtureDef);
        groundBody.setUserData(this);

        // Dispose the shape after use
        groundShape.dispose();
    }

    public Body getBody() {
        return groundBody;
    }
}
