package com.mygame.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.AngryBirds.Screen.HomeScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AngryBirdsMain extends Game {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public SpriteBatch batch;


    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode()); //this makes sure that the game opens in fullscreen mode
        this.setScreen(new HomeScreen(this)); //setting the initial screen to the HomeScreen
    }

    @Override
    public void render() {
        super.render(); //this renders the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
        getScreen().dispose(); // this disposes of the current screen
    }
}