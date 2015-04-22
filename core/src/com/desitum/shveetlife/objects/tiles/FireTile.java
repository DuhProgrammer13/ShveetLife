package com.desitum.shveetlife.objects.tiles;

import com.badlogic.gdx.Input;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.particles.ParticleSettings;
import com.desitum.shveetlife.objects.player.Player;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by Zmyth97 on 4/18/2015.
 */
public class FireTile extends TileObject {

    private GameInterface gi;
    private float health;
    private ParticleSettings particleSettings;

    public FireTile(GameInterface gi, float x, float y){
        super(Assets.fireTexture, 0, 0, Assets.fireTexture.getWidth(), Assets.fireTexture.getHeight());
        health = 30;
        particleSettings = new ParticleSettings(this.getX(), this.getY(), this.getWidth(), this.getHeight(), -1f, 1, -1, 0, 0.4f);

        this.gi = gi;

        this.setSize(10, 10);
        this.setX(x);
        this.setY(y);

        //particleSettings = new ParticleSettings(this.getX(), this.getY(), this.getWidth(), this.getHeight(), -1f, 1, -1, 1, 0.4f);

    }

    @Override
    public void update(float delta) {
        //gi.addParticles(new Particle(Assets.grassParticle, 0.4f, 5, 5, this.getX(), this.getY(), particleSettings));
        //return; //No need, it's a grass tile, doesn't do anything
    }

    @Override
    public void useKey(int key, Player player) {
        switch (key){
            case Input.Keys.SPACE:
                doDamage(player.getDamage(this.getClass()));
                break;
        }
    }

    @Override
    public float getPlayerSpeed() {
        return 20;
    }

    private void doDamage(float amount){
        health -= amount;
    }
}

