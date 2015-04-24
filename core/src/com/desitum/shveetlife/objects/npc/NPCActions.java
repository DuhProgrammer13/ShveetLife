package com.desitum.shveetlife.objects.npc;

import java.util.ArrayList;

/**
 * Created by Zmyth97 on 4/24/2015.
 */
public class NPCActions {
    private NPCController npcController;

    private ArrayList<Integer> actions;
    private boolean actionInProgress;

    private int amountOfActions;

    public NPCActions(NPCController npcController){
        this.npcController = npcController;

        //CHANGE THIS TO +1 WHENEVER YOU ADD A ACTION METHOD
        amountOfActions = 1;
        ////////////////////////////////////////////////////
        actions = new ArrayList<Integer>();
        actionInProgress = false;


        fillArrayList();
    }

    public void update(float delta){
        int randomChance = (int)(Math.random() * 100);
        if(randomChance == 1){

        }
    }

    private void fillArrayList(){
        for(int action = 0; action < amountOfActions; action++){
            actions.add(action);
        }
    }

    private void getActions(int actionID){
        if(actionID == 0){
            followAction();
        } else if (actionID == 1){
            buildFarmAction();
        } else {
            gatherWood();
        }
    }

    private void followAction(){

    }

    private void buildFarmAction(){

    }

    private void gatherWood(){

    }


}
