package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Structure extends Image {
    protected Body body;
    protected World world;
    public String Itexture;
    protected static final float PPM = 100f; // Pixels per meter
    public float positionX;
    public float positionY;

    // Constructor for Structure
    public Structure(float x, float y, String img, World world) {
        super(new Texture(Gdx.files.internal(img)));
        this.positionX = x;
        this.positionY = y;
        this.Itexture = img;
        this.world = world;

        setPosition(x, y);
        createPhysicsBody(x / PPM, y / PPM);
    }

    //  method to define structure-specific properties
    protected abstract void defineFixture(FixtureDef fixtureDef);

    // Initialize Box2D body for collision and physics
    private void createPhysicsBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = false;

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(((getWidth() / 2) / PPM), ((getHeight() / 2) / PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        defineFixture(fixtureDef);

        body.createFixture(fixtureDef);
        shape.dispose();

        body.setUserData(this);
    }

    public void update() {
        Vector2 position = body.getPosition();
        setPosition((position.x * PPM) - getWidth() / 2, (position.y * PPM) - getHeight() / 2);
    }
    public String getTextureName(){
        return Itexture;
    }

    public Body getBody() {
        return body;
    }
    public float getPostitionX(){
        return positionX;
    }
    public float getPostitionY(){
        return positionY;
    }
}
