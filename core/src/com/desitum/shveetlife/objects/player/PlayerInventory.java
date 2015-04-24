package com.desitum.shveetlife.objects.player;

import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.objects.tiles.TileData;
import com.desitum.shveetlife.objects.tiles.TileObject;
import com.desitum.shveetlife.world.GameInterface;

import java.util.ArrayList;

/**
 * Created by dvan6234 on 4/23/2015.
 */
public class PlayerInventory {
    private GameInterface gameInterface;
    private Player player;

    ArrayList<int[]> inventory;
    int[] itemSelected;

    public static final int TYPE = 0;
    public static final int THING = 1;
    public static final int AMOUNT = 2;

    public PlayerInventory(GameInterface gi, Player p){
        inventory = new ArrayList<int[]>();
        itemSelected = null;

        gameInterface = gi;
        player = p;
    }

    public void addItem(int type, int thing, int amount){
        boolean added = false;
        for (int[] item: inventory){
            if (item[TYPE] == type && item[THING] == thing){
                added = true;
                item[AMOUNT] += amount;
            }
        }
        if (!added){
            inventory.add(new int[]{type, thing, amount});
            if (itemSelected == null){
                itemSelected = inventory.get(0);
            }
            System.out.println("added: " + added + ", inventorySize: " + inventory.size());
            System.out.println(TileData.createTile(thing, 0, 0, gameInterface));
        }
    }

    public void selectItem(int position){
        itemSelected = inventory.get(position);
    }

    public void placeItem(){
        if (itemSelected != null){
            if (itemSelected[TYPE] == ProcessData.TILE){
                TileObject tileFrom = gameInterface.getTile(player.getPositionInFront());
                gameInterface.placeTileInFrontOfPlayer(TileData.createTile(itemSelected[THING], tileFrom.getX(), tileFrom.getY(), gameInterface));
                itemSelected[AMOUNT] -= 1;
            }
        }
    }
}
