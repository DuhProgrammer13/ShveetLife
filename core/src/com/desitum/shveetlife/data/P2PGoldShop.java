package com.desitum.shveetlife.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.desitum.shveetlife.libraries.Drawing;
import com.desitum.shveetlife.libraries.animation.MovementAnimator;
import com.desitum.shveetlife.libraries.interpolation.Interpolation;
import com.desitum.shveetlife.network.ProcessData;
import com.desitum.shveetlife.objects.menu.PopupButton;
import com.desitum.shveetlife.objects.menu.PopupButtonListener;
import com.desitum.shveetlife.objects.menu.PopupImage;
import com.desitum.shveetlife.objects.menu.PopupMenu;
import com.desitum.shveetlife.objects.menu.PopupScrollArea;
import com.desitum.shveetlife.objects.tiles.TileData;
import com.desitum.shveetlife.screens.GameScreen;
import com.desitum.shveetlife.world.GameInterface;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by kody on 4/26/15.
 * can be used by kody and people in []
 */
public class P2PGoldShop {
    private ArrayList<int[]> p1items;
    private ArrayList<int[]> p2items;

    private PopupMenu menu;
    private PopupScrollArea tilesScroll;

    private int selectedTileItem;

    private int amountOfTilesAvailable;

    private GameInterface gameInterface;

    public static final int ITEM_TYPE = 0;
    public static final int ITEM_ITEM = 1;
    public static final int ITEM_COUNT = 2;

    public static final int ITEM_TYPE_DATA = 2;
    public static final int ITEM_ITEM_DATA = 3;
    public static final int ITEM_COUNT_DATA = 4;

    public P2PGoldShop(GameInterface gi){
        gameInterface = gi;
        p1items = new ArrayList<int[]>();
        p2items = new ArrayList<int[]>();

        p2items.add(new int[]{ProcessData.TILE, TileData.GRASS, 5});
        p2items.add(new int[]{ProcessData.TILE, TileData.DIRT, 5});
        p2items.add(new int[]{ProcessData.TILE, TileData.WATER, 5});
        p2items.add(new int[]{ProcessData.TILE, TileData.FIRE, 5});
        amountOfTilesAvailable = 4;

        buildPopupMenu();
    }

    public String[] buyItem(int itemType, int itemItem, int amount){
        return removeItem(itemType, itemItem, amount);
    }

    public String[] addItem(int itemType, int itemItem, int amount){
        boolean added = false;
        for (int itemPos = 0; itemPos < p1items.size(); itemPos++){
            int[] item = p1items.get(itemPos);
            if (item[ITEM_TYPE] == itemType){
                if (item[ITEM_ITEM] == itemItem){
                    item[ITEM_COUNT] += amount;
                    added = true;
                }
            }
        }
        if (!added){
            p1items.add(new int[]{itemType, itemItem, amount});
            if (itemType == ProcessData.TILE) amountOfTilesAvailable++;
        }

        return new String[]{ProcessData.ADD + "", ProcessData.SHOP + "", itemType + "", itemItem + "", amount + ""};
    }

    public String[] removeItem(int itemType, int itemItem, int amount){
        for (int itemPos = 0; itemPos < p1items.size(); itemPos++){
            int[] item = p1items.get(itemPos);
            if (item[ITEM_TYPE] == itemType){
                if (item[ITEM_ITEM] == itemItem){
                    item[ITEM_COUNT] -= amount;
                }
            }
        }
        removeUnnecessary();

        return new String[]{ProcessData.REMOVE + "", ProcessData.SHOP + "", itemType + "", itemItem + "", amount + ""};
    }

    public void processData(String[] data){
        int[] toUse = new int[]{Integer.parseInt(data[ITEM_TYPE_DATA]), Integer.parseInt(data[ITEM_ITEM_DATA]), Integer.parseInt(data[ITEM_COUNT_DATA])};
        if (toUse[0] == ProcessData.ADD){
            addItemFromData(toUse);
        } if (toUse[0] == ProcessData.REMOVE) {
            removeItemFromData(toUse);
        }
    }

