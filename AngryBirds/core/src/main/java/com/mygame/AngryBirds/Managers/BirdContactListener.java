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

        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if (userDataA instanceof Bird || userDataB instanceof Bird) {
            Object otherObject = (userDataA instanceof Bird) ? userDataB : userDataA;

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
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }


    public boolean isBirdContactGround() {
        return birdContactGround;
    }

    public boolean isBirdContactPig() {
        return birdContactPig;
    }

    public boolean isBirdContactObject() {
        return birdContactObject;
    }

    public void resetFlags() {
        birdContactGround = false;
        birdContactPig = false;
        birdContactObject = false;
    }
}
