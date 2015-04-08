package com.desitum.shveetlife.objects;

import com.desitum.shveetlife.objects.tiles.GrassTile;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class Chunk {

    GameObject[][] chunkObjects;

    private float x;
    private float y;

    public static final float WIDTH = 160;
    public static final float HEIGHT = 160;

    private GameInterface gi;

    public Chunk(int x, int y, GameInterface gi){
        this.x = x;
        this.y = y;

        chunkObjects = new GameObject[16][16];
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[x].length; w++){
                chunkObjects[z][w] = new GrassTile(gi);
            }
        }
    }

    public void update(float delta){
        for (GameObject[] gameObjects: chunkObjects){
            for (GameObject gameObject: gameObjects){
                if (gameObject != null){
                    gameObject.update(delta);
                } else {
                    System.out.println("Oh shoot");
                }
            }
        }
    }

    public GameObject[][] getChunkObjects(){
        return chunkObjects;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}
