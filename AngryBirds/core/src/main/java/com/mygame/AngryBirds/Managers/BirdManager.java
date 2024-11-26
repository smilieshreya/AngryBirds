package com.mygame.AngryBirds.Managers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.mygame.AngryBirds.Objects.Bird;
import java.util.LinkedList;
import java.util.Queue;

public class BirdManager {
    private World world;
    private Vector2 slingshotPosition;
    private Queue<Bird> birdQueue; // Queue to manage available birds
    private Bird currentBird;  // Track the current bird being fired

    public BirdManager(World world, Vector2 slingshotPosition) {
        this.world = world;
        this.slingshotPosition = slingshotPosition;
        birdQueue = new LinkedList<>();
    }

    // Add a bird manually to the queue (use this method at the start of the level)
    public void addBird(Bird bird) {
        birdQueue.add(bird);
    }

    // Get the next bird available for launch
    public Bird getNextBird() {
        return birdQueue.poll(); // Remove and return the first bird in the queue
    }

    // Get the next bird and reset it to the slingshot position, making it ready
    public void resetNextBird() {
        System.out.println("Attempting to reset next bird");
        System.out.println("Birds left in queue: " + birdQueue.size());

        if (hasBirdsLeft()) {
            currentBird = getNextBird();

            System.out.println("Current bird retrieved: " + currentBird);
            System.out.println("Current bird initial position: " +
                    currentBird.getBody().getPosition().x * 100 + ", " +
                    currentBird.getBody().getPosition().y * 100);

            currentBird.resetBird();

            System.out.println("After reset, bird position: " +
                    currentBird.getBody().getPosition().x * 100 + ", " +
                    currentBird.getBody().getPosition().y * 100);

        } else {
            System.out.println("No birds left in queue");
        }
    }

    // Launch the current bird and set the next bird ready
    public void launchCurrentBird() {
        if (currentBird != null) {

            // Fire the bird with a potentially dynamic impulse
            currentBird.isReadyToFire = true; // Bird is no longer ready
            currentBird = null;
        }
    }

    private Vector2 calculateLaunchImpulse() {
        // Calculate launch impulse based on slingshot stretch, angle, etc.
        // This is just an example
        return new Vector2(10, 5); // Adjust based on your game's mechanics
    }

    // Check if there are birds left to launch
    public boolean hasBirdsLeft() {
        return !birdQueue.isEmpty();
    }

    // Get the number of birds left
    public int getBirdCount() {
        return birdQueue.size();
    }

    public Bird getCurrentBird() {
        return currentBird;
    }
    public boolean isBirdFired() {
        if (currentBird != null) {
            Vector2 velocity = currentBird.getBody().getLinearVelocity();
            Vector2 birdPosition = currentBird.getBody().getPosition();
            // Check if the bird's velocity is above a threshold
            return (velocity.len() > 9f && birdPosition.dst(slingshotPosition) > 15f); // Adjust threshold based on your game
        }
        return false;
    }

}