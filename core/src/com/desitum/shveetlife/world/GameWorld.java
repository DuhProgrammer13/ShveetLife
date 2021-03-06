package com.desitum.shveetlife.world;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.ShveetLife;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.data.P2PGoldShop;
import com.desitum.shveetlife.libraries.CollisionDetection;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.network.DataManager;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.objects.Chunk;
import com.desitum.shveetlife.objects.MenuButton;
import com.desitum.shveetlife.objects.MenuButtonOnClickListener;
import com.desitum.shveetlife.libraries.menu.OnClickListener;
import com.desitum.shveetlife.libraries.menu.PopupButton;
import com.desitum.shveetlife.libraries.menu.PopupMenu;
import com.desitum.shveetlife.libraries.menu.PopupScrollArea;
import com.desitum.shveetlife.libraries.menu.PopupSlider;
import com.desitum.shveetlife.objects.npc.NPC;
import com.desitum.shveetlife.objects.npc.NPCController;
import com.desitum.shveetlife.objects.particles.Particle;
import com.desitum.shveetlife.objects.player.Player;
import com.desitum.shveetlife.objects.player.Player2;
import com.desitum.shveetlife.objects.tiles.TileData;
import com.desitum.shveetlife.objects.tiles.TileObject;
import com.desitum.shveetlife.screens.GameScreen;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class GameWorld implements GameInterface{

    //region data members
    private Player player;
    private Player2 player2;
    private ArrayList<Chunk> loadedChunks;
    private ArrayList<Chunk> allChunks;

    private ShveetLife shveetLife;
    private ArrayList<String> data;
    private ArrayList<String> loadData;

    private NPCController npcController;
    private P2PGoldShop goldShop;

    private ArrayList<Particle> particles;

    private PopupMenu settingsMenu;
    private PopupSlider volumeSlider;

    private PopupMenu itemsMenu;
    private PopupScrollArea itemsScrollArea;

    private MenuButton goldShopButton;

    public static final int RUNNING = 0;
    public static final int PAUSED = 1;

    private int state = RUNNING;
    //endregion

    public GameWorld(ShveetLife sl){
        this.shveetLife = sl;

        player = new Player(this, 10, 10, 20, 10);
        player2 = new Player2(this, 10, 10, 10, 10);

        loadedChunks = new ArrayList<Chunk>();
        allChunks = new ArrayList<Chunk>();
        particles = new ArrayList<Particle>();
        data = new ArrayList<String>();

        loadedChunks.add( new Chunk(0, 0, this));

        npcController = new NPCController(this);
        int randomAmount = (int) (Math.random() * 10);
        if(randomAmount < 1){
            randomAmount =+ 1;
        }
        for (int count = 0; count < randomAmount; count++) {
            int randomX = (int) (Math.random() * 10);
            int randomY = (int) (Math.random() * 10);
            npcController.addNPC(new NPC(this, 10, 10, randomX, randomY, count));
        }

        goldShop = new P2PGoldShop(this);

        allChunks.add(loadedChunks.get(0));

        goldShopButton = new MenuButton(Assets.goldShopButtonUp, Assets.goldShopButtonDown, 0, GameScreen.FRUSTUM_WIDTH - 15, GameScreen.FRUSTUM_HEIGHT - 15, 10, 10);
        goldShopButton.setOnClickListener(new MenuButtonOnClickListener() {
            int clickCount = 1;
            @Override
            public void onClick() {
                clickCount++;
                if(clickCount % 2 == 0) {
                    moveInGoldShop();
                } else {
                    moveOutGoldShop();
                }
            }
        });

        createLoadString();

        DataManager.setGameWorld(this);

        setupPopupMenu();
        setupItemsMenu();
    }

    //region updateMethods
    public void update(float delta){
        settingsMenu.update(delta);
        if (player.inventoryUINeedsUpdate()){
            itemsScrollArea.setWidgets(player.getInventory().getPopupWidgets());
            itemsScrollArea.slideToPosition(itemsScrollArea.getPositionToCenter(player.getInventory().getSelectedItemPos()));
            itemsScrollArea.selectWidget(player.getInventory().getSelectedItemPos(), true);
        }
        itemsMenu.update(delta);
        goldShop.getPopupMenu().update(delta);

        player.update(delta);
        player2.update(delta, "");

        if (DataManager.mainServer != null) npcController.update(delta);

        for (Chunk chunk: loadedChunks){
            chunk.update(delta);
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
        if (state == RUNNING){
            player.useDirectionalKey(key);
        }
    }

    public void updateKeys(int key){
        if (state == RUNNING) {
            Chunk affectedChunk = null;
            for (Chunk loadedChunk : loadedChunks) {
                if (CollisionDetection.pointInRectangle(loadedChunk.getBoundingRect(), player.getPositionInFront())) {
                    affectedChunk = loadedChunk;
                }
            }
            if (affectedChunk == null) {
                return;
            }

            TileObject affectedObject = affectedChunk.getTileAt(player.getPositionInFront());
            if (affectedObject == null) {
                return;
            }

            switch (key) {
                case Input.Keys.SPACE:
                    affectedObject.useKey(key, player);
                    break;
                case Input.Keys.A:
                    player.useKey(key);
                    break;
                case Input.Keys.D:
                    player.useKey(key);
                    break;
                case Input.Keys.S:
                    player.useKey(key);
                    break;
            }
        }
    }

    public void updateTouch(Vector3 touchPoint, boolean isTouched){
        if (state == PAUSED){
            settingsMenu.updateTouchInput(touchPoint, isTouched);
        } else if (state == RUNNING){
            itemsMenu.updateTouchInput(touchPoint, isTouched);
            goldShop.getPopupMenu().updateTouchInput(touchPoint, isTouched);

            boolean clickInArea = CollisionDetection.pointInRectangle(goldShopButton.getBoundingRectangle(), touchPoint);
            if (clickInArea && isTouched){
                goldShopButton.onClickDown();
            } else if (clickInArea) {
                goldShopButton.onClickUp(true);
            } else {
                goldShopButton.onClickUp(false);
            }
        }
    }

    public void updateScroll(int amount){
        itemsMenu.udpateScrollInput(amount, null, false);
    }

    public void updateData(String info){
        if (info.contains(";")) {
            for (String infoPiece : info.split(";")) {
                String[] infoToUse = infoPiece.split(" ");
                if (Integer.parseInt(infoToUse[1]) == ProcessData.TILE) {
                    workOnTile(infoToUse);
                } else if (Integer.parseInt(infoToUse[1]) == ProcessData.PLAYER) {
                    editPlayer(infoToUse);
                } else if (Integer.parseInt(infoToUse[1]) == ProcessData.NPC){
                    npcController.updateFromString(infoToUse);
                } else if (Integer.parseInt(infoToUse[1]) == ProcessData.SHOP){

                }
            }
        } else {
            String[] infoToUse = info.split(" ");
            if (Integer.parseInt(infoToUse[1]) == ProcessData.TILE) {
                workOnTile(infoToUse);
            } else if (Integer.parseInt(infoToUse[1]) == ProcessData.PLAYER) {
                editPlayer(infoToUse);
            } else if (Integer.parseInt(infoToUse[1]) == ProcessData.NPC) {
                npcController.updateFromString(infoToUse);
            } else if (Integer.parseInt(infoToUse[1]) == ProcessData.SHOP) {

            }
        }
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
    //endregion

    //region GameInterface methods
    @Override
    public TileObject getTile(Vector3 pos) {
        return getChunkAt(pos).getTileAt(pos);
    }

    @Override
    public Chunk getChunkAt(Vector3 pos){
        Chunk returnChunk = null;
        for (Chunk chunk: loadedChunks){
            if (CollisionDetection.pointInRectangle(chunk.getBoundingRect(), pos)){
                returnChunk = chunk;
                break;
            }
        }
        return returnChunk;
    }

    @Override
    public void addParticles(Particle p) {
        particles.add(p);
    }

    @Override
    public void changeTile(TileObject from, TileObject to){
        Chunk chunk = getChunkAt(player.getPositionInFront());
        int[] position = chunk.changeTile(from, to);
        data.add(ProcessData.EDIT + " " + ProcessData.TILE + " " + position[0] + " " + position[1] + " " + TileData.getTile(to.getClass()) + " " + chunk.getX() + " " + chunk.getY());
    }

    @Override
    public void placeTileInFrontOfPlayer(TileObject to){
        Chunk chunk = getChunkAt(player.getPositionInFront());
        TileObject from = chunk.getTileAt(player.getPositionInFront());
        int[] position = chunk.changeTile(from, to);
        data.add(ProcessData.EDIT + " " + ProcessData.TILE + " " + position[0] + " " + position[1] + " " + TileData.getTile(to.getClass()) + " " + chunk.getX() + " " + chunk.getY());
    }

    @Override
    public void givePlayerItem(int type, int thing, int amount){
        player.giveItem(type, thing, amount);
    }

    public Vector3 getPlayerPosition(){
        return player.getPositionInFront();
    }

    @Override
    public boolean isPlayerMoving(){
        return player.isPlayerMoving();
    }

    @Override
    public void updateInventoryUI(){

    }

    @Override
    public void addItemToShop() {

    }

    @Override
    public int[] getSelectedItem() {
        int[] playerItem = player.getInventory().getSelectedItem();
        return new int[]{player.getInventory().getSelectedItemPos(), playerItem[0], playerItem[1], playerItem[3]};
    }
    //endregion

    //region loadGame
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

        loadData.add(npcController.toString());
    }

    public static GameWorld loadGameFromString(String loadString, ShveetLife sl){
        //GameWorld newWorld = newWorld = new GameWorld(sl, newWorldChunks, myNewPlayer, otherPlayer, newNPCs);
        GameWorld newWorld = new GameWorld(sl);

        ArrayList<Chunk> newWorldChunks = new ArrayList<Chunk>();
        String[] loadStrings = loadString.split(":");

        String[] chunkStrings = loadStrings[0].split("/");
        for (String chunkString: chunkStrings){
            Chunk chunkToAdd = Chunk.loadFromString(chunkString, newWorld);
            if (chunkToAdd == null){
            } else {
                newWorldChunks.add(chunkToAdd);
            }
        }
        newWorld.setAllChunks(newWorldChunks);

        Player2 otherPlayer = Player2.loadFromString(loadStrings[1], newWorld);
        newWorld.setPlayer2(otherPlayer);


        Player myNewPlayer = Player.loadFromString(loadStrings[2], newWorld);
        newWorld.setPlayer(myNewPlayer);

        NPCController newNPCs = new NPCController(newWorld);
        if (loadStrings.length == 4 && loadStrings[3] != null) {
            String[] npcStrings = loadStrings[3].split("/");
            for (String npcString : npcStrings) {
                newNPCs.addNPC(NPC.loadFromString(npcString, newWorld));
            }
        }
        newWorld.setNpcController(newNPCs);

        return newWorld;
    }

    public void setupItemsMenu(){
        itemsMenu = new PopupMenu(Assets.itemMenuBackground, 0, 0, 150, 15);

        itemsScrollArea = new PopupScrollArea(Assets.itemMenuScrollArea, 2.5f, 2.5f, 145, 10, 140, 10, PopupScrollArea.HORIZONTAL, 1, 5, 10);

        itemsMenu.addPopupWidget(itemsScrollArea);
    }
    //endregion

    //region setters
    public void setAllChunks(ArrayList<Chunk> chunksToSet){
        this.allChunks = chunksToSet;
        updateLoadedChunks();
    }

    public void setPlayer(Player p){
        this.player = p;
    }

    public void setPlayer2(Player2 p2){
        this.player2 = p2;
    }

    public void setNpcController(NPCController npcc){
        this.npcController = npcc;
    }

    private void setupPopupMenu(){
        float POPUP_WIDTH = 50;
        float POPUP_HEIGHT = GameScreen.FRUSTUM_HEIGHT - 20;
        settingsMenu = new PopupMenu(Assets.menuBackground, 50f,
                -POPUP_HEIGHT, POPUP_WIDTH, POPUP_HEIGHT);
        MovementAnimator moveInAnimator1 = new MovementAnimator(-POPUP_HEIGHT, 10, 1, Interpolation.DECELERATE_INTERPOLATOR);
        moveInAnimator1.setControllingY(true);
        MovementAnimator moveOutAnimator1 = new MovementAnimator(10, -POPUP_HEIGHT, 1, Interpolation.ANTICIPATE_INTERPOLATOR);
        moveOutAnimator1.setControllingY(true);
        settingsMenu.addIncomingAnimator(moveInAnimator1);
        settingsMenu.addOutgoingAnimator(moveOutAnimator1);

        volumeSlider = new PopupSlider(Assets.pathTexture, Assets.textFieldBackground, 5, POPUP_HEIGHT-15,
                POPUP_WIDTH-10, 5, 3, 10);

        PopupButton cancelButton = new PopupButton(Assets.cancelButtonUp, Assets.cancelButtonDown, 5,
                5, POPUP_WIDTH - 10, (POPUP_WIDTH-10)/3);

        PopupButton saveButton = new PopupButton(Assets.saveButtonUp, Assets.saveButtonDown, 5,
                cancelButton.getY() + cancelButton.getHeight() + 5, POPUP_WIDTH - 10, (POPUP_WIDTH-10)/3);

        PopupButton exitButton = new PopupButton(Assets.exitButtonUp, Assets.exitButtonDown, 5,
                saveButton.getY() + saveButton.getHeight() + 5, POPUP_WIDTH - 10, (POPUP_WIDTH-10)/3);

        settingsMenu.addPopupWidget(volumeSlider);
        settingsMenu.addPopupWidget(cancelButton);
        settingsMenu.addPopupWidget(saveButton);
        settingsMenu.addPopupWidget(exitButton);


        cancelButton.setButtonListener(new OnClickListener() {
            @Override
            public void onClick() {
                settingsMenu.moveOut();
                state = RUNNING;
            }
        });

        saveButton.setButtonListener(new OnClickListener() {
            @Override
            public void onClick() {
                settingsMenu.moveOut();
                state = RUNNING;
            }
        });

        exitButton.setButtonListener(new OnClickListener() {
            @Override
            public void onClick() {
                DataManager.exitGame();
            }
        });
    }
    //endregion

    //region getters
    public Player2 getPlayer2(){
        return player2;
    }

    public PopupMenu getItemsMenu(){
        return itemsMenu;
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

    public String getDataString(){
        String returnString = "";

        data.add(player.getUpdateString());

        String separator = "";
        for (String item: data){
            returnString += separator;
            returnString += item;
            separator = ";";
        }

        if (DataManager.mainServer != null){
            returnString += npcController.getUpdateString();
        }

        data = new ArrayList<String>();
        return returnString;
    }

    public ArrayList<String> getData(){
        return data;
    }

    public PopupMenu getSettingsMenu(){
        return settingsMenu;
    }

    public PopupMenu getGoldShopMenu(){
        return goldShop.getPopupMenu();
    }

    public Chunk getChunkAtPos(float x, float y){
        for (Chunk chunk: allChunks){
            if (CollisionDetection.pointInRectangle(chunk.getBoundingRect(), new Vector3(x, y, 0))){
                return chunk;
            }
        }
        return null;
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

    public NPCController getNpcController(){
        return npcController;
    }

    public MenuButton getGoldShopButton(){
        return goldShopButton;
    }
    //endregion

    public void exitGame() {
        if(shveetLife != null) {
            shveetLife.exitScreen();
        } else{
            JOptionPane.showMessageDialog(null, "Hahaha, it was null");
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

    private void editPlayer(String[] info){
        player2.setX(Float.parseFloat(info[Player.X]));
        player2.setY(Float.parseFloat(info[Player.Y]));
    }

    public void pauseGame(){
        settingsMenu.moveIn();
        player.pause();
        state = PAUSED;
    }

    public void moveInGoldShop(){
        goldShop.moveIn();
    }

    public void moveOutGoldShop(){
        goldShop.moveOut();
    }
}
