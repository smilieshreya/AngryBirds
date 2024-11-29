package com.mygame.AngryBirds.Managers;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.*;
import com.mygame.AngryBirds.Objects.Structure;
import com.mygame.AngryBirds.Screen.GameScreen;
import com.mygame.AngryBirds.Screen.HUD;

import java.util.ArrayList;
import java.util.List;

public class StructureContactListener implements ContactListener {
    private static final List<Body> bodiesToDestroy = new ArrayList<>();
    public Screen gamescreen;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Object userDataA = fixtureA.getBody().getUserData();
        Object userDataB = fixtureB.getBody().getUserData();

        // Handle collision for structures
        if (userDataA instanceof Structure) {
            handleStructureCollision((Structure) userDataA, fixtureB.getBody());
        } else if (userDataB instanceof Structure) {
            handleStructureCollision((Structure) userDataB, fixtureA.getBody());
        }
    }

    private void handleStructureCollision(Structure structure, Body otherBody) {
        float impactVelocity = otherBody.getLinearVelocity().len();
        if (impactVelocity > 2.5f) {// Threshold for breaking structure
            HUD.addScore(1000);
            markBodyForDestruction(structure);
        }
    }

    private void markBodyForDestruction(Structure structure) {
        Body body = structure.getBody();
        if (body != null) {
            bodiesToDestroy.add(body);
            GameScreen.structures.remove(structure);
            structure.remove(); // Remove sprite from the stage
        }
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold manifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {}

    public static void destroyMarkedBodies(World world) {
        for (Body body : bodiesToDestroy) {
            world.destroyBody(body);
        }
        bodiesToDestroy.clear();
    }
}