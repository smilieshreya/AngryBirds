package com.mygame.AngryBirds.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.mygame.AngryBirds.AngryBirdsMain; // Main game class reference
import com.mygame.AngryBirds.Objects.Bird;
import com.mygame.AngryBirds.Objects.Pig;
import com.mygame.AngryBirds.Objects.Structure;
// com.mygame.AngryBirds.Objects.Bird;
//import com.mygame.AngryBirds.Objects.Pig;
//import com.mygame.AngryBirds.Objects.Structure;

public class GameScreen implements Screen {
    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Skin skin;
    private TextButton pauseButton;
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
    private AngryBirdsMain game;

    public GameScreen(AngryBirdsMain game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("mapE.png")); //add the png
        backgroundImage = new Image(backgroundTexture);

        stage.addActor(backgroundImage);
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(AngryBirdsMain.WIDTH,AngryBirdsMain.HEIGHT,gameCamera);
        hud = new HUD(game.batch);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Freedom-10eM.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        BitmapFont font = generator.generateFont(parameter);

        bird = new Bird(150,190);
        pig = new Pig(1500,190);
        structure1 = new Structure(1400,190);
        structure2 = new Structure(1400,270);
        structure3 = new Structure(1600,190);
        structure4 = new Structure(1600,270);
        stage.addActor(bird);
        stage.addActor(pig);
        stage.addActor(structure1);
        stage.addActor(structure2);
        stage.addActor(structure3);
        stage.addActor(structure4);

        //this is to change or add new font to the skin;
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;

        //for creation of the buttons;
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        pauseButton = new TextButton("PAUSE",skin);

        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.right();
        table.add(pauseButton).pad(10);
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
