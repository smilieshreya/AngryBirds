package com.mygame.AngryBirds.Screen;

import com.badlogic.gdx.InputProcessor;

public class PauseScreenInputProcessor implements InputProcessor {
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Handle pause screen input (e.g., resume, quit)
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        // Handle key events on the pause screen
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }
}
