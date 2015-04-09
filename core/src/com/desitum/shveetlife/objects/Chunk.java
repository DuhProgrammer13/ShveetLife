package com.desitum.shveetlife.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.objects.tiles.GrassTile;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class Chunk {

    GameObject[][] chunkObjects;

    private float x;
    private float y;

    private Rectangle boundingRect;

    public static final float WIDTH = 160;
    public static final float HEIGHT = 160;

    private GameInterface gi;

    public Chunk(int x, int y, GameInterface gi){
        this.x = x;
        this.y = y;

        boundingRect = new Rectangle(x, y, WIDTH, HEIGHT);

        chunkObjects = new GameObject[16][16];
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[z].length; w++){
                chunkObjects[z][w] = new GrassTile(gi, this.x + z * 10, this.y + w * 10);
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

    public void changeTile(GameObject from, GameObject to){
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[z].length; w++){
                if (chunkObjects[z][w] == from){
                    chunkObjects[z][w] = to;
                }
            }
        }
    }

    public GameObject getObjectAt(Vector3 pos){
        for (GameObject[] gameObjects: chunkObjects){
            for (GameObject gameObject: gameObjects){
                if (CollisionDetection.pointInRectangle(gameObject.getBoundingRectangle(), pos)){
                    return gameObject;
                }
            }
        }
        return null;
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

    public Rectangle getBoundingRect(){
        return boundingRect;
    }
}
