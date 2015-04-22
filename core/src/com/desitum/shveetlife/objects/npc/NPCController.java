package com.desitum.shveetlife.objects.npc;

import com.desitum.shveetlife.world.GameInterface;

import java.util.ArrayList;

/**
 * Created by Zmyth97 on 4/21/2015.
 */
public class NPCController {
    //Will have such things as destroying tiles, making buildings, attacking players, stealing resources, random dancing, etc.
    private GameInterface gameInterface;

    private ArrayList<NPC> npcs;

    public NPCController(GameInterface gi){
        this.gameInterface = gi;

        npcs = new ArrayList<NPC>();
    }

    public void addNPC(NPC npc){
        npcs.add(npc);
    }

    public void update(float delta){
        for(NPC npc: npcs){
            if(!npc.isMoving()) {
                npc.wantsToMove();
            }
            npc.update(delta);
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
}
