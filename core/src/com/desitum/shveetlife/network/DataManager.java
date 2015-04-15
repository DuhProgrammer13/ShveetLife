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

    public static String startManager(String host, String ipAddress){
        gameWorld = null;
        if (host.equals("localhost")){
            mainServer = new Server();
            mainServer.RunServer();
        } else {
            myClient = new Client(new ProcessData());
            return myClient.startClient(ipAddress);
        }
        return "";
    }

    public static void sendData(){
        if (gameWorld == null) return;
        String data = gameWorld.getDataString();
        if (mainServer != null){
            mainServer.sendData(data, gameWorld);
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

    public static void setGameWorld(GameWorld gw){
        gameWorld = gw;
    }

    private static void setUpWorld(String data){

    }

    String setup = "CHUNK:PLAYER:YOURSELF:GAMEOBJECTS";
    String setup2 = "x y|0 0 0; 0 0 1; 0 0 2/0 0 0;0 0 1;0 0 2;0 0 3:NAME x y texture/inventory:NAME x y texture/inventory:GAMEOBJECT# x y/inventory";
}
