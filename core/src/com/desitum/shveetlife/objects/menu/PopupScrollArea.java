package com.desitum.shveetlife.objects.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
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
    private float widgetSize;
    private float spacing;
    private float activeWidth;
    private float activeHeight;

    public PopupScrollArea (Texture background, float x, float y, float width, float height, float activeWidth, float activeHeight, int scrollDirection, int columns, float spacing, float widgetSize){
        super(background, width, height, x, y);

        comingInAnimators = new ArrayList<Animator>();
        goingOutAnimators = new ArrayList<Animator>();
        comingInAnimatorsToAdd = new ArrayList<Animator>();
        goingOutAnimatorsToAdd = new ArrayList<Animator>();

        widgets = new ArrayList<PopupWidget>();

        scrollAmount = 0;
        this.scrollDirection = scrollDirection;
        this.columns = columns;
        this.widgetSize = widgetSize;
        this.spacing = spacing;
        this.activeWidth = activeWidth;
        this.activeHeight = activeHeight;
    }

    public void updateScrollInput(float amount){
        scrollAmount += amount;

        if (scrollAmount > 0){
            scrollAmount = 0;
        } else if (scrollAmount < -(widgets.size() - 1) * (widgetSize + spacing)){
            scrollAmount = -(widgets.size() - 1) * (widgetSize + spacing);
        }

        System.out.println(scrollAmount);
        System.out.println(widgets.size());
        System.out.println(widgetSize);
        System.out.println(-(widgets.size() + spacing) * widgetSize + "\n----------------");
        updateWidgets();
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

    public void updateWidgets(){
        for (int widgetNum = 0;widgetNum < widgets.size(); widgetNum++){
            PopupWidget widget = widgets.get(widgetNum);

            if (scrollDirection == HORIZONTAL){
                widget.setX(getX() + getWidth()/2 + (widgetNum / columns) * (widgetSize + spacing) + scrollAmount);
                float widgetDistanceFromCenter = (getX() + getWidth()/2 - widget.getX() - widget.getWidth()/2) / activeWidth/2;
                widgetDistanceFromCenter *= 4;
                if (widgetDistanceFromCenter < 0) widgetDistanceFromCenter *= -1;
                if (widgetDistanceFromCenter > 1) widgetDistanceFromCenter = 1;
                widget.setAlpha(1 - widgetDistanceFromCenter);
                widget.setScale(1 - widgetDistanceFromCenter, 1);
            } else {
                widget.setY(getY() + getWidth()/2 + (widgetNum / columns) * (widgetSize + spacing) + scrollAmount);
                float widgetDistanceFromCenter = (getY() + getHeight()/2 - widget.getY()) / activeWidth/2;
                if (widgetDistanceFromCenter < 0) widgetDistanceFromCenter *= -1;
                if (widgetDistanceFromCenter > 1) widgetDistanceFromCenter = 1;
                widget.setAlpha(1 - widgetDistanceFromCenter);
                widget.setScale(1 - widgetDistanceFromCenter, 1);
            }
        }
    }

    public void addWidget(PopupWidget toAdd){
        toAdd.setSize(widgetSize, widgetSize);

        if (scrollDirection == HORIZONTAL) {
            toAdd.setX(getX() + getWidth()/2 + (widgets.size() / columns) * (widgetSize + spacing));
            toAdd.setY(getY() + (widgets.size() % columns) * (widgetSize + spacing));
        }

        if (scrollDirection == HORIZONTAL){
            for (Animator anim: comingInAnimatorsToAdd){
                Animator dupAnim = anim.duplicate();
                if (dupAnim.getClass().equals(MovementAnimator.class)){
                    MovementAnimator dupMov = (MovementAnimator) dupAnim;
                    if (dupMov.isControllingX()){
                        dupMov.setStartPos(toAdd.getX() - dupMov.getDistance());
                        dupMov.setEndPos(toAdd.getX());
                    } if (dupMov.isControllingY()){
                        dupMov.setStartPos(toAdd.getY() - dupMov.getStartPos());
                        dupMov.setEndPos(toAdd.getY());
                    }
                    toAdd.addIncomingAnimator(dupMov);
                }
            }
            for (Animator anim: goingOutAnimatorsToAdd){
                Animator dupAnim = anim.duplicate();
                if (dupAnim.getClass().equals(MovementAnimator.class)){
                    MovementAnimator dupMov = (MovementAnimator) dupAnim;
                    if (dupMov.isControllingX()){
                        dupMov.setStartPos(toAdd.getX() - dupMov.getDistance());
                        dupMov.setEndPos(toAdd.getX());
                    } if (dupMov.isControllingY()){
                        dupMov.setStartPos(toAdd.getY() - dupMov.getDistance());
                        dupMov.setEndPos(toAdd.getY());
                    }
                    toAdd.addOutgoingAnimator(dupMov);
                }
            }

            widgets.add(toAdd);
            updateWidgets();
        }
    }

    public void setWidgets(ArrayList<PopupWidget> widgetsToSet){
        this.widgets = new ArrayList<PopupWidget>();
        for (PopupWidget widget: widgetsToSet){
            addWidget(widget);
        }
        updateWidgets();
    }

    public void goToWidget(int widgetNum){
        scrollAmount = -(widgetNum / columns) * (widgetSize + spacing);
    }
}
