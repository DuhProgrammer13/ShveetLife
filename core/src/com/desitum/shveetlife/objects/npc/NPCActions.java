package com.desitum.shveetlife.objects.npc;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/24/2015.
 */
public class NPCActions {
    private NPCController npcController;

    private boolean actionInProgress;
    private int amountOfActions;
    private int currentAction;
    private int npcAmount;
    private float actionDuration;

    private ArrayList<NPC> npcList;
    private ArrayList<Float> durationList;
    private ArrayList<Integer> wantedNPCList;

    public NPCActions(NPCController npcController){
        this.npcController = npcController;

        //CHANGE THIS TO +1 WHENEVER YOU ADD A ACTION METHOD, and add Duration to the Fill Method
        amountOfActions = 1;
        ////////////////////////////////////////////////////

        actionInProgress = false;

        currentAction = 0;
        npcAmount = 0;
        actionDuration = 0;

        durationList = new ArrayList<Float>();
        npcList = npcController.getNPCs();
        wantedNPCList = new ArrayList<Integer>();

        fillDurationList();
        fillWantedNPCList();
    }
    public void fillDurationList(){
        //PUT HOW LONG YOU WANT EACH ACTION TO LAST HERE

        durationList.add(30f); //Follow King Action
        durationList.add(5f); //Build Farm Action
    }
    public void fillWantedNPCList(){
        //PUT HOW MANY NPC'S YOU WANT TO DO EACH ACTION HERE

        wantedNPCList.add(5); //Follow Action
        wantedNPCList.add(2); //Build Farm Action
    }

    public void getAction(int actionID, NPC currentNPC){
        //FOR EACH ACTION YOU ADD, PUT IT HERE
        if(actionID == 0){
            followKingAction(currentNPC);
        } else if(actionID == 1){
            buildFarmAction(currentNPC);
        }

    }


    public void update(float delta) {
        if (!actionInProgress) {
            int randomChance = (int) (Math.random() * 10000);
            if (randomChance == 1) {
                currentAction = (int) (Math.random() * amountOfActions);
                actionDuration = durationList.get(currentAction);
                npcAmount = wantedNPCList.get(currentAction);
                if(npcAmount >= npcList.size()){
                    npcAmount = (npcList.size() - 1);
                }
                actionInProgress = true;
            } else {
                return;
            }
        } else if(actionInProgress){

            for(int npcCount = 1; npcCount < (npcAmount + 1); npcCount++){
                NPC currentNPC = npcList.get(npcCount);

                if(!currentNPC.doingAction()){
                    currentNPC.setDuration(actionDuration);
                    currentNPC.setDoingAction(true);
                } else {
                    if(currentNPC.timer < currentNPC.duration){
                        getAction(currentAction, currentNPC);
                    } else {
                        for(int count = 0; count < npcAmount; count++) {
                            npcList.get(count).setDoingAction(false);
                            npcList.get(count).setNPCMoving(false);
                        }
                        actionInProgress = false;
                    }
                }

            }
        }
    }


    public void followKingAction(NPC currentNPC){
        //Vector3 playerLocation = npcController.playerPosition();
        Vector3 kingLocation = npcController.npcPosition(npcList.get(0));
        Vector3 npcLocation = npcController.npcPosition(currentNPC);

        int randomDifference1 = (int)(Math.random() * 4);
        if(randomDifference1 < 2){
            randomDifference1++;
        }

        float endXLeft = (kingLocation.x - randomDifference1);
        float endXRight = (kingLocation.x + randomDifference1);
        float endYTop = (kingLocation.y + randomDifference1);
        float endYBottom = (kingLocation.y - randomDifference1);

        if(npcLocation.x < (endXLeft - 2) || npcLocation.x > (endXRight + 2)){
            if(npcLocation.x < (endXLeft - 1)){
                currentNPC.setNPCDirection(NPC.RIGHT);
                currentNPC.setNPCMoving(true);
            } else if(npcLocation.x > (endXRight + 1)){
                currentNPC.setNPCDirection(NPC.LEFT);
                currentNPC.setNPCMoving(true);
            } else {
                currentNPC.setNPCMoving(false);
            }
        } else if(npcLocation.y < (endYBottom - 2) || npcLocation.y > (endYTop + 2)){
            if(npcLocation.y < (endYBottom - 1)){
                currentNPC.setNPCDirection(NPC.UP);
                currentNPC.setNPCMoving(true);
            } else if(npcLocation.y > (endYTop + 1)){
                currentNPC.setNPCDirection(NPC.DOWN);
                currentNPC.setNPCMoving(true);
            } else {
                currentNPC.setNPCMoving(false);
            }
        } else {
            currentNPC.setNPCMoving(false);
        }
    }

    public void buildFarmAction(NPC currentNPC){
            followKingAction(currentNPC);
    }

}
