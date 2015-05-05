package com.desitum.shveetlife.objects.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.desitum.shveetlife.libraries.animation.Animator;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;

import java.util.ArrayList;

/**
 * Created by kody on 4/19/15.
 * can be used by kody and people in [Zack]
 */
public class PopupImage extends PopupWidget {
    private Texture downTexture;
    private Texture upTexture;

    private ArrayList<Animator> comingInAnimators;
    private ArrayList<Animator> goingOutAnimators;

    private boolean beenDown;
    private boolean enabledClicking;

    private PopupButtonListener buttonListener;

    public PopupImage(Texture upTexture, Texture highlight, float x, float y, float width, float height, boolean enabledClicking) {
        super(upTexture, width, height, x, y);

        this.upTexture = upTexture;
        this.downTexture = highlight;

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.comingInAnimators = new ArrayList<Animator>();
        this.goingOutAnimators = new ArrayList<Animator>();

        this.enabledClicking = enabledClicking;
    }

    public void onClickDown(){
        if (enabledClicking && !beenDown){
            beenDown = true;
        } else {
            beenDown = false;
        }
    }

    public void onClickUp(boolean clicked){
        if (buttonListener != null && clicked && beenDown){
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
        for (Animator anim2: comingInAnimators){
            MovementAnimator anim = (MovementAnimator) anim2;
            anim.start(false);
        }
    }

    @Override
    public void startOutgoingAnimators(){
        for (Animator anim: goingOutAnimators){
            anim.start(false);
        }
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        if (this.beenDown) batch.draw(downTexture, this.getX() + getWidth()/2 - (getWidth()/2) * getScaleX(), this.getY(), this.getWidth() * getScaleX(), this.getHeight());
    }

    public void setActive(){
        beenDown = true;
    }

    public void deactivate(){
        beenDown = false;
    }

    public void setButtonListener(PopupButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }
}
