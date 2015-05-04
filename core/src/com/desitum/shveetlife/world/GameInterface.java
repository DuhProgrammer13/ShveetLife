package com.desitum.shveetlife.world;

import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.tiles.TileObject;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public interface GameInterface {

    public void addParticles(Particle p);
    public void changeTile(TileObject from, TileObject to);
    public TileObject getTile(Vector3 pos);
    public Chunk getChunkAt(Vector3 pos);
    public void placeTileInFrontOfPlayer(TileObject to);
    public void givePlayerItem(int type, int thing, int amounts);
    public void updateInventoryUI();
    public void addItemToShop();
    public int[] getSelectedItem();
    public Vector3 getPlayerPosition();
    public boolean isPlayerMoving();
}
