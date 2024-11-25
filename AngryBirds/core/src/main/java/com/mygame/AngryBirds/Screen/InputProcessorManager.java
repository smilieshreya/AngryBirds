package com.mygame.AngryBirds.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class InputProcessorManager {
    private static Array<InputProcessor> inputProcessors = new Array<>();

    // Add a new input processor to the stack
    public static void pushProcessor(InputProcessor processor) {
        inputProcessors.add(processor);
        Gdx.input.setInputProcessor(processor);
    }

    // Remove the top input processor from the stack
    public static void popProcessor() {
        if (!inputProcessors.isEmpty()) {
            inputProcessors.removeIndex(inputProcessors.size - 1);
            if (!inputProcessors.isEmpty()) {
                Gdx.input.setInputProcessor(inputProcessors.peek());
            } else {
                Gdx.input.setInputProcessor(null); // No processors left
            }
        }
    }

    // Get the current input processor
    public static InputProcessor getCurrentProcessor() {
        return inputProcessors.isEmpty() ? null : inputProcessors.peek();
    }
}