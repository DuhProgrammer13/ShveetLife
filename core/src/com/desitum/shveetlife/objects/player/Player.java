package com.desitum.shveetlife.objects.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.data.GameKeys;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public class Player extends Sprite {

    private GameInterface gameInterface;
    private PlayerAnimator playerAnimator;
    private PlayerInventory inventory;

    private int direction;
    private boolean moving;

    private float speed;

    public static final int X = 2;
    public static final int Y = 3;

    public Player (GameInterface gi, float width, float height, float x, float y){
        super(Assets.player, 0, 0, Assets.player.getWidth(), Assets.player.getHeight());

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();
        this.speed = 30;

        this.gameInterface = gi;

        this.inventory = new PlayerInventory(gi, this);
    }

    public void update(float delta){
        if (moving){
            if (direction == GameKeys.RIGHT){
                setX(getX() + speed * delta);
            } else if (direction == GameKeys.LEFT){
                setX(getX() - speed * delta);
            } else if (direction == GameKeys.UP){
                setY(getY() + speed * delta);
            } else if (direction == GameKeys.DOWN){
                setY(getY() - speed * delta);
            }
            updateSpeed();
        }
    }

    public void useKey(int key){
        switch (key){
            case Input.Keys.A:
                inventory.placeItem();
        }
    }

    public void useDirectionalKey(int key){
        if (key == Input.Keys.DPAD_RIGHT){
            direction = GameKeys.RIGHT;
            moving = true;
        } else if (key == Input.Keys.DPAD_LEFT){
            direction = GameKeys.LEFT;
            moving = true;
        } else if (key == Input.Keys.DPAD_UP){
            direction = GameKeys.UP;
            moving = true;
        } else if (key == Input.Keys.DPAD_DOWN){
            direction = GameKeys.DOWN;
            moving = true;
        } else {
            moving = false;
        }
    }

    public float getDamage(Class c){
        return 1;
    }

    public Vector3 getPositionInFront(){
        if (direction == GameKeys.RIGHT){
            return new Vector3(getX() + getWidth() + 1, getY() + (getHeight()/2), 0);
        } else if (direction == GameKeys.LEFT){
            return new Vector3(getX() - 1, getY() + (getHeight()/2), 0);
        } else if (direction == GameKeys.UP){
            return new Vector3(getX() + (getWidth()/2), getY() + getHeight() + 1, 0);
        } else if (direction == GameKeys.DOWN){
            return new Vector3(getX() + (getWidth()/2), getY() - 1, 0);
        } else {
            return new Vector3(getX(), getY(), 0);
        }
    }

    public static Player loadFromString(String data, GameInterface gi){
        return new Player(gi, 10, 10, 10, 10);
    }

    public String toString(){
        //TODO need a big time fix here
        return "fetch a roo";
    }

    public String getUpdateString(){
        return ProcessData.EDIT + " " + ProcessData.PLAYER + " " + getX() + " " + getY();
    }

    private void updateSpeed(){
        speed = gameInterface.getTile(new Vector3(getX() + getWidth()/2, getY() + getHeight()/2, 0)).getPlayerSpeed();
    }

    public void pause(){
        moving = false;
    }

    public void giveItem(int type, int thing, int amount){
        inventory.addItem(type, thing, amount);
    }
}
