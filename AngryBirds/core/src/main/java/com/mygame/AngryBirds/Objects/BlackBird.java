package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;

public class BlackBird extends Bird {

    public BlackBird(World world, float x, float y) {
        super(world, x, y);
    }
    @Override
    protected String getTexturePath() {
        return "Bomb.jpg";
    }
    @Override
    public void createBirdBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100f, y / 100f);
        bodyDef.fixedRotation = false;

        CircleShape shape = new CircleShape();
        shape.setRadius(15 / 100f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.3f;
        fixtureDef.density = 1.5f;
        fixtureDef.restitution = 0.3f;

        birdBody = world.createBody(bodyDef);
        birdBody.setUserData(this);
        birdBody.setLinearDamping(0.5f);
        birdBody.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void render() {
        batch.begin();
        float rotation = (float) Math.toDegrees(birdBody.getAngle());

        batch.draw(birdTexture,
                birdBody.getPosition().x * 100,
                birdBody.getPosition().y * 100,
                32.5f,
                32.5f,
                75,
                85,
                1,
                1,
                rotation,
                0,
                0,
                birdTexture.getWidth(),
                birdTexture.getHeight(),
                false,
                false
        );
        batch.end();

        if (isDragging) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 1);
            shapeRenderer.rectLine(slingshotPosition, dragPosition, 5);
            shapeRenderer.end();
        }
    }
}
