package com.mygame.AngryBirds.Managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.AngryBirds.Objects.*;
import com.mygame.AngryBirds.Screen.GameScreen;
import com.mygame.AngryBirds.Screen.HUD;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    // Detailed game state capture
    public int score;
    public int level;
    public List<BirdState> birdStates;
    public List<PigState> pigStates;
    public List<StructureState> structureStates;
    public float worldGravity;
    public Vector2 catapultPosition;

    // Nested state classes
    public static class BirdState implements Serializable {
        public float x, y;
        public String birdType;
        public boolean wasFired;
        public float velocityX, velocityY;
    }

    public static class PigState implements Serializable {
        public float x, y;
        public float health;
        public String pigType;
    }

    public static class StructureState implements Serializable {
        public float x, y;
        public String structureType;
        public String textureName;
    }

    // Capture method for current game state
    public static GameState captureCurrentState(GameScreen gameScreen) {
        GameState state = new GameState();
        state.score = HUD.getScore();
        state.level = gameScreen.getCurrentLevel();
        state.worldGravity = gameScreen.world.getGravity().y;
        state.catapultPosition = gameScreen.getCatapultPosition();

        // Capture bird states
        state.birdStates = gameScreen.birds.stream()
                .map(GameState::captureBirdState)
                .collect(Collectors.toList());

        // Capture pig states
        state.pigStates = gameScreen.pigs.stream()
                .map(GameState::capturePigState)
                .collect(Collectors.toList());

        // Capture structure states
        state.structureStates = gameScreen.structures.stream()
                .map(GameState::captureStructureState)
                .collect(Collectors.toList());

        return state;
    }

    // Helper methods to capture individual object states
    private static BirdState captureBirdState(Bird bird) {
        BirdState state = new BirdState();
        state.x = bird.getInitialPosition().x;
        state.y = bird.getInitialPosition().y;
        state.birdType = bird.getClass().getSimpleName();
        state.wasFired = bird.wasFired;
        state.velocityX = bird.getBody().getLinearVelocity().x;
        state.velocityY = bird.getBody().getLinearVelocity().y;
        return state;
    }

    private static PigState capturePigState(Pig pig) {
        PigState state = new PigState();
        state.x = pig.getPostitionX();
        state.y = pig.getPostitionY();
        state.health = pig.returnHealth();
        state.pigType = pig.getClass().getSimpleName();
        return state;
    }

    private static StructureState captureStructureState(Structure structure) {
        StructureState state = new StructureState();
        state.x = structure.getPostitionX();
        state.y = structure.getPostitionY();
        state.structureType = structure.getClass().getSimpleName();
        state.textureName = structure.getTextureName();
        return state;
    }

    // Restoration method
    public void restoreGameState(GameScreen gameScreen) {
        clearCurrentGameWorld(gameScreen);
        // Clear existing game objects
        gameScreen.birds.clear();
        gameScreen.pigs.clear();
        gameScreen.structures.clear();

        // Restore score and level
        HUD.setScore(this.score);
        //setCurrentLevel(this.level);

        // Restore world gravity
        gameScreen.world.setGravity(new Vector2(0, this.worldGravity));

        // Restore birds
        for (BirdState birdState : this.birdStates) {
            Bird bird = createBirdFromState(gameScreen.world, birdState);
            gameScreen.birds.add(bird);
            gameScreen.stage.addActor(bird);
        }

        // Restore pigs
        for (PigState pigState : this.pigStates) {
            Pig pig = createPigFromState(gameScreen.world, pigState);
            gameScreen.pigs.add(pig);
            gameScreen.stage.addActor(pig);
        }

        // Restore structures
        for (StructureState structureState : this.structureStates) {
            Structure structure = createStructureFromState(gameScreen.world, structureState);
            gameScreen.structures.add(structure);
            gameScreen.stage.addActor(structure);
        }
    }
    private void clearCurrentGameWorld(GameScreen gameScreen) {
        // Clear birds
        for (Bird bird : gameScreen.birds) {
            bird.remove(); // Remove bird from stage
            gameScreen.world.destroyBody(bird.getBody()); // Destroy bird body from world
        }
        gameScreen.birds.clear();

        // Clear pigs
        for (Pig pig : gameScreen.pigs) {
            pig.remove(); // Remove pig from stage
            gameScreen.world.destroyBody(pig.getBody()); // Destroy pig body from world
        }
        gameScreen.pigs.clear();

        // Clear structures
        for (Structure structure : gameScreen.structures) {
            structure.remove(); // Remove structure from stage
            gameScreen.world.destroyBody(structure.getBody()); // Destroy structure body from world
        }
        gameScreen.structures.clear();
    }

    // Factory methods for object recreation (placeholder implementations)
    private Bird createBirdFromState(World world, BirdState state) {
        Bird bird = null;
        switch (state.birdType) {
            case "RedBird":
                bird = new RedBird(world, state.x, state.y);
                break;
            // Add other bird types
            case "YellowBird":
                bird = new YellowBird(world,state.x,state.y);
                break;
            case "BlackBird":
                bird = new BlackBird(world, state.x, state.y);
                break;
        }
        if (bird != null) {
            bird.wasFired = state.wasFired;
            bird.getBody().setLinearVelocity(state.velocityX, state.velocityY);
        }
        return bird;
    }

    // Similar methods for createPigFromState and createStructureFromState
    private Pig createPigFromState(World world, PigState state){
        Pig pig = null;
        switch (state.pigType){
            case "BigPig":
                pig = new BigPig(world, state.x,state.y);
                break;
            case "CorporalPig":
                pig = new CorporalPig(world, state.x,state.y);
                break;
            case "CrownPig":
                pig = new CrownPig(world,state.x,state.y);
                break;
        }
        if (pig!=null){
            pig.setHealth(state.health);
        }
        return pig;
    }
    private Structure createStructureFromState(World world, StructureState state){
        Structure structure = null;
        switch (state.structureType){
            case "WoodStructure":
                structure = new WoodStructure(state.x, state.y, state.textureName, world);
                break;
            case "GlassStructure":
                structure = new GlassStructure(state.x, state.y, state.textureName, world);
                break;
            case "StoneStructure":
                structure = new StoneStructure(state.x, state.y, state.textureName, world);
                break;
        }
        return structure;
    }
}