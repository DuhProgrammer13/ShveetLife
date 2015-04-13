package com.desitum.shveetlife.network;

/**
 * Created by dvan6234 on 4/13/2015.
 */
public class DataManager {

    public static Server mainServer;
    public static Client myClient;

    public static void startManager(String host, String ipAddress){
        if (host.equals("localhost")){
            mainServer = new Server();
        } else {
            myClient = new Client(new ProcessData());
            myClient.startClient(ipAddress);
        }
    }

    public static void sendData(String data){
        if (mainServer != null){
            mainServer.sendData(data);
        } else if (myClient != null){
            //myClient.sendData(data);
        }
    }

    public static void receiveData(){
        if (mainServer != null){
            //mainServer.readData();
        } else if (myClient != null){
            myClient.readData();
        }
    }
}
