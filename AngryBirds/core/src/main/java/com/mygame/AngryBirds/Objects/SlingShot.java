package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SlingShot extends Actor {
    private Texture texture;
    private Body body;
    private World world;
    private float width;
    private float height;

    public SlingShot(World world, float x, float y) {
        this.world = world;
        this.texture = new Texture(Gdx.files.internal("slingShotII.png"));

        this.width = texture.getWidth() / 100f; // Scale texture to match world units
        this.height = texture.getHeight() / 100f;

        createBody(x, y);
        setBounds(x, y, width, height);
    }

    private void createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((x / 100f)-0.47f, y / 100f); // Convert pixels to world units
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2)-0.1f, (height / 2)-0.95f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 position = body.getPosition();
        batch.draw(texture,
                36+position.x * 100 - width / 2 * 100,
                8+position.y * 100 - height / 2 * 100,
                width * 100,
                height * 100);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Update logic if needed
    }

    public void dispose() {
        texture.dispose();
        world.destroyBody(body);
    }
}
