package com.desitum.shveetlife.objects.tiles;

import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.GameObject;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by dvan6234 on 4/14/2015.
 */
public class TileData {

    public static final int GRASS = 0;
    public static final int DIRT = 1;

    public static final int POS_X = 2;
    public static final int POS_Y = 3;
    public static final int TILE_TYPE = 4;
    public static final int CHUNK_X = 5;
    public static final int CHUNK_Y = 6;

    public static final float WIDTH = 10;
    public static final float HEIGHT = 10;

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

    public static GameObject buildTileFromString(String[] info, Chunk chunk, GameInterface gi){
        int tileNum = Integer.parseInt(info[TILE_TYPE]);

        if (tileNum == GRASS){
            return new GrassTile(gi, chunk.getX() + Integer.parseInt(info[POS_X]) * TileData.WIDTH,
                    chunk.getY() + Integer.parseInt(info[POS_Y]) * TileData.HEIGHT);
        } else if (tileNum == DIRT){
            return new DirtTile(gi, chunk.getX() + Integer.parseInt(info[POS_X]) * TileData.WIDTH,
                    chunk.getY() + Integer.parseInt(info[POS_Y]) * TileData.HEIGHT);
        }
        return null;
    }
}
