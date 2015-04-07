package com.desitum.shveetlife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.network.Client;
import com.desitum.shveetlife.network.Server;
import com.desitum.shveetlife.objects.MenuButton;
import com.desitum.shveetlife.world.MenuRenderer;
import com.desitum.shveetlife.world.MenuWorld;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class MenuScreen implements Screen {

    public static String PLAY = "play";
    public static String SETTINGS = "settings";

    private static final float FRUSTUM_WIDTH = 150;
    private static final float FRUSTUM_HEIGHT = 100;

    private Viewport viewport;
    private OrthographicCamera cam;

    private MenuButton myButton;

    private Server myServer;
    private Client myClient;

    private MenuWorld menuWorld;
    private MenuRenderer menuRenderer;

    private boolean isTouched;
    private Vector3 touchPoint;

    SpriteBatch batch;
    Texture img;
    Texture background;

    private MovementAnimator myAnimator = new MovementAnimator(15, 5, 2, Interpolation.LINEAR_INTERPOLATOR);

    public MenuScreen (ShveetLife sl){
        //TODO need a splash loading screen
        Assets.loadMenuButtons();

        batch = new SpriteBatch();

        menuWorld = new MenuWorld();
        menuRenderer = new MenuRenderer(batch, menuWorld);

        background = new Texture("menu_bg.png");

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
        viewport = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        touchPoint = new Vector3(0, 0, 0);

        //myServer.RunServer();
        //myClient.RunClient();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        updateInput();
        update(delta);
        draw();
    }

    private void updateInput(){
        if (Gdx.input.isTouched()){
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            menuWorld.updateClickDown(touchPoint);
            isTouched = true;
        } else {
            if (isTouched) {
                menuWorld.updateClickUp(touchPoint);
            }
            isTouched = false;
        }
    }

    private void update(float delta){
        menuWorld.update(delta);
    }

    private void draw(){
        Gdx.gl.glClearColor(0, 0, .196f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(background, 0, 0, 160, 160);
        menuRenderer.draw();
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
