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

    private ArrayList<Animator> animators;

    public MenuButton(Texture texture, Texture clickText, float x, float y, float width, float height){
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());

        this.baseText = texture;
        this.clickText = clickText;

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.animators = new ArrayList<Animator>();
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
}
