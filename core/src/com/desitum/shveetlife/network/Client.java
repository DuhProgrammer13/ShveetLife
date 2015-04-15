package com.desitum.shveetlife.network;

import java.io.DataInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Created by Zmyth97 on 4/3/2015.
 */
public class Client {
    private static Socket socket;
    private static DataInputStream in;
    private ProcessData processor;


    public Client (ProcessData processor) {
        this.processor = processor;
    }

    public void startClient(String wantedIP) {
        try {
            System.out.println("Connecting...");
            socket = new Socket(wantedIP, 7777);
            System.out.println("Connection Successful");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problems with the Client");
        }

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
}
