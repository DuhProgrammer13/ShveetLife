package com.desitum.shveetlife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.desitum.shveetlife.screens.MenuScreen;

public class ShveetLife extends Game {
	
	@Override
	public void create () {
        this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}
}