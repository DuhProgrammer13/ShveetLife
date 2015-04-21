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
import com.desitum.shveetlife.data.Accounts;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.objects.MenuButton;
import com.desitum.shveetlife.objects.menu.PopupButton;
import com.desitum.shveetlife.objects.menu.PopupButtonListener;
import com.desitum.shveetlife.objects.menu.PopupMenu;
import com.desitum.shveetlife.world.MenuInterface;
import com.desitum.shveetlife.world.MenuRenderer;
import com.desitum.shveetlife.world.MenuWorld;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class MenuScreen implements Screen, MenuInterface {

    public static String PLAY = "play";
    public static String SETTINGS = "settings";

    public static final float FRUSTUM_WIDTH = 150;
    public static final float FRUSTUM_HEIGHT = 100;

    public int state;

    public static final int MAIN_MENU = 0;
    public static final int SETTINGS_MENU = 1;

    private ShveetLife shveetLife;
    private Accounts accounts;

    private Viewport viewport;
    private OrthographicCamera cam;

    private MenuButton myButton;

    private MenuWorld menuWorld;
    private MenuRenderer menuRenderer;

    private boolean isTouched;
    private Vector3 touchPoint;

    SpriteBatch batch;
    Texture background;



    private PopupMenu popupMenu;

    private MovementAnimator myAnimator = new MovementAnimator(15, 5, 2, Interpolation.LINEAR_INTERPOLATOR);

    public MenuScreen (ShveetLife sl){
        shveetLife = sl;

        batch = new SpriteBatch();

        menuWorld = new MenuWorld(this);
        menuRenderer = new MenuRenderer(batch, menuWorld);

        background = new Texture("menu/menu_bg.png");

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
        viewport = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        touchPoint = new Vector3(0, 0, 0);

        popupMenu = new PopupMenu(Assets.fireTexture, 10, -50, 80, 50);
        MovementAnimator yAnimator = new MovementAnimator(-50, 10, 1, Interpolation.DECELERATE_INTERPOLATOR);
        yAnimator.setControllingY(true);
        popupMenu.addIncomingAnimator(yAnimator);
        MovementAnimator yAnimator2 = new MovementAnimator(10, -50, 1, Interpolation.ANTICIPATE_INTERPOLATOR);
        yAnimator2.setControllingY(true);
        popupMenu.addOutgoingAnimator(yAnimator2);

        PopupButton cancelButton = new PopupButton(Assets.connectButtonUp, Assets.connectButtonDown, 5, 5, 30, 10, 1);
        cancelButton.setButtonListener(new PopupButtonListener() {
            @Override
            public void onClick() {
                popupMenu.moveOut();
                state = MAIN_MENU;
                System.out.println("Hey it worked");
                System.out.println(state);
            }
        });
        popupMenu.addPopupWidget(cancelButton);
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
        if (Gdx.input.isTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        }

        System.out.println(state);

        if (state == SETTINGS_MENU) {
            popupMenu.updateTouchInput(touchPoint, Gdx.input.isTouched());
        } else if (state == MAIN_MENU){
            menuWorld.updateTouchInput(touchPoint, Gdx.input.isTouched());
        }
    }

    private void update(float delta){
        menuWorld.update(delta);
        popupMenu.update(delta);
    }

    private void draw(){
        Gdx.gl.glClearColor(0, 0, .196f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(background, 0, 0, 160, 160);
        menuRenderer.draw();
        popupMenu.draw(batch);
        batch.end();

    }

    @Override
    public void playGame() {
        DataManager.startManager("localhost", "localhost");
        shveetLife.setScreen(new GameScreen(shveetLife));
    }

    @Override
    public void connect() {
        GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager("10.228.7.220", "10.228.7.220"));
        //GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager("192.168.1.4", "192.168.1.4"));
        //GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager("10.228.7.220", "localhost"));

        DataManager.receiveData();
        shveetLife.setScreen(new GameScreen(shveetLife));
    }

    @Override
    public void settings() {
        popupMenu.moveIn();
        state = SETTINGS_MENU;
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
