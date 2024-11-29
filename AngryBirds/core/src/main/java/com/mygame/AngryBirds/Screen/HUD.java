package com.mygame.AngryBirds.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    public static int score;

    private Label ScoreLabel;
    private BitmapFont customFont;

    public HUD(SpriteBatch sb) {
        score = 0;
        viewPort = new FitViewport(AngryBirdsMain.WIDTH, AngryBirdsMain.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        // Create the font
        customFont = new BitmapFont(Gdx.files.internal("font3.fnt"), Gdx.files.internal("font3.png"), false);

        // Create Label style
        Label.LabelStyle labelStyle = new Label.LabelStyle(customFont, Color.WHITE);

        // Create the Score Label
        ScoreLabel = new Label("SCORE: 0", labelStyle);

        // Create a table for layout
        Table table = new Table();
        table.top(); // Align to top
        table.setFillParent(true); // Make table fill the stage

        // Add the score label to the table
        table.add(ScoreLabel).expandX().padTop(10);

        // Add the table to the stage
        stage.addActor(table);
    }

    public static int getScore() {
        return score;
    }

    public static void addScore(int value) {
        score += value;
    }

    // New method to update the score label
    public void update() {
        ScoreLabel.setText("SCORE: " + score);
    }

    // Dispose method to clean up resources
    public void dispose() {
        stage.dispose();
        customFont.dispose();
    }
}