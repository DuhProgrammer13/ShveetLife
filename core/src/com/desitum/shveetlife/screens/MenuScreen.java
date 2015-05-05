package com.desitum.shveetlife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.data.Accounts;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.data.Settings;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.objects.menu.PopupButton;
import com.desitum.shveetlife.objects.menu.PopupButtonListener;
import com.desitum.shveetlife.objects.menu.PopupMenu;
import com.desitum.shveetlife.objects.menu.PopupSlider;
import com.desitum.shveetlife.objects.menu.PopupSliderListener;
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

    private MenuWorld menuWorld;
    private MenuRenderer menuRenderer;

    private Vector3 touchPoint;
    private float lastClick;

    SpriteBatch batch;

    private PopupMenu popupMenu;

    private MovementAnimator myAnimator = new MovementAnimator(15, 5, 2, Interpolation.LINEAR_INTERPOLATOR);

    public MenuScreen (ShveetLife sl){
        shveetLife = sl;

        batch = new SpriteBatch();

        menuWorld = new MenuWorld(this);
        menuRenderer = new MenuRenderer(batch, menuWorld);

        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH/2, FRUSTUM_HEIGHT/2, 0);
        viewport = new FitViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, cam);

        accounts = new Accounts();

        touchPoint = new Vector3(0, 0, 0);

        lastClick = 0;

        // code to create the settings menu
        // do not delete or edit without permission first
        popupMenu = new PopupMenu(Assets.fireTexture, 10, -130, 130, 80);
        MovementAnimator yAnimator = new MovementAnimator(-130, 10, 1, Interpolation.DECELERATE_INTERPOLATOR);
        yAnimator.setControllingY(true);
        popupMenu.addIncomingAnimator(yAnimator);
        MovementAnimator yAnimator2 = new MovementAnimator(10, -130, 1, Interpolation.ANTICIPATE_INTERPOLATOR);
        yAnimator2.setControllingY(true);
        popupMenu.addOutgoingAnimator(yAnimator2);

        PopupButton cancelButton = new PopupButton(Assets.cancelButtonUp, Assets.cancelButtonDown, 5, 5, 57.5f, 15);
        cancelButton.setButtonListener(new PopupButtonListener() {
            @Override
            public void onClick() {
                System.out.println("WHY?!?");
                popupMenu.moveOut();
                state = MAIN_MENU;

            }
        });
        popupMenu.addPopupWidget(cancelButton);

        final PopupSlider volumeSlider = new PopupSlider(Assets.pathTexture, Assets.textFieldBackground, 5, 60, 120, 5, 3, 10);
        volumeSlider.setSliderListener(new PopupSliderListener() {
            @Override
            public void onChange(float pos) {
                System.out.println(pos);
            }
        });
        popupMenu.addPopupWidget(volumeSlider);

        PopupButton okButton = new PopupButton(Assets.okButtonUp, Assets.okButtonDown, 67.5f, 5, 57.5f, 15);
        okButton.setButtonListener(new PopupButtonListener() {
            @Override
            public void onClick() {
                System.out.println("WHY?!?");
                Settings.setVolume(volumeSlider.getPosition());
                popupMenu.moveOut();
                state = MAIN_MENU;

            }
        });
        popupMenu.addPopupWidget(okButton);
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
        if (lastClick > 0){
            return;
        }
        if (Gdx.input.isTouched()) {
            cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            lastClick = 0.2f;
        }

        if (state == SETTINGS_MENU) {
            popupMenu.updateTouchInput(touchPoint, Gdx.input.isTouched());
        } else if (state == MAIN_MENU){
            menuWorld.updateTouchInput(touchPoint, Gdx.input.isTouched());
        }
    }

    private void update(float delta){
        menuWorld.update(delta);
        popupMenu.update(delta);
        lastClick -= delta;
    }

    private void draw(){
        Gdx.gl.glClearColor(0, 0, .196f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(Assets.menuBackground, 0, 0, MenuScreen.FRUSTUM_WIDTH, MenuScreen.FRUSTUM_HEIGHT);
        menuRenderer.draw();
        popupMenu.draw(batch);
        batch.end();

    }

    @Override
    public void playGame() {
//        JTextField userField = new JTextField(20);
//        JTextField passField = new JTextField(20);
//
//        JPanel myPanel = new JPanel(new GridLayout(10, 10));
//        myPanel.add(new JLabel("Username:"));
//        myPanel.add(userField);
//        myPanel.add(new JLabel("Password:"));
//        myPanel.add(passField);
//
//        int result = JOptionPane.showConfirmDialog(null, myPanel, "Connect To Server", JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//            String user = userField.getText();
//            String pass = passField.getText();
//            if (user.length() > 0 && pass.length() > 0) {
//                accounts.checkExisting(user, pass, "localhost");
//                if(accounts.isValid){
//                    DataManager.startManager("localhost", accounts.wantedIpAddress);
                    DataManager.startManager("localhost", "localhost");
                    GameScreen myGameScreen = new GameScreen(shveetLife);
                    DataManager.receiveData();
                    shveetLife.setScreen(new GameScreen(shveetLife));
//                } else if(accounts.tryAgain){
//                    connect();
//                }
//            } else {
//                JOptionPane.showMessageDialog(null, "You must fill out all 3 textfields!");
//            }
//        }
    }

    @Override
    public void connect() {
//            JTextField userField = new JTextField(20);
//            JTextField passField = new JTextField(20);
//            JTextField ipField = new JTextField(16);
//
//            JPanel myPanel = new JPanel(new GridLayout(10, 10));
//            myPanel.add(new JLabel("Username:"));
//            myPanel.add(userField);
//            myPanel.add(new JLabel("Password:"));
//            myPanel.add(passField);
//            myPanel.add(new JLabel("IP Address:"));
//            myPanel.add(ipField);
//
//            int result = JOptionPane.showConfirmDialog(null, myPanel, "Connect To Server", JOptionPane.OK_CANCEL_OPTION);
//            if (result == JOptionPane.OK_OPTION) {
//                String user = userField.getText();
//                String pass = passField.getText();
//                String ip = ipField.getText();
//                if (user.length() > 0 && pass.length() > 0 && ip.length() > 0) {
//                    accounts.checkExisting(user, pass, ip);
//                    if(accounts.isValid){
//                        GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager(accounts.wantedUsername, accounts.wantedIpAddress));
                        GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager("10.228.7.220", "localhost"));
                        DataManager.receiveData();
                        shveetLife.setScreen(myGameScreen);
//                    } else if(accounts.tryAgain){
//                        connect();
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "You must fill out all 3 textfields!");
//                }
//            }
            //GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager("10.228.7.220", "localhost"));
            //GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager("10.228.7.220", "10.228.7.220"));
            //GameScreen myGameScreen = new GameScreen(shveetLife, DataManager.startManager("192.168.1.4", "192.168.1.4"));


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
