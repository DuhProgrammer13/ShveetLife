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
import com.desitum.shveetlife.objects.player.Player2;
import com.desitum.shveetlife.objects.tiles.TileData;
import com.desitum.shveetlife.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class GameWorld implements GameInterface{

    private Player player;
    private Player2 player2;
    private ArrayList<Chunk> loadedChunks;
    private ArrayList<Chunk> allChunks;

    private ArrayList<String> data;
    private ArrayList<String> loadData;

    private ArrayList<Particle> particles;

    public GameWorld(ShveetLife sl){
        player = new Player(this, 10, 10, 10, 10);
        player2 = new Player2(this, 10, 10, 10, 10);

        loadedChunks = new ArrayList<Chunk>();
        allChunks = new ArrayList<Chunk>();
        particles = new ArrayList<Particle>();
        data = new ArrayList<String>();

        loadedChunks.add( new Chunk(0, 0, this));
        allChunks.add(loadedChunks.get(0));

        createLoadString();

        DataManager.setGameWorld(this);
    }

    public GameWorld(ShveetLife sl, ArrayList<Chunk> chunks, Player p, Player2 p2){
        player = p;
        player2 = p2;

        loadedChunks = new ArrayList<Chunk>();
        allChunks = new ArrayList<Chunk>();
        particles = new ArrayList<Particle>();
        data = new ArrayList<String>();

        loadedChunks.add( new Chunk(0, 0, this));
        allChunks.add(loadedChunks.get(0));

        createLoadString();

        DataManager.setGameWorld(this);
    }

    public void update(float delta){
        player.update(delta);
        player2.update(delta, "");

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

        data = new ArrayList<String>();
        return returnString;
    }

    private void updateLoadedChunks(){
        loadedChunks = new ArrayList<Chunk>();

        for (Chunk chunk: allChunks){
            if (chunk.getX() < player.getX() + player.getWidth()/2 + GameScreen.FRUSTUM_WIDTH/2 &&
                    chunk.getY() < player.getY() + player.getHeight()/2 + GameScreen.FRUSTUM_HEIGHT/2 &&
                    chunk.getX() + chunk.getWidth() > player.getX() - GameScreen.FRUSTUM_WIDTH/2 &&
                    chunk.getY() + chunk.getHeight() > player.getY() - GameScreen.FRUSTUM_HEIGHT/2){
                loadedChunks.add(chunk);
            }
        }
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
        loadData = new ArrayList<String>();

        loadData.add(chunkString);

        loadData.add(player2.toString());

        loadData.add(player.toString());
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

    public static GameWorld loadGameFromString(String loadString, ShveetLife sl){
        GameWorld newWorld = null;

        ArrayList<Chunk> newWorldChunks = new ArrayList<Chunk>();
        String[] loadStrings = loadString.split(":");

        String[] chunkStrings = loadStrings[0].split("/");
        for (String chunkString: chunkStrings){
            newWorldChunks.add(Chunk.loadFromString(chunkString, newWorld));
        }

        Player2 otherPlayer = Player2.loadFromString(loadStrings[1], newWorld);


        Player myNewPlayer = Player.loadFromString(loadStrings[2], newWorld);

        newWorld = new GameWorld(sl, newWorldChunks, myNewPlayer, otherPlayer);
        return newWorld;
    }
}
