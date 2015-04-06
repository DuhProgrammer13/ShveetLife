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
    private Server myServer;

    public Client(Server myServer) {
        this.myServer = myServer;
    }

    public void startClient() {
        try {
            System.out.println("Connecting...");
            socket = new Socket("localhost", 7777);
            System.out.println("Connection Successful");
            in = new DataInputStream(socket.getInputStream());
            System.out.println("Receiving Information...");
            String test = in.readUTF();
            myServer.sendData();
            System.out.println("Message From Server: " + test);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problems with the Client");
        }
    }
}
