package com.desitum.shveetlife.network;

import com.desitum.shveetlife.world.MenuInterface;

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
    private Client myClient;

    private boolean clientConnected;
    //private String ipAddress;

    public boolean serverStarted;

    public Server() {
        serverStarted = false;
        clientConnected = false;
        myClient = new Client(null);
    }

    public void RunServer() {
        try {
            System.out.println("Starting Server....");
            serverSocket = new ServerSocket(7777);
            System.out.println("Server Started.");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Server Start Failed");
        }
    }


    public void sendData(String command) {
        try {
            if(clientSocket != null) {
                System.out.println("Connection From: " + clientSocket.getInetAddress());
                out = new DataOutputStream(clientSocket.getOutputStream());
                out.writeUTF(command);
                System.out.println("Data has been sent");
                //myClient.readData();
            } else {
                clientSocket = serverSocket.accept();
            }
        } catch (Exception exception) {

        }
    }

}
