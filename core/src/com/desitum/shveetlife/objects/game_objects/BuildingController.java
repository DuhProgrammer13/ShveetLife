package com.desitum.shveetlife.objects.game_objects;

import com.desitum.shveetlife.network.ProcessData;

/**
 * Created by Zmyth97 on 4/26/2015.
 */
public class BuildingController {

    public BuildingController(){

    }

    public void update(float delta){

    }

    public String updateBuilding(Object building, float X, float Y){
        String returnString = "";
            returnString = ProcessData.PLACE + " " + ProcessData.BUILDING + " " + building + " " + X + " " + Y;
        return returnString;
    }
}
