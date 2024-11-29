package com.mygame.AngryBirds.Managers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygame.AngryBirds.Objects.Pig;

public class PigContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        // Check if a pig is involved
        if (bodyA.getUserData() instanceof Pig || bodyB.getUserData() instanceof Pig) {
            Pig pig = (bodyA.getUserData() instanceof Pig) ? (Pig) bodyA.getUserData() : (Pig) bodyB.getUserData();
            Body otherBody = (bodyA.getUserData() instanceof Pig) ? bodyB : bodyA;

            // Calculate impact velocity
            float impactVelocity = otherBody.getLinearVelocity().len();

            // Apply damage based on velocity threshold
            if (impactVelocity > 1.5f) { // Adjust threshold as needed
                float damage = calculateDamage(impactVelocity);
                pig.takeDamage(damage);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    private float calculateDamage(float velocity) {
        return Math.min(velocity * 50f, 100f); // Cap damage at 100
    }
}
