package com.desitum.shveetlife.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.desitum.shveetlife.objects.MenuButton;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class MenuRenderer {

    private SpriteBatch batch;
    private MenuWorld menuWorld;

    public MenuRenderer(SpriteBatch batcher, MenuWorld menuWorld){
        this.batch = batcher;
        this.menuWorld = menuWorld;
    }

    public void draw(){
        for (MenuButton menuButton: menuWorld.getMenuButtons()){
            menuButton.draw(batch);
        }
    }
}
