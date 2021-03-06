package com.desitum.shveetlife.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.animation.ScaleAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.objects.MenuButton;
import com.desitum.shveetlife.objects.MenuButtonOnClickListener;
import com.desitum.shveetlife.screens.MenuScreen;

import java.util.ArrayList;


/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class MenuWorld {

    private ArrayList<MenuButton> buttons;

    private MenuInterface menuInterface;

    private static final int PLAY = 0;
    private static final int CONNECT = 1;
    private static final int SETTINGS = 2;

    private MenuButton playButton;
    private MenuButton connectButton;
    private MenuButton settingsButton;

    private Sprite playButton2;

    private ScaleAnimator animator;


    public MenuWorld(MenuInterface mi){
        menuInterface = mi;

        buttons = new ArrayList<MenuButton>();

        //Create the Menu Buttons
        playButton = new MenuButton(Assets.playButtonUp, Assets.playButtonDown, PLAY, 10, 0, 25, 10);
        connectButton = new MenuButton(Assets.connectButtonUp, Assets.connectButtonDown, CONNECT, 10, 0, 25, 10);
        settingsButton = new MenuButton(Assets.settingsButtonUp, Assets.settingsButtonDown, SETTINGS, 10, 0, 25, 10);

        //Animate/Add Play Button
        playButton.addAnimator(new MovementAnimator(playButton, -playButton.getHeight(), 60, 0.8f, 0, Interpolation.DECELERATE_INTERPOLATOR, false, true));
        playButton.startAllAnimators();
        buttons.add(playButton);
        //Animate/Add Connect Button
        connectButton.addAnimator(new MovementAnimator(connectButton, -connectButton.getHeight(), 40, 0.8f, 0, Interpolation.DECELERATE_INTERPOLATOR, false, true));
        connectButton.startAllAnimators();
        buttons.add(connectButton);
        //Animate/Add Settings Button
        settingsButton.addAnimator(new MovementAnimator(settingsButton, -settingsButton.getHeight(), 20, 0.8f, 0, Interpolation.DECELERATE_INTERPOLATOR, false, true));
        settingsButton.startAllAnimators();
        buttons.add(settingsButton);

        setupOnClickListeners();

        playButton2 = new Sprite(Assets.dirtTexture, 20, 20);
        playButton2.setPosition(10, 10);
        playButton2.setOrigin(MenuScreen.FRUSTUM_WIDTH/2, MenuScreen.FRUSTUM_HEIGHT/2);

        animator = new ScaleAnimator(playButton2, 2, 1, 0, 1, Interpolation.ACCELERATE_INTERPOLATOR, true, true);
        animator.start(false);
    }

    public void update(float delta){
        for (MenuButton menuButton: buttons){
            menuButton.update(delta);
        }
        animator.update(delta);
    }

    public void updateTouchInput(Vector3 touchPos, boolean clickDown) {
        for (MenuButton button : buttons) {
            boolean clickInArea = CollisionDetection.pointInRectangle(button.getBoundingRectangle(), touchPos);
            if (clickInArea && clickDown) {
                button.onClickDown();
            } else if (clickInArea) {
                button.onClickUp(true);
            } else {
                button.onClickUp(false);
            }
        }
    }

    private void setupOnClickListeners(){
        playButton.setOnClickListener(new MenuButtonOnClickListener() {
            @Override
            public void onClick() {
                menuInterface.playGame();
            }
        });
        connectButton.setOnClickListener(new MenuButtonOnClickListener() {
            @Override
            public void onClick() {
                menuInterface.connect();
            }
        });
        settingsButton.setOnClickListener(new MenuButtonOnClickListener() {
            @Override
            public void onClick() {
                menuInterface.settings();
            }
        });
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return this.buttons;
    }

    public Sprite getPlayButton2(){
        return playButton2;
    }
}
