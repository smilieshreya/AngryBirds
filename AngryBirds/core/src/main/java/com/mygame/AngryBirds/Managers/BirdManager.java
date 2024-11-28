package com.mygame.AngryBirds.Managers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.mygame.AngryBirds.Objects.Bird;
import com.mygame.AngryBirds.Screen.GameScreen;

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
        if (hasBirdsLeft()) {
            currentBird = getNextBird();
            if (currentBird != null) {
                currentBird.wasFired = false;
                currentBird.resetBird();
                System.out.println("Bird reset successful");
            } else {
                System.out.println("Current bird is NULL");
            }
            currentBird.resetBird();
        }
        else{
            System.out.println("No Birds Here");
        }
    }

    // Launch the current bird and set the next bird ready

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
            //return (velocity.len() > 10f && birdPosition.dst(slingshotPosition) > 5f ); // Adjust threshold based on your game
            return (!(birdPosition.x==slingshotPosition.x/100));
        }
        return false;
    }

}