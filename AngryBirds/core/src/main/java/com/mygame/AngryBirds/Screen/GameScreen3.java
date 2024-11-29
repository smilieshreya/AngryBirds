package com.mygame.AngryBirds.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.AngryBirds.AngryBirdsMain;
import com.mygame.AngryBirds.Managers.*;
import com.mygame.AngryBirds.Objects.*;

import java.util.ArrayList;
import java.util.List;

public class GameScreen3 implements Screen {
    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Skin skin;
    private TextButton pauseButton;
    private TextButton restartButton;
    private HUD hud;
    private Viewport gamePort;
    private OrthographicCamera gameCamera;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Bird bird;
    private Bird bird2;
    private Bird bird3;
    private Pig pig;
    private Ground ground;
    public static List<Structure> structures = new ArrayList<>();
    public static List<Pig> pigs = new ArrayList<>();
    public static List<Bird> birds = new ArrayList<>();
    private Structure structure1;
    private Structure structure2;
    private Structure structure3;
    private Structure structure4;
    private Structure structure5;
    private Structure structure6;
    private Structure structure7;
    private Structure structure8;
    private Structure structure9;
    private SlingShot sling;
    private AngryBirdsMain game;
    private SpriteBatch batch;
    private InputMultiplexer inputMultiplexer;
    private DelegatingContactListener delegatingContactListener;
    private StructureContactListener structureListener;
    private BirdContactListener birdListener;
    private PigContactListener pigListener;
    private Label ScoreLabel;
    private BitmapFont customFont;
    private BirdManager birdManager;
    private Bird currentBird;
    private float accumulator = 0;
    private  Vector2 catapultPosition;
    private boolean hasProcessedCurrentBird = false;
    private int count;

    public GameScreen3(AngryBirdsMain game) {
        this.game = game;
        inputMultiplexer = new InputMultiplexer();
        batch = new SpriteBatch();
        stage = new Stage(new FitViewport(AngryBirdsMain.WIDTH, AngryBirdsMain.HEIGHT));
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("map2.png")); // Add the PNG
        backgroundImage = new Image(backgroundTexture);
        world = new World(new Vector2(0, -10), true); // Adding the Box2D world
        debugRenderer = new Box2DDebugRenderer();
        stage.addActor(backgroundImage);
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(AngryBirdsMain.WIDTH, AngryBirdsMain.HEIGHT, gameCamera);
        hud = new HUD(game.batch);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        BitmapFont font = generator.generateFont(parameter);

        birds.clear();
        pigs.clear();
        structures.clear();
        pig = new CrownPig(world, 1400, 400);
        pigs.add(pig);
        pigListener = new PigContactListener();
        sling = new SlingShot(world, 340, 280);
        catapultPosition = new Vector2(315, 285);
        birdManager = new BirdManager(world, catapultPosition);
        bird = new BlackBird(world, catapultPosition.x, catapultPosition.y);
        birds.add(bird);
        bird2 = new BlackBird(world,200,280);
        birds.add(bird2);
        birdListener = new BirdContactListener();
        bird3 = new BlackBird(world,120,280);
        birds.add(bird3);

        birdManager.addBird(bird);
        birdManager.addBird(bird2);
        birdManager.addBird(bird3);
        birdManager.resetNextBird();

        float scale = 100f;
        ground = new Ground(world, 0 / scale, AngryBirdsMain.WIDTH / scale, 173 / scale);
        structure1 = new StoneStructure(1320, 280, "Stone_Box.jpg", world);
        structures.add(structure1);
        structure2 = new StoneStructure(1320, 320, "Stone_Box.jpg", world);
        structures.add(structure2);
        structure3 = new StoneStructure(1320, 360, "Stone_Box.jpg", world);
        structures.add(structure3);
        structure4 = new StoneStructure(1520, 280, "Stone_Box.jpg", world);
        structures.add(structure4);
        structure5 = new StoneStructure(1520, 320, "Stone_Box.jpg", world);
        structures.add(structure5);
        structure6 = new StoneStructure(1520, 360, "Stone_Box.jpg", world);
        structures.add(structure6);
        structure8 = new StoneStructure(1520, 400, "Stone_Box.jpg", world);
        structures.add(structure8);
        structure9 = new StoneStructure(1520, 440, "Stone_Box.jpg", world);
        structures.add(structure9);
        structure7 = new StoneStructure(1400, 390, "Stone_Plank_with_Markings.jpg", world);
        structures.add(structure7);
        structureListener = new StructureContactListener();
        stage.addActor(bird);
        stage.addActor(bird2);
        stage.addActor(bird3);
        stage.addActor(pig);
        stage.addActor(sling);
        stage.addActor(structure1);
        stage.addActor(structure2);
        stage.addActor(structure3);
        stage.addActor(structure4);
        stage.addActor(structure5);
        stage.addActor(structure6);
        stage.addActor(structure7);
        stage.addActor(structure8);
        stage.addActor(structure9);
        gameCamera.zoom = 0.07f;
        // For creation of the buttons
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        skin.get(TextButton.TextButtonStyle.class).font = font;
        pauseButton = new TextButton("PAUSE", skin);
        restartButton = new TextButton("RESTART", skin);

