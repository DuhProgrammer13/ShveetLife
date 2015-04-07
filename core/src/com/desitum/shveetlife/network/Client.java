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
    private ProcessData processor;

    public Client(Server myServer, ProcessData processor) {
        this.myServer = myServer;
        this.processor = processor;
    }

    public void startClient() {
        try {
            System.out.println("Connecting...");
            socket = new Socket("localhost", 7777);
            System.out.println("Connection Successful");
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, "Problems with the Client");
        }

    }

    public void readData(){
        try {
            in = new DataInputStream(socket.getInputStream());
            System.out.println("Receiving Information...");
            String data = in.readUTF();
            processData(data);
        } catch(Exception exception){
           JOptionPane.showMessageDialog(null, "Problem with Reading the Data");
        }
    }

    public void processData(String data){
        String request = "";
        String[] requestArray = data.split(" ");
        String command = requestArray[0];
        String object = requestArray[1];
        int commandId = Integer.parseInt(command);
        int objectId = Integer.parseInt(object);
        processor.process(commandId, objectId);
    }
}
