package com.desitum.shveetlife.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Client {
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private ProcessData processor;


    public Client () {
    }

    public String startClient(String wantedIP) {
        try {
            System.out.println("Connecting...");
            socket = new Socket(wantedIP, 9001);
            System.out.println("Connection Successful");
            in = new DataInputStream(socket.getInputStream());
            String data = in.readUTF();
            System.out.println(data);
            return data;
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problems with the Client");
        }
        return "";
    }

    public String readData(){
        String data = "";
        try {
            in = new DataInputStream(socket.getInputStream());
            data = in.readUTF();
            System.out.println(data);
        } catch(Exception exception){
        }
        return data;
    }

    public void sendData(String command) {
        try {
            if(socket != null) {
                System.out.println("Connection From: " + socket.getInetAddress());
                out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(command);
                System.out.println("Data has been sent");
            } else {
                //If the server is null you have bigger problems than an empty else section... :P
                //If the server is null you have bigger problems than an empty else section... :P
            }
        } catch (Exception exception) {

        }
    }
}
