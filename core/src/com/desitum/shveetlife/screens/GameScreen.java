package com.desitum.shveetlife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.network.Client;
import com.desitum.shveetlife.network.Server;
import com.desitum.shveetlife.objects.MenuButton;
import com.desitum.shveetlife.world.GameRenderer;
import com.desitum.shveetlife.world.GameWorld;

import java.util.ArrayList;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class GameScreen implements Screen {

    public static final float FRUSTUM_WIDTH = 150;
    public static final float FRUSTUM_HEIGHT = 100;

    private ShveetLife shveetLife;

    private Viewport viewport;
    private OrthographicCamera cam;
    private SpriteBatch batch;

    private MenuButton myButton;

    private Server myServer;
    private Client myClient;

    private GameWorld gameWorld;
    private GameRenderer gameRenderer;

    private boolean isTouched;
    private Vector3 touchPoint;

    private ArrayList<Integer> directionalKeys;
    private ArrayList<Integer> commandKeys;

    public GameScreen(ShveetLife sl) {
        batch = new SpriteBatch();

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
        viewport = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        gameWorld = new GameWorld(sl);
        gameRenderer = new GameRenderer(gameWorld, batch);

        directionalKeys = new ArrayList<Integer>();
        directionalKeys.add(Input.Keys.DPAD_DOWN);
        directionalKeys.add(Input.Keys.DPAD_UP);
        directionalKeys.add(Input.Keys.DPAD_LEFT);
        directionalKeys.add(Input.Keys.DPAD_RIGHT);

        commandKeys = new ArrayList<Integer>();
        commandKeys.add(Input.Keys.SPACE);
    }

    public GameScreen(ShveetLife sl, String loadString){
        batch = new SpriteBatch();

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
        viewport = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        gameWorld = GameWorld.loadGameFromString(loadString);
        gameRenderer = new GameRenderer(gameWorld, batch);

        directionalKeys = new ArrayList<Integer>();
        directionalKeys.add(Input.Keys.DPAD_DOWN);
        directionalKeys.add(Input.Keys.DPAD_UP);
        directionalKeys.add(Input.Keys.DPAD_LEFT);
        directionalKeys.add(Input.Keys.DPAD_RIGHT);

        commandKeys = new ArrayList<Integer>();
        commandKeys.add(Input.Keys.SPACE);
    }

    @Override
    public void render(float delta) {
        updateInput();
        update(delta);
        draw();
    }

    public void updateInput(){
        boolean dirKeyPressed = false;
        for (int key: directionalKeys){
            if (Gdx.input.isKeyPressed(key)){
                dirKeyPressed = true;
                gameWorld.updateDirectionalKey(key);
            }
        }
        if (!dirKeyPressed){ gameWorld.updateDirectionalKey(-1); }

        for (int key: commandKeys){
            if (Gdx.input.isKeyPressed(key)){
                gameWorld.updateKeys(key);
            }
        }
    }

    public void update(float delta){
        gameWorld.update(delta);
    }

    public void draw(){
        Gdx.gl.glClearColor(0, 0, .196f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        gameRenderer.draw();
        batch.end();
    }

    @Override
    public void show() {

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
