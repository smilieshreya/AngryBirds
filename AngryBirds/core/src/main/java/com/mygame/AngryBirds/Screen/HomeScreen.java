package com.mygame.AngryBirds.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.AngryBirds.AngryBirdsMain;
import com.mygame.AngryBirds.Screen.LevelSelectorScreen;

public class HomeScreen implements Screen {
    private Viewport gamePort;
    private OrthographicCamera gameCamera;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Texture playTexture;
    private Texture settingsTexture;
    private Texture exitTexture;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontParameter parameter;
    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton exitButton;
    private AngryBirdsMain game;
    
    public HomeScreen(AngryBirdsMain game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(AngryBirdsMain.WIDTH,AngryBirdsMain.HEIGHT,gameCamera);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("angrybirds-regular.ttf"));
        parameter = new FreeTypeFontParameter();
        parameter.size = 48; 
        BitmapFont font = generator.generateFont(parameter);
        
        //will load the background;
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundImage = new Image(backgroundTexture);
        
        //this will add the background to the stage;
        stage.addActor(backgroundImage);
        
        //this is to change or add new font to the skin;
        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;
        
        //for creation of the buttons;
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        
        playButton = new TextButton("PLAY",skin);
        settingsButton = new TextButton("SETTINGS",skin);
        exitButton = new TextButton("EXIT", skin);

        
        //this will arrange the buttons on the screen;
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(playButton).width(150).height(50).pad(15);
        table.row();
        table.add(settingsButton).width(250).height(50).pad(15);
        table.row();
        table.add(exitButton).width(140).height(50).pad(15);
        
        stage.addActor(table);
        
        Gdx.input.setInputProcessor(stage);
        addListener(); //this will add listeners for the buttons 
    }
    
    private void addListener() {
        //PLAY button changes the screen to Level Select Screen;
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelSelectorScreen(game));  // Switch to GameScreen
            }
        });
        //SETTINGS button changes the Screen to Settings Menu Screen;
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                game.setScreen(new SettingsScreen(game)); //Switches to Settings Menu Screen;
            }
        });
        //EXIT button exits the Game;
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Gdx.app.exit(); //this will quit the game;
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
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        generator.dispose();
    }
}
