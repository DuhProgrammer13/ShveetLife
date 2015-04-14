package com.desitum.shveetlife.network;

import com.desitum.shveetlife.world.GameWorld;

import java.util.ArrayList;

/**
 * Created by dvan6234 on 4/13/2015.
 */
public class DataManager {

    public static Server mainServer;
    public static Client myClient;
    public static GameWorld gameWorld;

    public static void startManager(String host, String ipAddress){
        gameWorld = null;
        if (host.equals("localhost")){
            mainServer = new Server();
            mainServer.RunServer();
        } else {
            myClient = new Client(new ProcessData());
            myClient.startClient(ipAddress);
        }
    }

    public static void sendData(){
        if (gameWorld == null) return;
        String data = gameWorld.getDataString();
        if (mainServer != null){
            mainServer.sendData(data);
        } else if (myClient != null){
            //myClient.sendData(data);
        }
    }

    public static void receiveData(){
        if (gameWorld == null) return;
        if (mainServer != null){
            //mainServer.readData();
        } else if (myClient != null){
            myClient.readData();
        }
    }
}
