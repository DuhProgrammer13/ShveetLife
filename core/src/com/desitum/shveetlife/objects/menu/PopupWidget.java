package com.desitum.shveetlife.objects.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.desitum.shveetlife.libraries.animation.Animator;

/**
 * Created by kody on 4/18/15.
 * can be used by kody and people in []
 */
public abstract class PopupWidget extends Sprite {

    public PopupWidget(Texture texture, float width, float height, float x, float y) {
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());

        setSize(width, height);
        setX(x);
        setY(y);
    }

    public abstract void update(float delta);

    public abstract void addIncomingAnimator(Animator anim);

    public abstract void addOutgoingAnimator(Animator anim);

    public abstract void startIncomingAnimators();

    public abstract void startOutgoingAnimators();

    public void draw(SpriteBatch batch){
        super.draw(batch);
    }
}
