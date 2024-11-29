package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public abstract class Bird extends Actor implements InputProcessor {
    private static final List<Body> bodiesToDestroy = new ArrayList<>();
    public Vector2 slingshotPosition;
    private float maxDragDistance = 150f;
    public Texture birdTexture;
    public Body birdBody;
    public boolean isDragging = false;
    public boolean isReadyToFire = false;
    public Vector2 initialPosition;
    public Vector2 dragPosition;
    public World world;
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public float x_cord;
    public float y_cord;
    public boolean wasFired;

    public Bird(World world, float x, float y) {
        this.x_cord = x;
        this.y_cord = y;
        this.world = world;
        this.wasFired = false;
        this.initialPosition = new Vector2(x, y);
        this.slingshotPosition = new Vector2(340 - 25, 280);
        this.dragPosition = new Vector2(x, y);
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        // Initialize bird texture from child class
        this.birdTexture = new Texture(Gdx.files.internal(getTexturePath()));

        createBirdBody(x, y + 100);

        System.out.println("Bird InputProcessor Registered");
    }

    protected abstract String getTexturePath();

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
        birdBody.setUserData(this);
        birdBody.setLinearDamping(0.5f);
        birdBody.createFixture(fixtureDef);

        shape.dispose();
    }

    public void update() {
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

        if (isDragging) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 1);
            shapeRenderer.rectLine(slingshotPosition, dragPosition, 5);
            shapeRenderer.end();
        }
    }
    public void launch() {
        isReadyToFire = false;
        System.out.println("Bird launched");
    }

    public Texture getTexture() {
        return birdTexture;
    }

    public Body getBody() {
        return birdBody;
    }

    public void resetBird() {
        birdBody.setTransform(slingshotPosition.x / 100, slingshotPosition.y / 90, 0);
        birdBody.setLinearVelocity(0, 0);
        birdBody.setAngularVelocity(0);
        isReadyToFire = true;
        isDragging = false;
    }
    private void markForDestruction() {
        if (birdBody != null) {
            bodiesToDestroy.add(birdBody);
            this.remove(); // Remove sprite from the stage
        }
    }

    public static void destroyMarkedBodies(World world) {
        for (Body body : bodiesToDestroy) {
            world.destroyBody(body);
        }
        bodiesToDestroy.clear();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
            Vector2 worldPosition = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
            Vector2 slingshotToDrag = new Vector2(worldPosition).sub(slingshotPosition);
            if (slingshotToDrag.len() > maxDragDistance) {
                slingshotToDrag.setLength(maxDragDistance);
            }
            dragPosition.set(slingshotPosition).add(slingshotToDrag);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging) {
            isDragging = false;
            Vector2 launchVelocity = slingshotPosition.cpy().sub(dragPosition).scl(0.1f);  // Adjust the scaling factor
            birdBody.setLinearVelocity(launchVelocity.x, launchVelocity.y);
            isReadyToFire = true;
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
