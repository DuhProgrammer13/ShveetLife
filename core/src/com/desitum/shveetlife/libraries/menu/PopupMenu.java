package com.desitum.shveetlife.libraries.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.libraries.animation.Animator;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;

import java.util.ArrayList;

/**
 * Created by kody on 4/18/15.
 * can be used by kody and people in [Zack, Kody]
 */
public class PopupMenu extends Sprite {

    private ArrayList<PopupWidget> widgets;
    private ArrayList<Animator> incomingAnimators;
    private ArrayList<Animator> outgoingAnimators;
    private ArrayList<Animator> incomingAnimatorsToAdd;
    private ArrayList<Animator> outgoingAnimatorsToAdd;

    private Texture background;

    private float x;
    private float y;
    private float width;
    private float height;

    private int commandToSend;

    public PopupMenu(Texture background, float x, float y, float width, float height){
        super(background, 0, 0, background.getWidth(), background.getHeight());
        widgets = new ArrayList<PopupWidget>();
        incomingAnimators = new ArrayList<Animator>();
        outgoingAnimators = new ArrayList<Animator>();
        incomingAnimatorsToAdd = new ArrayList<Animator>();
        outgoingAnimatorsToAdd = new ArrayList<Animator>();

        this.background = background;

        setPosition(x, y);
        this.setSize(width, height);
    }

    public void draw(SpriteBatch batch){
        batch.draw(background, x, y, width, height);

        for (PopupWidget widget: widgets){
            widget.draw(batch);
        }
    }

    public void updateTouchInput(Vector3 touchPos, boolean clickDown){
        for (PopupWidget widget: widgets){
            boolean clickInArea = CollisionDetection.pointInRectangle(widget.getBoundingRectangle(), touchPos);
            if (widget.getClass().equals(PopupButton.class)){
                PopupButton button = (PopupButton) widget;
                if (clickInArea && clickDown){
                    button.onClickDown();
                } else if (clickInArea) {
                    button.onClickUp(true);
                } else {
                    button.onClickUp(false);
                }
            } else if (widget.getClass().equals(PopupSlider.class)){
                PopupSlider slider = (PopupSlider) widget;
                if (clickInArea && clickDown){
                    slider.onClickDown(touchPos);
                } else if (clickInArea) {
                    slider.onClickUp();
                } else {
                    slider.onClickUp(); // handles if not in area
                }
            } else if (widget.getClass().equals(PopupScrollArea.class)){
                PopupScrollArea popupScrollArea = (PopupScrollArea) widget;
                popupScrollArea.updateTouchInput(touchPos, clickDown);
            }
        }
    }

    public void udpateScrollInput(int amount, Vector3 mousePos, boolean posMatters){
        for (PopupWidget widget: widgets){
            if (!widget.getClass().equals(PopupScrollArea.class)){
                continue;
            }
            if (posMatters){
                if (CollisionDetection.pointInRectangle(widget.getBoundingRectangle(), mousePos)){
                    PopupScrollArea scrollArea = (PopupScrollArea) widget;
                    scrollArea.updateScrollInput(amount);
                }
            } else {
                PopupScrollArea scrollArea = (PopupScrollArea) widget;
                scrollArea.updateScrollInput(amount);
            }
        }
    }

    public void update(float delta){
        for (PopupWidget widget: widgets){
            widget.update(delta);
        }

        updateAnimation(delta);
    }
//Kody is an idiot
    private void updateAnimation(float delta){
        for (Animator anim: incomingAnimators) {
            anim.update(delta);
        }

        for (Animator anim: outgoingAnimators) {
            anim.update(delta);
        }
    }

    public void addIncomingAnimator(Animator anim){
        incomingAnimators.add(anim);
        incomingAnimatorsToAdd.add(anim);
    }

    public void addOutgoingAnimator(Animator anim){
        outgoingAnimators.add(anim);
        outgoingAnimatorsToAdd.add(anim);
    }

    public void addPopupWidget(PopupWidget toAdd){
        for (Animator anim: incomingAnimatorsToAdd){
            Animator dupAnim = anim.duplicate();
            if (dupAnim.getClass().equals(MovementAnimator.class)){
                MovementAnimator dupMov = (MovementAnimator) dupAnim;
                if (dupMov.isControllingX()){
                    dupMov.setStartPos(toAdd.getX() + dupMov.getStartPos());
                    dupMov.setEndPos(toAdd.getX() + dupMov.getEndPos());
                } if (dupMov.isControllingY()){
                    dupMov.setStartPos(toAdd.getY() + dupMov.getStartPos());
                    dupMov.setEndPos(toAdd.getY() + dupMov.getEndPos());
                }
                toAdd.addIncomingAnimator(dupMov);
            }
        }
        for (Animator anim: outgoingAnimatorsToAdd){
            Animator dupAnim = anim.duplicate();
            if (dupAnim.getClass().equals(MovementAnimator.class)){
                MovementAnimator dupMov = (MovementAnimator) dupAnim;
                if (dupMov.isControllingX()){
                    dupMov.setStartPos(toAdd.getX() + dupMov.getStartPos());
                    dupMov.setEndPos(toAdd.getX() + dupMov.getEndPos());
                } if (dupMov.isControllingY()){
                    dupMov.setStartPos(toAdd.getY() + dupMov.getStartPos());
                    dupMov.setEndPos(toAdd.getY() + dupMov.getEndPos());
                }
                toAdd.addOutgoingAnimator(dupMov);
            }
        }

        toAdd.setX(x + toAdd.getX());
        toAdd.setY(y + toAdd.getY());
        widgets.add(toAdd);
    }

    public void moveIn(){
        for (PopupWidget widget: widgets){
            widget.startIncomingAnimators();
        }
        for (Animator anim: incomingAnimators){
            anim.start(false);
        }
    }

    public void moveOut(){
        for (PopupWidget widget: widgets){
            widget.startOutgoingAnimators();
        }
        for (Animator anim: outgoingAnimators){
            anim.start(false);
        }
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }
}
