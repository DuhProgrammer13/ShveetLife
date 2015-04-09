package com.desitum.shveetlife.objects.particles;

import com.badlogic.gdx.graphics.Texture;
import com.desitum.shveetlife.libraries.animation.Animator;
import com.desitum.shveetlife.objects.GameObject;
import com.desitum.shveetlife.objects.player.Player;

import java.util.ArrayList;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public class Particle extends GameObject {

    private ArrayList<Animator> animators;

    private float lifetime;

    public Particle(Texture texture, float lifetime, float width, float height, float x, float y, ParticleSettings ps){
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.animators = new ArrayList<Animator>();

        this.animators.add(ps.getXAnimator());
        this.animators.add(ps.getYAnimator());

        for (Animator animator: animators){
            animator.setSprite(this);
        }

        startAllAnimators();

        this.lifetime = lifetime;
    }

    @Override
    public void update(float delta){
        for (Animator anim: animators){
            anim.update(delta);
        }

        lifetime -= delta;
    }

    public float getLifetime(){
        return lifetime;
    }

    @Override
    public void useKey(int key, Player p) {
        return; // YOU CAN't INTERACT WITH PARTICLES
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
