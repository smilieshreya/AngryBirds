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

public class Bird extends Actor implements InputProcessor {
    private Texture birdTexture;
    private Body birdBody;
    private boolean isDragging = false;
    private Vector2 initialPosition;
    private Vector2 dragPosition;
    private World world;
    private SpriteBatch batch;
    private float x_cord;
    private float y_cord;

    public Bird(World world, float x, float y) {
        this.x_cord = x;
        this.y_cord = y;
        this.world = world;
        this.birdTexture = new Texture(Gdx.files.internal("Red_Bird.png"));
        this.initialPosition = new Vector2(x, y);
        this.dragPosition = new Vector2(x, y);
        this.batch = new SpriteBatch();

        createBirdBody(x, y+100);

        // Register this class as input processor
        //Gdx.input.setInputProcessor(this);
        System.out.println("Bird InputProcessor Registered");
    }

    private void createBirdBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 100f, y / 100f);
        bodyDef.fixedRotation = false;  // Allow rotation
        // Convert to world coordinates

        CircleShape shape = new CircleShape();
        shape.setRadius(15 / 100f);  // Radius in world units

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.3f;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.3f; // Bounciness
        birdBody = world.createBody(bodyDef);
        birdBody.setLinearDamping(0.5f);
        birdBody.createFixture(fixtureDef);
        //birdBody.setAngularVelocity(2f);
        shape.dispose();
    }

    public void update() {
            dragPosition.set(birdBody.getPosition().x * 100, birdBody.getPosition().y * 100);
            //System.out.println("Bird Position: " + birdBody.getPosition());// Sync texture position with body position
    }


    public void render() {
        batch.begin();
        float rotation = (float) Math.toDegrees(birdBody.getAngle());

        batch.draw(birdTexture,
                birdBody.getPosition().x * 100,  // x position (centered)
                birdBody.getPosition().y * 100,  // y position (centered)
                32.5f,  // originX
                32.5f,  // originY
                65,     // width
                65,     // height
                1,      // scaleX
                1,      // scaleY
                rotation,   // rotation
                0,      // srcX
                0,      // srcY
                birdTexture.getWidth(),    // srcWidth
                birdTexture.getHeight(),   // srcHeight
                false,  // flipX
                false   // flipY
        );
        batch.end();
    }

    public Texture getTexture() {
        return birdTexture;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touch Down at: " + screenX + ", " + screenY); // Log touchDown
        Vector2 worldPosition = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);  // Convert to world coordinates
        if (worldPosition.dst(dragPosition) < 50) { // Check if click is near the bird
            isDragging = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            // Convert touch position from screen coordinates to world coordinates (flipping Y-axis)
            dragPosition.set(screenX, Gdx.graphics.getHeight() - screenY);
            System.out.println("Dragging to: " + dragPosition);  // Log the dragging position
            return true;
        }
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isDragging) {
            isDragging = false;

            // Calculate the velocity vector
            Vector2 launchVelocity = initialPosition.cpy().sub(dragPosition).scl(5f);  // Adjust scaling factor for launch strength

            // Apply the velocity to the bird body
            birdBody.setLinearVelocity(launchVelocity.x / 100, launchVelocity.y / 100);  // Apply linear velocity to the body
            System.out.println("Bird launched with velocity: " + launchVelocity);  // Log launch velocity
        }
        return true;
    }

    public Body getBody() {
        return birdBody;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    // Unused methods from InputProcessor
    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }
}
