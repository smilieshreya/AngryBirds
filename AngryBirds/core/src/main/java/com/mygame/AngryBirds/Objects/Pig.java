package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.AngryBirds.Screen.GameScreen;

import java.util.ArrayList;
import java.util.List;

public abstract class Pig extends Actor {
    public static final List<Body> bodiesToDestroy = new ArrayList<>();
    protected float health;
    protected Sprite sprite;
    protected Body body;
    protected static final float PPM = 100f; // Pixels per meter
    public Screen gamescreen;
    public float positionX;
    public float positionY;


    public Pig(World world, float x, float y, String texturePath) {
        health = 100f;
        this.positionX = x;
        this.positionY = y;
        // Initialize the sprite
        sprite = createSprite(texturePath);
        sprite.setOriginCenter();

        createPhysicsBody(world, x / PPM, y / PPM);

        syncSpriteWithBody();
    }

    // setting fixture properties
    protected abstract void defineFixture(FixtureDef fixtureDef);

    private void createPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.55f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        defineFixture(fixtureDef);

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this); //user data for collision
    }

    public Sprite createSprite(String texturePath) {
        Sprite newSprite = new Sprite(new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.internal(texturePath)));
        newSprite.setSize(1.0f * PPM, 1.0f * PPM);
        return newSprite;
    }

    private void syncSpriteWithBody() {
        sprite.setPosition((body.getPosition().x * PPM) - sprite.getWidth() / 2,
                ((body.getPosition().y * PPM) - sprite.getHeight() / 2));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        syncSpriteWithBody();
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public void draw(com.badlogic.gdx.graphics.g2d.Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            markForDestruction();
        }
    }

    public void markForDestruction() {
        if (body != null) {
            bodiesToDestroy.add(body);
            GameScreen.pigs.remove(this);
            this.remove(); // Remove sprite from the stage
        }
    }

    public static void destroyMarkedBodies(World world) {
        for (Body body : bodiesToDestroy) {
            world.destroyBody(body);
        }
        bodiesToDestroy.clear();
    }
    public float returnHealth(){
        return health;
    }
    public void setHealth(float HealthValue){
        health = HealthValue;
    }
    public float getPostitionX(){
        return positionX;
    }
    public float getPostitionY(){
        return positionY;
    }
    public Body getBody(){
        return body;
    }
}
