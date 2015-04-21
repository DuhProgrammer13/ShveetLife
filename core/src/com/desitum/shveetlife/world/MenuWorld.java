package com.desitum.shveetlife.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.objects.MenuButton;

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

    public MenuWorld(MenuInterface mi){
        menuInterface = mi;

        buttons = new ArrayList<MenuButton>();

        //Create the Menu Buttons
        MenuButton playButton = new MenuButton(Assets.playButtonUp, Assets.playButtonDown, PLAY, 10, 0, 25, 10);
        MenuButton connectButton = new MenuButton(Assets.connectButtonUp, Assets.connectButtonDown, CONNECT, 10, 0, 25, 10);
        MenuButton settingsButton = new MenuButton(Assets.settingsButtonUp, Assets.settingsButtonDown, SETTINGS, 10, 0, 25, 10);

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


    }

    public void update(float delta){
        for (MenuButton menuButton: buttons){
            menuButton.update(delta);
        }
    }

    public void updateTouchInput(Vector3 touchPos, boolean clickDown) {
        for (MenuButton button : buttons) {
            boolean clickInArea = CollisionDetection.pointInRectangle(button.getBoundingRectangle(), touchPos);
            if (clickInArea && clickDown) {
                button.onClickDown();
            } else if (clickInArea) {
                button.onClickUp(true);
                useButtonCommand(button);
            } else {
                button.onClickUp(false);
            }
        }
    }

    public void useButtonCommand(MenuButton button){
        if (button.getCommand() == PLAY){
            menuInterface.playGame();
        } else if (button.getCommand() == CONNECT){
            menuInterface.connect();
        } else if (button.getCommand() == SETTINGS){
            menuInterface.settings();
        }
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return this.buttons;
    }
}
