package com.desitum.shveetlife.objects.tiles;

import com.badlogic.gdx.Input;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.objects.particles.ParticleSettings;
import com.desitum.shveetlife.objects.player.Player;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by kody on 4/18/15.
 * can be used by kody and people in []
 */
public class WaterTile extends TileObject {
    private GameInterface gi;
    private float health;
    private ParticleSettings particleSettings;

    /** Doesn't need to extend Sprite cause nothing is ever drawn, it just needs
     * to store data for the basic needs of destroying, digging, and whatever
     * else we decide to add
     * @param gi
     */
    public WaterTile(GameInterface gi, float x, float y){
        super(Assets.waterTexture, 0, 0, Assets.waterTexture.getWidth(), Assets.waterTexture.getHeight());
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
        return 20;
    }

    private void doDamage(float amount){
        health -= amount;
        if (health <= 0){
            gi.changeTile(this, new PathTile(gi, this.getX(), this.getY()));
            gi.givePlayerItem(ProcessData.TILE, TileData.WATER, 1);
            gi.givePlayerItem(ProcessData.TILE, TileData.FIRE, 1);
            gi.givePlayerItem(ProcessData.TILE, TileData.PATH, 1);
        }
    }
}
