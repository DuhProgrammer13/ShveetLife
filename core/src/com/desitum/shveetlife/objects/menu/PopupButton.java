package com.desitum.shveetlife.objects.menu;

import com.badlogic.gdx.graphics.Texture;
import com.desitum.shveetlife.libraries.animation.Animator;

import java.util.ArrayList;

/**
 * Created by kody on 4/19/15.
 * can be used by kody and people in []
 */
public class PopupButton extends PopupWidget {
    private Texture downTexture;
    private Texture upTexture;

    private ArrayList<Animator> comingInAnimators;
    private ArrayList<Animator> goingOutAnimators;

    private int command;

    private PopupButtonListener buttonListener;

    public PopupButton(Texture upTexture, Texture downTexture, float x, float y, float width, float height, int command) {
        super(upTexture, width, height, x, y);

        this.upTexture = upTexture;
        this.downTexture = downTexture;

        this.command = command;

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.comingInAnimators = new ArrayList<Animator>();
        this.goingOutAnimators = new ArrayList<Animator>();
    }

    public void onClickDown(){
        this.setTexture(downTexture);
    }

    public void onClickUp(boolean clicked){
        this.setTexture(upTexture);
        if (buttonListener != null && clicked){
            buttonListener.onClick();
        }
    }

    public void resetState(){
        this.setTexture(downTexture);
    }

    @Override
    public void update(float delta){
        for (Animator anim: comingInAnimators){
            anim.update(delta);
        }

        for (Animator anim: goingOutAnimators){
            anim.update(delta);
        }
    }

    public void addIncomingAnimator(Animator anim){
        anim.setSprite(this, anim.updateX(), anim.updateY());
        this.comingInAnimators.add(anim);
    }

    public void addOutgoingAnimator(Animator anim){
        anim.setSprite(this, anim.updateX(), anim.updateY());
        this.goingOutAnimators.add(anim);
    }

    public void startIncomingAnimators(){
        for (Animator anim: comingInAnimators){
            anim.start(true);
        }
    }

    public void startOutgoingAnimators(){
        for (Animator anim: goingOutAnimators){
            anim.start(true);
        }
    }

    public int getCommand(){
        return command;
    }

    public void setButtonListener(PopupButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }
}
