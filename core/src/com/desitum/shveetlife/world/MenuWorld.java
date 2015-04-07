package com.desitum.shveetlife.world;

import com.badlogic.gdx.math.Vector3;
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

    public MenuWorld(){
        buttons = new ArrayList<MenuButton>();

        MenuButton playButton = new MenuButton(Assets.playButtonUp, Assets.playButtonDown, 10, 0, 25, 10);
        playButton.addAnimator(new MovementAnimator(playButton, -playButton.getHeight(), 60, 0.8f, 0, Interpolation.DECELERATE_INTERPOLATOR, false, true));
        playButton.startAllAnimators();

        buttons.add(playButton);
        playButton = new MenuButton(Assets.playButtonUp, Assets.playButtonDown, 10, 0, 25, 10);
        playButton.addAnimator(new MovementAnimator(playButton, -playButton.getHeight(), 40, 0.8f, 0, Interpolation.DECELERATE_INTERPOLATOR, false, true));
        playButton.startAllAnimators();
        buttons.add(playButton);
        playButton = new MenuButton(Assets.playButtonUp, Assets.playButtonDown, 10, 0, 25, 10);
        playButton.addAnimator(new MovementAnimator(playButton, -playButton.getHeight(), 20, 0.8f, 0, Interpolation.DECELERATE_INTERPOLATOR, false, true));
        playButton.startAllAnimators();
        buttons.add(playButton);


    }

    public void update(float delta){
        for (MenuButton menuButton: buttons){
            menuButton.update(delta);
        }
    }

    public void updateClickDown(Vector3 clickPos){
        for (MenuButton button: buttons){
            if (CollisionDetection.pointInRectangle(button.getBoundingRectangle(), clickPos)){
                button.onClickDown();
            } else {
                button.resetState();
            }
        }
    }

    public void updateClickUp(Vector3 clickPos){
        for (MenuButton button: buttons){
            if (CollisionDetection.pointInRectangle(button.getBoundingRectangle(), clickPos)){
                button.onClickUp(true);
            } else {
                button.onClickUp(false);
            }
        }
    }

    public ArrayList<MenuButton> getMenuButtons(){
        return this.buttons;
    }
}
