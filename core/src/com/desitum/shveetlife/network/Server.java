package com.desitum.shveetlife.network;

import com.desitum.shveetlife.world.GameWorld;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/5/2015.
 */
public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static DataOutputStream out;
    private static DataInputStream in;


    public boolean serverStarted;

    public Server() {
        serverStarted = false;

    }

    public void RunServer() {
        try {
            serverSocket = new ServerSocket(9001);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Server Start Failed");
        }
    }

    public String readData(){
        String data = "";
        try {
            if(clientSocket != null && in == null) {
                in = new DataInputStream(clientSocket.getInputStream());
            }
            if(clientSocket != null && in.available() > 0){
                data = in.readUTF();
            }
        } catch(Exception exception){
        }
        return data;
    }


    public void sendData(String command, GameWorld gameWorld) {
        try {
            if(clientSocket != null) {
                out = new DataOutputStream(clientSocket.getOutputStream());
                out.writeUTF(command);
                out.flush();
            } else if (clientSocket == null){
                clientSocket = serverSocket.accept();
                out = new DataOutputStream(clientSocket.getOutputStream());
                out.writeUTF(gameWorld.getGameLoad());
                out.flush();
                readData();
            }
        } catch (Exception exception) {

        }
    }

    public void disconnect(){
        try {
            serverSocket.close();
        } catch(Exception exception){
            JOptionPane.showMessageDialog(null, "It had troubles closing the server?");
        }
    }

}