        ScoreLabel = new Label(String.format("Score: %07d",HUD.score), new Label.LabelStyle(font, Color.BLACK));
        Table table = new Table();
        table.setFillParent(true);
        table.top();
        table.left();
        table.add(pauseButton).pad(10);
        table.add(restartButton).pad(10);
        table.add(ScoreLabel).padLeft(1350);
        stage.addActor(table);

        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(bird);
        inputMultiplexer.addProcessor(bird2);
        inputMultiplexer.addProcessor(bird3);
        Gdx.input.setInputProcessor(inputMultiplexer);

        delegatingContactListener = new DelegatingContactListener();
        delegatingContactListener.addListener(structureListener);
        delegatingContactListener.addListener(pigListener);
        delegatingContactListener.addListener(birdListener);
        world.setContactListener(delegatingContactListener);
        addListeners();
    }

    private void addListeners() {
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //com.mygame.AngryBirds.Screen.InputProcessorManager.pushProcessor(new PauseScreenInputProcessor()); // Switch to PauseScreen input
                game.setScreen(new PauseScreen(game)); // Switch to PauseScreen
            }
        });
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.input.setInputProcessor(null);
                game.setScreen(new GameScreen3(game)); // Restart the game
            }
        });
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScoreLabel.setText(String.format("Score: %07d",HUD.score));
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        hud.stage.act(delta);
        stage.act(Gdx.graphics.getDeltaTime());

        for (Actor actor : stage.getActors()) {
            if (actor instanceof Structure) {
                ((Structure) actor).update();
            }
        }
        stage.draw();

        bird.render();
        bird.update();
        bird2.render();
        bird2.update();
        bird3.render();
        bird3.update();
        StructureContactListener.destroyMarkedBodies(world);
        Pig.destroyMarkedBodies(world);
        for (Structure structure : structures) {
            structure.update();
        }
        System.out.println("Birds: "+birds.size());

        if (birdManager.isBirdFired()) {
            inputMultiplexer.removeProcessor(birdManager.getCurrentBird());
            birds.remove(birdManager.getCurrentBird());
            System.out.println("Birds: "+birds.size());
            birdManager.resetNextBird();
            birdManager.getCurrentBird().wasFired = true;
            hasProcessedCurrentBird = true;
        }

        if (!birdManager.isBirdFired()) {
            hasProcessedCurrentBird = false;
        }
        for (Pig pig:pigs){
            if (pig.returnHealth()<=0f){
                pigs.remove(pig);
            }
        }
        if (pigs.isEmpty()) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    Gdx.input.setInputProcessor(null);
                    game.setScreen(new winScreen(game,"LEVEL III"));
                }
            });
        }else{
            if(birds.isEmpty() && birdManager.getCurrentBird().wasFired && (birdListener.isBirdContactGround() ||birdListener.isBirdContactObject())){
                if(!pigs.isEmpty()){
                    Gdx.input.setInputProcessor(null);
                    game.setScreen(new lostScreen(game,"LEVEL III"));
                }
            }
        }


        if (birdListener.isBirdContactGround()) {
            System.out.println("Bird hit the ground! Handle game logic.");
            birdListener.resetFlags(); // Reset after handling
        }
        if (birdListener.isBirdContactPig()) {
            System.out.println("Bird hit a pig! Handle game logic.");
            birdListener.resetFlags(); // Reset after handling
        }
        if (birdListener.isBirdContactObject()) {
            System.out.println("Bird hit an object! Handle game logic.");
            birdListener.resetFlags(); // Reset after handling
        }

        debugRenderer.render(world, gameCamera.combined);
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void resize(int i, int i1) {
        gamePort.update(i, i1);
    }

    @Override
    public void pause() {
        // Handle pause logic
    }

    @Override
    public  void resume() {
        // Handle resume logic
    }

    @Override
    public void hide() {
        // Handle hide logic
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        skin.dispose();
        batch.dispose();
        //RedBird.getTexture().dispose();
        if (generator != null) generator.dispose();
    }
}
