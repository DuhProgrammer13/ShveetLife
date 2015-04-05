package com.desitum.shveetlife.libraries.animation;

import com.desitum.crackTheCode.libraries.interpolation.AccelerateDecelerateInterpolator;
import com.desitum.crackTheCode.libraries.interpolation.AccelerateInterpolator;
import com.desitum.crackTheCode.libraries.interpolation.AnticipateInterpolator;
import com.desitum.crackTheCode.libraries.interpolation.BounceInterpolator;
import com.desitum.crackTheCode.libraries.interpolation.DecelerateInterpolator;
import com.desitum.crackTheCode.libraries.interpolation.Interpolation;
import com.desitum.crackTheCode.libraries.interpolation.Interpolator;
import com.desitum.crackTheCode.libraries.interpolation.LinearInterpolator;
import com.desitum.crackTheCode.libraries.interpolation.OvershootInterpolator;

/**
 * Created by kody on 2/24/15.
 * can be used by kody and people in []
 */
public class MovementAnimator {

    private float startPos;
    private float endPos;
    private float travelDistance;
    private float currentPosition;

    private float duration;
    private float timeInAnimation;

    private boolean running;
    private boolean ran;
    private Interpolator interpolator;

    public MovementAnimator(float startPos, float endPos, float duration, int interpolator){
        this.startPos = startPos;
        this.endPos = endPos;
        this.travelDistance = endPos - startPos;
        this.currentPosition = startPos;

        this.duration = duration;

        setupInterpolator(interpolator);
    }

    public void update(float delta){
        if (!running){
            return;
        }
        timeInAnimation += delta/duration;
        if (timeInAnimation >= 1){
            timeInAnimation = 1;
            stop();
        }

        currentPosition = interpolator.getInterpolation(timeInAnimation) * travelDistance + startPos;
    }

    public float getCurrentPos(){
        return currentPosition;
    }

    public void start(boolean isProtected){
        if (isProtected && !ran){
            running = true;
        } else if (!isProtected) {
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
        } else if (interpolator == Interpolation.LINEAR_INTERPOLATOR){
            this.interpolator = LinearInterpolator.$();
        }
    }

    public void reset(){
        ran = false;
        running = false;
        currentPosition = startPos;
        timeInAnimation = 0;
    }
}
