package com.desitum.shveetlife.objects.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by dvan6234 on 4/14/2015.
 */
public class TileData {

    public static final int GRASS = 0;
    public static final int DIRT = 1;
    public static final int WATER = 2;
    public static final int PATH = 3;
    public static final int FIRE = 4;

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
        } else if (c.equals(WaterTile.class)){
            return WATER;
        } else if (c.equals(PathTile.class)){
            return PATH;
        } else if (c.equals(FireTile.class)){
            return FIRE;
        }
        return -1;
    }

    public static TileObject createTile(int tileNum, float posX, float posY, GameInterface gi){
        if (tileNum == GRASS){
            return new GrassTile(gi, posX, posY);
        } else if (tileNum == DIRT){
            return new DirtTile(gi, posX, posY);
        } else if (tileNum == WATER){
            return new WaterTile(gi, posX, posY);
        } else if (tileNum == PATH) {
            return new PathTile(gi, posX, posY);
        } else if (tileNum == FIRE) {
            return new FireTile(gi, posX, posY);
        }
        return null;
    }

    public static TileObject buildTileFromString(String[] info, Chunk chunk, GameInterface gi){
        int tileNum = Integer.parseInt(info[TILE_TYPE]);

        if (tileNum == GRASS){
            return new GrassTile(gi, chunk.getX() + Integer.parseInt(info[POS_X]) * TileData.WIDTH,
                    chunk.getY() + Integer.parseInt(info[POS_Y]) * TileData.HEIGHT);
        } else if (tileNum == DIRT){
            return new DirtTile(gi, chunk.getX() + Integer.parseInt(info[POS_X]) * TileData.WIDTH,
                    chunk.getY() + Integer.parseInt(info[POS_Y]) * TileData.HEIGHT);
        } else if (tileNum == WATER){
            return new WaterTile(gi, chunk.getX() + Integer.parseInt(info[POS_X]) * TileData.WIDTH,
                    chunk.getY() + Integer.parseInt(info[POS_Y]) * TileData.HEIGHT);
        } else if (tileNum == PATH){
            return new PathTile(gi, chunk.getX() + Integer.parseInt(info[POS_X]) * TileData.WIDTH,
                    chunk.getY() + Integer.parseInt(info[POS_Y]) * TileData.HEIGHT);
        } else if (tileNum == FIRE){
            return new FireTile(gi, chunk.getX() + Integer.parseInt(info[POS_X]) * TileData.WIDTH,
                    chunk.getY() + Integer.parseInt(info[POS_Y]) * TileData.HEIGHT);
        }
        return null;
    }

    public int getConnectingTileNum(Class c){
        return 0;
    }

    public static Texture getTileTexture(int tileNum){
        Texture returnTexture = null;
        switch (tileNum){
            case GRASS:
                returnTexture = Assets.grassTexture;
                break;
            case DIRT:
                returnTexture = Assets.dirtTexture;
                break;
            case PATH:
                returnTexture = Assets.pathTexture;
                break;
            case FIRE:
                returnTexture = Assets.fireTexture;
                break;
            case WATER:
                returnTexture = Assets.waterTexture;
                break;
        }
        return returnTexture;
    }

    public static int getTilePrice(int tileNum){
        int price = 0;
        switch (tileNum){
            case GRASS:
                price = 10;
                break;
            case DIRT:
                price = 5;
                break;
            case WATER:
                price = 20;
                break;
            case PATH:
                price = 50;
                break;
            case FIRE:
                price = 50;
                break;
        }
        return price;
    }
}
