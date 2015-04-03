package com.desitum.shveetlife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.shveetlife.ShveetLife;

import java.awt.Menu;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class MenuScreen implements Screen {

    private static final float FRUSTUM_WIDTH = 150;
    private static final float FRUSTUM_HEIGHT = 100;

    private Viewport viewport;
    private OrthographicCamera cam;

    SpriteBatch batch;
    Texture img;

    public MenuScreen (ShveetLife sl){
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
        viewport = new FitViewport(150, 100, cam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        draw();
    }

    private void draw(){
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    }
}
