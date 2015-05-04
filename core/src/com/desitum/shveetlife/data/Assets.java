package com.desitum.shveetlife.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Assets {
    //Button Textures
    public static Texture playButtonUp;
    public static Texture playButtonDown;
    public static Texture connectButtonUp;
    public static Texture connectButtonDown;
    public static Texture settingsButtonUp;
    public static Texture settingsButtonDown;
    public static Texture cancelButtonUp;
    public static Texture cancelButtonDown;
    public static Texture okButtonUp;
    public static Texture okButtonDown;
    public static Texture saveButtonUp;
    public static Texture saveButtonDown;
    public static Texture exitButtonUp;
    public static Texture exitButtonDown;

    public static Texture leftButtonUp;
    public static Texture leftButtonDown;
    public static Texture rightButtonUp;
    public static Texture rightButtonDown;
    public static Texture goldShopButtonUp;
    public static Texture goldShopButtonDown;

    //Menu Textures
    public static Texture textCursor;
    public static Texture textSelection;
    public static Texture textFieldBackground;
    public static Texture menuBackground;
    public static Texture itemMenuBackground;
    public static Texture itemMenuScrollArea;

    //Tile Textures
    public static Texture grassTexture;
    public static Texture dirtTexture;
    public static Texture waterTexture;
    public static Texture fireTexture;
    public static Texture pathTexture;

    //Building Textures
    public static Texture castle;

    //Particle Textures
    public static Texture grassParticle;
    public static Texture fireParticle;
    //public static Texture dirtParticle;

    //Character Textures
    public static Texture player;
    public static Texture player2;
    public static Texture kingNPC;
    public static Texture npc;


    //Misc Textures
    public static Texture emptyTexture;
    public static BitmapFont textFieldFont;

    public static void loadMenuButtons(){
        playButtonUp = new Texture("menu/play_button_up.png");
        playButtonDown = new Texture("menu/play_button_down.png");
        connectButtonUp = new Texture("menu/connect_button_up.png");
        connectButtonDown = new Texture("menu/connect_button_down.png");
        settingsButtonUp = new Texture("menu/settings_button_up.png");
        settingsButtonDown = new Texture("menu/settings_button_down.png");
        cancelButtonUp = new Texture("menu/cancel_button_up.png");
        cancelButtonDown = new Texture("menu/cancel_button_down.png");
        okButtonUp = new Texture("menu/ok_button_up.png");
        okButtonDown = new Texture("menu/ok_button_down.png");
        saveButtonUp = new Texture("menu/save_button_up.png");
        saveButtonDown = new Texture("menu/save_button_down.png");
        exitButtonUp = new Texture("menu/exit_button_up.png");
        exitButtonDown = new Texture("menu/exit_button_down.png");

        leftButtonUp = new Texture("menu/left_arrow_up_button.png");
        leftButtonDown = new Texture("menu/left_arrow_down_button.png");
        rightButtonUp = new Texture("menu/right_arrow_up_button.png");
        rightButtonDown = new Texture("menu/right_arrow_down_button.png");
        goldShopButtonUp = new Texture("menu/shop_button_up.png");
        goldShopButtonDown = new Texture("menu/shop_button_down.png");

        textCursor = new Texture("menu/cursor.png");
        textSelection = new Texture("menu/highlight.png");
        textFieldBackground = new Texture("menu/textFieldBackground.png");
        menuBackground = new Texture("menu/menu_bg.png");
        itemMenuBackground = new Texture("menu/items_menu_area.png");
        itemMenuScrollArea = new Texture("menu/items_scroll_area.png");

        textFieldFont = new BitmapFont(Gdx.files.internal("font/cartoon.fnt"), Gdx.files.internal("font/cartoon.png"), false);
        textFieldFont.setScale(0.15f);
        textFieldFont.setColor(Color.BLACK);
    }

    public static void loadGameTextures(){
        player = new Texture("game/player/player_square.png");
        player2 = new Texture("game/player/player_square_two.png");
        npc = new Texture("game/player/npc_square.png");
        kingNPC = new Texture("game/player/king_npc.png");

        castle = new Texture("game/buildings/castle.png");

        loadGameParticles();
        loadGameTiles();
    }

    public static void loadGameTiles(){
        grassTexture = new Texture("game/grass_land.png");
        dirtTexture = new Texture("game/tiles/dirt.png");
        waterTexture = new Texture("game/tiles/water.png");
        fireTexture = new Texture("game/tiles/fire.png");
        pathTexture = new Texture("game/tiles/path.png");
        emptyTexture = new Texture("game/empty.png");
    }

    public static void loadGameParticles(){
        grassParticle = new Texture("game/particles/grass_particles.png");
        fireParticle = new Texture("game/particles/fire_particles.png");
    }

    public static void dispose(){
        playButtonUp.dispose();
        playButtonDown.dispose();
        connectButtonUp.dispose();
        connectButtonDown.dispose();
        settingsButtonUp.dispose();
        settingsButtonDown.dispose();
        //Lots to add to this.....
    }
}
