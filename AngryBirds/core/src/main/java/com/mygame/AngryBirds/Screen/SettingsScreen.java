package com.mygame.AngryBirds.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygame.AngryBirds.AngryBirdsMain;

public class SettingsScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton muteAllButton;
    private TextButton backButton;
    private AngryBirdsMain game;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public SettingsScreen(AngryBirdsMain game) {
        this.game = game;  // Store reference to main game instance
        stage = new Stage(new ScreenViewport());

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundImage = new Image(backgroundTexture);
        stage.addActor(backgroundImage);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Freedom-10eM.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        BitmapFont font = generator.generateFont(parameter);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;
        // Create buttons
        muteAllButton = new TextButton("MUTE", skin);
        backButton = new TextButton("BACK", skin);

        parameter.size = 64;
        font = generator.generateFont(parameter);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("large-font");
        Label headingLabel = new Label("SETTINGS", labelStyle);
        headingLabel.setPosition(Gdx.graphics.getWidth() / 2f - headingLabel.getWidth() / 2f, Gdx.graphics.getHeight()-400);
        // Arrange buttons in a table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(muteAllButton).pad(10);
        table.row();
        table.add(backButton).pad(10);
        stage.addActor(headingLabel);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);  // Set stage to handle inputs

        // Add listeners to buttons
        addListeners();
    }
    private void addListeners() {
        // Resume button takes you back to the game screen
        muteAllButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //mute audio;  // Return to GameScreen
            }
        });

        // Main Menu button takes you back to the home screen
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HomeScreen(game));  // Return to HomeScreen
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  // Clear screen
        stage.act(Gdx.graphics.getDeltaTime());    // Process actions
        stage.draw();                              // Draw the stage
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
