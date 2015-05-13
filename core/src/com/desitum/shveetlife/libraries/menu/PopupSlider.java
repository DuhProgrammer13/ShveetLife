package com.desitum.shveetlife.libraries.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.libraries.animation.Animator;

import java.util.ArrayList;

/**
 * Created by kody on 4/21/15.
 * can be used by kody and people in []
 */
public class PopupSlider extends PopupWidget {
    private Texture barTexture;
    private Texture sliderTexture;

    private ArrayList<Animator> comingInAnimators;
    private ArrayList<Animator> goingOutAnimators;

    private PopupSliderListener sliderListener;

    private float sliderX;
    private float sliderWidth;
    private float sliderHeight;

    private boolean beingMoved;

    public PopupSlider(Texture barTexture, Texture sliderTexture, float x, float y, float width, float height, float sliderWidth, float sliderHeight) {
        super(barTexture, width, height, x, y);

        this.beingMoved = false;

        this.sliderTexture = sliderTexture;
        this.barTexture = barTexture;

        this.sliderWidth = sliderWidth;
        this.sliderHeight = sliderHeight;

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.comingInAnimators = new ArrayList<Animator>();
        this.goingOutAnimators = new ArrayList<Animator>();
    }

    public void onClickDown(Vector3 pos){
        sliderX = pos.x - getX();
        beingMoved = true;
    }

    public void onClickUp(){
        if (sliderListener != null  && beingMoved){
            sliderListener.onChange(getPosition());
        }
        beingMoved = false;
    }

    public void resetState(){
        this.setTexture(barTexture);
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
            anim.start(false);
        }
    }

    public void startOutgoingAnimators(){
        for (Animator anim: goingOutAnimators){
            anim.start(false);
        }
    }

    public void setSliderListener(PopupSliderListener sliderListener) {
        this.sliderListener = sliderListener;
    }

    public float getPosition(){
        return sliderX/getWidth();
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        batch.draw(sliderTexture, getX() + sliderX - sliderWidth/2, getY() + getHeight()/2 - sliderHeight/2, sliderWidth, sliderHeight);
    }
}
