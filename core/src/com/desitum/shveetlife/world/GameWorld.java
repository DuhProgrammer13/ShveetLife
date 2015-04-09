package com.desitum.shveetlife.world;

import com.badlogic.gdx.Input;
import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.GameObject;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class GameWorld implements GameInterface{

    private Player player;
    private ArrayList<Chunk> loadedChunks;
    private ArrayList<Chunk> allChunks;

    private ArrayList<Particle> particles;

    public GameWorld(ShveetLife sl){
        player = new Player(this, 10, 10, 10, 10);

        loadedChunks = new ArrayList<Chunk>();
        allChunks = new ArrayList<Chunk>();
        particles = new ArrayList<Particle>();

        loadedChunks.add( new Chunk(0, 0, this));
    }

    public void update(float delta){
        player.update(delta);

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
            System.out.println("Shoot");
            return;
        }

        GameObject affectedObject = affectedChunk.getObjectAt(player.getPositionInFront());
        if (affectedObject == null){
            System.out.println("Shoot1");
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
                chunk.changeTile(from, to);
            }
        }
    }
}
