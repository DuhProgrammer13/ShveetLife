package com.desitum.shveetlife.objects.tiles;

import com.badlogic.gdx.Input;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.objects.GameObject;
import com.desitum.shveetlife.objects.player.Player;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public class GrassTile extends GameObject {

    private GameInterface gi;
    private float health;

    /** Doesn't need to extend Sprite cause nothing is ever drawn, it just needs
     * to store data for the basic needs of destroying, digging, and whatever
     * else we decide to add
     * @param gi
     */
    public GrassTile(GameInterface gi){
        super(Assets.emptyTexture, 0, 0, Assets.emptyTexture.getWidth(), Assets.emptyTexture.getHeight());
    }

    @Override
    public void update(float delta) {
        return; //No need, it's a grass tile, doesn't do anything
    }

    @Override
    public void useKey(int key, Player player) {
        switch (key){
            case Input.Keys.SPACE:
                doDamage(player.getDamage(this.getClass()));
                // TODO KODY needs particles :P
        }
    }

    private void doDamage(float amount){

    }
}
