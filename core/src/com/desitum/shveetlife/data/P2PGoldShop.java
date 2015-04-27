package com.desitum.shveetlife.data;

import java.util.ArrayList;

/**
 * Created by kody on 4/26/15.
 * can be used by kody and people in []
 */
public class P2PGoldShop {
    private ArrayList<int[]> p1items;
    private ArrayList<int[]> p2items;

    public static final int ITEM_TYPE = 0;
    public static final int ITEM_ITEM = 1;
    public static final int ITEM_COUNT = 2;

    public static final int ITEM_TYPE_DATA = 2;
    public static final int ITEM_ITEM_DATA = 3;
    public static final int ITEM_COUNT_DATA = 4;

    public P2PGoldShop(){
        p1items = new ArrayList<int[]>();
        p2items = new ArrayList<int[]>();
    }

    public void processData(String[] data){
        int[] toAdd = new int[]{Integer.parseInt(data[ITEM_TYPE_DATA]), Integer.parseInt(data[ITEM_ITEM_DATA]), Integer.parseInt(data[ITEM_COUNT_DATA])};
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
}
