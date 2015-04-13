package com.desitum.shveetlife.objects.player;

import com.badlogic.gdx.graphics.Texture;
import com.desitum.shveetlife.data.Assets;

/**
 * Created by kody on 4/7/15.
 * can be used by kody and people in []
 */
public class PlayerAnimator {

    Texture onlyTexture;

    public PlayerAnimator(Player p){
        onlyTexture = Assets.player;
    }

    public void update(float delta){
        return;
    }

    public Texture getCurrentTexture(){
        return onlyTexture;
    }
}
