package com.desitum.shveetlife.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.objects.tiles.GrassTile;
import com.desitum.shveetlife.objects.tiles.TileData;
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

    private Chunk(GameObject[][] objects, Rectangle boundingRect, GameInterface gameInterface){
        this.gi = gameInterface;
        this.chunkObjects = objects;
        this.boundingRect = boundingRect;
    }

    public Chunk loadFromString(String data, GameInterface gameInterface) {
        Chunk returnChunk = null;

        Rectangle boundingRect2 = new Rectangle(0, 0, 160, 160);
        String[] data1 = data.split("|");

        String[] data2 = data1[0].split(" ");
        boundingRect2.setX(Integer.parseInt(data2[0]));
        this.x = boundingRect2.getX();
        boundingRect2.setY(Integer.parseInt(data2[1]));
        this.y = boundingRect2.getY();

        String[] data3 = data1[1].split(";");
        chunkObjects = new GameObject[16][16];
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[z].length; w++){
                chunkObjects[z][w] = TileData.createTile(Integer.parseInt(data3[z*16 + w].split(" ")[0]), this.x + z * 10, this.y + w * 10, gameInterface);
            }
        }

        return returnChunk;
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

    public int[] changeTile(GameObject from, GameObject to){
        int[] returnArray = new int[2];
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[z].length; w++){
                if (chunkObjects[z][w] == from){
                    chunkObjects[z][w] = to;

                    returnArray[0] = z;
                    returnArray[1] = w;
                    return returnArray;
                }
            }
        }
        return null;
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

    public String getLoadString(){
        String returnString = "";

        String separator = "";
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[z].length; w++){
                returnString += separator + TileData.getTile(chunkObjects[z][w].getClass()) + " " + z + " " + y;
                separator = ";";
            }
        }

        return returnString;
    }
}
