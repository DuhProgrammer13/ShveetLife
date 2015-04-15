package com.desitum.shveetlife.objects.tiles;

import com.desitum.shveetlife.objects.GameObject;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by dvan6234 on 4/14/2015.
 */
public class TileData {

    public static final int GRASS = 0;
    public static final int DIRT = 1;

    public static int getTile(Class c){
        if (c.equals(GrassTile.class)){
            return GRASS;
        } else if (c.equals(DirtTile.class)){
            return DIRT;
        }
        return -1;
    }

    public static GameObject createTile(int tileNum, float posX, float posY, GameInterface gi){
        if (tileNum == GRASS){
            return new GrassTile(gi, posX, posY);
        } else if (tileNum == DIRT){
            return new DirtTile(gi, posX, posY);
        }
        return null;
    }
}
