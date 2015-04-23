package com.desitum.shveetlife.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.shveetlife.libraries.animation.Animator;

import java.util.ArrayList;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class MenuButton extends Sprite {

    private Texture baseText;
    private Texture clickText;

    private int command;

    private boolean beenDown;

    private ArrayList<Animator> animators;
    private MenuButtonOnClickListener onClickListener;

    public MenuButton(Texture texture, Texture clickText, int command, float x, float y, float width, float height){
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());

        this.onClickListener = null;
        this.baseText = texture;
        this.clickText = clickText;

        this.command = command;

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.animators = new ArrayList<Animator>();
    }

    public void onClickDown(){
        this.setTexture(clickText);
        beenDown = true;
    }

    public void onClickUp(boolean clicked){
        this.setTexture(baseText);
        if (onClickListener != null && clicked && beenDown){
            onClickListener.onClick();
        }
        beenDown = false;
    }

    public void resetState(){
        this.setTexture(baseText);
    }

    public void update(float delta){
        for (Animator anim: animators){
            anim.update(delta);
        }
    }

    public void addAnimator(Animator anim){
        this.animators.add(anim);
    }

    public void startAllAnimators(){
        for (Animator anim: animators){
            anim.start(true);
        }
    }

    public int getCommand(){
        return command;
    }

    public void setOnClickListener(MenuButtonOnClickListener listener){
        this.onClickListener = listener;
    }
}
