package com.desitum.shveetlife.world;

import com.badlogic.gdx.Input;
import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.GameObject;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.player.Player;
import com.desitum.shveetlife.objects.tiles.TileData;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class GameWorld implements GameInterface{

    private Player player;
    private Player player2;
    private ArrayList<Chunk> loadedChunks;
    private ArrayList<Chunk> allChunks;

    private ArrayList<String> data;
    private ArrayList<String> loadData;

    private ArrayList<Particle> particles;

    public GameWorld(ShveetLife sl){
        player = new Player(this, 10, 10, 10, 10);
        player2 = new Player(this, 10, 10, 10, 10);

        loadedChunks = new ArrayList<Chunk>();
        allChunks = new ArrayList<Chunk>();
        particles = new ArrayList<Particle>();
        data = new ArrayList<String>();

        loadedChunks.add( new Chunk(0, 0, this));

        createLoadString();

        DataManager.setGameWorld(this);
    }

    public void update(float delta){
        player.update(delta);
        player2.update(delta);

        for (Chunk chunk: loadedChunks){
            chunk.update(delta);
        }

        Iterator<Particle> iter = particles.iterator();
        while(iter.hasNext()){
            Particle p = iter.next();
            p.update(delta);
            if (p.getLifetime() <= 0) iter.remove();
        }
    }

    public void updateDirectionalKey(int key){
        player.useDirectionalKey(key);
    }

    public void updateKeys(int key){
        Chunk affectedChunk = null;
        for (Chunk loadedChunk: loadedChunks){
            if (CollisionDetection.pointInRectangle(loadedChunk.getBoundingRect(), player.getPositionInFront())){
                affectedChunk = loadedChunk;
            }
        }
        if (affectedChunk == null){
            return;
        }

        GameObject affectedObject = affectedChunk.getObjectAt(player.getPositionInFront());
        if (affectedObject == null){
            return;
        }

        switch (key){
            case Input.Keys.SPACE:
                affectedObject.useKey(key, player);
                break;
        }
    }
    @Override
    public void addParticles(Particle p) {
        particles.add(p);
    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Chunk> getChunks(){
        return loadedChunks;
    }

    public ArrayList<Particle> getParticles(){
        return particles;
    }

    public void changeTile(GameObject from, GameObject to){
        for (Chunk chunk: loadedChunks){
            if (CollisionDetection.pointInRectangle(chunk.getBoundingRect(), player.getPositionInFront())){
                int[] position = chunk.changeTile(from, to);
                data.add(ProcessData.EDIT + " TILE " + position[0] + " " + position[1] + " " + TileData.getTile(to.getClass()));
            }
        }
    }

    public String getDataString(){
        String returnString = "";

        String separator = "";
        for (String item: data){
            returnString += separator;
            returnString += item;
            separator = ";";
        }
        returnString = "This is my data";
        return returnString;
    }
    public ArrayList<String> getData(){
        return data;
    }

    private void createLoadString(){
        String chunkAppend = "";
        String chunkString = "";
        for (Chunk chunk: allChunks){
            chunkString += chunkAppend;
            chunkString += chunk.getLoadString();
            chunkAppend = "/";
        }
        loadData.add(chunkString);
    }
    public String getGameLoad(){
        String returnString = "";

        String append = "";
        for (String s: loadData){
            returnString += append + s;
            append = ":";
        }

        return returnString;
    }

    public static GameWorld loadGameFromString(){
        GameWorld newWorld = null;

        return newWorld;
    }
}
