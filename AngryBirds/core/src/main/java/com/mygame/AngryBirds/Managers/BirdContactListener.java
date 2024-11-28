package com.mygame.AngryBirds.Managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.mygame.AngryBirds.Objects.Bird;
import com.mygame.AngryBirds.Objects.Ground;
import com.mygame.AngryBirds.Objects.Pig;
import com.mygame.AngryBirds.Objects.Structure;

public class BirdContactListener implements ContactListener {

    private boolean birdContactGround = false;
    private boolean birdContactPig = false;
    private boolean birdContactObject = false;

    @Override
    public void beginContact(Contact contact) {
        // Retrieve the fixtures involved in the contact
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        // Check if one of the fixtures is a Bird
        if (userDataA instanceof Bird || userDataB instanceof Bird) {
            Object otherObject = (userDataA instanceof Bird) ? userDataB : userDataA;

            // Determine what the bird made contact with
            if (otherObject instanceof Ground) {
                birdContactGround = true;
                System.out.println("Bird made contact with the ground.");
            } else if (otherObject instanceof Pig) {
                birdContactPig = true;
                System.out.println("Bird made contact with a pig!");
            } else if (otherObject instanceof Structure) {
                birdContactObject = true;
                System.out.println("Bird made contact with a structure.");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        // You can handle the end of contact if necessary, e.g., resetting flags
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Optional: Add behavior before the collision is resolved
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Optional: Add behavior after the collision is resolved
    }

    // Getters to check contact flags
    public boolean isBirdContactGround() {
        return birdContactGround;
    }

    public boolean isBirdContactPig() {
        return birdContactPig;
    }

    public boolean isBirdContactObject() {
        return birdContactObject;
    }

    // Reset flags after processing
    public void resetFlags() {
        birdContactGround = false;
        birdContactPig = false;
        birdContactObject = false;
    }
}
