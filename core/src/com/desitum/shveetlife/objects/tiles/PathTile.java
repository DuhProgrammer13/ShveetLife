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
public class PathTile extends TileObject {
    private GameInterface gi;
    private float health;
    private ParticleSettings particleSettings;

    public PathTile(GameInterface gi, float x, float y){
        super(Assets.pathTexture, 0, 0, Assets.pathTexture.getWidth(), Assets.pathTexture.getHeight());
        health = 40;

        this.gi = gi;

        this.setSize(10, 10);

        this.setPosition(x, y);
    }

    @Override
    public void update(float delta) {
        return;
    }

    @Override
    public void useKey(int key, Player player) {
        switch (key){
            case Input.Keys.SPACE:
                doDamage(player.getDamage(this.getClass()));
                // TODO KODY needs particles :P Enjoy!!!
                break;
        }
    }

    @Override
    public float getPlayerSpeed() {
        return 60;
    }

    private void doDamage(float amount){
        health -= amount;
        //particleSettings = new ParticleSettings(this.getX(), this.getY(), this.getWidth(), this.getHeight(), -1f, 1, -1, 0, 0.4f);

        //gi.addParticles(new Particle(Assets.dirtTexture, 0.4f, 5, 5, this.getX(), this.getY(), particleSettings));
        if (health <= 0){
            gi.changeTile(this, new FireTile(gi, this.getX(), this.getY()));
        }
    }

}
