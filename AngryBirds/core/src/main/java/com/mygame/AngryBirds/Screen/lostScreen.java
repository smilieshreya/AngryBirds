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
import com.mygame.AngryBirds.Objects.scoreBoard;
import com.mygame.AngryBirds.Objects.starsBoard;

public class lostScreen implements Screen {
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Stage stage;
    private Skin skin;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private AngryBirdsMain game;
    private scoreBoard board;
    private TextButton backButton;
    private starsBoard star;


    public lostScreen(AngryBirdsMain game,String level) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font = generator.generateFont(parameter);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;

        backgroundTexture = new Texture(Gdx.files.internal("map2_dark.png")); //add the appropriate screen
        backgroundImage = new Image(backgroundTexture);
        stage.addActor(backgroundImage);

        board = new scoreBoard(450,220,"scb.png");
        stage.addActor(board);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("large-font");
        Label levelLable = new Label(level, labelStyle);
        Label highScorelable = new Label("HIGHSCORE:",labelStyle);
        Label lossLabel = new Label("BETTER LUCK NEXT TIME :(", labelStyle);
        Label levelfailed = new Label("LEVEL LOST!", labelStyle);
        Label score = new Label("SCORE:",labelStyle);

        Label ScoreLabel = new Label(String.format("%07d", HUD.score), labelStyle);
        levelLable.setPosition(575,745);
        highScorelable.setPosition(1000,745);
        lossLabel.setPosition(515,575);
        levelfailed.setPosition(1130,575);
        score.setPosition(515, 475);
        ScoreLabel.setPosition(515,400);
        star = new starsBoard(1100,420,"0stars.png");
        backButton = new TextButton("BACK TO HOME",skin);
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.add(backButton).pad(10);
        stage.addActor(levelLable);
        stage.addActor(highScorelable);
        stage.addActor(lossLabel);
        stage.addActor(levelfailed);
        stage.addActor(score);
        stage.addActor(ScoreLabel);
        stage.addActor(star);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
        addListeners();

    }
    private void addListeners() {
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelSelectorScreen(game));// Switch to PauseScreen
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

