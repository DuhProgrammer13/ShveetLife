package com.desitum.shveetlife.objects.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.desitum.shveetlife.libraries.Drawing;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.objects.menu.PopupImage;
import com.desitum.shveetlife.objects.menu.PopupWidget;
import com.desitum.shveetlife.objects.tiles.TileData;
import com.desitum.shveetlife.objects.tiles.TileObject;
import com.desitum.shveetlife.world.GameInterface;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by dvan6234 on 4/23/2015.
 */
public class PlayerInventory {
    private GameInterface gameInterface;
    private Player player;

    private boolean needUpdate;

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
            needUpdate = true;
            updateItems();
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
                updateItems();
            }
        }
    }

    private void updateItems(){
        Iterator<int[]> iter = inventory.iterator();
        while (iter.hasNext()){
            int[] item = iter.next();
            if (item[AMOUNT] == 0){
                iter.remove();
                itemSelected = null;
                needUpdate = true;
                gameInterface.updateInventoryUI();
            }
        }
    }

    public boolean needsUpdate(){
        return needUpdate;
    }

    public ArrayList<PopupWidget> getPopupWidgets(){
        ArrayList<PopupWidget> popupWidgetArrayList = new ArrayList<PopupWidget>();
        Texture highlight = Drawing.getHollowRectangle(50, 50, 5, new Color(0.2f, 0.2f, 1, 0.6f));

        for(int[] item: inventory){
            if (item[TYPE] == ProcessData.TILE){
                popupWidgetArrayList.add(new PopupImage(TileData.getTileTexture(item[THING]), highlight, 0,0,10,10, false));
            }
        }

        needUpdate = false;

        return popupWidgetArrayList;
    }
}
