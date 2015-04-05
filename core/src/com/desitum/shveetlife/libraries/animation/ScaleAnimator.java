package com.desitum.shveetlife.libraries.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.shveetlife.libraries.interpolation.AccelerateDecelerateInterpolator;
import com.desitum.shveetlife.libraries.interpolation.AccelerateInterpolator;
import com.desitum.shveetlife.libraries.interpolation.AnticipateInterpolator;
import com.desitum.shveetlife.libraries.interpolation.BounceInterpolator;
import com.desitum.shveetlife.libraries.interpolation.DecelerateInterpolator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.libraries.interpolation.Interpolator;
import com.desitum.shveetlife.libraries.interpolation.OvershootInterpolator;

/**
 * Created by dvan6234 on 2/24/2015.
 */
public class ScaleAnimator implements Animator {

    private float duration;
    private float startScale;
    private float endScale;

    private float scaleSize;

    private float timeInAnimation;
    private float animationDelay;
    private float currentDelay;

    private boolean running;
    private boolean ran;
    private boolean growing;

    private Interpolator interpolator;

    private Sprite controllingSprite;
    private boolean controllingX;
    private boolean controllingY;

    public ScaleAnimator(float duration, float startScale, float endScale, int interpolator){
        this.duration = duration;
        this.startScale = startScale;
        this.endScale = endScale;

        this.controllingSprite = null;

        timeInAnimation = 0;
        currentDelay = 0;
        animationDelay = 0;

        if (startScale > endScale){
            growing = false;
        } else {
            growing = true;
        }

        setupInterpolator(interpolator);
    }

    public ScaleAnimator(float duration, float delay, float startScale, float endScale, int interpolator){
        this.duration = duration;
        this.startScale = startScale;
        this.endScale = endScale;

        this.controllingSprite = null;

        timeInAnimation = 0;
        currentDelay = 0;
        animationDelay = delay;

        if (startScale > endScale){
            growing = false;
        } else {
            growing = true;
        }

        setupInterpolator(interpolator);
    }

    public ScaleAnimator(Sprite sprite, float duration, float delay, float startScale, float endScale, int interpolator, boolean controlWidth, boolean controlHeight){
        this.duration = duration;
        this.startScale = startScale;
        this.endScale = endScale;

        this.controllingSprite = sprite;
        this.controllingX = controlWidth;
        this.controllingY = controlHeight;

        timeInAnimation = 0;
        currentDelay = 0;
        animationDelay = delay;

        if (startScale > endScale){
            growing = false;
        } else {
            growing = true;
        }

        setupInterpolator(interpolator);
    }

    @Override
    public void update(float delta){
        if (!running){
            return;
        }
        if (currentDelay < animationDelay){
            currentDelay += delta;
            return;
        }

        timeInAnimation += delta / duration;

        if (timeInAnimation >= 1){
            timeInAnimation = 1;
            stop();
        }

        if (growing){
            scaleSize = startScale + (endScale - startScale) * interpolator.getInterpolation(timeInAnimation);
        } else {
            scaleSize = startScale - (startScale - endScale) * interpolator.getInterpolation(timeInAnimation);
        }

        if (controllingSprite != null){
            if (controllingY){
                this.controllingSprite.setScale(this.controllingSprite.getScaleX(), this.getScaleSize());
            }
            if (controllingX){
                this.controllingSprite.setScale(this.getScaleSize(), this.controllingSprite.getScaleY());
            }
        }
    }

    public float getScaleSize(){
        return scaleSize;
    }

    public void start(boolean isProtected){
        if (isProtected && !ran){
            running = true;
        } else  if (!isProtected) {
            running = true;
        }
        ran = true;
    }

    public void stop(){
        running = false;
    }

    public boolean didFinish(){
        if (ran && !running){
            return true;
        }
        return false;
    }

    public boolean isRunning(){
        return running;
    }

    private void setupInterpolator(int interpolator){
        if (interpolator == Interpolation.ACCELERATE_INTERPOLATOR){
            this.interpolator = AccelerateInterpolator.$();
        } else if (interpolator == Interpolation.DECELERATE_INTERPOLATOR){
            this.interpolator = DecelerateInterpolator.$();
        } else if (interpolator == Interpolation.ANTICIPATE_INTERPOLATOR){
            this.interpolator = AnticipateInterpolator.$();
        } else if (interpolator == Interpolation.OVERSHOOT_INTERPOLATOR){
            this.interpolator = OvershootInterpolator.$();
        } else if (interpolator == Interpolation.ACCELERATE_DECELERATE_INTERPOLATOR){
            this.interpolator = AccelerateDecelerateInterpolator.$();
        } else if (interpolator == Interpolation.BOUNCE_INTERPOLATOR){
            this.interpolator = BounceInterpolator.$();
        }
    }
}
