package com.mygame.AngryBirds.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.AngryBirds.AngryBirdsMain;

public class HUD {
    public Stage stage;
    private Viewport viewPort;
    private Integer score;

    Label ScoreLabel;
    BitmapFont customFont;

    public HUD(SpriteBatch sb){
        score = 0;

        viewPort = new FitViewport(AngryBirdsMain.WIDTH, AngryBirdsMain.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        // Load custom font from .fnt file
        customFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3.png"), false);

        Table table = new Table();
        table.top();
        table.right();
        table.setFillParent(true);

        ScoreLabel = new Label(String.format("Score: %07d", score), new Label.LabelStyle(customFont, Color.WHITE));
        table.add(ScoreLabel);
        stage.addActor(table);
    }
}
