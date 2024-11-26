package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bird extends Actor implements InputProcessor {
    private Vector2 slingshotPosition;
    public Texture birdTexture;
    public Body birdBody;
    public boolean isDragging = false;
    public boolean isReadyToFire = false;  // Track if bird is ready to be fired
    public Vector2 initialPosition;
    public Vector2 dragPosition;
    public World world;
    public SpriteBatch batch;
    public float x_cord;
    public float y_cord;

    public Bird(World world, float x, float y) {
        this.x_cord = x;
        this.y_cord = y;
        this.world = world;
        this.initialPosition = new Vector2(x, y);
        this.slingshotPosition = new Vector2(340-25, 280);
        this.dragPosition = new Vector2(x, y);
        this.batch = new SpriteBatch();

        // Initialize bird texture from child class
        this.birdTexture = new Texture(Gdx.files.internal(getTexturePath()));

        createBirdBody(x, y + 100);

        System.out.println("Bird InputProcessor Registered");
    }

    protected abstract String getTexturePath(); // Child classes must define the texture path

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
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.3f;

        birdBody = world.createBody(bodyDef);
        birdBody.setLinearDamping(0.5f);
        birdBody.createFixture(fixtureDef);

        shape.dispose();
    }

    public void update() {
        // Sync bird's drag position with its body position
        dragPosition.set(birdBody.getPosition().x * 100, birdBody.getPosition().y * 100);
    }

    public void render() {
        batch.begin();
        float rotation = (float) Math.toDegrees(birdBody.getAngle());

        batch.draw(birdTexture,
                birdBody.getPosition().x * 100,
                birdBody.getPosition().y * 100,
                32.5f,
                32.5f,
                65,
                65,
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
    }

    public Texture getTexture() {
        return birdTexture;
    }

    public Body getBody() {
        return birdBody;
    }

    public void resetBird() {
        // Reset bird to its initial position and set it to "ready-to-fire" state
        birdBody.setTransform(slingshotPosition.x/100, slingshotPosition.y/70, 0);
        birdBody.setLinearVelocity(0, 0);
        birdBody.setAngularVelocity(0);
        isReadyToFire = true;
        isDragging = false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Only allow dragging if the bird is ready to be fired
        if (isReadyToFire) {
            Vector2 worldPosition = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
            if (worldPosition.dst(dragPosition) < 50) {
                isDragging = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            dragPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging) {
            isDragging = false;
            Vector2 launchVelocity = initialPosition.cpy().sub(dragPosition).scl(5f);
            birdBody.setLinearVelocity(launchVelocity.x / 100, launchVelocity.y / 100);
            isReadyToFire = false;  // Bird is no longer ready once it is fired
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    // Unused methods
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
