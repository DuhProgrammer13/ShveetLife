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
        this.setTexture(upTexture);
    }

    public void onClickUp(boolean clicked){
        if (!clicked){
            this.setTexture(upTexture);
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
        this.comingInAnimators.add(anim);
    }

    public void addOutgoingAnimator(Animator anim){
        this.goingOutAnimators.add(anim);
    }

    public void startIncomingAnimators(){
        for (Animator anim: comingInAnimators){
            anim.start(false);
        }
    }

    public void startOutgoingAnimators(){
        for (Animator anim: goingOutAnimators){
            anim.start(false);
        }
    }

    public int getCommand(){
        return command;
    }
}
