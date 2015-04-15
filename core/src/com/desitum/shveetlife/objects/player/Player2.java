package com.desitum.shveetlife.objects.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.data.GameKeys;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public class Player2 extends Sprite {

    private GameInterface gameInterface;
    private PlayerAnimator playerAnimator;

    private int direction;
    private boolean moving;

    private static final int SPEED = 30;

    public Player2(GameInterface gi, float width, float height, float x, float y){
        //super(texture, 0, 0, texture.getWidth(), texture.getHeight());
        super(Assets.player2, 0, 0, Assets.player2.getWidth(), Assets.player2.getHeight());

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();

        this.gameInterface = gi;
    }

    public void update(float delta, String command){
        String[] info = command.split(" ");

        setX(Integer.parseInt(info[0]));
        setY(Integer.parseInt(info[1]));
    }

    public static Player2 loadFromString(String data, GameInterface gi){
        String[] dataStrings = data.split(" ");

        float x = Integer.parseInt(dataStrings[1]);
        float y = Integer.parseInt(dataStrings[2]);
        float width = Integer.parseInt(dataStrings[3]);
        float height = Integer.parseInt(dataStrings[4]);

        return new Player2(gi, width, height, x, y);
    }

    @Override
    public String toString(){
        return "Name " + getX() + " " + getY() + " " + getWidth() + " " + getHeight();
    }
}
