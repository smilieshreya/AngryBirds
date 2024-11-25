package com.mygame.AngryBirds.Objects;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.AngryBirds.Objects.Bird;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;

public class BirdManager {
    private ArrayDeque<Bird> birds;
    private World world;
    private Vector2 catapultPosition;

    public BirdManager(World world, Vector2 catapultPosition) {
        this.world = world;
        this.catapultPosition = catapultPosition;
        this.birds = new ArrayDeque<>();
    }

    public void addBird(Bird bird) {
        birds.add(bird);
    }

    public Bird getNextBird() {
        return birds.poll();
    }

    public void placeNextBirdOnCatapult() {
        if (!birds.isEmpty()) {
            Bird nextBird = birds.peek();
            nextBird.getBody().setTransform(catapultPosition, 0);
            nextBird.getBody().setLinearVelocity(0, 0);
            nextBird.getBody().setType(BodyDef.BodyType.StaticBody);
        }
    }

    public boolean hasMoreBirds() {
        return !birds.isEmpty();
    }
}
