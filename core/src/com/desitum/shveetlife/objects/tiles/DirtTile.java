package com.desitum.shveetlife.objects.tiles;

import com.badlogic.gdx.Input;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.particles.ParticleSettings;
import com.desitum.shveetlife.objects.player.Player;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by kody on 4/8/15.
 * can be used by kody and people in []
 */
public class DirtTile extends TileObject {
    private GameInterface gi;
    private float health;
    private ParticleSettings particleSettings;

    /** Doesn't need to extend Sprite cause nothing is ever drawn, it just needs
     * to store data for the basic needs of destroying, digging, and whatever
     * else we decide to add
     * @param gi
     */
    public DirtTile(GameInterface gi, float x, float y){
        super(Assets.dirtTexture, 0, 0, Assets.dirtTexture.getWidth(), Assets.dirtTexture.getHeight());
        health = 20;

        this.gi = gi;

        this.setSize(10, 10);

        this.setPosition(x, y);
    }

    @Override
    public void update(float delta) {
        return; //No need, it's a grass tile, doesn't do anything
    }

    @Override
    public void useKey(int key, Player player) {
        switch (key){
            case Input.Keys.SPACE:
                doDamage(player.getDamage(this.getClass()));
                // TODO KODY needs particles :P
        }
    }

    @Override
    public float getPlayerSpeed() {
        return 40;
    }

    private void doDamage(float amount){
        health -= amount;
        particleSettings = new ParticleSettings(this.getX(), this.getY(), this.getWidth(), this.getHeight(), -1f, 1, -1, 0, 0.4f);

        gi.addParticles(new Particle(Assets.dirtTexture, 0.4f, 5, 5, this.getX(), this.getY(), particleSettings));
        if (health <= 0){
            gi.changeTile(this, new WaterTile(gi, this.getX(), this.getY()));
        }
    }
}
