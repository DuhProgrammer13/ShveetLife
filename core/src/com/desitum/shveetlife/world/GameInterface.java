package com.desitum.shveetlife.world;

import com.desitum.shveetlife.objects.GameObject;
import com.desitum.shveetlife.objects.particles.Particle;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public interface GameInterface {

    public void addParticles(Particle p);
    public void changeTile(GameObject from, GameObject to);
}
