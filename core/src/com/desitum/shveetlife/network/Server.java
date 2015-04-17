package com.desitum.shveetlife.network;

import com.desitum.shveetlife.world.GameWorld;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;


import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/5/2015.
 */
public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static BufferedWriter out;
    private static BufferedReader in;


    public boolean serverStarted;

    public Server() {
        serverStarted = false;

    }

    public void RunServer() {
        try {
            System.out.println("Starting Server....");
            serverSocket = new ServerSocket(9001);
            System.out.println("Server Started.");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Server Start Failed");
        }
    }

    public String readData(){
        String data = "";
        try {
            if(clientSocket != null) {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                data = in.readLine();
                System.out.println(data);
            }
        } catch(Exception exception){
            System.out.println(exception);
        }
        return data;
    }


    public void sendData(String command, GameWorld gameWorld) {
        try {
            if(clientSocket != null) {
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                out.write(command);
                out.flush();
                out.flush();
            } else {
                clientSocket = serverSocket.accept();
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                out.write(command);
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
