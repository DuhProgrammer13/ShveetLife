package com.desitum.shveetlife.network;

import com.desitum.shveetlife.world.GameWorld;

/**
 * Created by dvan6234 on 4/13/2015.
 */
public class DataManager {

    public static Server mainServer;
    public static Client myClient;
    public static GameWorld gameWorld;

    private static String connectionType;

    public static String startManager(String host, String ipAddress){
        gameWorld = null;
        connectionType = host;
        if (host.equals("localhost")){
            mainServer = new Server();
            mainServer.RunServer();
        } else {
            myClient = new Client();
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
            myClient.sendData(data);
            System.out.println("Made out of send");
        }
    }

    public static void receiveData(){
        if (gameWorld == null) return;
        if (mainServer != null){
            String dataRead = mainServer.readData();
            if (!mainServer.readData().equals("")){
                gameWorld.updateData(dataRead);
            }
        } else if (myClient != null){
            String dataRead = myClient.readData();
            if (!dataRead.equals("")){
                gameWorld.updateData(dataRead);
                System.out.println("Made out of recieve");
            }
        }
    }

    public static void setGameWorld(GameWorld gw){
        gameWorld = gw;
    }

    public static void exitGame(){
       if(connectionType == "localhost"){
            mainServer.disconnect();
            myClient = null;
            gameWorld.exitScreen();
       } else {
            myClient.disconnect();
            gameWorld.exitScreen();
       }
    }

    private static void setUpWorld(String data){

    }

    String setup = "CHUNK:PLAYER:YOURSELF:GAMEOBJECTS";
    String setup2 = "x y|0 0 0; 0 0 1; 0 0 2/0 0 0;0 0 1;0 0 2;0 0 3:NAME x y texture/inventory:NAME x y texture/inventory:GAMEOBJECT# x y/inventory";
}