    public void addItemFromData(int[] toAdd) {
        boolean added = false;
        for (int itemPos = 0; itemPos < p2items.size(); itemPos++){
            int[] item = p2items.get(itemPos);
            if (item[ITEM_TYPE] == toAdd[ITEM_TYPE]){
                if (item[ITEM_ITEM] == toAdd[ITEM_ITEM]){
                    item[ITEM_COUNT] += toAdd[ITEM_COUNT];
                    added = true;
                }
            }
        }
        if (!added){
            p2items.add(toAdd);
        }
    }

    public void removeItemFromData(int[] toRemove) {
        for (int itemPos = 0; itemPos < p2items.size(); itemPos++){
            int[] item = p2items.get(itemPos);
            if (item[ITEM_TYPE] == toRemove[ITEM_TYPE]){
                if (item[ITEM_ITEM] == toRemove[ITEM_ITEM]){
                    item[ITEM_COUNT] -= toRemove[ITEM_COUNT];
                }
            }
        }
        removeUnnecessary();
    }

    public void removeUnnecessary (){
        Iterator<int[]> iter = p2items.iterator();
        while (iter.hasNext()){
            int[] items = iter.next();
            if (items[ITEM_COUNT] <= 0){
                iter.remove();
            }
        }

        iter = p1items.iterator();
        while (iter.hasNext()){
            int[] items = iter.next();
            if (items[ITEM_COUNT] <= 0){
                iter.remove();
            }
        }
    }

    private void buildPopupMenu(){
        Texture highlight = Drawing.getHollowRectangle(50, 50, 5, new Color(0.2f, 0.2f, 1, 0.6f));

        menu = new PopupMenu(Assets.itemMenuBackground, 10, -(GameScreen.FRUSTUM_HEIGHT - 20), GameScreen.FRUSTUM_WIDTH - 20, GameScreen.FRUSTUM_HEIGHT - 20);

        MovementAnimator moveInAnimator = new MovementAnimator(-menu.getHeight(), 10, 1, Interpolation.DECELERATE_INTERPOLATOR);
        moveInAnimator.setControllingY(true);
        menu.addIncomingAnimator(moveInAnimator);

        tilesScroll = new PopupScrollArea(Assets.itemMenuScrollArea, 20, menu.getHeight() - 20, menu.getWidth() - 40, 15, menu.getWidth() - 40, 15, PopupScrollArea.HORIZONTAL, 1, 5, 15);

        PopupButton tileLeftButton = new PopupButton(Assets.leftButtonUp, Assets.leftButtonDown, 5, tilesScroll.getY() + 2.5f, 10, 10);
        tileLeftButton.setButtonListener(new PopupButtonListener() {
            @Override
            public void onClick() {
                tileMoveLeft();
            }
        });

        PopupButton tileRightButton = new PopupButton(Assets.rightButtonUp, Assets.rightButtonDown, menu.getWidth() - 15, tilesScroll.getY() + 2.5f, 10, 10);
        tileRightButton.setButtonListener(new PopupButtonListener() {
            @Override
            public void onClick() {
                tileMoveRight();
            }
        });

        menu.addPopupWidget(tilesScroll);

        menu.addPopupWidget(tileLeftButton);
        menu.addPopupWidget(tileRightButton);

        for (int[] item: p2items) {
            if (item[ITEM_TYPE] == ProcessData.TILE){
                tilesScroll.addWidget(new PopupImage(TileData.getTileTexture(item[ITEM_ITEM]), highlight, 0, 0, 15, 15, false));
            }
        }
    }

    public PopupMenu getPopupMenu(){
        return menu;
    }

    private void tileMoveLeft(){
        selectedTileItem -= 1;
        if (selectedTileItem < 0) selectedTileItem = 0;
        tilesScroll.selectWidget(selectedTileItem, true);
        tilesScroll.slideToPosition(tilesScroll.getPositionToCenter(selectedTileItem));
    }

    private void tileMoveRight(){
        selectedTileItem += 1;
        if (selectedTileItem >= amountOfTilesAvailable) selectedTileItem = amountOfTilesAvailable - 1;
        tilesScroll.selectWidget(selectedTileItem, true);
        tilesScroll.slideToPosition(tilesScroll.getPositionToCenter(selectedTileItem));
    }

    public void appear(){
        menu.moveIn();
    }

    public void dissappear(){
        menu.moveOut();
    }

    public void moveIn(){
        appear();
    }

    public void moveOut(){
        dissappear();
    }
}
