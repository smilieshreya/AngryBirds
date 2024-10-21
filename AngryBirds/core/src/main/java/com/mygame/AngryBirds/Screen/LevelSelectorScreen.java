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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.AngryBirds.AngryBirdsMain;

public class LevelSelectorScreen implements Screen {
    private Viewport gamePort;
    private OrthographicCamera gameCamera;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Stage stage;
    private Skin skin;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private AngryBirdsMain game;
    private TextButton level1Button, level2Button, level3Button, backButton;
    
    public LevelSelectorScreen(AngryBirdsMain game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        gameCamera = new OrthographicCamera();
        gamePort = new FitViewport(AngryBirdsMain.WIDTH,AngryBirdsMain.HEIGHT,gameCamera);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Freedom-10eM.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        BitmapFont font = generator.generateFont(parameter);

        backgroundTexture = new Texture(Gdx.files.internal("gameSelect.png"));
        backgroundImage = new Image(backgroundTexture);
        stage.addActor(backgroundImage);

        skin.add("large-font", font, BitmapFont.class);
        skin.get(TextButton.TextButtonStyle.class).font = font;
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        
        level1Button = new TextButton("LEVEL I", skin);
        level2Button = new TextButton("LEVEL II", skin);
        level3Button = new TextButton("LEVEL III", skin);
        backButton = new TextButton("BACK", skin);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("large-font");
        Label headingLabel = new Label("SELECT LEVEL", labelStyle);
        headingLabel.setPosition(Gdx.graphics.getWidth() / 2f - headingLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 170);
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(level1Button).width(250).height(100).pad(10);
        table.add(level2Button).width(250).height(100).pad(10);
        table.add(level3Button).width(250).height(100).pad(10);
        table.add(backButton).width(200).height(100).pad(10);
        stage.addActor(headingLabel);
        stage.addActor(table);
        
        Gdx.input.setInputProcessor(stage);
        addListener();
    }
    private void addListener(){
        level1Button.addListener(event -> {
            if (level1Button.isPressed()){
                game.setScreen(new GameScreen(game));
                return true;
            }
            return false;
        });

        level2Button.addListener(event -> {
            if (level2Button.isPressed()){
                game.setScreen(new GameScreen(game));
                return true;
            }
            return false;
        });
        level3Button.addListener(event -> {
            if (level3Button.isPressed()){
                game.setScreen(new GameScreen(game));
                return true;
            }
            return false;
        });
        backButton.addListener(event -> {
            if (backButton.isPressed()){
                game.setScreen(new HomeScreen(game));
                return true;
            }
            return false;
        });

    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    
}
