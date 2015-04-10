package com.desitum.shveetlife.libraries.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by dvan6234 on 2/24/2015.
 */
public interface Animator {
    public void update(float delta);
    public void start(boolean isProtected);
    public void setSprite(Sprite control, boolean controlx, boolean controly);
}
