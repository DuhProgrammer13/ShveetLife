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
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.objects.MenuButton;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class MenuScreen implements Screen {

    private static final float FRUSTUM_WIDTH = 150;
    private static final float FRUSTUM_HEIGHT = 100;

    private Viewport viewport;
    private OrthographicCamera cam;

    private MenuButton myButton;

    SpriteBatch batch;
    Texture img;

    private MovementAnimator myAnimator = new MovementAnimator(15, 5, 2, Interpolation.LINEAR_INTERPOLATOR);

    public MenuScreen (ShveetLife sl){
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
        viewport = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        myButton = new MenuButton(img, img, 0, 0, 15, 10);
        myButton.addAnimator(new MovementAnimator(myButton, 0, 135, 2, Interpolation.ACCELERATE_INTERPOLATOR, true, false));
        myButton.addAnimator(new MovementAnimator(myButton, 0, 90, 2, Interpolation.DECELERATE_INTERPOLATOR, false, true));
        myButton.startAllAnimators();

        myAnimator.start(false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        draw();
        myAnimator.update(delta);
        myButton.update(delta);
        System.out.println(myAnimator.getCurrentPos());
    }

    private void draw(){
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        myButton.draw(batch);
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
