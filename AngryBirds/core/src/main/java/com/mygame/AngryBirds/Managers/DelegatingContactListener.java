package com.mygame.AngryBirds.Managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class DelegatingContactListener implements ContactListener {
    private final Array<ContactListener> listeners = new Array<>();

    // Add a new listener
    public void addListener(ContactListener listener) {
        listeners.add(listener);
    }

    // Remove a listener
    public void removeListener(ContactListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public void beginContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        for (ContactListener listener : listeners) {
            listener.preSolve(contact, oldManifold);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (ContactListener listener : listeners) {
            listener.postSolve(contact, impulse);
        }
    }
}

