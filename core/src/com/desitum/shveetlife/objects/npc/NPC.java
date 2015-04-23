package com.desitum.shveetlife.objects.npc;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by Zmyth97 on 4/21/2015.
 */
public class NPC extends Sprite {

    private GameInterface gameInterface;

    private int direction;
    private boolean moving;
    private boolean needToUpdate;

    private float duration;
    private float time;

    private float speed;

    public static final float WIDTH = 10;
    public static final float HEIGHT = 10;

    private final int RIGHT = 1;
    private final int LEFT = 2;
    private final int UP = 3;
    private final int DOWN = 4;

    public static final int ID = 2;
    public static final int X = 3;
    public static final int Y = 4;

    private int id;

    private Vector3 centerPos;

    public NPC (GameInterface gi, float width, float height, float x, float y, int id){
        super(Assets.npc, 0, 0, Assets.npc.getWidth(), Assets.npc.getHeight());

        this.id = id;

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();
        this.speed = 30;
        this.duration = 0;
        this.needToUpdate = false;

        centerPos = new Vector3(getOriginX(), getOriginY(), 0);

        this.gameInterface = gi;
    }

    public void update(float delta){
        centerPos.set(getOriginX(), getOriginY(), 0);
        if (gameInterface.getChunkAt(centerPos) == null){
            return;
        }else {
            needToUpdate = true;
        }
        if (time <= duration){
            if (moving) {
                if (direction == RIGHT) {
                    if(gameInterface.getChunkAt(new Vector3(getX() + speed * delta, getY(), 0))!= null) {
                        setX(getX() + speed * delta);
                    }
                } else if (direction == LEFT) {
                    if(gameInterface.getChunkAt(new Vector3(getX() - speed * delta, getY(), 0))!= null) {
                        setX(getX() - speed * delta);
                    }
                } else if (direction == UP) {
                    if(gameInterface.getChunkAt(new Vector3(getX(), getY() + speed * delta, 0))!= null) {
                        setY(getY() + speed * delta);
                    }
                } else if (direction == DOWN) {
                    if(gameInterface.getChunkAt(new Vector3(getX(), getY() - speed * delta, 0))!= null) {
                        setY(getY() - speed * delta);
                    }
                }
                updateSpeed();
                time += delta;
            }
        } else {
            moving = false;
        }
    }

    public void wantsToMove(){

        time = 0;
        duration = (float)(Math.random() * 3.0);
        if(duration <= 1){
            duration += 1;
        }
        int randomChoice = (int)(Math.random() * 400);
        if (randomChoice == 0){
            direction = RIGHT;
            moving = true;
        } else if (randomChoice == 1){
            direction = LEFT;
            moving = true;
        } else if (randomChoice == 2){
            direction = UP;
            moving = true;
        } else if (randomChoice == 3){
            direction = DOWN;
            moving = true;
        } else {
            moving = false;
        }
    }

    public float getDamage(Class c){
        return 1;
    }

    public Vector3 getPositionInFront(){ //NOT USED YET, BUT WILL FOR CONTROLLER LATER
        if (direction == RIGHT){
            return new Vector3(getX() + getWidth() + 1, getY() + (getHeight()/2), 0);
        } else if (direction == LEFT){
            return new Vector3(getX() - 1, getY() + (getHeight()/2), 0);
        } else if (direction == UP){
            return new Vector3(getX() + (getWidth()/2), getY() + getHeight() + 1, 0);
        } else if (direction == DOWN){
            return new Vector3(getX() + (getWidth()/2), getY() - 1, 0);
        } else {
            return new Vector3(getX(), getY(), 0);
        }
    }

    public static NPC loadFromString(String data, GameInterface gi){
        String[] dataStrings = data.split(" ");

        float x = Float.parseFloat(dataStrings[1]);
        float y = Float.parseFloat(dataStrings[2]);
        float width = Float.parseFloat(dataStrings[3]);
        float height = Float.parseFloat(dataStrings[4]);
        int id = Integer.parseInt(dataStrings[5]);

        return new NPC(gi, width, height, x, y, id);
    }


    @Override
    public String toString(){
        return "NPC " + getX() + " " + getY() + " " + getWidth() + " " + getHeight() + " " + id;
    }

    public String getUpdateString(){
        String returnString = null;
        if (needToUpdate) {
            returnString = ProcessData.EDIT + " " + ProcessData.NPC + " " + id + " " + getX() + " " + getY();
        }
        return returnString;
    }

    private void updateSpeed(){
        speed = gameInterface.getTile(new Vector3(getOriginX(), getOriginY(), 0)).getPlayerSpeed()/2;
    }

    public boolean isMoving(){
        return moving;
    }

    public int getId(){
        return id;
    }

    public boolean needsUpdate(){
        return needToUpdate;
    }
}
