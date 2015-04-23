package com.desitum.shveetlife.objects.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.libraries.animation.Animator;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;

import java.util.ArrayList;

/**
 * Created by dvan6234 on 4/23/2015.
 */
public class PopupScrollArea extends PopupWidget {

    ArrayList<Animator> comingInAnimators;
    ArrayList<Animator> goingOutAnimators;
    ArrayList<Animator> comingInAnimatorsToAdd;
    ArrayList<Animator> goingOutAnimatorsToAdd;

    ArrayList<PopupWidget> widgets;

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private float scrollAmount;
    private int scrollDirection;
    private int columns;
    private float spacing;

    public PopupScrollArea (Texture background, float x, float y, float width, float height, float activeWidth, float activeHeight, int scrollDirection, int columns, float spacing){
        super(background, width, height, x, y);

        comingInAnimators = new ArrayList<Animator>();
        goingOutAnimators = new ArrayList<Animator>();
        comingInAnimatorsToAdd = new ArrayList<Animator>();
        goingOutAnimatorsToAdd = new ArrayList<Animator>();

        scrollAmount = 0;
    }

    public void updateScrollInput(float amount){
        scrollAmount += amount;

        for (PopupWidget widget: widgets){

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
            }
        }
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
        this.comingInAnimatorsToAdd.add(anim);
    }

    @Override
    public void addOutgoingAnimator(Animator anim){
        anim.setSprite(this, anim.updateX(), anim.updateY());
        this.goingOutAnimators.add(anim);
        this.goingOutAnimatorsToAdd.add(anim);
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

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);

        for (PopupWidget wid: widgets){
            wid.draw(batch);
        }
    }

    public void addWidget(PopupWidget toAdd){
        if (scrollDirection == HORIZONTAL){
            for (Animator anim: comingInAnimatorsToAdd){
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
            for (Animator anim: goingOutAnimatorsToAdd){
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

            toAdd.setX(getX() + toAdd.getX());
            toAdd.setY(getY() + toAdd.getY());
            widgets.add(toAdd);
        }
    }
}
