package com.desitum.shveetlife.libraries.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.desitum.shveetlife.libraries.animation.Animator;

import java.util.ArrayList;

/**
 * Created by kody on 5/23/15.
 * can be used by kody and people in [kody}]
 */
public class PopupTextLabel extends PopupWidget{

    private Texture backgroundTexture;

    private ArrayList<Animator> comingInAnimators;
    private ArrayList<Animator> goingOutAnimators;

    private boolean beenDown;

    private OnClickListener buttonListener;

    public PopupTextLabel(Texture backgroundTexture, Color highlightColor, float x, float y, float width, float height) {
        super(backgroundTexture, width, height, x, y);

        this.backgroundTexture = backgroundTexture;

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.comingInAnimators = new ArrayList<Animator>();
        this.goingOutAnimators = new ArrayList<Animator>();
    }

    public void onClickDown(){
        beenDown = true;
    }

    public void onClickUp(boolean clicked){
        this.setTexture(backgroundTexture);
        if (buttonListener != null && clicked && beenDown){
            buttonListener.onClick();
        }
        beenDown = false;
    }

    public void resetState(){
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

    @Override
    public void addIncomingAnimator(Animator anim){
        anim.setSprite(this, anim.updateX(), anim.updateY());
        this.comingInAnimators.add(anim);
    }

    @Override
    public void addOutgoingAnimator(Animator anim){
        anim.setSprite(this, anim.updateX(), anim.updateY());
        this.goingOutAnimators.add(anim);
    }

    @Override
    public void startIncomingAnimators(){
        for (Animator anim: comingInAnimators){
            anim.start(false);
        }
    }

    @Override
    public void startOutgoingAnimators(){
        for (Animator anim: goingOutAnimators){
            anim.start(false);
        }
    }

    public void setButtonListener(OnClickListener buttonListener) {
        this.buttonListener = buttonListener;
    }
}
