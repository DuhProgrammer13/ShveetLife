package com.desitum.shveetlife.world;

import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.Particle;
import com.desitum.shveetlife.objects.player.Player;

import java.util.ArrayList;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class GameWorld implements GameInterface{

    private Player player;
    private ArrayList<Chunk> loadedChunks;
    private ArrayList<Chunk> allChunks;

    public GameWorld(ShveetLife sl){
        player = new Player(this, 10, 10, 10, 10);

        loadedChunks = new ArrayList<Chunk>();
        allChunks = new ArrayList<Chunk>();

        loadedChunks.add( new Chunk(0, 0, this));
    }

    public void update(float delta){
        player.update(delta);

        for (Chunk chunk: loadedChunks){
            chunk.update(delta);
        }
    }

    public void updateDirectionalKey(int key){
        player.useDirectionalKey(key);
    }

    @Override
    public void addParticles(Particle p) {

    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Chunk> getChunks(){
        return loadedChunks;
    }
}
