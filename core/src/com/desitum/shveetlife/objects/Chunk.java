package com.desitum.shveetlife.objects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.objects.tiles.GrassTile;
import com.desitum.shveetlife.objects.tiles.TileData;
import com.desitum.shveetlife.objects.tiles.TileObject;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by dvan6234 on 4/3/2015.
 */
public class Chunk {

    TileObject[][] chunkObjects;

    private float x;
    private float y;

    private Rectangle boundingRect;

    public static final float WIDTH = 160;
    public static final float HEIGHT = 160;

    private GameInterface gi;

    public Chunk(float x, float y, GameInterface gi){
        this.x = x;
        this.y = y;

        boundingRect = new Rectangle(x, y, WIDTH, HEIGHT);

        chunkObjects = new TileObject[16][16];
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[z].length; w++){
                chunkObjects[z][w] = new GrassTile(gi, this.x + z * 10, this.y + w * 10);
            }
        }
    }

    private Chunk(TileObject[][] objects, Rectangle boundingRect, GameInterface gameInterface){
        this.x = boundingRect.getX();
        this.y = boundingRect.getY();
        this.gi = gameInterface;
        this.chunkObjects = objects;
        this.boundingRect = boundingRect;
    }

    public static Chunk loadFromString(String data, GameInterface gameInterface) {
        Chunk returnChunk = null;

        Rectangle boundingRect2 = new Rectangle(0, 0, 160, 160);
        String[] data1 = data.split("\\|");
        for (String str: data1){
        }

        String[] data2 = data1[0].split(" ");
        for (String str: data2){
        }
        boundingRect2.setX(Float.parseFloat(data2[0]));
        boundingRect2.setY(Float.parseFloat(data2[1]));

        String[] data3 = data1[1].split(";");
        TileObject[][] chunkObjects2 = new TileObject[16][16];
        for (int z = 0; z < chunkObjects2.length; z++){
            for (int w = 0; w < chunkObjects2[z].length; w++){
                chunkObjects2[z][w] = TileData.createTile(Integer.parseInt(data3[z*16 + w].split(" ")[0]), boundingRect2.getX() + z * 10, boundingRect2.getY() + w * 10, gameInterface);
            }
        }

        returnChunk = new Chunk(chunkObjects2, boundingRect2, gameInterface);

        return returnChunk;
    }

    public void update(float delta){
        for (TileObject[] gameObjects: chunkObjects){
            for (TileObject gameObject: gameObjects){
                if (gameObject != null){
                    gameObject.update(delta);
                } else {
                }
            }
        }
    }

    public int[] changeTile(TileObject from, TileObject to){
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

    public int[] changeTileAtPosition(int[] pos, TileObject to){
        int[] returnArray = new int[2];

        chunkObjects[pos[0]][pos[1]] = to;

        return pos;
    }

    public TileObject getTileAt(Vector3 pos){
        for (TileObject[] gameObjects: chunkObjects){
            for (TileObject gameObject: gameObjects){
                if (CollisionDetection.pointInRectangle(gameObject.getBoundingRectangle(), pos)){
                    return gameObject;
                }
            }
        }
        return null;
    }

    public TileObject[][] getChunkObjects(){
        return chunkObjects;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getWidth(){
        return boundingRect.getWidth();
    }

    public float getHeight(){
        return boundingRect.getHeight();
    }

    public Rectangle getBoundingRect(){
        return boundingRect;
    }

    public String getLoadString(){
        String returnString = "";

        returnString += x + " " + y + "|";
        String separator = "";
        for (int z = 0; z < chunkObjects.length; z++){
            for (int w = 0; w < chunkObjects[z].length; w++){
                returnString += separator + TileData.getTile(chunkObjects[z][w].getClass()) + " " + z + " " + w;
                separator = ";";
            }
        }

        return returnString;
    }
}
