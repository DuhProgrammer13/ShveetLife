package com.desitum.shveetlife.objects.npc;

import com.badlogic.gdx.math.Vector3;
import com.desitum.shveetlife.world.GameInterface;

import java.util.ArrayList;

/**
 * Created by Zmyth97 on 4/21/2015.
 */
public class NPCController {
    private GameInterface gameInterface;
    private NPCActions npcActions;

    private ArrayList<NPC> npcs;

    public NPCController(GameInterface gi){
        this.gameInterface = gi;

        npcs = new ArrayList<NPC>();
        npcActions = new NPCActions(this);
    }

    public void addNPC(NPC npc){
        npcs.add(npc);
    }

    public void update(float delta){
        for(NPC npc: npcs){
            if(!npc.doingAction()) {
                if (!npc.isMoving()) {
                    npc.idleMove();
                }
            }
            npc.update(delta);
        }
        npcActions.update(delta);
    }

    public void updateFromString(String[] npcInfo){
        int id = Integer.parseInt(npcInfo[NPC.ID]);
        for (NPC npc: npcs) {
            if (id == npc.getId()) {
                npc.setX(Float.parseFloat(npcInfo[NPC.X]));
                npc.setY(Float.parseFloat(npcInfo[NPC.Y]));
            }
        }
    }

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    @Override
    public String toString(){
        String npcAppend = "";
        String npcString = "";
        for(NPC npc: npcs){
            npcString += npcAppend;
            npcString += npc.toString();
            npcAppend = "/";
        }
        return npcString;
    }

    public String getUpdateString(){
        String returnString = "";
        for (NPC npc: npcs){
            if (npc.needsUpdate()) {
                returnString += ";";
                returnString += npc.getUpdateString();
            }
        }
        return returnString;
    }

    public Vector3 playerPosition(){
        return gameInterface.getPlayerPosition();
    }

    public Vector3 npcPosition(NPC npc){
        return npc.getNPCPosition();
    }

}
