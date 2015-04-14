package com.desitum.shveetlife.objects.particles;

import com.desitum.shveetlife.libraries.animation.Animator;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;

import java.util.Random;

/**
 * Created by kody on 4/8/15.
 * can be used by kody and people in []
 */
public class ParticleSettings {

    private float speedX;
    private float speedY;

    private MovementAnimator xAnimator;
    private MovementAnimator yAnimator;

    public ParticleSettings(float x, float y, float objectWidth, float objectHeight, float minSpeedX, float maxSpeedX, float minSpeedY, float maxSpeedY, float duration){
        Random random = new Random();
        speedX = random.nextFloat() * (maxSpeedX - minSpeedX) + minSpeedX;
        speedX *= 5;
        speedY = random.nextFloat() * (maxSpeedY - minSpeedY) + minSpeedY;
        speedY *= 5;

        float startPosX = random.nextFloat() * (objectWidth) + x;
        float startPosY = random.nextFloat() * (objectHeight) + y;

        xAnimator = new MovementAnimator(startPosX, startPosX + random.nextFloat() * 5, duration, Interpolation.DECELERATE_INTERPOLATOR);
        yAnimator = new MovementAnimator(startPosY, startPosY + random.nextFloat() * 5, duration, Interpolation.ACCELERATE_INTERPOLATOR);
    }

    public Animator getXAnimator(){
        return xAnimator;
    }

    public Animator getYAnimator(){
        return yAnimator;
    }
}
