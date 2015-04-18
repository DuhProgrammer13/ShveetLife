package com.desitum.shveetlife.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Assets {

    public static Texture playButtonUp;
    public static Texture playButtonDown;
    public static Texture connectButtonUp;
    public static Texture connectButtonDown;
    public static Texture settingsButtonUp;
    public static Texture settingsButtonDown;

    public static TextureRegionDrawable textCursor;
    public static TextureRegionDrawable textSelection;
    public static TextureRegionDrawable textFieldBackground;

    public static Texture grassTexture;
    public static Texture dirtTexture;
    public static Texture waterTexture;

    public static Texture grassParticle;
    public static Texture dirtParticle;

    public static Texture emptyTexture;

    public static Texture player;
    public static Texture player2;

    public static BitmapFont textFieldFont;

    public static void loadMenuButtons(){
        playButtonUp = new Texture("menu/play_button_up.png");
        playButtonDown = new Texture("menu/play_button_down.png");
        connectButtonUp = new Texture("menu/connect_button_up.png");
        connectButtonDown = new Texture("menu/connect_button_down.png");
        settingsButtonUp = new Texture("menu/settings_button_up.png");
        settingsButtonDown = new Texture("menu/settings_button_down.png");

        textCursor = new TextureRegionDrawable(new TextureRegion(new Texture("menu/cursor.png")));
        textSelection = new TextureRegionDrawable(new TextureRegion(new Texture("menu/highlight.png")));
        textFieldBackground = new TextureRegionDrawable(new TextureRegion(new Texture("menu/textFieldBackground.png")));

        textFieldFont = new BitmapFont(Gdx.files.internal("font/cartoon.fnt"), Gdx.files.internal("font/cartoon.png"), false);
        textFieldFont.setScale(0.15f);
        textFieldFont.setColor(Color.BLACK);
    }

    public static void loadGameTextures(){
        player = new Texture("game/player/player_square.png");
        player2 = new Texture("game/player/player_square_two.png");

        loadGameParticles();
        loadGameTiles();
    }

    public static void loadGameTiles(){
        grassTexture = new Texture("game/grass_land.png");
        dirtTexture = new Texture("game/tiles/dirt.png");
        waterTexture = new Texture("game/tiles/water.png");
        emptyTexture = new Texture("game/empty.png");
    }

    public static void loadGameParticles(){
        grassParticle = new Texture("game/particles/grass_particles.png");
    }

    public static void dispose(){
        playButtonUp.dispose();
        playButtonDown.dispose();
        connectButtonUp.dispose();
        connectButtonDown.dispose();
        settingsButtonUp.dispose();
        settingsButtonDown.dispose();
    }
}
