package com.desitum.shveetlife.world;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.menu.PopupMenu;
import com.desitum.shveetlife.objects.npc.NPC;
import com.desitum.shveetlife.objects.tiles.TileObject;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.player.Player;
import com.desitum.shveetlife.objects.player.Player2;
import com.desitum.shveetlife.objects.tiles.TileData;
import com.desitum.shveetlife.screens.GameScreen;
import com.desitum.shveetlife.screens.MenuScreen;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class GameWorld implements GameInterface{

    private Player player;
    private Player2 player2;
    private ArrayList<NPC> npcs;
    private ArrayList<Chunk> loadedChunks;
    private ArrayList<Chunk> allChunks;

    private ShveetLife shveetLife;
    private ArrayList<String> data;
    private ArrayList<String> loadData;

    private ArrayList<Particle> particles;

    public GameWorld(ShveetLife sl){
        this.shveetLife = sl;

        player = new Player(this, 10, 10, 20, 10);
        player2 = new Player2(this, 10, 10, 10, 10);

        npcs = new ArrayList<NPC>();
        loadedChunks = new ArrayList<Chunk>();
        allChunks = new ArrayList<Chunk>();
        particles = new ArrayList<Particle>();
        data = new ArrayList<String>();

        loadedChunks.add( new Chunk(0, 0, this));

        int randomAmount = (int)(Math.random() * 10);
        for(int count = 0; count < randomAmount; count++) {
            int randomX = (int)(Math.random() * 10);
            int randomY = (int)(Math.random() * 10);
            npcs.add(new NPC(this, 10, 10, randomX, randomY));
        }

        allChunks.add(loadedChunks.get(0));

        createLoadString();

        DataManager.setGameWorld(this);
    }

    public GameWorld(ShveetLife sl, ArrayList<Chunk> chunks, Player p, Player2 p2, ArrayList<NPC> npcs){
        player = p;
        player2 = p2;

        this.npcs = npcs;
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

        for(NPC npc: npcs){
            int moveChance = (int)(Math.random() * 6); //Small chance to move each NPC each update
            if(moveChance == 1) {
                npc.update(delta);
            }
        }

        Iterator<Particle> iter = particles.iterator();
        while(iter.hasNext()){
            Particle p = iter.next();
            p.update(delta);
            if (p.getLifetime() <= 0) iter.remove();
        }

        updateLoadedChunks();
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

        TileObject affectedObject = affectedChunk.getTileAt(player.getPositionInFront());
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

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    public void changeTile(TileObject from, TileObject to){
        Chunk chunk = getChunkAt(player.getPositionInFront());
        int[] position = chunk.changeTile(from, to);
        data.add(ProcessData.EDIT + " " + ProcessData.TILE + " " + position[0] + " " + position[1] + " " + TileData.getTile(to.getClass()) + " " + chunk.getX() + " " + chunk.getY());
    }

    @Override
    public TileObject getTile(Vector3 pos) {
        return getChunkAt(pos).getTileAt(pos);
    }

    private Chunk getChunkAt(Vector3 pos){
        Chunk returnChunk = null;
        for (Chunk chunk: loadedChunks){
            if (CollisionDetection.pointInRectangle(chunk.getBoundingRect(), pos)){
                returnChunk = chunk;
                break;
            }
        }
        return returnChunk;
    }

    public String getDataString(){
        String returnString = "";

        data.add(player.getUpdateString());

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

        ArrayList<Vector3> posToCheck = new ArrayList<Vector3>();
        posToCheck.add(new Vector3(player.getX() + GameScreen.FRUSTUM_WIDTH, player.getY(), 0));
        posToCheck.add(new Vector3(player.getX() - GameScreen.FRUSTUM_WIDTH, player.getY(), 0));
        posToCheck.add(new Vector3(player.getX(), player.getY() + GameScreen.FRUSTUM_HEIGHT, 0));
        posToCheck.add(new Vector3(player.getX(), player.getY() - GameScreen.FRUSTUM_HEIGHT, 0));
        posToCheck.add(new Vector3(player.getX() - GameScreen.FRUSTUM_WIDTH, player.getY() - GameScreen.FRUSTUM_HEIGHT, 0));
        posToCheck.add(new Vector3(player.getX() - GameScreen.FRUSTUM_WIDTH, player.getY() + GameScreen.FRUSTUM_HEIGHT, 0));
        posToCheck.add(new Vector3(player.getX() + GameScreen.FRUSTUM_WIDTH, player.getY() - GameScreen.FRUSTUM_HEIGHT, 0));
        posToCheck.add(new Vector3(player.getX() + GameScreen.FRUSTUM_WIDTH, player.getY() + GameScreen.FRUSTUM_HEIGHT, 0));

        for (int currentPos = 0; currentPos < posToCheck.size(); currentPos++){
            boolean inChunk = false;
            for (Chunk chunk: allChunks){
                if (CollisionDetection.pointInRectangle(chunk.getBoundingRect(), posToCheck.get(currentPos))){
                    inChunk = true;
                    break;
                }
            }

            if (!inChunk){
                Chunk toGoOffOf = getChunkAtPos(player.getX(), player.getY());
                switch (currentPos){
                    case 0:
                        allChunks.add(new Chunk(toGoOffOf.getX() + Chunk.WIDTH, toGoOffOf.getY(), this));
                        break;
                    case 1:
                        allChunks.add(new Chunk(toGoOffOf.getX() - Chunk.WIDTH, toGoOffOf.getY(), this));
                        break;
                    case 2:
                        allChunks.add(new Chunk(toGoOffOf.getX(), toGoOffOf.getY() + Chunk.HEIGHT, this));
                        break;
                    case 3:
                        allChunks.add(new Chunk(toGoOffOf.getX(), toGoOffOf.getY() - Chunk.HEIGHT, this));
                        break;
                    case 4:
                        allChunks.add(new Chunk(toGoOffOf.getX() - Chunk.WIDTH, toGoOffOf.getY() - Chunk.HEIGHT, this));
                        break;
                    case 5:
                        allChunks.add(new Chunk(toGoOffOf.getX() - Chunk.WIDTH, toGoOffOf.getY() + Chunk.HEIGHT, this));
                        break;
                    case 6:
                        allChunks.add(new Chunk(toGoOffOf.getX() + Chunk.WIDTH, toGoOffOf.getY() - Chunk.HEIGHT, this));
                        break;
                    case 7:
                        allChunks.add(new Chunk(toGoOffOf.getX() + Chunk.WIDTH, toGoOffOf.getY() + Chunk.HEIGHT, this));
                        break;
                }
            }
        }

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

        String npcAppend = "";
        String npcString = "";
        for(NPC npc: npcs){
            npcString += npcAppend;
            npcString += npc.toString();
            npcAppend = "/";
        }
        loadData.add(npcString);
    }

    public String getGameLoad(){
        String returnString = "";

        createLoadString();

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
        ArrayList<NPC> newNPCs = new ArrayList<NPC>();

        String[] chunkStrings = loadStrings[0].split("/");
        for (String chunkString: chunkStrings){
            newWorldChunks.add(Chunk.loadFromString(chunkString, newWorld));
        }
        String[] npcStrings = loadStrings[3].split("/");
        for(String npcString: npcStrings){
            newNPCs.add(NPC.loadFromString(npcString, newWorld));
        }

        Player2 otherPlayer = Player2.loadFromString(loadStrings[1], newWorld);


        Player myNewPlayer = Player.loadFromString(loadStrings[2], newWorld);

        newWorld = new GameWorld(sl, newWorldChunks, myNewPlayer, otherPlayer, newNPCs);
        return newWorld;
    }

    public void updateData(String info){
        System.out.println(info);
        if (info.contains(";")) {
            for (String infoPiece : info.split(";")) {
                String[] infoToUse = infoPiece.split(" ");
                if (Integer.parseInt(infoToUse[1]) == ProcessData.TILE) {
                    workOnTile(infoToUse);
                } else if (Integer.parseInt(infoToUse[1]) == ProcessData.PLAYER) {
                    editPlayer(infoToUse);
                }
            }
        } else {
            String[] infoToUse = info.split(" ");
            if (Integer.parseInt(infoToUse[1]) == ProcessData.TILE) {
                workOnTile(infoToUse);
            } else if (Integer.parseInt(infoToUse[1]) == ProcessData.PLAYER) {
                editPlayer(infoToUse);
            }
        }
    }

    private void workOnTile(String[] info){
        for (Chunk chunk: allChunks){
            if (chunk.getX() == Float.parseFloat(info[TileData.CHUNK_X]) &&
                    chunk.getY() == Float.parseFloat(info[TileData.CHUNK_Y])){
                int[] tilePos = {Integer.parseInt(info[TileData.POS_X]), Integer.parseInt(info[TileData.POS_Y])};
                chunk.changeTileAtPosition(tilePos, TileData.buildTileFromString(info, chunk, this));
            }
        }
    }

    public Player2 getPlayer2(){
        return player2;
    }

    private void editPlayer(String[] info){
        player2.setX(Float.parseFloat(info[Player.X]));
        player2.setY(Float.parseFloat(info[Player.Y]));
    }

    public void exitScreen(){
        shveetLife.setScreen(new MenuScreen(shveetLife));

    }

    public Chunk getChunkAtPos(float x, float y){
        for (Chunk chunk: allChunks){
            if (CollisionDetection.pointInRectangle(chunk.getBoundingRect(), new Vector3(x, y, 0))){
                return chunk;
            }
        }
        return null;
    }
}
