package com.mygame.AngryBirds.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygame.AngryBirds.AngryBirdsMain; // Main game class reference
import com.mygame.AngryBirds.Objects.*;
import com.mygame.AngryBirds.Objects.Bird;
import com.mygame.AngryBirds.Objects.Pig;
import com.mygame.AngryBirds.Objects.Structure;

public class GameScreen implements Screen {
    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Skin skin;
    private TextButton pauseButton;
    private TextButton restartButton;
    private TextButton winScreenButton;
    private TextButton lossScreenButton;
    private HUD hud;
    private Viewport gamePort;
    private OrthographicCamera gameCamera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Bird bird;
    private Pig pig;
    private Structure structure1;
    private Structure structure2;
    private Structure structure3;
    private Structure structure4;
    private Structure structure5;
    private SlingShot sling;
    private AngryBirdsMain game;
    Label ScoreLabel;
    BitmapFont customFont;

    public GameScreen(AngryBirdsMain game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("map2.png")); //add the png
        backgroundImage = new Image(backgroundTexture);

        stage.addActor(backgroundImage);
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(AngryBirdsMain.WIDTH,AngryBirdsMain.HEIGHT,gameCamera);
        hud = new HUD(game.batch);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        BitmapFont font = generator.generateFont(parameter);

        bird = new Bird(150,190);
        pig = new Pig(1500,190);
        sling = new SlingShot(240,190);
        structure1 = new Structure(1400,190,"Wooden_Box.png");
        structure2 = new Structure(1400,270,"Wooden_Box.png");
        structure3 = new Structure(1600,190,"Wooden_Box.png");
        structure4 = new Structure(1600,270,"Wooden_Box.png");
        structure5 = new Structure(1440,350,"Wooden_Plank.png");
        stage.addActor(bird);
        stage.addActor(pig);
        stage.addActor(sling);
        stage.addActor(structure1);
        stage.addActor(structure2);
        stage.addActor(structure3);
        stage.addActor(structure4);
        stage.addActor(structure5);

        //this is to change or add new font to the skin;
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;

        //for creation of the buttons;
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        pauseButton = new TextButton("PAUSE",skin);
        restartButton = new TextButton("RESTART",skin);
        winScreenButton = new TextButton("WIN", skin);
        lossScreenButton = new TextButton("LOSS",skin);
        Integer Score = HUD.getScore();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;
        ScoreLabel = new Label(String.format("Score: %07d", Score), new Label.LabelStyle(font, Color.BLACK));

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.add(pauseButton).pad(10);
        table.add(restartButton).pad(10);
        table.add(winScreenButton).pad(10);
        table.add(lossScreenButton).pad(10);
        table.add(ScoreLabel).padLeft(950);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        addListeners();
    }
    private void addListeners() {
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PauseScreen(game));  // Switch to PauseScreen
            }
        });
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });
        winScreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new winScreen(game));
            }
        });
        lossScreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new lostScreen(game));
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

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
        backgroundTexture.dispose();
        skin.dispose();
    }
}
