package com.desitum.shveetlife.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.npc.NPC;
import com.desitum.shveetlife.objects.tiles.TileObject;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.tiles.GrassTile;
import com.desitum.shveetlife.screens.GameScreen;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class GameRenderer {

    private SpriteBatch gameBatch;
    private OrthographicCamera gameCam;
    private GameWorld world;

    public GameRenderer (GameWorld world, SpriteBatch batch){
        this.world = world;
        this.gameBatch = batch;
        gameCam = new OrthographicCamera(GameScreen.FRUSTUM_WIDTH, GameScreen.FRUSTUM_HEIGHT);
        gameCam.position.set(GameScreen.FRUSTUM_WIDTH/2, GameScreen.FRUSTUM_HEIGHT/2, 0);
    }

    public void draw(){
        gameCam.position.set(this.world.getPlayer().getX(), this.world.getPlayer().getY(), 0);

        gameCam.update();
        gameBatch.setProjectionMatrix(gameCam.combined);

        drawNPC();
        drawChunks();
        drawParticles();
        drawPlayer();
    }

    private void drawNPC(){
        for (NPC npc: world.getNPCs()) {
            npc.draw(gameBatch);
        }
    }

    private void drawChunks(){
        for (Chunk chunk: world.getChunks()){
            gameBatch.draw(Assets.grassTexture, chunk.getX(), chunk.getY(), Chunk.WIDTH, Chunk.HEIGHT);

            for (TileObject[] gameObjects: chunk.getChunkObjects()){
                for (TileObject gameObject: gameObjects){
                    if (gameObject.getClass().equals(GrassTile.class)){
                    } else {
                        gameObject.draw(gameBatch);
                    }
                }
            }
        }
    }

    private void drawParticles(){
        for (Particle p: world.getParticles()){
            p.draw(gameBatch);
        }
    }

    private void drawPlayer(){
        world.getPlayer2().draw(gameBatch);
        world.getPlayer().draw(gameBatch);
    }

    public OrthographicCamera getCam(){
        return gameCam;
    }

    public void resetCam(){
        gameCam.position.set(GameScreen.FRUSTUM_WIDTH/2, GameScreen.FRUSTUM_HEIGHT/2, 0);
    }
}
