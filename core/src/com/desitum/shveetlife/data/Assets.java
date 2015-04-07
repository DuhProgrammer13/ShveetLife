package com.desitum.shveetlife.data;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Assets {

    public static Texture playButtonUp;
    public static Texture playButtonDown;

    public static void loadMenuButtons(){
        playButtonUp = new Texture("play_button_up.png");
        playButtonDown = new Texture("play_button_down.png");
    }
}
