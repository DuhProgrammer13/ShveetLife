package com.desitum.shveetlife.libraries;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by dvan6234 on 2/17/2015.
 */
public class ColorEffects {
    private float pointInTransition;
    private float duration;

    private float startRed;
    private float startGreen;
    private float startBlue;

    private float slopeRed;
    private float slopeGreen;
    private float slopeBlue;

    private float endRed;
    private float endGreen;
    private float endBlue;

    private float currentRed;
    private float currentGreen;
    private float currentBlue;

    private boolean transforming;

    public ColorEffects(Color startColor, Color endColor, float duration) {
        transforming = false;

        if (duration <= 0) {
            currentRed = endColor.r;
            endRed = (int) endColor.r;
            slopeRed = 0;
            currentGreen = endColor.r;
            endGreen = (int) endColor.g;
            slopeGreen = 0;
            currentBlue = endColor.b;
            endBlue = (int) endColor.b;
            slopeBlue = 0;
            return;
        }


        this.duration = duration;

        startRed = startColor.r;
        startGreen = startColor.g;
        startBlue = startColor.b;

        slopeRed = (endColor.r - startColor.r);
        slopeGreen = (endColor.g - startColor.g);
        slopeBlue = (endColor.b - startColor.b);

        currentRed = startColor.r;
        currentGreen = startColor.g;
        currentBlue = startColor.b;

        endRed = endColor.r;
        endGreen = endColor.g;
        endBlue = endColor.b;
    }

    public void start() {
        //if (currentRed == endRed && currentGreen == endGreen && currentBlue == endBlue) return;
        transforming = true;
    }

    public void update(float delta) {
        if (transforming) {
            pointInTransition += delta / duration;

            currentRed = slopeRed * pointInTransition + startRed;
            currentGreen = slopeGreen * pointInTransition + startGreen;
            currentBlue = slopeBlue * pointInTransition + startBlue;

            if (pointInTransition >= 1) {
                transforming = false;

                currentRed = endRed;
                currentGreen = endGreen;
                currentBlue = endBlue;
            }
        }
    }

    public Color getCurrentColor() {
        return new Color(currentRed, currentGreen, currentBlue, 1);
    }
}

