package com.mygame.AngryBirds.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygame.AngryBirds.AngryBirdsMain;


public class PauseScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private TextButton resumeButton;
    private TextButton levelMenuButton;
    private TextButton homeButton;
    private AngryBirdsMain game;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public PauseScreen(AngryBirdsMain game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());

        backgroundTexture = new Texture(Gdx.files.internal("map2_dark.png"));
        backgroundImage = new Image(backgroundTexture);
        stage.addActor(backgroundImage);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        BitmapFont font = generator.generateFont(parameter);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;
        // Create buttons
        resumeButton = new TextButton("RESUME", skin);
        levelMenuButton = new TextButton("LEVEL MENU", skin);
        homeButton = new TextButton("MAIN MENU", skin);

        // Arrange buttons in a table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(resumeButton).pad(10);
        table.row();
        table.add(levelMenuButton).pad(10);
        table.row();
        table.add(homeButton).pad(10);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);  // Set stage to handle inputs

        // Add listeners to buttons
        addListeners();
    }
    private void addListeners() {
        // Resume button takes you back to the game screen
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));  // Return to GameScreen
            }
        });

        // Main Menu button takes you back to the home screen
        levelMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelSelectorScreen(game));  // Return to HomeScreen
            }
        });

        // Exit button exits the application
        homeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new HomeScreen(game));
                //Gdx.app.exit();  // Exit the game
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
