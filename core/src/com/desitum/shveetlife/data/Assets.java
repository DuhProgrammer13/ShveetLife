package com.desitum.shveetlife.data;

import com.badlogic.gdx.graphics.Texture;

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

    public static Texture grassTexture;

    public static Texture emptyTexture;

    public static Texture player;

    public static void loadMenuButtons(){
        playButtonUp = new Texture("menu/play_button_up.png");
        playButtonDown = new Texture("menu/play_button_down.png");
        connectButtonUp = new Texture("menu/connect_button_up.png");
        connectButtonDown = new Texture("menu/connect_button_down.png");
        settingsButtonUp = new Texture("menu/settings_button_up.png");
        settingsButtonDown = new Texture("menu/settings_button_down.png");
    }

    public static void loadGameTextures(){
        grassTexture = new Texture("game/grass_land.png");
        emptyTexture = new Texture("game/empty.png");
        player = new Texture("game/player/player_square.png");
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
