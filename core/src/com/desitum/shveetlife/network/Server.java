package com.desitum.shveetlife.network;

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
    private Client myClient;

    public boolean serverStarted;

    public Server() {
        serverStarted = false;
        myClient = new Client(this, null);
    }

    public void RunServer() {
        try {
            System.out.println("Starting Server....");
            serverSocket = new ServerSocket(7777);
            System.out.println("Server Started.");
            myClient.startClient();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Server Start Failed");
        }
    }

    public void sendData(String command) {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Connection From: " + clientSocket.getInetAddress());
            out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF(command);
            System.out.println("Data has been sent");
            myClient.readData();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problems with Send Data Method");
        }
    }

}
