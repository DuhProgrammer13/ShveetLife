package com.desitum.shveetlife.objects.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.desitum.shveetlife.objects.player.Player;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public abstract class TileObject extends Sprite {

    public TileObject(Texture texture, float x, float y, float width, float height){
        super(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    public abstract void update(float delta);

    public abstract void useKey(int key, Player p);
}
