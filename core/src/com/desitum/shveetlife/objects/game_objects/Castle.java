package com.desitum.shveetlife.objects.game_objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.data.Assets;
import com.desitum.shveetlife.world.GameInterface;

/**
 * Created by Zmyth97 on 4/26/2015.
 */
public class Castle extends Sprite {
    private GameInterface gi;
    private BuildingController buildController;
    private Vector3 centerPos;
    private boolean placed;

    public Castle(GameInterface gi, BuildingController buildController, float width, float height, float x, float y, int cost){
        super(Assets.castle, 0, 0, Assets.castle.getWidth(), Assets.castle.getHeight());

        this.setSize(width, height);
        this.setPosition(x, y);

        this.setOriginCenter();
        this.gi = gi;
        this.buildController = buildController;

        placed = false;

        centerPos = new Vector3(getOriginX(), getOriginY(), 0);
    }

    public void update(float delta){
        if (gi.getChunkAt(centerPos) == null){
            placed = false;
            return;
        }else {
            if(!placed) {
                placed = true;
                buildController.updateBuilding(this, this.getX(), this.getY());
            }
        }
    }

    private void setBoundaries(){
        //NEED A METHOD TO SET TILES TO "BLOCKED"?
    }

}
